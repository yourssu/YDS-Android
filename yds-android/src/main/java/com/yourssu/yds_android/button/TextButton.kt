package com.yourssu.yds_android.button

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.annotation.StyleRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.TextViewCompat
import com.yourssu.yds_android.R
import com.yourssu.yds_android.util.getColorForCurrentState
import com.yourssu.yds_android.util.resize
import com.yourssu.yds_android.util.safeGetDrawable
import com.yourssu.yds_android.util.safeSetTextColor

class TextButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    @Px
    private var iconSize = 0
        set(value) {
            field = value
            startIcon.resize(value)
            endIcon.resize(value)
        }

    var startIcon: Drawable? = null
        set(value) {
            field = value
            field.resize(iconSize)
            tintDrawables()
        }

    var endIcon: Drawable? = null
        set(value) {
            field = value
            field.resize(iconSize)
            tintDrawables()
        }

    var useTextColorAsIconTintColor = false
        set(value) {
            if (field != value) {
                field = value
                tintDrawables()
            }
        }

    @DrawableRes
    var startIconResource: Int = 0
        set(value) {
            field = value
            startIcon = context.safeGetDrawable(value)
        }

    @DrawableRes
    var endIconResource: Int = 0
        set(value) {
            field = value
            endIcon = context.safeGetDrawable(value)
        }

    var startIconTintColor: ColorStateList? = null
        get() {
            return field ?: if (useTextColorAsIconTintColor) textColors
            else null
        }

    var endIconTintColor: ColorStateList? = null
        get() {
            return field ?: if (useTextColorAsIconTintColor) textColors
            else null
        }

    /**
     * [isCompleted] true means Opt-out, false means Opt-in
     */
    var isCompleted: Boolean
        get() = isActivated
        set(value) {
            super.setActivated(value)
        }

    init {
        initialize(attrs, defStyleAttr, defStyleRes = 0)
    }

    private fun initialize(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.TextButton, defStyleAttr, defStyleRes)
        val title = a.getString(R.styleable.TextButton_android_text)
        val textAppearance = a.getResourceId(R.styleable.TextButton_android_textAppearance, 0)
        val textColors = a.getColorStateList(R.styleable.TextButton_android_textColor)
        val textSize = a.getDimension(R.styleable.TextButton_android_textSize, 0f)
        val startDrawable = a.getDrawable(R.styleable.TextButton_yds_startIcon)
        val endDrawable = a.getDrawable(R.styleable.TextButton_yds_endIcon)
        val drawableStartTintColor = a.getColorStateList(R.styleable.TextButton_yds_startIconTintColor)
        val drawableEndTintColor = a.getColorStateList(R.styleable.TextButton_yds_endIconTintColor)
        val isOptOut = a.getBoolean(R.styleable.TextButton_yds_completed, false)
        val iconSize = a.getDimensionPixelSize(R.styleable.TextButton_yds_iconSize, 0)
        val drawablePadding = a.getDimensionPixelSize(R.styleable.TextButton_yds_iconPadding, 0)
        val useTextColor = a.getBoolean(R.styleable.TextButton_yds_useTextColorAsIconTintColor, false)

        a.recycle()

        text = title
        if (textAppearance != 0) {
            TextViewCompat.setTextAppearance(this, textAppearance)
        }

        safeSetTextColor(textColors)
        useTextColorAsIconTintColor = useTextColor

        if (textSize > 0f) {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        }

        this.startIconTintColor = drawableStartTintColor
        this.endIconTintColor = drawableEndTintColor
        this.iconSize = iconSize
        this.startIcon = startDrawable
        this.endIcon = endDrawable
        compoundDrawablePadding = drawablePadding
        this.isCompleted = isOptOut

        gravity = Gravity.CENTER
        isSingleLine = true
        maxLines = 1
        ellipsize = TextUtils.TruncateAt.END
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        val drawables = compoundDrawables
        if (drawables[0] != null && startIconTintColor != null) {
            wrapDrawable(startIcon, startIconTintColor)
        }

        if (drawables[2] != null && endIconTintColor != null) {
            wrapDrawable(endIcon, endIconTintColor)
        }
    }

    private fun tintDrawables() {
        setCompoundDrawables(
            wrapDrawable(startIcon, startIconTintColor),
            null,
            wrapDrawable(endIcon, endIconTintColor),
            null
        )
    }

    private fun wrapDrawable(drawable: Drawable?, colorStateList: ColorStateList?) = drawable?.let {
        val tintColor = colorStateList.getColorForCurrentState(drawableState)
        val tintDrawable = if (tintColor != Color.TRANSPARENT) {
            val wrapped = DrawableCompat.wrap(it).mutate()
            DrawableCompat.setTint(wrapped, tintColor)
            DrawableCompat.setTintMode(wrapped, PorterDuff.Mode.SRC_IN)
            wrapped
        } else {
            it.mutate()
        }
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            tintDrawable.invalidateSelf()
        }
        tintDrawable
    }

    private fun applyStyle(style: Style) {
        initialize(null, 0, style.style)
    }

    override fun setTextColor(color: Int) {
        super.setTextColor(color)
        tintDrawables()
    }

    override fun setTextColor(colors: ColorStateList?) {
        super.setTextColor(colors)
        tintDrawables()
    }

    companion object {
        fun createButton(context: Context, withStyle: Style) = TextButton(context).apply {
            applyStyle(withStyle)
        }
    }

    enum class Style(@StyleRes val style: Int) {
        XSmall(R.style.TextButton_XSmall),
        Medium(R.style.TextButton_Medium),
        Small(R.style.TextButton_Small)
    }
}