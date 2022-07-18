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
import com.yourssu.storybook.databinding.FragmentTopBarBinding

class TopBarFragment : BaseFragment() {
    private lateinit var binding: FragmentTopBarBinding
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

    private val onEndRightIconValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            viewModel.endRightIconText.value = firstValue
            viewModel.endRightIcon.value = Icon.getValueByName(firstValue)
        }
    }

    private val onEndLeftIconValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            viewModel.endLeftIconText.value = firstValue
            viewModel.endLeftIcon.value = Icon.getValueByName(firstValue)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTopBarBinding.inflate(inflater,  container, false)
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

        binding.endRightIconSelect.setOnClickListener {
            bottomSheet {
                text {
                    text = "endRightIcon"
                    typo = Typo.SubTitle2

                    setLayout(leftMarginPx = context.dpToIntPx(16f))
                }
                picker {
                    setFirstRow(iconList)
                    setFirstRowPosition(iconList.indexOf(viewModel.startIconText.value))
                    this.onValueChangeListener = onEndRightIconValueChangeListener
                }
            }
        }

        binding.endLeftIconSelect.setOnClickListener {
            bottomSheet {
                text {
                    text = "endLeftIcon"
                    typo = Typo.SubTitle2

                    setLayout(leftMarginPx = context.dpToIntPx(16f))
                }
                picker {
                    setFirstRow(iconList)
                    setFirstRowPosition(iconList.indexOf(viewModel.startIconText.value))
                    this.onValueChangeListener = onEndLeftIconValueChangeListener
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