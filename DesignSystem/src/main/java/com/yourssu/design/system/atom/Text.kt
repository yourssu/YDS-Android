package com.yourssu.design.system.atom

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.yourssu.design.R
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.system.foundation.Typography
import com.yourssu.design.undercarriage.size.getDimenFloat
import com.yourssu.design.system.language.ComponentGroup

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

    private fun setTextInfo() {
        setTextAppearance(Typo.getStyle(typo))
        includeFontPadding = false
        val lineHeightPx = context.getDimenFloat(Typo.getLineHeight(typo))
        val lineSpacing = lineHeightPx - textSize.toInt()

        setPadding(0, lineSpacing.toInt() / 2, 0, lineSpacing.toInt() / 2)
        setLineSpacing(lineSpacing, 1f)
    }

    companion object {
        fun Context.text(block: Text.() -> Unit) = Text(this).run {
            block.invoke(this)
            this
        }

        fun ViewGroup.text(block: Text.() -> Unit) = Text(this.context).run {
            block.invoke(this)
            this@text.addView(this)
            this
        }

        fun ComponentGroup.text(block: Text.() -> Unit) = Text(this.componentContext).run {
            block.invoke(this)
            this@text.addComponent(this)
            this
        }
    }
}