package com.yourssu.design.system.atom

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.adapters.ListenerUtil
import com.yourssu.design.R
import com.yourssu.design.databinding.LayoutSearchTextFieldBinding

class SearchTextField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    companion object {
        @JvmStatic
        @BindingAdapter("android:text")
        fun setText(searchTextField: SearchTextField, text: String) {
            searchTextField.setText(text, TextView.BufferType.EDITABLE)
        }

        @JvmStatic
        @BindingAdapter("android:onEditorAction")
        fun setOnEditorActionListener(
            searchTextField: SearchTextField,
            onEditorActionListener: TextView.OnEditorActionListener
        ) {
            searchTextField.binding.edittext.setOnEditorActionListener(onEditorActionListener)
        }

        @JvmStatic
        @BindingAdapter("isDisabled")
        fun setIsDisabled(searchTextField: SearchTextField, isDisabled: Boolean) {
            searchTextField.isDisabled = isDisabled
        }

        @JvmStatic
        @BindingAdapter("placeholderText")
        fun setPlaceholderText(searchTextField: SearchTextField, placeholderText: String) {
            searchTextField.placeholderText = placeholderText
        }

        @JvmStatic
        @BindingAdapter(
            value = ["android:beforeTextChanged", "android:onTextChanged", "android:afterTextChanged", "android:textAttrChanged"],
            requireAll = false
        )
        fun setTextWatcher(
            searchTextField: SearchTextField,
            before: BeforeTextChanged?,
            on: OnTextChanged?,
            after: AfterTextChanged?,
            textAttrChanged: InverseBindingListener?
        ) {
            val newValue: TextWatcher? =
                if (before == null && after == null && on == null && textAttrChanged == null) {
                    null
                } else {
                    object : TextWatcher {
                        override fun beforeTextChanged(
                            s: CharSequence,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {
                            before?.beforeTextChanged(s, start, count, after)
                        }

                        override fun onTextChanged(
                            s: CharSequence,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                            on?.onTextChanged(s, start, before, count)
                            textAttrChanged?.onChange()
                        }

                        override fun afterTextChanged(s: Editable) {
                            after?.afterTextChanged(s)
                        }
                    }
                }
            val oldValue = ListenerUtil.trackListener(
                searchTextField,
                newValue,
                R.id.textWatcher
            )
            oldValue?.let {
                searchTextField.removeTextChangedListener(it)
            }
            newValue?.let {
                searchTextField.addTextChangedListener(it)
            }
        }
    }

    init {
        initView(context, attrs)
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setIconColor()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    var isDisabled: Boolean = false
        set(value) {
            field = value
            setCurrentState()
        }

    private fun initView(context: Context, attrs: AttributeSet?) {
        inflateLayout(context)
        initAttributes(context, attrs)
        setCurrentState()
    }

    private lateinit var binding: LayoutSearchTextFieldBinding

    var text: Editable
        get() {
            return binding.edittext.text
        }
        set(value) {
            binding.edittext.text = value
        }

    var placeholderText: CharSequence
        get() {
            return binding.edittext.hint
        }
        set(value) {
            binding.edittext.hint = value
        }

    var inputType: Int
        get() {
            return binding.edittext.inputType
        }
        set(value) {
            binding.edittext.inputType = value
        }

    var imeOptions: Int
        set(value) {
            binding.edittext.imeOptions = value
        }
        get() {
            return binding.edittext.imeOptions
        }

    private fun inflateLayout(context: Context) {
        binding = LayoutSearchTextFieldBinding.inflate(
            LayoutInflater.from(context), this, true
        )
        binding.edittext.setOnFocusChangeListener { v, hasFocus ->
            binding.btn.visibility = if (hasFocus) {
                if (binding.edittext.text.isNotEmpty()) {
                    VISIBLE
                } else {
                    GONE
                }
            } else {
                GONE
            }
        }
    }

    @SuppressLint("CustomViewStyleable")
    private fun initAttributes(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray: TypedArray =
                context.obtainStyledAttributes(attrs, R.styleable.TextField)

            binding.edittext.setText(
                (typedArray.getString(R.styleable.TextField_android_text) ?: "")
            )
            inputType = typedArray.getInt(
                R.styleable.TextField_android_inputType,
                InputType.TYPE_CLASS_TEXT
            )
            imeOptions = typedArray.getInt(R.styleable.TextField_android_imeOptions, EditorInfo.IME_ACTION_SEARCH)

            typedArray.recycle()
        }
    }

    fun extendSelection(index: Int) {
        binding.edittext.extendSelection(index)
    }

    fun getFreezesText(): Boolean {
        return binding.edittext.freezesText
    }

    fun selectAll() {
        binding.edittext.selectAll()
    }

    fun setEllipsize(ellipsis: TextUtils.TruncateAt) {
        binding.edittext.ellipsize = ellipsis
    }

    fun setSelection(start: Int, stop: Int) {
        binding.edittext.setSelection(start, stop)
    }

    fun setSelection(index: Int) {
        binding.edittext.setSelection(index)
    }

    fun setText(text: CharSequence, type: TextView.BufferType) {
        binding.edittext.setText(text, type)
    }

    fun setText(resid: Int) {
        binding.edittext.setText(resid)
    }

    fun setText(resid: Int, type: TextView.BufferType) {
        binding.edittext.setText(resid, type)
    }

    fun setText(text: CharArray, start: Int, len: Int) {
        binding.edittext.setText(text, start, len)
    }

    fun addTextChangedListener(watcher: TextWatcher) {
        binding.edittext.addTextChangedListener(watcher)
    }

    fun removeTextChangedListener(watcher: TextWatcher) {
        binding.edittext.removeTextChangedListener(watcher)
    }

    private fun setEditTextTextColor(color: Int) {
        binding.edittext.setTextColor(resources.getColor(color, null))
    }

    private fun setPlaceholderTextColor(color: Int) {
        binding.edittext.setHintTextColor(resources.getColor(color, null))
    }

    private fun setSearchIconColor(color: Int) {
        binding.searchIcon.setColorFilter(resources.getColor(color, null))
    }

    private fun setCurrentState() {
        setIconColor()
        setTextColor()
        binding.edittext.isEnabled = !isDisabled
    }

    private fun setIconColor() {
        when {
            isDisabled -> {
                setSearchIconColor(R.color.textDisabled)
            }
            binding.edittext.text.isEmpty() -> {
                setSearchIconColor(R.color.textTertiary)
            }
            else -> {
                setSearchIconColor(R.color.textSecondary)
            }
        }
    }

    private fun setTextColor() {
        when {
            isDisabled -> {
                setEditTextTextColor(R.color.textDisabled)
                setPlaceholderTextColor(R.color.textDisabled)
            }
            else -> {
                setEditTextTextColor(R.color.textSecondary)
                setPlaceholderTextColor(R.color.textTertiary)
            }
        }
    }

    interface AfterTextChanged {
        fun afterTextChanged(s: Editable?)
    }

    interface BeforeTextChanged {
        fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
    }

    interface OnTextChanged {
        fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
    }
}