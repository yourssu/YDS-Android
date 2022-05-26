package com.yourssu.design.system.atom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.yourssu.design.databinding.LayoutListToggleItemBinding

class ListToggleItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    var text: String = ""
        set(value){
            field = value
            setListToggleItemInfo()
        }

    override fun isSelected(): Boolean {
        return binding.listItemToggle.isSelected
    }

    override fun setSelected(selected: Boolean) {
        binding.listItemToggle.isSelected = selected
    }

    fun setOnSelectedListener(listener: Toggle.SelectedListener) {
        binding.listItemToggle.setOnSelectedListener(listener)
    }


    private fun setListToggleItemInfo() {
        binding.listItemText.text = text
    }

    private val binding : LayoutListToggleItemBinding by lazy {
        LayoutListToggleItemBinding.inflate(LayoutInflater.from(context), this, true)
    }

    companion object{

        @JvmStatic
        @BindingAdapter("android:text")
        fun setText(listToggleItem: ListToggleItem, text: String?){
            listToggleItem.text = text ?: ""
        }

        @JvmStatic
        @BindingAdapter("isSelected")
        fun setToggleSelected(listToggleItem: ListToggleItem, value: Boolean?){
            listToggleItem.isSelected = value ?: false
        }

        @JvmStatic
        @BindingAdapter("selectedListener")
        fun setToggleSelectedListener(listToggleItem: ListToggleItem, selectedListener: Toggle.SelectedListener){
            listToggleItem.setOnSelectedListener(selectedListener)
        }
    }


}