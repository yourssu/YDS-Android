package com.yourssu.design.system.atom

import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
<<<<<<< HEAD:DesignSystem/src/main/java/com/yourssu/design/system/atom/CheckBox.kt
=======
import androidx.core.content.withStyledAttributes
>>>>>>> [StoryBook] Checkbox Page 구현 및 일부 수정:DesignSystem/src/main/java/com/yourssu/design/system/atom/Checkbox.kt
import androidx.databinding.BindingAdapter
import com.yourssu.design.R
import com.yourssu.design.databinding.LayoutCheckBoxBinding
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.undercarriage.size.dpToPx
import kotlin.math.abs


class Checkbox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutCheckBoxBinding =
        LayoutCheckBoxBinding.inflate(LayoutInflater.from(context), this, true)

    var size: Int = SMALL
<<<<<<< HEAD:DesignSystem/src/main/java/com/yourssu/design/system/atom/CheckBox.kt
        set(size) {
            field = size
=======
        set(value) {
            field = value
>>>>>>> [StoryBook] Checkbox Page 구현 및 일부 수정:DesignSystem/src/main/java/com/yourssu/design/system/atom/Checkbox.kt
            setSizeState()
        }

    var label: String = ""
        set(label) {
            field = label
            changeText()
        }

    var isDisabled: Boolean = false
        set(isDisabled) {
            field = isDisabled
            setState()
        }

<<<<<<< HEAD:DesignSystem/src/main/java/com/yourssu/design/system/atom/CheckBox.kt
=======

    init {
        context.withStyledAttributes(attrs, R.styleable.Checkbox) {
            isDisabled = getBoolean(R.styleable.Checkbox_isDisabled, false)
            isSelected = getBoolean(R.styleable.Checkbox_isSelected, false)
            size = getInt(R.styleable.Checkbox_size, SMALL)
            label = getString(R.styleable.Checkbox_label).toString()
        }
        setState()
    }

    interface SelectedListener {
        fun onSelected(boolean: Boolean)
    }

    private var selectedListener: SelectedListener? = null

    fun setOnSelectedListener(listener: SelectedListener) {
        selectedListener = listener
    }

>>>>>>> [StoryBook] Checkbox Page 구현 및 일부 수정:DesignSystem/src/main/java/com/yourssu/design/system/atom/Checkbox.kt
    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        selectedListener?.onSelected(selected)
        setState()
    }

    private val touchPoint = PointF(0f, 0f)

    private fun setState() {
        setTotalColor()
        setDrawable()
        setSizeState()
    }

    private fun setTotalColor() {
        when (isDisabled) {
            true ->
                changeTotalColor(R.color.buttonDisabled)
            false ->
                if (isSelected)
                    changeTotalColor(R.color.buttonPoint)
                else
                    changeTotalColor(R.color.buttonNormal)
        }
    }

    private fun setDrawable() {
        when (isSelected) {
            true -> changeDrawable(Icon.ic_checkcircle_filled)
            false -> changeDrawable(Icon.ic_checkcircle_line)
        }
    }

    private fun setSizeState() {
        when (size) {
<<<<<<< HEAD:DesignSystem/src/main/java/com/yourssu/design/system/atom/CheckBox.kt
            SMALL -> {
                if (isDisabled) {
                    changeTotalSize(Typo.Button4, MARGIN_SMALL, IconView.ExtraSmall)
                } else {
                    changeTotalSize(Typo.Button3, MARGIN_SMALL, IconView.ExtraSmall)
                }
            }
=======
            SMALL -> changeTotalSize(Typo.Button4, MARGIN_SMALL, IconView.ExtraSmall)
>>>>>>> [StoryBook] Checkbox Page 구현 및 일부 수정:DesignSystem/src/main/java/com/yourssu/design/system/atom/Checkbox.kt
            MEDIUM -> changeTotalSize(Typo.Button3, MARGIN_MEDIUM, IconView.Small)
            LARGE -> changeTotalSize(Typo.Button3, MARGIN_LARGE, IconView.Medium)
            else -> changeTotalSize(Typo.Button4, MARGIN_SMALL, IconView.ExtraSmall)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
<<<<<<< HEAD:DesignSystem/src/main/java/com/yourssu/design/system/atom/CheckBox.kt
                if (!isDisabled) {
                    touchPoint.set(event.x, event.y)
                }
            }
            MotionEvent.ACTION_UP -> {
                if (abs(touchPoint.x - event.x) < width.toFloat()
                    && abs(touchPoint.y - event.y) < height.toFloat()
                ) {
                    performClick()
                }
=======

            }
            MotionEvent.ACTION_UP -> {
                performClick()
>>>>>>> [StoryBook] Checkbox Page 구현 및 일부 수정:DesignSystem/src/main/java/com/yourssu/design/system/atom/Checkbox.kt
            }
        }

        return true
    }

    private fun toggle() {
        if (!isDisabled) {
            isSelected = !isSelected
            setState()
        }
    }

    override fun performClick(): Boolean {
        super.performClick()
        toggle()
        return true
    }

    companion object {
        const val SMALL = 1
        const val MEDIUM = 2
        const val LARGE = 3
<<<<<<< HEAD:DesignSystem/src/main/java/com/yourssu/design/system/atom/CheckBox.kt
=======

>>>>>>> [StoryBook] Checkbox Page 구현 및 일부 수정:DesignSystem/src/main/java/com/yourssu/design/system/atom/Checkbox.kt
        private const val MARGIN_SMALL = 4f
        private const val MARGIN_MEDIUM = 8f
        private const val MARGIN_LARGE = 8f

        @JvmStatic
        @BindingAdapter("isDisabled")
<<<<<<< HEAD:DesignSystem/src/main/java/com/yourssu/design/system/atom/CheckBox.kt
        fun setDisabled(checkBox: CheckBox, isDisabled: Boolean) {
            checkBox.isDisabled = isDisabled
=======
        fun setIsDisabled(checkbox: Checkbox, isDisabled: Boolean) {
            checkbox.isDisabled = isDisabled
>>>>>>> [StoryBook] Checkbox Page 구현 및 일부 수정:DesignSystem/src/main/java/com/yourssu/design/system/atom/Checkbox.kt
        }

        @JvmStatic
        @BindingAdapter("isSelected")
<<<<<<< HEAD:DesignSystem/src/main/java/com/yourssu/design/system/atom/CheckBox.kt
        fun setSelected(checkBox: CheckBox, isSelected: Boolean) {
            checkBox.isSelected = isSelected
=======
        fun setIsSelected(checkbox: Checkbox, isSelected: Boolean) {
            checkbox.isSelected = isSelected
>>>>>>> [StoryBook] Checkbox Page 구현 및 일부 수정:DesignSystem/src/main/java/com/yourssu/design/system/atom/Checkbox.kt
        }

        @JvmStatic
        @BindingAdapter("label")
<<<<<<< HEAD:DesignSystem/src/main/java/com/yourssu/design/system/atom/CheckBox.kt
        fun setText(checkBox: CheckBox, label: String) {
            checkBox.label = label
=======
        fun setLabel(checkbox: Checkbox, label: String) {
            checkbox.label = label
>>>>>>> [StoryBook] Checkbox Page 구현 및 일부 수정:DesignSystem/src/main/java/com/yourssu/design/system/atom/Checkbox.kt
        }

        @JvmStatic
        @BindingAdapter("size")
<<<<<<< HEAD:DesignSystem/src/main/java/com/yourssu/design/system/atom/CheckBox.kt
        fun setSize(checkBox: CheckBox, size: Int) {
            checkBox.size = size
=======
        fun setSize(checkbox: Checkbox, size: Int) {
            checkbox.size = size
        }

        @JvmStatic
        @BindingAdapter("selectedListener")
        fun setSelectedListener(checkbox: Checkbox, listener: SelectedListener) {
            checkbox.setOnSelectedListener(listener)
>>>>>>> [StoryBook] Checkbox Page 구현 및 일부 수정:DesignSystem/src/main/java/com/yourssu/design/system/atom/Checkbox.kt
        }
    }

    // change means will access to binding
    private fun changeTotalSize(typo: Int, marginSize: Float, imageSize: Int) {
        changeTypo(typo)
        changeMarginLeft(marginSize)
        changeImageSize(imageSize)
    }

    private fun changeTotalColor(color_id: Int) {
        changeDrawableColor(color_id)
        changeTextColor(color_id)
    }

    private fun changeTextColor(colorId: Int) {
        binding.text.setTextColor(ContextCompat.getColor(context, colorId))
    }

    private fun changeDrawableColor(colorId: Int) {
        binding.icon.setColorFilter(ContextCompat.getColor(context, colorId))
    }

    private fun changeTypo(typo: Int) {
        binding.text.typo = typo
    }

    private fun changeImageSize(value: Int) {
        binding.icon.setIconViewSize(value)
    }

    private fun changeMarginLeft(value: Float) {
        (binding.text.layoutParams as LayoutParams).setMargins(
            context.dpToPx(value).toInt(),
            0,
            0,
            0
        )
    }

    private fun changeText() {
        binding.text.text = label
    }

    private fun changeDrawable(value: Int) {
        binding.icon.setIconResource(value)
    }
}
