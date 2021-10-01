package com.yourssu.design.system.atom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import com.yourssu.design.databinding.LayoutPickerBinding
import com.yourssu.design.undercarriage.custom.RawPicker
import java.lang.Exception
import java.lang.IndexOutOfBoundsException

class Picker : FrameLayout {
    constructor(context: Context) : super(context) {
        initView()
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initView()
    }


    private fun initView() {
        setFirstRow(listOf())
        setSecondRow(listOf())
        setThirdRow(listOf())
        setValueChangeListener()
    }

    interface OnValueChangeListener {
        fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String
        )
    }

    private val binding: LayoutPickerBinding by lazy {
        LayoutPickerBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private var firstRowList = listOf<String>()
    private var secondRowList = listOf<String>()
    private var thirdRowList = listOf<String>()

    private var firstSelectedValue = ""
    private var secondSelectedValue = ""
    private var thirdSelectedValue = ""

    var onValueChangeListener: OnValueChangeListener? = null

    fun getFirstValue() = firstSelectedValue
    fun getSecondValue() = secondSelectedValue
    fun getThirdValue() = thirdSelectedValue

    fun getTotalValue(): String {
        var totalValue = firstSelectedValue
        if (firstSelectedValue.isNotEmpty()) totalValue += " "
        totalValue += secondSelectedValue
        if (secondSelectedValue.isNotEmpty()) totalValue += " "
        totalValue += thirdSelectedValue
        return totalValue
    }

    private fun setValueChangeListener() {
        binding.firstRow.setOnValueChangedListener(object : RawPicker.OnValueChangeListener {
            override fun onValueChange(picker: RawPicker, oldVal: String, newVal: String) {
                firstSelectedValue = newVal
                notifyValueChanged()
            }
        })
        binding.secondRow.setOnValueChangedListener(object :RawPicker.OnValueChangeListener {
            override fun onValueChange(picker: RawPicker, oldVal: String, newVal: String) {
                secondSelectedValue = newVal
                notifyValueChanged()
            }
        })
        binding.thirdRow.setOnValueChangedListener(object :RawPicker.OnValueChangeListener {
            override fun onValueChange(picker: RawPicker, oldVal: String, newVal: String) {
                thirdSelectedValue = newVal
                notifyValueChanged()
            }
        })
    }

    private fun notifyValueChanged() {
        val totalValue = getTotalValue()
        onValueChangeListener?.onValueChange(
            firstSelectedValue,
            secondSelectedValue,
            thirdSelectedValue,
            totalValue
        )
    }

    fun setFirstRow(list: List<String>) {
        firstRowList = list
        if (list.size > 1) {
            setPickerSetting(binding.firstRow, list)
            firstSelectedValue = list.first()
        } else {
            binding.firstRow.visibility = View.GONE
        }
    }

    fun setSecondRow(list: List<String>) {
        secondRowList = list
        if (list.size > 1) {
            setPickerSetting(binding.secondRow, list)
            secondSelectedValue = list.first()
        } else {
            binding.secondRow.visibility = View.GONE
        }
    }

    fun setThirdRow(list: List<String>) {
        thirdRowList = list
        if (list.size > 1) {
            setPickerSetting(binding.thirdRow, list)
            thirdSelectedValue = list.first()
        } else {
            binding.thirdRow.visibility = View.GONE
        }
    }

    fun setFirstRowPosition(position: Int) {
        binding.firstRow.scrollTo(position)
    }

    fun setSecondRowPosition(position: Int) {
        binding.secondRow.scrollTo(position)
    }

    fun setThirdRowPosition(position: Int) {
        binding.thirdRow.scrollTo(position)
    }

    private fun setPickerSetting(numberPicker: RawPicker, list: List<String>) {
        numberPicker.run {
            visibility = View.VISIBLE
            setRawPickerItemCount(7)
            setWrapSelectorRawPicker(false)
            var max = ""
            list.forEach {
                if (it.length > max.length) {
                    max = it
                }
            }
            setAdapter(object : RawPicker.RawPickerAdapter() {
                override fun getValue(position: Int): String {
                    return try {
                        list[position]
                    } catch (e: ArrayIndexOutOfBoundsException) {
                        ""
                    } catch (e: IndexOutOfBoundsException) {
                        ""
                    } catch (e: Exception) {
                        ""
                    }
                }

                override fun getPosition(vale: String): Int {
                    list.forEachIndexed { index, s ->
                        if (vale == s) {
                            return index
                        }
                    }
                    return 0
                }

                override fun getTextWithMaximumLength(): String {
                    return max
                }

                override fun getMaxValidIndex(): Int {
                    return list.size - 1
                }

                override fun getMinValidIndex(): Int {
                    return 0
                }
            })
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("firstRow")
        fun setFirstRow(picker: Picker, list: List<String>) {
            picker.setFirstRow(list)
        }

        @JvmStatic
        @BindingAdapter("secondRow")
        fun setSecondRow(picker: Picker, list: List<String>) {
            picker.setSecondRow(list)
        }

        @JvmStatic
        @BindingAdapter("thirdRow")
        fun setThirdRow(picker: Picker, list: List<String>) {
            picker.setThirdRow(list)
        }
    }
}