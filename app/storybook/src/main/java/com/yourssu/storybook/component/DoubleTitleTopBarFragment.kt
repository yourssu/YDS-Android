package com.yourssu.storybook.component

import android.os.Bundle
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
import com.yourssu.storybook.BaseFragment
import com.yourssu.storybook.databinding.FragmentDoubleTitleTopBarBinding

class DoubleTitleTopBarFragment : BaseFragment() {
    private lateinit var binding: FragmentDoubleTitleTopBarBinding
    private val viewModel: DoubleTitleTopBarViewModel by viewModels()
    private val iconList = Icon.getList().map { Icon.getName(it) }

    private val onFirstIconValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            viewModel.firstIconText.value = firstValue
            viewModel.firstIcon.value = Icon.getValueByName(firstValue)
        }
    }

    private val onSecondIconValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            viewModel.secondIconText.value = firstValue
            viewModel.secondIcon.value = Icon.getValueByName(firstValue)
        }
    }

    private val onThirdIconValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            viewModel.thirdIconText.value = firstValue
            viewModel.thirdIcon.value = Icon.getValueByName(firstValue)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDoubleTitleTopBarBinding.inflate(inflater, container, false)
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

    override fun onPortrait() {
        super.onPortrait()
        viewModel.isLandscape.value = false
    }

    override fun onLandscape() {
        super.onLandscape()
        viewModel.isLandscape.value = true
    }
}