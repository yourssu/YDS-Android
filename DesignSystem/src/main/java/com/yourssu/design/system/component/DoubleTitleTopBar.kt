package com.yourssu.design.system.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.yourssu.design.databinding.LayoutDoubleTitleTopBarBinding
import com.yourssu.design.system.atom.Badge
import com.yourssu.design.system.foundation.Icon

class DoubleTitleTopBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: LayoutDoubleTitleTopBarBinding =
        LayoutDoubleTitleTopBarBinding.inflate(LayoutInflater.from(context), this, true)

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

    @Icon.Iconography
    var firstIcon: Int? = null
        set(icon) {
            field = icon
            binding.firstButton.icon = icon
        }

    @Icon.Iconography
    var secondIcon: Int? = null
        set(icon) {
            field = icon
            binding.secondButton.icon = icon
        }

    @Icon.Iconography
    var thirdIcon: Int? = null
        set(icon) {
            field = icon
            binding.thirdButton.icon = icon
        }

    var firstText: String = ""
        set(value) {
            field = value
            binding.firstButton.text = value
        }

    var secondText: String = ""
        set(value) {
            field = value
            binding.secondButton.text = value
        }

    var thirdText: String = ""
        set(value) {
            field = value
            binding.thirdButton.text = value
        }

    var firstDisabled: Boolean = false
        set(value) {
            field = value
            binding.firstButton.isDisabled = value
        }

    var secondDisabled: Boolean = false
        set(value) {
            field = value
            binding.secondButton.isDisabled = value
        }

    var thirdDisabled: Boolean = false
        set(value) {
            field = value
            binding.thirdButton.isDisabled = value
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
        @BindingAdapter("firstButtonClickListener")
        fun setFirstButtonClickListener(
            doubleTitleTopBar: DoubleTitleTopBar,
            onClickListener: OnClickListener,
        ) {
            doubleTitleTopBar.firstButtonListener = onClickListener
        }

        @JvmStatic
        @BindingAdapter("secondButtonClickListener")
        fun setSecondButtonClickListener(
            doubleTitleTopBar: DoubleTitleTopBar,
            onClickListener: OnClickListener,
        ) {
            doubleTitleTopBar.secondButtonListener = onClickListener
        }

        @JvmStatic
        @BindingAdapter("thirdButtonClickListener")
        fun setThirdButtonClickListener(
            doubleTitleTopBar: DoubleTitleTopBar,
            onClickListener: OnClickListener,
        ) {
            doubleTitleTopBar.thirdButtonListener = onClickListener
        }

        @JvmStatic
        @BindingAdapter("firstDisabled")
        fun setFirstDisabled(doubleTitleTopBar: DoubleTitleTopBar, isDisabled: Boolean) {
            doubleTitleTopBar.firstDisabled = isDisabled
        }

        @JvmStatic
        @BindingAdapter("secondDisabled")
        fun setSecondDisabled(doubleTitleTopBar: DoubleTitleTopBar, isDisabled: Boolean) {
            doubleTitleTopBar.secondDisabled = isDisabled
        }

        @JvmStatic
        @BindingAdapter("thirdDisabled")
        fun setThirdDisabled(doubleTitleTopBar: DoubleTitleTopBar, isDisabled: Boolean) {
            doubleTitleTopBar.thirdDisabled = isDisabled
        }

    }
}