package com.yourssu.storybook.component

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
import com.yourssu.storybook.databinding.FragmentSingleTitleTopBarBinding

class SingleTitleTopBarFragment : Fragment() {
    private lateinit var binding: FragmentSingleTitleTopBarBinding
    private val viewModel: SingleTitleTopBarViewModel by viewModels()
    private val iconList = Icon.getList().map { Icon.getName(it) }.toMutableList()

    init {
        iconList.add(0, "No Icon")
    }

    private val onFirstIconValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            if (firstValue == "No Icon") {
                viewModel.firstIconText.value = firstValue
                viewModel.firstIcon.value = null
            }
            else {
                viewModel.firstIconText.value = firstValue
                viewModel.firstIcon.value = Icon.getValueByName(firstValue)
            }
        }
    }

    private val onSecondIconValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            if (firstValue == "No Icon") {
                viewModel.secondIconText.value = firstValue
                viewModel.secondIcon.value = null
            }
            else {
                viewModel.secondIconText.value = firstValue
                viewModel.secondIcon.value = Icon.getValueByName(firstValue)
            }
        }
    }

    private val onThirdIconValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            if (firstValue == "No Icon") {
                viewModel.thirdIconText.value = firstValue
                viewModel.thirdIcon.value = null
            }
            else {
                viewModel.thirdIconText.value = firstValue
                viewModel.thirdIcon.value = Icon.getValueByName(firstValue)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSingleTitleTopBarBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        initView()
        return binding.root
    }

    private fun initView() {
        binding.firstIconSelect.setOnClickListener {
            bottomSheet {
                text {
                    text = "First Icon"
                    typo = Typo.SubTitle2

                    setLayout(leftMarginPx = context.dpToIntPx(16f))
                }
                picker {
                    setFirstRow(iconList)
                    setFirstRowPosition(iconList.indexOf(viewModel.firstIconText.value))
                    this.onValueChangeListener = onFirstIconValueChangeListener
                }
            }
        }

        binding.secondIconSelect.setOnClickListener {
            bottomSheet {
                text {
                    text = "Second Icon"
                    typo = Typo.SubTitle2

                    setLayout(leftMarginPx = context.dpToIntPx(16f))
                }
                picker {
                    setFirstRow(iconList)
                    setFirstRowPosition(iconList.indexOf(viewModel.secondIconText.value))
                    this.onValueChangeListener = onSecondIconValueChangeListener
                }
            }
        }

        binding.thirdIconSelect.setOnClickListener {
            bottomSheet {
                text {
                    text = "Third Icon"
                    typo = Typo.SubTitle2

                    setLayout(leftMarginPx = context.dpToIntPx(16f))
                }
                picker {
                    setFirstRow(iconList)
                    setFirstRowPosition(iconList.indexOf(viewModel.thirdIconText.value))
                    this.onValueChangeListener = onThirdIconValueChangeListener
                }
            }
        }
    }
}