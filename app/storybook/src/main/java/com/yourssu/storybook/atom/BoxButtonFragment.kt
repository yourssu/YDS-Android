package com.yourssu.storybook.atom

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yourssu.design.system.atom.BoxButton
import com.yourssu.design.system.atom.Picker
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.system.language.*
import com.yourssu.design.undercarriage.size.dpToIntPx
import com.yourssu.storybook.databinding.FragmentBoxButtonBinding

class BoxButtonFragment : Fragment() {

    lateinit var binding: FragmentBoxButtonBinding
    private val viewModel: BoxButtonViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance() = BoxButtonFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBoxButtonBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initView()

        return binding.root
    }

    private val roundingList = listOf("4", "8")
    private val sizeList = listOf("ExtraLarge", "Large", "Medium", "Small")
    private val typeList = listOf("FILLED", "TINTED", "LINE")
    private val iconList = Icon.getList().map { Icon.getName(it) }

    private val onRoundingValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            viewModel.roundingText.value = firstValue
            viewModel.rounding.value = firstValue.toInt()
        }
    }
    private val onSizeValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            viewModel.sizeText.value = firstValue
            viewModel.size.value = when (firstValue) {
                "ExtraLarge" -> BoxButton.ExtraLarge
                "Large" -> BoxButton.Large
                "Medium" -> BoxButton.Medium
                "Small" -> BoxButton.Small
                else -> BoxButton.ExtraLarge
            }
        }
    }

    private val onTypeValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            viewModel.typeText.value = firstValue
            viewModel.type.value = when (firstValue) {
                "FILLED" -> BoxButton.FILLED
                "TINTED" -> BoxButton.TINTED
                "LINE" -> BoxButton.LINE
                else -> BoxButton.FILLED
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
        binding.roundingSelect.setOnClickListener {
            bottomSheet {
                text {
                    text = "rounding"
                    typo = Typo.SubTitle2

                    setLayout(leftMarginPx = context.dpToIntPx(16f))
                }
                picker {
                    setFirstRow(roundingList)
                    setFirstRowPosition(roundingList.indexOf(viewModel.roundingText.value))
                    this.onValueChangeListener = onRoundingValueChangeListener
                }
            }
        }
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
        binding.typeSelect.setOnClickListener {
            bottomSheet {
                text {
                    text = "type"
                    typo = Typo.SubTitle2

                    setLayout(leftMarginPx = context.dpToIntPx(16f))
                }
                picker {
                    setFirstRow(typeList)
                    setFirstRowPosition(typeList.indexOf(viewModel.typeText.value))
                    this.onValueChangeListener = onTypeValueChangeListener
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