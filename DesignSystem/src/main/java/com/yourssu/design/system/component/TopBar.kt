package com.yourssu.design.system.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.yourssu.design.databinding.LayoutTopbarBinding
import com.yourssu.design.system.foundation.Icon

class TopBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: LayoutTopbarBinding =
        LayoutTopbarBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String = ""
        set(title) {
            field = title
            setTitleText()
        }

    var startIconButtonClickListener: OnClickListener? = null
        set(value) {
            binding.startIconButton.setOnClickListener(value)
        }

    var startTextButtonClickListener: OnClickListener? = null
        set(value) {
            binding.startTextButton.setOnClickListener(value)
        }

    var endFirstIconButtonClickListener: OnClickListener? = null
        set(value) {
            binding.endFirstIconButton.setOnClickListener(value)
        }

    var endSecondIconButtonClickListener: OnClickListener? = null
        set(value) {
            binding.endSecondIconButton.setOnClickListener(value)
        }

    var endTextButtonClickListener: OnClickListener? = null
        set(value) {
            binding.endTextButton.setOnClickListener(value)
        }

    var startText: String = ""
        set(value) {
            field = value
            binding.startTextButton.text = value
        }

    @Icon.Iconography
    var startIcon: Int? = null
        set(icon) {
            field = icon
            binding.startIconButton.icon = icon
        }

    var endText: String = ""
        set(value) {
            field = value
            binding.endTextButton.text = value
        }

    @Icon.Iconography
    var endFirstIcon: Int? = null
        set(icon) {
            field = icon
            binding.endFirstIconButton.icon = icon
        }

    @Icon.Iconography
    var endSecondIcon: Int? = null
        set(icon) {
            field = icon
            binding.endSecondIconButton.icon = icon
        }

    private fun setTitleText() {
        binding.title.text = title
    }

    companion object {
        @JvmStatic
        @BindingAdapter("startIcon")
        fun setStartIcon(topBar: TopBar, @Icon.Iconography icon: Int?) {
            topBar.startIcon = icon
        }

        @JvmStatic
        @BindingAdapter("startText")
        fun setStartText(topBar: TopBar, startText: String) {
            topBar.startText = startText
        }

        @JvmStatic
        @BindingAdapter("endText")
        fun setEndText(topBar: TopBar, endText: String) {
            topBar.endText = endText
        }

        @JvmStatic
        @BindingAdapter("endFirstIcon")
        fun setEndFirstIcon(topBar: TopBar, @Icon.Iconography icon: Int?) {
            topBar.endFirstIcon = icon
        }

        @JvmStatic
        @BindingAdapter("endSecondIcon")
        fun setEndSecondIcon(topBar: TopBar, @Icon.Iconography icon: Int?) {
            topBar.endSecondIcon = icon
        }

        @JvmStatic
        @BindingAdapter("title")
        fun setTitle(topBar: TopBar, title: String) {
            topBar.title = title
        }

        @JvmStatic
        @BindingAdapter("onStartIconClick")
        fun setStartIconClickListener(topBar: TopBar, onClickListener:OnClickListener) {
            topBar.startIconButtonClickListener = onClickListener
        }

        @JvmStatic
        @BindingAdapter("onStartTextClick")
        fun setStartTextClickListener(topBar: TopBar, startTextClickListener: OnClickListener) {
            topBar.startTextButtonClickListener = startTextClickListener
        }

        @JvmStatic
        @BindingAdapter("onEndTextClick")
        fun setOnEndTextClickListener(topBar: TopBar, endTextClickListener: OnClickListener) {
            topBar.endTextButtonClickListener = endTextClickListener
        }

        @JvmStatic
        @BindingAdapter("onEndFirstIconClick")
        fun setOnEndFirstIconClickListener(topBar: TopBar, endFirstIconClickListener: OnClickListener) {
            topBar.endFirstIconButtonClickListener = endFirstIconClickListener
        }

        @JvmStatic
        @BindingAdapter("onEndSecondIconClick")
        fun setOnEndSecondIconClickListener(topBar: TopBar, endSecondIconClickListener: OnClickListener) {
            topBar.endSecondIconButtonClickListener = endSecondIconClickListener
        }
    }
}