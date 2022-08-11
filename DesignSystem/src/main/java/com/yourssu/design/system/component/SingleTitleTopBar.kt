package com.yourssu.design.system.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.yourssu.design.databinding.LayoutSingleTitleTopBarBinding
import com.yourssu.design.system.foundation.Icon

class SingleTitleTopBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: LayoutSingleTitleTopBarBinding =
        LayoutSingleTitleTopBarBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String = ""
        set(title) {
            field = title
            binding.title.text = title
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
        fun setTitle(singleTitleTopBar: SingleTitleTopBar, title: String) {
            singleTitleTopBar.title = title
        }

        @JvmStatic
        @BindingAdapter("firstIcon")
        fun setFirstIcon(singleTitleTopBar: SingleTitleTopBar, @Icon.Iconography icon: Int?) {
            singleTitleTopBar.firstIcon = icon
        }

        @JvmStatic
        @BindingAdapter("secondIcon")
        fun setSecondIcon(singleTitleTopBar: SingleTitleTopBar, @Icon.Iconography icon: Int?) {
            singleTitleTopBar.secondIcon = icon
        }

        @JvmStatic
        @BindingAdapter("thirdIcon")
        fun setThirdIcon(singleTitleTopBar: SingleTitleTopBar, @Icon.Iconography icon: Int?) {
            singleTitleTopBar.thirdIcon = icon
        }

        @JvmStatic
        @BindingAdapter("firstText")
        fun setFirstText(singleTitleTopBar: SingleTitleTopBar, value: String) {
            singleTitleTopBar.firstText = value
        }

        @JvmStatic
        @BindingAdapter("secondText")
        fun setSecondText(singleTitleTopBar: SingleTitleTopBar, value: String) {
            singleTitleTopBar.secondText = value
        }

        @JvmStatic
        @BindingAdapter("thirdText")
        fun setThirdText(singleTitleTopBar: SingleTitleTopBar, value: String) {
            singleTitleTopBar.thirdText = value
        }

        @JvmStatic
        @BindingAdapter("firstButtonClickListener")
        fun setFirstButtonClickListener(
            singleTitleTopBar: SingleTitleTopBar,
            onClickListener: OnClickListener
        ) {
            singleTitleTopBar.firstButtonListener = onClickListener
        }

        @JvmStatic
        @BindingAdapter("secondButtonClickListener")
        fun setSecondButtonClickListener(
            singleTitleTopBar: SingleTitleTopBar,
            onClickListener: OnClickListener
        ) {
            singleTitleTopBar.secondButtonListener = onClickListener
        }

        @JvmStatic
        @BindingAdapter("thirdButtonClickListener")
        fun setThirdButtonClickListener(
            singleTitleTopBar: SingleTitleTopBar,
            onClickListener: OnClickListener
        ) {
            singleTitleTopBar.thirdButtonListener = onClickListener
        }

        @JvmStatic
        @BindingAdapter("firstDisabled")
        fun setFirstDisabled(singleTitleTopBar: SingleTitleTopBar, isDisabled: Boolean) {
            singleTitleTopBar.firstDisabled = isDisabled
        }

        @JvmStatic
        @BindingAdapter("secondDisabled")
        fun setSecondDisabled(singleTitleTopBar: SingleTitleTopBar, isDisabled: Boolean) {
            singleTitleTopBar.secondDisabled = isDisabled
        }

        @JvmStatic
        @BindingAdapter("thirdDisabled")
        fun setThirdDisabled(singleTitleTopBar: SingleTitleTopBar, isDisabled: Boolean) {
            singleTitleTopBar.thirdDisabled = isDisabled
        }
    }
}