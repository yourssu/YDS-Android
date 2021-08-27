package com.yourssu.design.system.atom

import android.content.Context
import android.graphics.Color
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

//        setButtonDrawable(R.drawable.selector_check_box)
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

    interface AvailabilityState : State
    interface SizeState : State
    interface SelectivityState : State

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


    inner class Selected : SelectivityState {
        override fun apply() {
            isChecked = true
            val disable = !isEnabled
            buttonDrawable = if (disable) {
                val dr = AppCompatResources.getDrawable(context, R.drawable.ic_checkcircle_filled)
                if (dr != null) {
                    DrawableCompat.setTint(dr, Color.parseColor("#B5B9BD"))
                }
                dr
            } else {
                val dr = AppCompatResources.getDrawable(context, R.drawable.ic_checkcircle_filled)
                if (dr != null) {
                    DrawableCompat.setTint(dr, Color.parseColor("#816DEC"))
                    setTextColor(Color.parseColor("#816DEC"))
                }
                dr
            }

        }
    }

    inner class NotSelected : SelectivityState {
        override fun apply() {
            isChecked = false
            val disable = !isEnabled
            buttonDrawable = if (disable) {
                val dr = AppCompatResources.getDrawable(context, R.drawable.ic_checkcircle_line)
                if (dr != null) {
                    DrawableCompat.setTint(dr, Color.parseColor("#B5B9BD"))
                }
                dr
            } else {
                val dr = AppCompatResources.getDrawable(context, R.drawable.ic_checkcircle_line)
                if (dr != null) {
                    DrawableCompat.setTint(dr, Color.parseColor("#505458"))
                    setTextColor(Color.parseColor("#505458"))
                }
                dr
            }
        }
    }

    inner class Small : SizeState {
        override fun apply() {
            changeTextSize()
            changePadding()
            changeImageSize()
        }

        private fun changeImageSize() {
//        val dr:Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.ic_checkcircle_filled, null)
//        dr.setBounds(0,0,)
//        setButtonDrawable(R.drawable.selector_check_box)
//            val dr: Drawable? = compoundDrawables[0]
//            Log.d("KWK", dr.toString())
//            dr?.setBounds(0, 0, context.dpToPx(13.33f).toInt(), context.dpToPx(13.33f).toInt())
//            setCompoundDrawables(dr, null, null, null)
        }

        private fun changePadding() {
            setPadding(context.dpToPx(SMALL_PADDING).toInt(), 0, 0, 0)
        }

        private fun changeTextSize() {
            textSize = context.dpToPx(SMALL_TEXT_SIZE)
        }
    }

    inner class Medium : SizeState {
        override fun apply() {
            changeTextSize()
            changePadding()
            changeImageSize()
        }

        private fun changeImageSize() {
            // 어떤 종류의 반영이든지 그걸 한번에 처리해줘야할듯.
//            val dr = AppCompatResources.getDrawable(context, R.drawable.ic_checkcircle_filled)
//            dr?.toBitmap(1000, 1000)
//            buttonDrawable = dr
        }

        private fun changePadding() {
            setPadding(context.dpToPx(MEDIUM_PADDING).toInt(), 0, 0, 0)
        }

        private fun changeTextSize() {
            textSize = context.dpToPx(MEDIUM_TEXT_SIZE)
        }
    }

    inner class Large : SizeState {
        override fun apply() {
            changeTextSize()
            changePadding()
            changeImageSize()
        }

        private fun changeImageSize() {
            val dr: Drawable? = compoundDrawables[0]
            Log.d("KWK", dr.toString())
        }

        private fun changePadding() {
            setPadding(context.dpToPx(LARGE_PADDING).toInt(), 0, 0, 0)
        }

        private fun changeTextSize() {
            textSize = context.dpToPx(LARGE_TEXT_SIZE)
        }
    }
}