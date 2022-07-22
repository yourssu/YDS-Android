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

    var endRightButtonClickListener: OnClickListener? = null
        set(value) {
            binding.endRightButton.setOnClickListener(value)
        }

    var endLeftButtonClickListener: OnClickListener? = null
        set(value) {
            binding.endLeftButton.setOnClickListener(value)
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

    var endRightText: String = ""
        set(value) {
            field = value
            binding.endRightButton.text = value
        }

    var endLeftText: String = ""
        set(value) {
            field = value
            binding.endLeftButton.text = value
        }

    @Icon.Iconography
    var endRightIcon: Int? = null
        set(icon) {
            field = icon
            binding.endRightButton.icon = icon
        }

    @Icon.Iconography
    var endLeftIcon: Int? = null
        set(icon) {
            field = icon
            binding.endLeftButton.icon = icon
        }

    private fun setTitleText() {
        binding.title.text = title
    }

    fun setStartButtonEnabled(isEnabled: Boolean) {
        binding.startButton.isEnabled = isEnabled
    }

    fun setEndRightButtonEnabled(isEnabled: Boolean) {
        binding.endRightButton.isEnabled = isEnabled
    }

    fun setEndLeftButtonEnabled(isEnabled: Boolean) {
        binding.endLeftButton.isEnabled = isEnabled
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
        @BindingAdapter("endRightText")
        fun setEndRightText(topBar: TopBar, endRightText: String) {
            topBar.endRightText = endRightText
        }

        @JvmStatic
        @BindingAdapter("endLeftText")
        fun setEndLeftText(topBar: TopBar, endLeftText: String) {
            topBar.endLeftText = endLeftText
        }

        @JvmStatic
        @BindingAdapter("endRightIcon")
        fun setEndRightIcon(topBar: TopBar, @Icon.Iconography icon: Int?) {
            topBar.endRightIcon = icon
        }

        @JvmStatic
        @BindingAdapter("endLeftIcon")
        fun setEndLeftIcon(topBar: TopBar, @Icon.Iconography icon: Int?) {
            topBar.endLeftIcon = icon
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
        @BindingAdapter("onEndRightClick")
        fun setOnEndRightClickListener(topBar: TopBar, endRightClickListener: OnClickListener) {
            topBar.endRightButtonClickListener = endRightClickListener
        }

        @JvmStatic
        @BindingAdapter("onEndLeftIconClick")
        fun setOnEndLeftClickListener(topBar: TopBar, endLeftClickListener: OnClickListener) {
            topBar.endLeftButtonClickListener = endLeftClickListener
        }

        @JvmStatic
        @BindingAdapter("startButtonEnabled")
        fun setStartButtonEnabled(topBar: TopBar, isEnabled: Boolean) {
            topBar.setStartButtonEnabled(isEnabled)
        }

        @JvmStatic
        @BindingAdapter("endRightButtonEnabled")
        fun setEndRightButtonEnabled(topBar: TopBar, isEnabled: Boolean) {
            topBar.setEndRightButtonEnabled(isEnabled)
        }

        @JvmStatic
        @BindingAdapter("endLeftButtonEnabled")
        fun setEndLeftButtonEnabled(topBar: TopBar, isEnabled: Boolean) {
            topBar.setEndLeftButtonEnabled(isEnabled)
        }
    }
}