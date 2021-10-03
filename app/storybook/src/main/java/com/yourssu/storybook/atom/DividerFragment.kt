package com.yourssu.storybook.atom

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yourssu.design.system.atom.Divider
import com.yourssu.design.system.atom.Picker
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.system.foundation.ItemColor
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.system.language.*
import com.yourssu.design.undercarriage.size.dpToIntPx
import com.yourssu.storybook.databinding.FragmentDividerBinding

class DividerFragment : Fragment() {

    lateinit var binding: FragmentDividerBinding
    private val viewModel: DividerViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance() = DividerFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDividerBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initView()

        return binding.root
    }

    private val directionList = listOf("HORIZONTAL", "VERTICAL")
    private val thicknessList = listOf("THIN", "THICK")

    private val onDirectionValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            viewModel.directionText.value = firstValue
            viewModel.direction.value = when (firstValue) {
                "HORIZONTAL" -> Divider.HORIZONTAL
                "VERTICAL" -> Divider.VERTICAL
                else -> Divider.HORIZONTAL
            }
        }
    }
    private val onThicknessValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            viewModel.thicknessText.value = firstValue
            viewModel.thickness.value =  when (firstValue) {
                "THIN" -> Divider.THIN
                "THICK" -> Divider.THICK
                else -> Divider.THIN
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.directionSelect.setOnClickListener {
            bottomSheet {
                text {
                    text = "direction"
                    typo = Typo.SubTitle2

                    setLayout(leftMarginPx = context.dpToIntPx(16f))
                }
                picker {
                    setFirstRow(directionList)
                    setFirstRowPosition(directionList.indexOf(viewModel.directionText.value))
                    this.onValueChangeListener = onDirectionValueChangeListener
                }
            }
        }
        binding.thicknessSelect.setOnClickListener {
            bottomSheet {
                text {
                    text = "thickness"
                    typo = Typo.SubTitle2

                    setLayout(leftMarginPx = context.dpToIntPx(16f))
                }
                picker {
                    setFirstRow(thicknessList)
                    setFirstRowPosition(thicknessList.indexOf(viewModel.thicknessText.value))
                    this.onValueChangeListener = onThicknessValueChangeListener
                }
            }
        }
    }
}