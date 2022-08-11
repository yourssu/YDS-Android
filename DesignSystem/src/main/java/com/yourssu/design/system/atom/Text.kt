package com.yourssu.design.system.atom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.yourssu.design.R
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.system.foundation.Typography
import com.yourssu.design.undercarriage.size.getDimenFloat

open class Text: AppCompatTextView {
    constructor(context: Context) : super(context) {
        initView(context, null)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    @Typography
    var typo: Int = Typo.Title1
        set(@Typography typo) {
            field = typo
            setTextInfo()
        }

    private fun initView(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Text)

            typo = typedArray.getInteger(R.styleable.Text_typo, Typo.Title1)
            setTextInfo()

            typedArray.recycle()
        } else {
            setTextInfo()
        }
    }

    /** 폰트가 가지고 있는 기본 lineHeight (우측 링크 참고) */ // https://velog.io/@tura/typography-sync
    /** 디자이너는 Baseline To Cap-Height 를 사용한다 (피그마 기준)
     *  반면 개발자가 wrap-content 로 구현하면 폰트가 가지고 있는 기본 lineHeight 의 영향을 받게됨
     *  이 수치는 includeFontPadding 과는 별개의 값임
     *  따라서 폰트의 lineHeight 를 구해서 수치 계산시 차이를 계산하고 차이만큼 padding 을 넣어 보정해줘야함
     *  디자이너가 요청한 LineHeight 값 == figmaLineHeight
     *  폰트의 LineHeight 값 == fontLineHeight
     *  lineSpacing = figmaLineHeight - fontLineHeight
     *  topPadding == bottomPadding == (lineSpacing / 2) */
    private fun setTextInfo() {
        // 폰트 적용
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
}