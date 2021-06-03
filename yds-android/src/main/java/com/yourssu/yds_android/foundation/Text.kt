package com.yourssu.yds_android.foundation

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DimenRes
import androidx.annotation.StyleRes
import androidx.appcompat.widget.AppCompatTextView
import com.yourssu.yds_android.R
import com.yourssu.yds_android.exception.InvalidTypeException
import com.yourssu.yds_android.util.getDimenFloat

class Text: AppCompatTextView {
    constructor(context: Context) : super(context) {
        initView(context, null)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Text)
            val ydsTextStyle = styleValueOf(typedArray.getInteger(R.styleable.Text_typo, 0))

            setTextInfo(ydsTextStyle)

            typedArray.recycle()
        }
    }

    private fun setTextInfo(ydsTextStyle: YDSTextStyle) {
        setTextAppearance(ydsTextStyle.style)
        includeFontPadding = false
        val lineHeight = context.getDimenFloat(ydsTextStyle.lineHeightRes)
        val lineSpacing = lineHeight - textSize.toInt()

        setPadding(0, lineSpacing.toInt() / 2, 0, lineSpacing.toInt() / 2)
        setLineSpacing(lineSpacing, 1f)
    }

    companion object {
        // 코드로 만들때 사용
        fun createText(context: Context, ydsTextStyle: YDSTextStyle): Text
                = Text(context).apply { setTextInfo(ydsTextStyle) }

        // data binding const
        const val Title1 = 0
        const val Title2 = 1
        const val SubTitle1 = 2
        const val SubTitle2 = 3
        const val SubTitle3 = 4
        const val Body1 = 5
        const val Body2 = 6
        const val Button0 = 7
        const val Button1 = 8
        const val Button2 = 9
        const val Button3 = 10
        const val Button4 = 11
        const val Caption1 = 12
        const val Caption2 = 13
    }

    enum class YDSTextStyle(val value: Int, @StyleRes val style: Int, @DimenRes val lineHeightRes: Int) {
        TITLE1(Title1, R.style.Text_Title1, R.dimen.title1_line_height),
        TITLE2(Title2, R.style.Text_Title2, R.dimen.title2_line_height),
        SUBTITLE1(SubTitle1, R.style.Text_SubTitle1, R.dimen.subtitle1_line_height),
        SUBTITLE2(SubTitle2, R.style.Text_SubTitle2, R.dimen.subtitle2_line_height),
        SUBTITLE3(SubTitle3, R.style.Text_SubTitle3, R.dimen.subtitle3_line_height),
        BODY1(Body1, R.style.Text_Body1, R.dimen.body1_line_height),
        BODY2(Body2, R.style.Text_Body2, R.dimen.body2_line_height),
        BUTTON0(Button0, R.style.Text_Button0, R.dimen.button0_line_height),
        BUTTON1(Button1, R.style.Text_Button1, R.dimen.button1_line_height),
        BUTTON2(Button2, R.style.Text_Button2, R.dimen.button2_line_height),
        BUTTON3(Button3, R.style.Text_Button3, R.dimen.button3_line_height),
        BUTTON4(Button4, R.style.Text_Button4, R.dimen.button4_line_height),
        CAPTION1(Caption1, R.style.Text_Caption1, R.dimen.caption1_line_height),
        CAPTION2(Caption2, R.style.Text_Caption2, R.dimen.caption2_line_height)
    }

    private fun styleValueOf(typeValue: Int): YDSTextStyle {
        YDSTextStyle.values().forEach {
            if (it.value == typeValue) {
                return it
            }
        }
        throw InvalidTypeException("Text Style")
    }

    // data binding method
    fun setTypo(value: Int) {
        val style = styleValueOf(value)

        setTextAppearance(style.style)
    }
}