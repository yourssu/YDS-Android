package com.yourssu.storybook.atom

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yourssu.design.system.atom.Picker
import com.yourssu.design.system.atom.PlainButton
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.system.language.*
import com.yourssu.design.undercarriage.size.dpToIntPx
import com.yourssu.storybook.databinding.FragmentPlainButtonBinding

class PlainButtonFragment : Fragment() {

    lateinit var binding: FragmentPlainButtonBinding
    private val viewModel: PlainButtonViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance() = PlainButtonFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPlainButtonBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initView()

        return binding.root
    }

    private val sizeList = listOf("SMALL", "MEDIUM", "LARGE")
    private val iconList = Icon.getList().map { Icon.getName(it) }

    private val onSizeValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            viewModel.sizeText.value = firstValue
            viewModel.size.value = when (firstValue) {
                "SMALL" -> PlainButton.SMALL
                "MEDIUM" -> PlainButton.MEDIUM
                "LARGE" -> PlainButton.LARGE
                else -> PlainButton.SMALL
            }
        }
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
        binding.sizeSelect.setOnClickListener {
            bottomSheet {
                text {
                    text = "size"
                    typo = Typo.SubTitle2

                    setLayout(leftMarginPx = context.dpToIntPx(16f))
                }
                picker {
                    setFirstRow(sizeList)
                    setFirstRowPosition(sizeList.indexOf(viewModel.sizeText.value))
                    this.onValueChangeListener = onSizeValueChangeListener
                }
            }
        }
        binding.leftIconSelect.setOnClickListener {
            bottomSheet {
                text {
                    text = "leftIcon"
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
                    text = "rightIcon"
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