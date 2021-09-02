package com.yourssu.design.system.atom

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.getStringOrThrow
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.drawable.DrawableCompat
import com.yourssu.design.R
import com.yourssu.design.databinding.LayoutCheckBoxBinding
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.undercarriage.size.dpToPx


class CheckBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutCheckBoxBinding =
        LayoutCheckBoxBinding.inflate(LayoutInflater.from(context), this, true)


    private var isDisabled: Boolean = false
        set(bool) {
            field = bool
            setAvailabilityState(field)
        }

    var size: Int = SIZE_SMALL
        set(value) {
            field = value
            setSizeState(field)
        }

    private var label: String = "텍스트"
        set(value) {
            field = value
            setText(label)
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.CheckBox) {
            isDisabled = getBoolean(R.styleable.CheckBox_isDisabled, false)
            isSelected = getBoolean(R.styleable.CheckBox_isSelected, false)
            size = getInt(R.styleable.CheckBox_size, SIZE_SMALL)
            label = getStringOrThrow(R.styleable.CheckBox_label)
        }

        initStates(isDisabled, isSelected, size, label)
    }

    private fun initStates(isDisabled: Boolean, isSelected: Boolean, size: Int, label: String) {
        setSizeState(size)
        setAvailabilityState(isDisabled)
        setSelectivityState(isSelected)
        setText(label)
    }

    private fun setText(label: String) {
        binding.text.text = label
    }

    private fun setAvailabilityState(disabled: Boolean) {
        isEnabled = !disabled
    }

    private fun setSelectivityState(selected: Boolean) {
        isSelected = selected
        when (isSelected) {
            true -> {
                if (isEnabled) {
                    changeTotalColor(R.color.buttonPoint)
                    changeDrawable(Icon.ic_checkcircle_filled)
                } else {
                    changeTotalColor(R.color.buttonDisabled)
                    changeDrawable(Icon.ic_checkcircle_filled)
                }
            }
            false -> {
                if (isEnabled) {
                    changeTotalColor(R.color.buttonNormal)
                    changeDrawable(Icon.ic_checkcircle_line)
                } else {
                    changeTotalColor(R.color.buttonDisabled)
                    changeDrawable(Icon.ic_checkcircle_line)
                }
            }
        }
    }

    private fun setSizeState(size: Int) {
        when (size) {
            SIZE_SMALL -> {
                changeTypo(Typo.Button4)
                changeMarginLeft(MARGIN_SMALL)
                changeImageSize(IconView.ExtraSmall)
            }
            SIZE_MEDIUM -> {
                changeTypo(Typo.Button3)
                changeMarginLeft(MARGIN_MEDIUM)
                changeImageSize(IconView.Small)
            }
            SIZE_LARGE -> {
                changeTypo(Typo.Button3)
                changeMarginLeft(MARGIN_LARGE)
                changeImageSize(IconView.Medium)
            }
            else -> {
                changeTypo(Typo.Button4)
                changeMarginLeft(MARGIN_SMALL)
                changeImageSize(IconView.ExtraSmall)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)

        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d("KWK", "ACTION_DOWN")
                true
            }
            MotionEvent.ACTION_UP -> {
                Log.d("KWK", "ACTION_UP")
                performClick() // Call this method to handle the response, and
                true
            }
            else -> false  // Return false for other touch events
        }
    }

    override fun performClick(): Boolean {
        super.performClick()
        if (isEnabled) {
            setSelectivityState(!isSelected)
        }
        invalidate()
        return true
    }

    companion object {
        private const val SIZE_SMALL = 1
        private const val SIZE_MEDIUM = 2
        private const val SIZE_LARGE = 3
        private const val MARGIN_SMALL = 4f
        private const val MARGIN_MEDIUM = 8f
        private const val MARGIN_LARGE = 8f
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

    private fun changeDrawable(value: Int) {
        binding.icon.setIconResource(value)
    }
}

//class CheckBox @JvmOverloads constructor(
//    context: Context, attrs: AttributeSet? = null
//) : androidx.appcompat.widget.AppCompatCheckBox(context, attrs) {
//
//    private lateinit var availabilityState: AvailabilityState
//    private lateinit var selectivityState: SelectivityState
//    private lateinit var sizeState: SizeState
//
//    var isDisabled: Boolean = false
//        set(bool) {
//            field = bool
//            setAvailabilityState(field)
//        }
//
//    // 이거 피그마 따라서 isSeleted 하려고 했는데 Conflicting declarations:
//    var is_Selected: Boolean = false
//        set(bool) {
//            field = bool
//            setSelectivityState(field)
//        }
//
//    var size: Int = SIZE_SMALL
//        set(value) {
//            field = value
//            setSizeState(field)
//        }
//
//    init {
//        context.withStyledAttributes(attrs, R.styleable.CheckBox) {
//            isDisabled = getBoolean(R.styleable.CheckBox_isDisabled, false)
//            is_Selected = getBoolean(R.styleable.CheckBox_isSelected, false)
//            size = getInt(R.styleable.CheckBox_size, SIZE_SMALL)
//        }
//
//        initStates(isDisabled, is_Selected, size)
//    }
//
//    private fun initStates(isDisabled: Boolean, isSelected: Boolean, size: Int) {
//        setSizeState(size)
//        setAvailabilityState(isDisabled)
//        setSelectivityState(isSelected)
//    }
//
//    private fun setAvailabilityState(isDisabled: Boolean) {
//        availabilityState = when (isDisabled) {
//            true -> Disabled()
//            false -> Enabled()
//        }
//        availabilityState.apply()
//    }
//
//    private fun setSelectivityState(isSelected: Boolean) {
//        selectivityState = when (isSelected) {
//            true -> Selected()
//            false -> NotSelected()
//        }
//        selectivityState.apply()
//    }
//
//    private fun setSizeState(size: Int) {
//        sizeState = when (size) {
//            SIZE_SMALL -> Small()
//            SIZE_MEDIUM -> Medium()
//            SIZE_LARGE -> Large()
//            else -> Small()
//        }
//        sizeState.apply()
//    }
//
//    override fun performClick(): Boolean {
//        if (super.performClick()) return true
//        setSelectivityState(isChecked)
//        return true
//    }
//
//    interface State {
//        fun apply()
//    }
//
//    abstract inner class AvailabilityState(private val enabled: Boolean) : State {
//        override fun apply() {
//            isEnabled = enabled
//        }
//    }
//
//    abstract inner class SelectivityState(private val drawable_id: Int) : State {
//        private val disable = !isEnabled
//
//        override fun apply() {
//            changeDrawable(drawable_id)
//            if (disable)
//                onDisable()
//            else
//                onEnable()
//        }
//
//        abstract fun onDisable()
//
//        abstract fun onEnable()
//    }
//
//    abstract inner class SizeState(
//        private val padding_size: Float,
//        private val text_size: Float,
//        private val image_size: Float
//    ) : State {
//        override fun apply() {
//            changeTextSize(text_size)
//            changePadding(padding_size)
//            changeImageSize(image_size)
//        }
//    }
//
//    // 최대한 메인 속성은 생성자로 넣어주는게 예쁜듯
//    inner class Disabled : AvailabilityState(false)
//    inner class Enabled : AvailabilityState(true)
//
//    //저는 이렇게 미리보기 이미지가 옆에 뜨는게 좋더라구요
//    inner class Selected : SelectivityState(R.drawable.ic_checkcircle_filled) {
//
//        override fun apply() {
//            isChecked = true
//            super.apply()
//        }
//
//        override fun onDisable() {
//            changeTotalColor(R.color.buttonDisabled)
//        }
//
//        override fun onEnable() {
//            changeTotalColor(R.color.buttonPoint)
//        }
//    }
//
//    inner class NotSelected : SelectivityState(R.drawable.ic_checkcircle_line) {
//        override fun apply() {
//            isChecked = false
//            super.apply()
//        }
//
//        override fun onDisable() {
//            changeTotalColor(R.color.buttonDisabled)
//        }
//
//        override fun onEnable() {
//            changeTotalColor(R.color.buttonNormal)
//        }
//    }
//
//    // 최대한 메인 속성은 생성자로 넣어주는게 예쁜듯
//    inner class Small : SizeState(SMALL_PADDING, SMALL_TEXT_SIZE, SMALL_IMAGE_RATIO)
//    inner class Medium : SizeState(MEDIUM_PADDING, MEDIUM_TEXT_SIZE, MEDIUM_IMAGE_RATIO)
//    inner class Large : SizeState(LARGE_PADDING, LARGE_TEXT_SIZE, LARGE_IMAGE_RATIO)
//
//    companion object {
//        private const val SIZE_SMALL = 0
//        private const val SIZE_MEDIUM = 1
//        private const val SIZE_LARGE = 2
//        private const val SMALL_PADDING = 4f
//        private const val MEDIUM_PADDING = 8f
//        private const val LARGE_PADDING = 8f
//        private const val SMALL_TEXT_SIZE = 12f
//        private const val MEDIUM_TEXT_SIZE = 14f
//        private const val LARGE_TEXT_SIZE = 14f
//        private const val LARGE_IMAGE_RATIO = 0.83f
//        private const val MEDIUM_IMAGE_RATIO = 0.66f
//        private const val SMALL_IMAGE_RATIO = 0.54f
//
//
//        fun Context.checkBox(block: CheckBox.() -> Unit) = CheckBox(this).run {
//            block.invoke(this)
//            this
//        }
//
//        fun ViewGroup.checkBox(block: CheckBox.() -> Unit) = CheckBox(this.context).run {
//            block.invoke(this)
//            this@checkBox.addView(this)
//            this
//        }
//
//        fun ComponentGroup.checkBox(block: CheckBox.() -> Unit) =
//            CheckBox(this.componentContext).run {
//                block.invoke(this)
//                this@checkBox.addComponent(this)
//                this
//            }
//
//    }
//
//    private fun changeTotalColor(color_id: Int) {
//        changeDrawableColor(color_id)
//        changeTextColor(color_id)
//    }
//
//    private fun changeDrawableColor(color_id: Int) {
//        val dr = buttonDrawable
//        if (dr != null) {
//            DrawableCompat.setTint(dr, ContextCompat.getColor(context, color_id))
//        }
//        buttonDrawable = dr
//    }
//
//    private fun changeDrawable(drawable_id: Int) {
//        buttonDrawable = AppCompatResources.getDrawable(context, drawable_id)
//    }
//
//    private fun changeTextColor(color_id: Int) {
//        setTextColor(ContextCompat.getColor(context, color_id))
//    }
//
//    private fun changePadding(padding_size: Float) {
//        setPadding(context.dpToPx(padding_size).toInt(), 0, 0, 0)
//    }
//
//    private fun changeTextSize(text_size: Float) {
//        textSize = context.dpToPx(text_size)
//    }
//
//    private fun changeImageSize(image_ratio: Float) {
//        // 도저히 체크박스 크기 바꾸는 방법을 모르겠음.
//        scaleX = image_ratio
//        scaleY = image_ratio
//    }
//}