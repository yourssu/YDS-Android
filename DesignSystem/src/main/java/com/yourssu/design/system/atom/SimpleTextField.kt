package com.yourssu.design.system.atom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.yourssu.design.R
import com.yourssu.design.databinding.LayoutSimpleTextFieldBinding
import android.text.*
import android.widget.TextView
import androidx.databinding.InverseBindingListener
import androidx.databinding.adapters.ListenerUtil


class SimpleTextField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    companion object {
        @JvmStatic
        @BindingAdapter("android:text")
        fun setText(simpleTextField: SimpleTextField, text: String) {
            simpleTextField.setText(text, TextView.BufferType.EDITABLE)
        }

        @JvmStatic
        @BindingAdapter("isDisabled")
        fun setIsDisabled(simpleTextField: SimpleTextField, isDisabled: Boolean) {
            simpleTextField.isDisabled = isDisabled
        }

        @JvmStatic
        @BindingAdapter("isNegative")
        fun setIsNegative(simpleTextField: SimpleTextField, isNegative: Boolean) {
            simpleTextField.isNegative = isNegative
        }

        @JvmStatic
        @BindingAdapter("isPositive")
        fun setIsPositive(simpleTextField: SimpleTextField, isPositive: Boolean) {
            simpleTextField.isPositive = isPositive
        }

        @JvmStatic
        @BindingAdapter("placeholderText")
        fun setPlaceholderText(simpleTextField: SimpleTextField, placeholderText: String) {
            simpleTextField.placeholderText = placeholderText
        }

        @JvmStatic
        @BindingAdapter("fieldLabelText")
        fun setFieldLabelText(simpleTextField: SimpleTextField, fieldLabelText: String) {
            simpleTextField.fieldLabelText = fieldLabelText
        }

        @JvmStatic
        @BindingAdapter("helperLabelText")
        fun setHelperLabelText(simpleTextField: SimpleTextField, helperLabelText: String) {
            simpleTextField.helperLabelText = helperLabelText
        }

        @JvmStatic
        @BindingAdapter(
            value = ["android:beforeTextChanged", "android:onTextChanged", "android:afterTextChanged", "android:textAttrChanged"],
            requireAll = false
        )
        fun setTextWatcher(
            simpleTextField: SimpleTextField,
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
                simpleTextField,
                newValue,
                R.id.textWatcher
            )
            oldValue?.let {
                simpleTextField.removeTextChangedListener(it)
            }
            newValue?.let {
                simpleTextField.addTextChangedListener(it)
            }
        }
    }

    private lateinit var binding: LayoutSimpleTextFieldBinding

    init {
        initView(context, attrs)
    }

    var isDisabled: Boolean = false
        set(value) {
            field = value
            setCurrentState()
        }

    var isNegative: Boolean = false
        set(value) {
            field = value
            setCurrentState()
        }

    var isPositive: Boolean = false
        set(value) {
            field = value
            setCurrentState()
        }

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

    var hint: CharSequence
        get() {
            return placeholderText
        }
        set(value) {
            placeholderText = value
        }

    var fieldLabelText: CharSequence
        get() {
            return binding.fieldLabel.text
        }
        set(value) {
            binding.fieldLabel.text = value
        }

    var helperLabelText: CharSequence
        get() {
            return binding.helperLabel.text
        }
        set(value) {
            binding.helperLabel.text = value
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

    private fun initView(context: Context, attrs: AttributeSet?) {
        binding = LayoutSimpleTextFieldBinding.inflate(
            LayoutInflater.from(context), this, true
        )

        if (attrs != null) {
            val typedArray: TypedArray =
                context.obtainStyledAttributes(attrs, R.styleable.SimpleTextField)

            binding.edittext.setText(
                (typedArray.getString(R.styleable.SimpleTextField_android_text) ?: "")
            )
            hint = typedArray.getString(R.styleable.SimpleTextField_android_hint) ?: ""
            inputType = typedArray.getInt(
                R.styleable.SimpleTextField_android_inputType,
                InputType.TYPE_CLASS_TEXT
            )
            imeOptions = typedArray.getInt(R.styleable.SimpleTextField_android_imeOptions, 0)

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

    private fun setCurrentState() {
        setTextColor()
        setBackground()
    }

    private fun setTextColor() {
        when {
            isDisabled -> {
                setPlaceholderTextColor(R.color.textDisabled)
                setFieldLabelTextColor(R.color.textDisabled)
                setHelperLabelColor(R.color.textDisabled)
            }
            isNegative -> {
                setPlaceholderTextColor(R.color.textTertiary)
                setFieldLabelTextColor(R.color.textSecondary)
                setHelperLabelColor(R.color.textWarned)
            }
            isPositive -> {
                setPlaceholderTextColor(R.color.textTertiary)
                setFieldLabelTextColor(R.color.textSecondary)
                setHelperLabelColor(R.color.textTertiary)
            }
            else -> {
                setPlaceholderTextColor(R.color.textTertiary)
                setFieldLabelTextColor(R.color.textSecondary)
                setHelperLabelColor(R.color.textTertiary)
            }
        }
    }

    private fun setPlaceholderTextColor(color: Int) {
        binding.edittext.setHintTextColor(resources.getColor(color, null))
    }

    private fun setFieldLabelTextColor(color: Int) {
        binding.fieldLabel.setTextColor(resources.getColor(color, null))
    }

    private fun setHelperLabelColor(color: Int) {
        binding.helperLabel.setTextColor(resources.getColor(color, null))
    }

    private fun setBackground() {
        when {
            isDisabled -> binding.inputField.setBackgroundResource(R.drawable.text_field_background)
            isNegative -> binding.inputField.setBackgroundResource(R.drawable.text_field_negative_background)
            isPositive -> binding.inputField.setBackgroundResource(R.drawable.text_field_positive_background)
            else -> binding.inputField.setBackgroundResource(R.drawable.text_field_background)
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