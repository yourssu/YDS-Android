package com.yourssu.design.system.component

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.adapters.ListenerUtil
import com.yourssu.design.R
import com.yourssu.design.databinding.LayoutSearchTopBarBinding
import com.yourssu.design.system.atom.SearchTextField

class SearchTopBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding =
        LayoutSearchTopBarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        initAttributes(context, attrs)
    }

    var leftArrowButtonClickListener: OnClickListener? = null
        set(value) {
            binding.leftArrowButton.setOnClickListener(value)
        }

    var text: Editable
        get() {
            return binding.searchTextField.text
        }
        set(value) {
            binding.searchTextField.text = value
        }

    var isDisabled: Boolean
        set(value) {
            binding.searchTextField.isDisabled = value
        }
        get() {
            return binding.searchTextField.isDisabled
        }

    var placeholderText: CharSequence
        get() {
            return binding.searchTextField.placeholderText
        }
        set(value) {
            binding.searchTextField.placeholderText = value
        }

    var inputType: Int
        get() {
            return binding.searchTextField.inputType
        }
        set(value) {
            binding.searchTextField.inputType = value
        }

    var imeOptions: Int
        set(value) {
            binding.searchTextField.imeOptions = value
        }
        get() {
            return binding.searchTextField.imeOptions
        }

    var leftArrowDisabled: Boolean = false
        set(value) {
            binding.leftArrowButton.isDisabled = value
        }

    @SuppressLint("CustomViewStyleable")
    private fun initAttributes(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray: TypedArray =
                context.obtainStyledAttributes(attrs, R.styleable.TextField)

            setText(
                typedArray.getString(R.styleable.TextField_android_text) ?: "",
                TextView.BufferType.EDITABLE
            )

            typedArray.recycle()
        }
    }

    fun extendSelection(index: Int) {
        binding.searchTextField.extendSelection(index)
    }

    fun getFreezesText(): Boolean {
        return binding.searchTextField.getFreezesText()
    }

    fun selectAll() {
        binding.searchTextField.selectAll()
    }

    fun setEllipsize(ellipsis: TextUtils.TruncateAt) {
        binding.searchTextField.setEllipsize(ellipsis)
    }

    fun setSelection(start: Int, stop: Int) {
        binding.searchTextField.setSelection(start, stop)
    }

    fun setSelection(index: Int) {
        binding.searchTextField.setSelection(index)
    }

    fun setText(text: CharSequence, type: TextView.BufferType) {
        binding.searchTextField.setText(text, type)
    }

    fun setText(resid: Int) {
        binding.searchTextField.setText(resid)
    }

    fun setText(resid: Int, type: TextView.BufferType) {
        binding.searchTextField.setText(resid, type)
    }

    fun setText(text: CharArray, start: Int, len: Int) {
        binding.searchTextField.setText(text, start, len)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("onLeftArrowButtonClick")
        fun setOnLeftArrowButtonClickListener(
            searchTopBar: SearchTopBar,
            leftArrowButtonClickListener: OnClickListener
        ) {
            searchTopBar.leftArrowButtonClickListener = leftArrowButtonClickListener
        }

        @JvmStatic
        @BindingAdapter(
            value = ["android:beforeTextChanged", "android:onTextChanged", "android:afterTextChanged", "android:textAttrChanged"],
            requireAll = false
        )
        fun setTextWatcher(
            searchTopBar: SearchTopBar,
            before: SearchTextField.BeforeTextChanged?,
            on: SearchTextField.OnTextChanged?,
            after: SearchTextField.AfterTextChanged?,
            textAttrChanged: InverseBindingListener?
        ) {
            SearchTextField.setTextWatcher(searchTopBar.binding.searchTextField, before, on, after, textAttrChanged)
        }

        @JvmStatic
        @BindingAdapter("android:text")
        fun setText(searchTopBar: SearchTopBar, text: String) {
            Log.d("hello", text)
            searchTopBar.setText(text, TextView.BufferType.EDITABLE)
        }

        @JvmStatic
        @BindingAdapter("android:onEditorAction")
        fun setOnEditorActionListener(
            searchTopBar: SearchTopBar,
            onEditorActionListener: TextView.OnEditorActionListener
        ) {
            SearchTextField.setOnEditorActionListener(
                searchTopBar.binding.searchTextField,
                onEditorActionListener
            )
        }

        @JvmStatic
        @BindingAdapter("placeholderText")
        fun setPlaceHolderText(searchTopBar: SearchTopBar, placeholderText: String) {
            searchTopBar.placeholderText = placeholderText
        }

        @JvmStatic
        @BindingAdapter("isDisabled")
        fun setIsDisabled(searchTopBar: SearchTopBar, isDisabled: Boolean) {
            searchTopBar.isDisabled = isDisabled
        }

        @JvmStatic
        @BindingAdapter("leftArrowDisabled")
        fun setLeftArrowDisabled(searchTopBar: SearchTopBar, isDisabled: Boolean) {
            searchTopBar.leftArrowDisabled = isDisabled
        }
    }
}