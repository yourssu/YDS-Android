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
import android.widget.TextView
import com.yourssu.design.R
import com.yourssu.design.databinding.LayoutSimpleTextFieldBinding
import com.yourssu.design.system.rule.Vibration
import com.yourssu.design.system.rule.vibe
import com.yourssu.design.undercarriage.animation.startAnim
import com.yourssu.design.undercarriage.base.TextField

class SimpleTextField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextField(context, attrs, defStyleAttr) {

    private lateinit var binding: LayoutSimpleTextFieldBinding

    override var text: Editable
        get() {
            return binding.edittext.text
        }
        set(value) {
            binding.edittext.text = value
        }

    override var placeholderText: CharSequence
        get() {
            return binding.edittext.hint
        }
        set(value) {
            binding.edittext.hint = value
        }

    override var fieldLabelText: CharSequence
        get() {
            return binding.fieldLabel.text
        }
        set(value) {
            binding.fieldLabel.text = value
        }

    override var helperLabelText: CharSequence
        get() {
            return binding.helperLabel.text
        }
        set(value) {
            binding.helperLabel.text = value
        }

    override var inputType: Int
        get() {
            return binding.edittext.inputType
        }
        set(value) {
            binding.edittext.inputType = value
        }

    override var imeOptions: Int
        set(value) {
            binding.edittext.imeOptions = value
        }
        get() {
            return binding.edittext.imeOptions
        }

    override fun inflateLayout(context: Context) {
        binding = LayoutSimpleTextFieldBinding.inflate(
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
    override fun initAttributes(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray: TypedArray =
                context.obtainStyledAttributes(attrs, R.styleable.TextField)

            binding.edittext.setText(
                (typedArray.getString(R.styleable.TextField_android_text) ?: "")
            )
            hint = typedArray.getString(R.styleable.TextField_android_hint) ?: ""
            inputType = typedArray.getInt(
                R.styleable.TextField_android_inputType,
                InputType.TYPE_CLASS_TEXT
            )
            imeOptions = typedArray.getInt(R.styleable.TextField_android_imeOptions, 0)

            typedArray.recycle()
        }
    }

    override fun setOnEditorActionListener(onEditorActionListener: TextView.OnEditorActionListener) {
        binding.edittext.setOnEditorActionListener(onEditorActionListener)
    }

    override fun extendSelection(index: Int) {
        binding.edittext.extendSelection(index)
    }

    override fun getFreezesText(): Boolean {
        return binding.edittext.freezesText
    }

    override fun selectAll() {
        binding.edittext.selectAll()
    }

    override fun setEllipsize(ellipsis: TextUtils.TruncateAt) {
        binding.edittext.ellipsize = ellipsis
    }

    override fun setSelection(start: Int, stop: Int) {
        binding.edittext.setSelection(start, stop)
    }

    override fun setSelection(index: Int) {
        binding.edittext.setSelection(index)
    }

    override fun setText(text: CharSequence, type: TextView.BufferType) {
        binding.edittext.setText(text, type)
    }

    override fun setText(resid: Int) {
        binding.edittext.setText(resid)
    }

    override fun setText(resid: Int, type: TextView.BufferType) {
        binding.edittext.setText(resid, type)
    }

    override fun setText(text: CharArray, start: Int, len: Int) {
        binding.edittext.setText(text, start, len)
    }

    override fun addTextChangedListener(watcher: TextWatcher) {
        binding.edittext.addTextChangedListener(watcher)
    }

    override fun removeTextChangedListener(watcher: TextWatcher) {
        binding.edittext.removeTextChangedListener(watcher)
    }

    override fun shake() {
        binding.root.startAnim(R.anim.textfield_error_motion)
        context.vibe(Vibration.FAILURE)
    }

    override fun changeEditTextEnabled() {
        binding.edittext.isEnabled = !isDisabled
    }

    override fun setDisabledTextColor() {
        setEditTextTextColor(R.color.textDisabled)
        setPlaceholderTextColor(R.color.textDisabled)
        setFieldLabelTextColor(R.color.textDisabled)
        setHelperLabelTextColor(R.color.textDisabled)
    }

    override fun setPositiveTextColor() {
        setEditTextTextColor(R.color.textSecondary)
        setPlaceholderTextColor(R.color.textTertiary)
        setFieldLabelTextColor(R.color.textSecondary)
        setHelperLabelTextColor(R.color.textTertiary)
    }

    override fun setNegativeTextColor() {
        setEditTextTextColor(R.color.textSecondary)
        setPlaceholderTextColor(R.color.textTertiary)
        setFieldLabelTextColor(R.color.textSecondary)
        setHelperLabelTextColor(R.color.textWarned)
    }

    override fun setDefaultTextColor() {
        setEditTextTextColor(R.color.textSecondary)
        setPlaceholderTextColor(R.color.textTertiary)
        setFieldLabelTextColor(R.color.textSecondary)
        setHelperLabelTextColor(R.color.textTertiary)
    }

    override fun setEditTextTextColor(color: Int) {
        binding.edittext.setTextColor(resources.getColor(color, null))
    }

    override fun setPlaceholderTextColor(color: Int) {
        binding.edittext.setHintTextColor(resources.getColor(color, null))
    }

    override fun setFieldLabelTextColor(color: Int) {
        binding.fieldLabel.setTextColor(resources.getColor(color, null))
    }

    override fun setHelperLabelTextColor(color: Int) {
        binding.helperLabel.setTextColor(resources.getColor(color, null))
    }

    override fun setBackground() {
        when {
            isDisabled -> binding.inputField.setBackgroundResource(R.drawable.text_field_background)
            isNegative -> binding.inputField.setBackgroundResource(R.drawable.text_field_negative_background)
            isPositive -> binding.inputField.setBackgroundResource(R.drawable.text_field_positive_background)
            else -> binding.inputField.setBackgroundResource(R.drawable.text_field_background)
        }
    }
}