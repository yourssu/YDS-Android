package com.yourssu.design.system.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.yourssu.design.databinding.LayoutSingleTitleTopBarBinding

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
            setTitleText()
        }

    private fun setTitleText() {
        binding.title.text = title
    }

    companion object {
        @JvmStatic
        @BindingAdapter("title")
        fun setTitle(singleTitleTopBar: SingleTitleTopBar, title: String) {
            singleTitleTopBar.title = title
        }
    }
}