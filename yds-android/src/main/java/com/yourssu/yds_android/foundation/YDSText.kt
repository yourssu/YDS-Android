package com.yourssu.yds_android.foundation

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.yourssu.yds_android.R
import com.yourssu.yds_android.exception.InvalidTypeException
import com.yourssu.yds_android.util.dpToIntPx
import com.yourssu.yds_android.util.dpToPx

class YDSText: AppCompatTextView {
    constructor(context: Context) : super(context) {
        initView(context, null)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    // 코드로 만들때 사용
    fun setStyle(ydsTextStyle: YDSTextStyle) {
        settingView(ydsTextStyle)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        var style: YDSTextStyle = YDSTextStyle.TITLE1
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.YDSText)
            style = styleValueOf(typedArray.getInteger(R.styleable.YDSText_viewType, 0))

            typedArray.recycle()
        }

        settingView(style)
    }

    private fun settingView(style: YDSTextStyle) {

        includeFontPadding = false

        when (style) {
            YDSTextStyle.TITLE1 -> {
                val font = ResourcesCompat.getFont(context, R.font.noto_sans_bold_hestia)
                typeface = font
                setLineSpacing(8.4f.dpToPx(context),1f)
                setPadding(0, 4.2f.dpToIntPx(context), 0, 4.2f.dpToIntPx(context))
                setTextSize(TypedValue.COMPLEX_UNIT_PX, 28f.dpToPx(context))
            }
            YDSTextStyle.TITLE2 -> {

            }
            YDSTextStyle.SUBTITLE1 -> {

            }
            YDSTextStyle.SUBTITLE2 -> {

            }
            YDSTextStyle.SUBTITLE3 -> {

            }
            YDSTextStyle.BODY1 -> {

            }
            YDSTextStyle.BODY2 -> {

            }
            YDSTextStyle.BUTTON1 -> {

            }
            YDSTextStyle.BUTTON2 -> {

            }
            YDSTextStyle.RECT_BUTTON1 -> {

            }
            YDSTextStyle.RECT_BUTTON2 -> {

            }
            YDSTextStyle.CAPTION1 -> {

            }
            YDSTextStyle.CAPTION2 -> {

            }
        }
    }

    enum class YDSTextStyle(val value: Int) {
        TITLE1(0),
        TITLE2(1),
        SUBTITLE1(2),
        SUBTITLE2(3),
        SUBTITLE3(4),
        BODY1(5),
        BODY2(6),
        BUTTON1(7),
        BUTTON2(8),
        RECT_BUTTON1(9),
        RECT_BUTTON2(10),
        CAPTION1(11),
        CAPTION2(12)
    }

    private fun styleValueOf(typeValue: Int): YDSTextStyle {
        YDSTextStyle.values().forEach {
            if (it.value == typeValue) {
                return it
            }
        }
        throw InvalidTypeException()
    }
}