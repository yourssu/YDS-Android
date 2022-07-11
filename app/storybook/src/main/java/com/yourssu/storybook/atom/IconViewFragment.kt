package com.yourssu.storybook.atom

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yourssu.design.system.atom.IconView
import com.yourssu.design.system.atom.Picker
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.system.language.bottomSheet
import com.yourssu.design.system.language.picker
import com.yourssu.design.system.language.setLayout
import com.yourssu.design.system.language.text
import com.yourssu.design.undercarriage.size.dpToIntPx
import com.yourssu.storybook.BaseFragment
import com.yourssu.storybook.databinding.FragmentIconViewBinding

class IconViewFragment : BaseFragment() {

    lateinit var binding: FragmentIconViewBinding
    private val viewModel: IconViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance() = IconViewFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentIconViewBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initView()

        return binding.root
    }

    private val iconList = Icon.getList().map { Icon.getName(it) }
    private val sizeList = listOf("ExtraSmall", "Small", "Medium", "Large")

    private val onIconValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            viewModel.iconText.value = firstValue
            viewModel.icon.value = Icon.getValueByName(firstValue)
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
            viewModel.size.value =  when (firstValue) {
                "ExtraSmall" -> IconView.ExtraSmall
                "Small" -> IconView.Small
                "Medium" -> IconView.Medium
                "Large" -> IconView.Large
                else -> IconView.ExtraSmall
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.iconSelect.setOnClickListener {
            bottomSheet {
                text {
                    text = "icon"
                    typo = Typo.SubTitle2

                    setLayout(leftMarginPx = context.dpToIntPx(16f))
                }
                picker {
                    setFirstRow(iconList)
                    setFirstRowPosition(iconList.indexOf(viewModel.iconText.value))
                    this.onValueChangeListener = onIconValueChangeListener
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