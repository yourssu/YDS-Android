package com.yourssu.design.system.atom

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.annotation.IntDef
import androidx.core.content.ContextCompat
import com.yourssu.design.R

@SuppressLint("ViewConstructor")
class Badge : LinearLayout {
    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context, attrs)
    }

    private lateinit var badgeFrame: LinearLayout
    private lateinit var badgeIcon: IconView
    private lateinit var badgeContent: Text

    @BadgeTheme
    private var theme: Int = MONO
        set(theme) {
            field = theme
            setBadgeInfo()
            requestLayout()
        }

    private var text: String = "test"
        set(text) {
            field = text
            setBadgeInfo()
            requestLayout()
        }

    private var icon: Int = NONE_ICON
        set(icon) {
            field = icon
            setBadgeInfo()
            requestLayout()
        }

    private fun initView(context: Context, attrs: AttributeSet?) {

        View.inflate(context, R.layout.layout_badge, this)
        badgeFrame = findViewById(R.id.badgeFrame)
        badgeIcon = findViewById(R.id.badgeIcon)
        badgeContent = findViewById(R.id.badgeContent)

        if (attrs != null) {
            val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.Badge)

            theme = typedArray.getInteger(R.styleable.Badge_badgeTheme, MONO)
            text = typedArray.getString(R.styleable.Badge_badgeText).toString()
            icon = typedArray.getInteger(R.styleable.Badge_badgeIcon, NONE_ICON)

            setBadgeInfo()

            typedArray.recycle()
        } else {
            setBadgeInfo()
        }
    }

    private fun setBadgeInfo() {
        val color = getBadgeColor(theme)

        val drawable =
            ContextCompat.getDrawable(context, R.drawable.badge_background) as GradientDrawable
        drawable.setColor(ContextCompat.getColor(context, color))
        badgeFrame.background = drawable

        badgeContent.text = text

        if (icon == NONE_ICON) {
            badgeFrame.setPadding(dpToIntPx(12), 0, dpToIntPx(12), 0)
            badgeIcon.visibility = View.GONE
        } else {
            badgeFrame.setPadding(dpToIntPx(8), 0, dpToIntPx(8), 0)
            badgeIcon.visibility = View.VISIBLE
            badgeIcon.setIconResource(icon)
        }
    }

    private fun dpToIntPx(dp: Int): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(value = [MONO, GREEN, EMERALD, AQUA, BLUE, INDIGO, VIOLET, PURPLE, PINK])
    annotation class BadgeTheme

    companion object {
        const val MONO: Int = 0
        const val GREEN: Int = 1
        const val EMERALD: Int = 2
        const val AQUA: Int = 3
        const val BLUE: Int = 4
        const val INDIGO: Int = 5
        const val VIOLET: Int = 6
        const val PURPLE: Int = 7
        const val PINK: Int = 8

        const val NONE_ICON: Int = -99999
    }

    @ColorRes
    private fun getBadgeColor(theme: Int) = when (theme) {
        MONO -> R.color.monoItemBG
        GREEN -> R.color.greenItemBG
        EMERALD -> R.color.emeraldItemBG
        AQUA -> R.color.aquaItemBG
        BLUE -> R.color.blueItemBG
        INDIGO -> R.color.indigoItemBG
        VIOLET -> R.color.violetItemBG
        PURPLE -> R.color.purpleItemBG
        PINK -> R.color.pinkItemBG
        else -> R.color.monoItemBG
    }
}