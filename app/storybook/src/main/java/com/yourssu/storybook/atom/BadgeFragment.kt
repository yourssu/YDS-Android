package com.yourssu.storybook.atom

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yourssu.design.system.atom.Picker
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.system.foundation.ItemColor
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.system.language.*
import com.yourssu.design.undercarriage.size.dpToIntPx
import com.yourssu.storybook.databinding.FragmentBadgeBinding

class BadgeFragment : Fragment() {

    lateinit var binding: FragmentBadgeBinding
    private val viewModel: BadgeViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance() = BadgeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBadgeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initView()

        return binding.root
    }

    private val colorList = ItemColor.values().map { it.toString() }
    private val iconList = Icon.getList().map { Icon.getName(it) }

    private val onColorValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            viewModel.colorText.value = firstValue
            viewModel.color.value = ItemColor.valueOf(firstValue)
        }
    }
    private val onIconValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            viewModel.iconText.value = firstValue
            viewModel.icon.value = Icon.getValueByName(firstValue)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.colorSelect.setOnClickListener {
            bottomSheet {
                text {
                    text = "color"
                    typo = Typo.SubTitle2

                    setLayout(leftMarginPx = context.dpToIntPx(16f))
                }
                picker {
                    setFirstRow(colorList)
                    setFirstRowPosition(colorList.indexOf(viewModel.colorText.value))
                    this.onValueChangeListener = onColorValueChangeListener
                }
            }
        }
        binding.iconSelect.setOnClickListener {
            bottomSheet {
                text {
                    text = "icon"
                    typo = Typo.SubTitle2

                    setLayout(leftMarginPx = context.dpToIntPx(16f))
                }
                picker {
                    setFirstRow(iconList)
                    setFirstRowPosition(iconList.indexOf(viewModel.iconText.value))
                    this.onValueChangeListener = onIconValueChangeListener
                }
            }
        }
    }
}