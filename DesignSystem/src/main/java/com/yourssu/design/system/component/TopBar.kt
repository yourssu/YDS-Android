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

    var startButtonClickListener: OnClickListener? = null
        set(value) {
            binding.startButton.setOnClickListener(value)
        }

    var endFirstButtonClickListener: OnClickListener? = null
        set(value) {
            binding.endFirstButton.setOnClickListener(value)
        }

    var endSecondButtonClickListener: OnClickListener? = null
        set(value) {
            binding.endSecondButton.setOnClickListener(value)
        }

    var startText: String = ""
        set(value) {
            field = value
            binding.startButton.text = value
        }

    @Icon.Iconography
    var startIcon: Int? = null
        set(icon) {
            field = icon
            binding.startButton.icon = icon
        }

    var endFirstText: String = ""
        set(value) {
            field = value
            binding.endFirstButton.text = value
        }

    var endSecondText: String = ""
        set(value) {
            field = value
            binding.endSecondButton.text = value
        }

    @Icon.Iconography
    var endFirstIcon: Int? = null
        set(icon) {
            field = icon
            binding.endFirstButton.icon = icon
        }

    @Icon.Iconography
    var endSecondIcon: Int? = null
        set(icon) {
            field = icon
            binding.endSecondButton.icon = icon
        }

    private fun setTitleText() {
        binding.title.text = title
    }

    private fun setStartButtonEnabled(isEnabled: Boolean) {
        binding.startButton.isEnabled = isEnabled
    }

    private fun setEndFirstButtonEnabled(isEnabled: Boolean) {
        binding.endFirstButton.isEnabled = isEnabled
    }

    private fun setEndSecondButtonEnabled(isEnabled: Boolean) {
        binding.endSecondButton.isEnabled = isEnabled
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
        @BindingAdapter("endFirstText")
        fun setEndFirstText(topBar: TopBar, endFirstText: String) {
            topBar.endFirstText = endFirstText
        }

        @JvmStatic
        @BindingAdapter("endSecondText")
        fun setEndSecondText(topBar: TopBar, endSecondText: String) {
            topBar.endSecondText = endSecondText
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
        fun setStartIconClickListener(topBar: TopBar, onClickListener: OnClickListener) {
            topBar.startButtonClickListener = onClickListener
        }

        @JvmStatic
        @BindingAdapter("onEndFirstClick")
        fun setOnEndFirstClickListener(topBar: TopBar, endFirstClickListener: OnClickListener) {
            topBar.endFirstButtonClickListener = endFirstClickListener
        }

        @JvmStatic
        @BindingAdapter("onEndSecondIconClick")
        fun setOnEndSecondClickListener(topBar: TopBar, endSecondClickListener: OnClickListener) {
            topBar.endSecondButtonClickListener = endSecondClickListener
        }

        @JvmStatic
        @BindingAdapter("startButtonEnabled")
        fun setStartButtonEnabled(topBar: TopBar, isEnabled: Boolean) {
            topBar.setStartButtonEnabled(isEnabled)
        }

        @JvmStatic
        @BindingAdapter("endFirstButtonEnabled")
        fun setEndFirstButtonEnabled(topBar: TopBar, isEnabled: Boolean) {
            topBar.setEndFirstButtonEnabled(isEnabled)
        }

        @JvmStatic
        @BindingAdapter("endSecondButtonEnabled")
        fun setEndSecondButtonEnabled(topBar: TopBar, isEnabled: Boolean) {
            topBar.setEndSecondButtonEnabled(isEnabled)
        }
    }
}