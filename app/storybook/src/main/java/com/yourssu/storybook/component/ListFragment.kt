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
import com.yourssu.storybook.databinding.FragmentListBinding

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private val viewModel: ListViewModel by viewModels()
    private lateinit var binding: FragmentListBinding
    private val iconList = Icon.getList().map { Icon.getName(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(LayoutInflater.from(context), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
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
            viewModel.listItemLeftIconText.value = firstValue
            viewModel.listItemLeftIcon.value = Icon.getValueByName(firstValue)
        }
    }

    private val onRightIconValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            viewModel.listItemRightIconText.value = firstValue
            viewModel.listItemRightIcon.value = Icon.getValueByName(firstValue)
        }
    }

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
                    setFirstRowPosition(iconList.indexOf(viewModel.listItemLeftIconText.value))
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
                    setFirstRowPosition(iconList.indexOf(viewModel.listItemRightIconText.value))
                    this.onValueChangeListener = onRightIconValueChangeListener
                }
            }
        }
    }


}