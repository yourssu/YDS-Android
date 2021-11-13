package com.yourssu.design.system.component

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.yourssu.design.databinding.LayoutDoubleTitleTopBarBinding
import com.yourssu.design.system.foundation.Icon

class DoubleTitleTopBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: LayoutDoubleTitleTopBarBinding =
        LayoutDoubleTitleTopBarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.firstButton.visibility = GONE
        binding.secondButton.visibility = GONE
        binding.thirdButton.visibility = GONE
    }

    var title: String = ""
        set(title) {
            field = title
            binding.title.text = title
        }

    var subtitle: String = ""
        set(subtitle) {
            field = subtitle
            binding.subTitle.text = subtitle
        }

    var firstButtonListener: OnClickListener? = null
        set(clickListener) {
            field = clickListener
            binding.firstButton.setOnClickListener(firstButtonListener)
        }

    var secondButtonListener: OnClickListener? = null
        set(clickListener) {
            field = clickListener
            binding.secondButton.setOnClickListener(secondButtonListener)
        }

    var thirdButtonListener: OnClickListener? = null
        set(clickListener) {
            field = clickListener
            binding.thirdButton.setOnClickListener(thirdButtonListener)
        }

    var firstButtonVisibility: Int = GONE
        set(value) {
            field = value
            binding.firstButton.visibility = value
        }

    var secondButtonVisibility: Int = GONE
        set(value) {
            field = value
            binding.secondButton.visibility = value
        }

    var thirdButtonVisibility: Int = GONE
        set(value) {
            field = value
            binding.thirdButton.visibility = value
        }

    @Icon.Iconography
    var firstIcon: Int? = null
        set(icon) {
            field = icon
            firstButtonVisibility = if (firstText == "" && firstIcon == null) {
                GONE
            } else {
                VISIBLE
            }
            binding.firstButton.icon = icon
        }

    @Icon.Iconography
    var secondIcon: Int? = null
        set(icon) {
            field = icon
            secondButtonVisibility = if (secondText == "" && secondIcon == null) {
                GONE
            } else {
                VISIBLE
            }
            binding.secondButton.icon = icon
        }

    @Icon.Iconography
    var thirdIcon: Int? = null
        set(icon) {
            field = icon
            thirdButtonVisibility = if (thirdText == "" && thirdIcon == null) {
                GONE
            } else {
                VISIBLE
            }
            binding.thirdButton.icon = icon
        }

    var firstText: String = ""
        set(value) {
            field = value
            firstButtonVisibility = if (firstText == "" && firstIcon == null) {
                GONE
            } else {
                VISIBLE

            }
            binding.firstButton.text = value
        }

    var secondText: String = ""
        set(value) {
            field = value
            secondButtonVisibility = if (secondText == "" && secondIcon == null) {
                GONE
            } else {
                VISIBLE
            }
            binding.secondButton.text = value
        }

    var thirdText: String = ""
        set(value) {
            field = value
            thirdButtonVisibility = if (thirdText == "" && thirdIcon == null) {
                GONE
            } else {
                VISIBLE
            }
            binding.thirdButton.text = value
        }

    companion object {
        @JvmStatic
        @BindingAdapter("title")
        fun setTitle(doubleTitleTopBar: DoubleTitleTopBar, title: String) {
            doubleTitleTopBar.title = title
        }

        @JvmStatic
        @BindingAdapter("subtitle")
        fun setSubTitle(doubleTitleTopBar: DoubleTitleTopBar, subtitle: String) {
            doubleTitleTopBar.subtitle = subtitle
        }

        @JvmStatic
        @BindingAdapter("firstIcon")
        fun setFirstIcon(doubleTitleTopBar: DoubleTitleTopBar, @Icon.Iconography icon: Int?) {
            doubleTitleTopBar.firstIcon = icon
        }

        @JvmStatic
        @BindingAdapter("secondIcon")
        fun setSecondIcon(doubleTitleTopBar: DoubleTitleTopBar, @Icon.Iconography icon: Int?) {
            doubleTitleTopBar.secondIcon = icon
        }

        @JvmStatic
        @BindingAdapter("thirdIcon")
        fun setThirdIcon(doubleTitleTopBar: DoubleTitleTopBar, @Icon.Iconography icon: Int?) {
            doubleTitleTopBar.thirdIcon = icon
        }

        @JvmStatic
        @BindingAdapter("firstText")
        fun setFirstText(doubleTitleTopBar: DoubleTitleTopBar, value: String) {
            doubleTitleTopBar.firstText = value
        }

        @JvmStatic
        @BindingAdapter("secondText")
        fun setSecondText(doubleTitleTopBar: DoubleTitleTopBar, value: String) {
            doubleTitleTopBar.secondText = value
        }

        @JvmStatic
        @BindingAdapter("thirdText")
        fun setThirdText(doubleTitleTopBar: DoubleTitleTopBar, value: String) {
            doubleTitleTopBar.thirdText = value
        }

        @JvmStatic
        @BindingAdapter("firstButtonVisibility")
        fun setFirstButtonVisibility(doubleTitleTopBar: DoubleTitleTopBar, state: Int) {
            doubleTitleTopBar.firstButtonVisibility = state
        }

        @JvmStatic
        @BindingAdapter("secondButtonVisibility")
        fun setSecondButtonVisibility(doubleTitleTopBar: DoubleTitleTopBar, state: Int) {
            doubleTitleTopBar.secondButtonVisibility = state
        }

        @JvmStatic
        @BindingAdapter("thirdButtonVisibility")
        fun setThirdButtonVisibility(doubleTitleTopBar: DoubleTitleTopBar, state: Int) {
            doubleTitleTopBar.thirdButtonVisibility = state
        }


        @JvmStatic
        @BindingAdapter("firstButtonClickListener")
        fun setFirstButtonClickListener(
            doubleTitleTopBar: DoubleTitleTopBar,
            onClickListener: OnClickListener
        ) {
            doubleTitleTopBar.firstButtonListener = onClickListener
        }

        @JvmStatic
        @BindingAdapter("secondButtonClickListener")
        fun setSecondButtonClickListener(
            doubleTitleTopBar: DoubleTitleTopBar,
            onClickListener: OnClickListener
        ) {
            doubleTitleTopBar.secondButtonListener = onClickListener
        }

        @JvmStatic
        @BindingAdapter("thirdButtonClickListener")
        fun setThirdButtonClickListener(
            doubleTitleTopBar: DoubleTitleTopBar,
            onClickListener: OnClickListener
        ) {
            doubleTitleTopBar.thirdButtonListener = onClickListener
        }

    }
}