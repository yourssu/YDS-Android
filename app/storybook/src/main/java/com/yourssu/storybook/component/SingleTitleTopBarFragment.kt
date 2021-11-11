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
import com.yourssu.storybook.databinding.FragmentTopBarBinding

class SingleTitleTopBarFragment : Fragment() {
    private lateinit var binding: FragmentSingleTitleTopBarBinding
    private val viewModel: TopBarViewModel by viewModels()
    private val iconList = Icon.getList().map { Icon.getName(it) }

    private val onStartIconValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            viewModel.startIconText.value = firstValue
            viewModel.startIcon.value = Icon.getValueByName(firstValue)
        }
    }

    private val onEndFirstIconValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            viewModel.endFirstIconText.value = firstValue
            viewModel.endFirstIcon.value = Icon.getValueByName(firstValue)
        }
    }

    private val onEndSecondIconValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            viewModel.endSecondIconText.value = firstValue
            viewModel.endSecondIcon.value = Icon.getValueByName(firstValue)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSingleTitleTopBarBinding.inflate(inflater,  container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        initView()
        return binding.root
    }

    private fun initView() {
        binding.startIconSelect.setOnClickListener {
            bottomSheet {
                text {
                    text = "startIcon"
                    typo = Typo.SubTitle2

                    setLayout(leftMarginPx = context.dpToIntPx(16f))
                }
                picker {
                    setFirstRow(iconList)
                    setFirstRowPosition(iconList.indexOf(viewModel.startIconText.value))
                    this.onValueChangeListener = onStartIconValueChangeListener
                }
            }
        }

        binding.endFirstIconSelect.setOnClickListener {
            bottomSheet {
                text {
                    text = "endFirstIcon"
                    typo = Typo.SubTitle2

                    setLayout(leftMarginPx = context.dpToIntPx(16f))
                }
                picker {
                    setFirstRow(iconList)
                    setFirstRowPosition(iconList.indexOf(viewModel.startIconText.value))
                    this.onValueChangeListener = onEndFirstIconValueChangeListener
                }
            }
        }

        binding.endSecondIconSelect.setOnClickListener {
            bottomSheet {
                text {
                    text = "endSecondIcon"
                    typo = Typo.SubTitle2

                    setLayout(leftMarginPx = context.dpToIntPx(16f))
                }
                picker {
                    setFirstRow(iconList)
                    setFirstRowPosition(iconList.indexOf(viewModel.startIconText.value))
                    this.onValueChangeListener = onEndSecondIconValueChangeListener
                }
            }
        }
    }
}