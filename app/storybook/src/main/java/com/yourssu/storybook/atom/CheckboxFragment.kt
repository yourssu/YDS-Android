package com.yourssu.storybook.atom

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yourssu.design.system.atom.Checkbox
import com.yourssu.design.system.atom.Picker
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.system.language.*
import com.yourssu.design.undercarriage.size.dpToIntPx
import com.yourssu.storybook.databinding.FragmentCheckboxBinding

class CheckboxFragment : Fragment() {

    lateinit var binding: FragmentCheckboxBinding
    private val viewModel: CheckboxViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance() = CheckboxFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCheckboxBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initView()

        return binding.root
    }

    private val sizeList = listOf("SMALL", "MEDIUM", "LARGE")

    private val onSizeValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            viewModel.sizeText.value = firstValue
            viewModel.size.value = when (firstValue) {
                "SMALL" -> Checkbox.SMALL
                "MEDIUM" -> Checkbox.MEDIUM
                "LARGE" -> Checkbox.LARGE
                else -> Checkbox.SMALL
            }
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
    }
}