package com.yourssu.design.undercarriage.base

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.adapters.ListenerUtil
import com.yourssu.design.R

abstract class TextField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    companion object {
        @JvmStatic
        @BindingAdapter("android:text")
        fun setText(textField: TextField, text: String) {
            textField.setText(text, TextView.BufferType.EDITABLE)
        }

        @JvmStatic
        @BindingAdapter("android:onEditorAction")
        fun setOnEditorActionListener(
            textField: TextField,
            onEditorActionListener: TextView.OnEditorActionListener
        ) {
            textField.setOnEditorActionListener(onEditorActionListener)
        }

        @JvmStatic
        @BindingAdapter("isDisabled")
        fun setIsDisabled(textField: TextField, isDisabled: Boolean) {
            textField.isDisabled = isDisabled
        }

        @JvmStatic
        @BindingAdapter("isNegative")
        fun setIsNegative(textField: TextField, isNegative: Boolean) {
            textField.isNegative = isNegative
        }

        @JvmStatic
        @BindingAdapter("isPositive")
        fun setIsPositive(textField: TextField, isPositive: Boolean) {
            textField.isPositive = isPositive
        }

        @JvmStatic
        @BindingAdapter("placeholderText")
        fun setPlaceholderText(textField: TextField, placeholderText: String) {
            textField.placeholderText = placeholderText
        }

        @JvmStatic
        @BindingAdapter("fieldLabelText")
        fun setFieldLabelText(textField: TextField, fieldLabelText: String) {
            textField.fieldLabelText = fieldLabelText
        }

        @JvmStatic
        @BindingAdapter("helperLabelText")
        fun setHelperLabelText(textField: TextField, helperLabelText: String) {
            textField.helperLabelText = helperLabelText
        }

        @JvmStatic
        @BindingAdapter(
            value = ["android:beforeTextChanged", "android:onTextChanged", "android:afterTextChanged", "android:textAttrChanged"],
            requireAll = false
        )
        fun setTextWatcher(
            textField: TextField,
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
                textField,
                newValue,
                R.id.textWatcher
            )
            oldValue?.let {
                textField.removeTextChangedListener(it)
            }
            newValue?.let {
                textField.addTextChangedListener(it)
            }
        }
    }

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

    abstract var text: Editable

    abstract var placeholderText: CharSequence

    var hint: CharSequence
        get() {
            return placeholderText
        }
        set(value) {
            placeholderText = value
        }

    abstract var fieldLabelText: CharSequence

    abstract var helperLabelText: CharSequence

    abstract var inputType: Int

    abstract var imeOptions: Int

    private fun initView(context: Context, attrs: AttributeSet?) {
        inflateLayout(context)
        initAttributes(context, attrs)
        setCurrentState()
    }

    internal abstract fun inflateLayout(context: Context)

    internal abstract fun initAttributes(context: Context, attrs: AttributeSet?)

    abstract fun setOnEditorActionListener(onEditorActionListener: TextView.OnEditorActionListener)

    abstract fun extendSelection(index: Int)

    abstract fun getFreezesText(): Boolean

    abstract fun selectAll()

    abstract fun setEllipsize(ellipsis: TextUtils.TruncateAt)

    abstract fun setSelection(start: Int, stop: Int)

    abstract fun setSelection(index: Int)

    abstract fun setText(text: CharSequence, type: TextView.BufferType)

    abstract fun setText(resid: Int)

    abstract fun setText(resid: Int, type: TextView.BufferType)

    abstract fun setText(text: CharArray, start: Int, len: Int)

    abstract fun addTextChangedListener(watcher: TextWatcher)

    abstract fun removeTextChangedListener(watcher: TextWatcher)

    private fun setCurrentState() {
        setTextColor()
        setBackground()
        changeEditTextEnabled()
        if (isDisabled) {
            setText("", TextView.BufferType.EDITABLE)
        }
    }

    private fun setTextColor() {
        when {
            isDisabled -> {
                setDisabledTextColor()
            }
            isNegative -> {
                setNegativeTextColor()
            }
            isPositive -> {
                setPositiveTextColor()
            }
            else -> {
                setDefaultTextColor()
            }
        }
    }

    abstract fun shake()

    abstract fun changeEditTextEnabled()

    abstract fun setDisabledTextColor()

    abstract fun setPositiveTextColor()

    abstract fun setNegativeTextColor()

    abstract fun setDefaultTextColor()

    abstract fun setPlaceholderTextColor(color: Int)

    abstract fun setFieldLabelTextColor(color: Int)

    abstract fun setHelperLabelTextColor(color: Int)

    abstract fun setBackground()

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