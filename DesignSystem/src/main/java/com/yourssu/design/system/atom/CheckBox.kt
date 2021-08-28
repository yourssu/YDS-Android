package com.yourssu.design.system.atom

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.toBitmap
import com.yourssu.design.R
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.undercarriage.size.dpToPx

class CheckBox @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : androidx.appcompat.widget.AppCompatCheckBox(context, attrs) {

    private lateinit var availabilityState: AvailabilityState
    private lateinit var selectivityState: SelectivityState
    private lateinit var sizeState: SizeState


    init {
        var isDisabled = false
        var isSelected = false
        var size = 0

        context.withStyledAttributes(attrs, R.styleable.CheckBox) {
            isDisabled = getBoolean(R.styleable.CheckBox_isDisabled, false)
            isSelected = getBoolean(R.styleable.CheckBox_isSelected, false)
            size = getInt(R.styleable.CheckBox_size, SIZE_SMALL)
        }

        initStates(isDisabled, isSelected, size)
    }

    private fun initStates(isDisabled: Boolean, isSelected: Boolean, size: Int) {
        setSizeState(size)
        setAvailabilityState(isDisabled)
        setSelectivityState(isSelected)
    }

    private fun setAvailabilityState(isDisabled: Boolean) {
        availabilityState = when (isDisabled) {
            true -> Disabled()
            false -> Enabled()
        }
        availabilityState.apply()
    }

    private fun setSelectivityState(isSelected: Boolean) {
        selectivityState = when (isSelected) {
            true -> Selected()
            false -> NotSelected()
        }
        selectivityState.apply()
    }

    private fun setSizeState(size: Int) {
        sizeState = when (size) {
            SIZE_SMALL -> Small()
            SIZE_MEDIUM -> Medium()
            SIZE_LARGE -> Large()
            else -> Small()
        }
        sizeState.apply()
    }

    override fun performClick(): Boolean {
        if (super.performClick()) return true
        setSelectivityState(isChecked)
        return true
    }

    companion object {
        private const val SIZE_SMALL = 0
        private const val SIZE_MEDIUM = 1
        private const val SIZE_LARGE = 2
        private const val SMALL_PADDING = 4f
        private const val MEDIUM_PADDING = 8f
        private const val LARGE_PADDING = 8f
        private const val SMALL_TEXT_SIZE = 12f
        private const val MEDIUM_TEXT_SIZE = 14f
        private const val LARGE_TEXT_SIZE = 14f
    }

    interface State {
        fun apply()
    }

    // 어쩔때 인터페이스? 추상클래스?
    interface AvailabilityState : State

    abstract inner class SelectivityState : State {
        fun changeDrawableColor(dr: Drawable?, colorString: String) {
            if (dr != null) {
                DrawableCompat.setTint(dr, Color.parseColor(colorString))
            }
            buttonDrawable = dr
        }

        fun changeTextColor(colorString: String) {
            setTextColor(Color.parseColor(colorString))
        }
    }

    interface SizeState : State {
        override fun apply() {
            changeTextSize()
            changePadding()
            changeImageSize()
        }

        fun changeImageSize()

        fun changePadding()

        fun changeTextSize()
    }


    inner class Disabled : AvailabilityState {
        override fun apply() {
            isEnabled = false
            setTextColor(Color.parseColor("#B5B9BD"))
        }
    }

    inner class Enabled : AvailabilityState {
        override fun apply() {
            isEnabled = true
        }
    }

    inner class Selected : SelectivityState() {
        override fun apply() {
            isChecked = true
            val disable = !isEnabled
            val dr = AppCompatResources.getDrawable(context, R.drawable.ic_checkcircle_filled)

            if (disable) {
                // 값은 XML에 있다.
                changeDrawableColor(dr, "#B5B9BD")
            } else {
                changeDrawableColor(dr, "#816DEC")
                changeTextColor("#816DEC")
            }

        }
    }

    inner class NotSelected : SelectivityState() {
        override fun apply() {
            isChecked = false
            val disable = !isEnabled
            val dr = AppCompatResources.getDrawable(context, R.drawable.ic_checkcircle_line)

            if (disable) {
                changeDrawableColor(dr, "#B5B9BD")
            } else {
                changeDrawableColor(dr, "#505458")
                changeTextColor("#505458")
            }
        }
    }

    // 구조 개편하기 생성자에 따라서 스테이트를 변경하는걸로?
    inner class Small : SizeState {
        override fun changeImageSize() {
            val dr = buttonDrawable
            Log.d("KWK_TYPE",dr.toString())
            dr?.setBounds(0,0,5,5)
            Log.d("KWK_SMALL", dr?.bounds.toString())
        }

        override fun changePadding() {
            setPadding(context.dpToPx(SMALL_PADDING).toInt(), 0, 0, 0)
        }

        override fun changeTextSize() {
            textSize = context.dpToPx(SMALL_TEXT_SIZE)
        }
    }

    inner class Medium : SizeState {
        override fun changeImageSize() {
            val dr = buttonDrawable
            Log.d("KWK_MEME", dr?.bounds.toString())
        }

        override fun changePadding() {
            setPadding(context.dpToPx(MEDIUM_PADDING).toInt(), 0, 0, 0)
        }

        override fun changeTextSize() {
            // 텍스트 크기가 disabled일때 랑 enabled일때랑 다르다.
            textSize = context.dpToPx(MEDIUM_TEXT_SIZE)
        }
    }

    inner class Large : SizeState {
        override fun changeImageSize() {

        }

        override fun changePadding() {
            setPadding(context.dpToPx(LARGE_PADDING).toInt(), 0, 0, 0)
        }

        override fun changeTextSize() {
            textSize = context.dpToPx(LARGE_TEXT_SIZE)
        }
    }
}