package com.yourssu.design.system.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.yourssu.design.databinding.LayoutDoubleTitleTopBarBinding

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
            setTitleText()
        }

    var subtitle: String = ""
        set(subtitle) {
            field = subtitle
            setSubTitleText()
        }

    private fun setTitleText() {
        binding.title.text = title
    }

    private fun setSubTitleText() {
        binding.subTitle.text = subtitle
    }

    companion object {
        @JvmStatic
        @BindingAdapter("title")
        fun setTitle(doubleTitleTopBar: DoubleTitleTopBar, title: String) {
            doubleTitleTopBar.title = title
        }

        @BindingAdapter("subtitle")
        fun setSubTitle(doubleTitleTopBar: DoubleTitleTopBar, subtitle: String) {
            doubleTitleTopBar.subtitle = subtitle
        }
    }
}