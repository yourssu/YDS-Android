package com.yourssu.design.system.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.databinding.BindingAdapter
import com.yourssu.design.databinding.LayoutListSubheaderBinding
import com.yourssu.design.undercarriage.size.dpToIntPx

class List @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayoutCompat(context, attrs) {

    var subHeader: String = ""
        set(value) {
            field = value
            setListInfo()
        }

    init {
        orientation = VERTICAL
        updatePadding(
            top = context.dpToIntPx(LIST_VERTICAL_PADDING),
            bottom = context.dpToIntPx(LIST_VERTICAL_PADDING)
        )
    }

    private val subHeaderBinding: LayoutListSubheaderBinding =
        LayoutListSubheaderBinding.inflate(LayoutInflater.from(context), this, true)

    private fun setListInfo() {
        if (subHeader == "") {
            subHeaderBinding.root.isVisible = false
            return
        }
        subHeaderBinding.root.isVisible = true
        subHeaderBinding.textSubHeader.text = subHeader

    }

    companion object {
        private const val LIST_VERTICAL_PADDING = 8f

        @JvmStatic
        @BindingAdapter("subHeader")
        fun setSubHeader(list: List, text: String?) {
            list.subHeader = text ?: ""
        }
    }
}