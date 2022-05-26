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
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.system.language.bottomSheet
import com.yourssu.design.system.language.picker
import com.yourssu.design.system.language.setLayout
import com.yourssu.design.system.language.text
import com.yourssu.design.undercarriage.size.dpToIntPx
import com.yourssu.storybook.databinding.FragmentListItemBinding

class ListItemFragment : Fragment() {

    lateinit var binding: FragmentListItemBinding
    private val viewModel: ListItemViewModel by viewModels()
    private val iconList = Icon.getList().map { Icon.getName(it) }

    companion object {
        @JvmStatic
        fun newInstance() = ListItemFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListItemBinding.inflate(LayoutInflater.from(context), container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initView()
        return binding.root
    }

    private val onLeftIconValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            viewModel.leftIconText.value = firstValue
            viewModel.leftIcon.value = Icon.getValueByName(firstValue)
        }
    }

    private val onRightIconValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            viewModel.rightIconText.value = firstValue
            viewModel.rightIcon.value = Icon.getValueByName(firstValue)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.leftIconSelect.setOnClickListener {
            bottomSheet {
                text {
                    text = "Left Icon"
                    typo = Typo.SubTitle2

                    setLayout(leftMarginPx = context.dpToIntPx(16f))
                }
                picker {
                    setFirstRow(iconList)
                    setFirstRowPosition(iconList.indexOf(viewModel.leftIconText.value))
                    this.onValueChangeListener = onLeftIconValueChangeListener
                }
            }
        }
        binding.rightIconSelect.setOnClickListener {
            bottomSheet {
                text {
                    text = "Right Icon"
                    typo = Typo.SubTitle2

                    setLayout(leftMarginPx = context.dpToIntPx(16f))
                }
                picker {
                    setFirstRow(iconList)
                    setFirstRowPosition(iconList.indexOf(viewModel.rightIconText.value))
                    this.onValueChangeListener = onRightIconValueChangeListener
                }
            }
        }
    }

}