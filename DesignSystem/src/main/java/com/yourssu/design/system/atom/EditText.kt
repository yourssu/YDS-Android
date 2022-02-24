package com.yourssu.design.system.atom

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.yourssu.design.R
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.system.foundation.Typography
import com.yourssu.design.undercarriage.size.getDimenFloat

class EditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {

    init {
        setTextColor(ContextCompat.getColor(context, R.color.textPrimary))
        setBackgroundColor(Color.TRANSPARENT)
        setHintTextColor(ContextCompat.getColor(context, R.color.textTertiary))
        isFocusableInTouchMode = true
    }

    private var text: String = ""
        set(text) {
            field = text
            changeText()
        }

    private var hint: String = ""
        set(text) {
            field = text
            changeHint()
        }

    @Typography
    private var typo: Int = Typo.SubTitle1
        set(@Typography typo) {
            field = typo
            setTextInfo()
        }

    private var editTextType: Int = TITLE
        set(value) {
            field = value
            changeEditTextType()
        }

    private fun changeHint() {
        setHint(hint)
    }

    private fun setTextInfo() {
        setTextAppearance(Typo.getStyle(typo))

        // 폰트의 커스텀 padding 제거
        includeFontPadding = false

        // 폰트가 가지고 있는 lineHeight 값 추출
        val fontLineHeight = paint.getFontMetrics(paint.fontMetrics)

        // 디자이너가 요청한 lineHeight 값
        val figmaLineHeight = context.getDimenFloat(Typo.getLineHeight(typo))

        // 줄 간 간격인 lineSpacing 이자 (topPadding, bottomPadding) 의 합
        val lineSpacing = figmaLineHeight - fontLineHeight

        setPadding(0, lineSpacing.toInt() / 2, 0, lineSpacing.toInt() / 2)
        setLineSpacing(lineSpacing, 1f)
    }

    private fun changeEditTextType() {
        typo = when (editTextType) {
            TITLE -> {
                Typo.SubTitle1
            }
            CONTENT -> {
                Typo.Body1
            }
            else -> {
                Typo.SubTitle1
            }
        }
        Log.d("KWK", Typo.getName(typo))
    }

    private fun changeText() {
        setText(text)
        requestLayout()
        invalidate()
    }

    companion object {
        const val TITLE = 0
        const val CONTENT = 1

        @JvmStatic
        @BindingAdapter("android:hint")
        fun setHint(editText: EditText, hint: String) {
            editText.hint = hint
        }

        @JvmStatic
        @BindingAdapter("android:text")
        fun setText(editText: EditText, text: String) {
            editText.text = text
        }

        @JvmStatic
        @BindingAdapter("editTextType")
        fun setEditTextType(editText: EditText, editTextType: Int) {
            editText.editTextType = editTextType
        }
    }

}