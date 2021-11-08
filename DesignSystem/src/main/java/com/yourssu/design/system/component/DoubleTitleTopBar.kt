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

    var firstButtonVisibility: Int = GONE
        set(value) {
            field = value
            binding.firstButton.visibility = value
            requestLayout()
        }

    var secondButtonVisibility: Int = GONE
        set(value) {
            field = value
            binding.secondButton.visibility = value
            requestLayout()
        }

    var thirdButtonVisibility: Int = GONE
        set(value) {
            field = value
            binding.thirdButton.visibility = value
            requestLayout()
        }

    companion object {
        const val VISIBLE = View.VISIBLE
        const val INVISIBLE = View.VISIBLE
        const val GONE = View.GONE


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