package com.yourssu.storybook.atom

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yourssu.design.system.atom.Picker
import com.yourssu.design.system.atom.ProfileImageView
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.system.language.*
import com.yourssu.design.undercarriage.size.dpToIntPx
import com.yourssu.storybook.R
import com.yourssu.storybook.databinding.FragmentProfileImageViewBinding

class ProfileImageViewFragment : Fragment() {

    lateinit var binding: FragmentProfileImageViewBinding
    private val viewModel: ProfileImageViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance() = ProfileImageViewFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileImageViewBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initView()

        return binding.root
    }

    private val imageList = listOf("bunny", "hox", "oldeng", "wind")
    private val sizeList = listOf("ExtraSmall", "Small", "Medium", "Large", "ExtraLarge")

    private val onImageValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            viewModel.imageText.value = firstValue
            when (firstValue) {
                imageList[0] -> viewModel.imageButtonClick(0)
                imageList[1] -> viewModel.imageButtonClick(1)
                imageList[2] -> viewModel.imageButtonClick(2)
                imageList[3] -> viewModel.imageButtonClick(3)
            }
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
            when (firstValue) {
                sizeList[0] -> viewModel.sizeButtonClick(ProfileImageView.ExtraSmall)
                sizeList[1] -> viewModel.sizeButtonClick(ProfileImageView.Small)
                sizeList[2] -> viewModel.sizeButtonClick(ProfileImageView.Medium)
                sizeList[3] -> viewModel.sizeButtonClick(ProfileImageView.Large)
                sizeList[4] -> viewModel.sizeButtonClick(ProfileImageView.ExtraLarge)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.imageSelect.setOnClickListener {
            bottomSheet {
                text {
                    text = "image"
                    typo = Typo.SubTitle2
                    textColor(R.color.textPrimary)
                    setLayout(leftMarginPx = context.dpToIntPx(16f))
                }
                picker {
                    setFirstRow(imageList)
                    setFirstRowPosition(imageList.indexOf(viewModel.imageText.value))
                    this.onValueChangeListener = onImageValueChangeListener
                }
            }
        }
        binding.sizeSelect.setOnClickListener {
            bottomSheet {
                text {
                    text = "size"
                    typo = Typo.SubTitle2
                    textColor(R.color.textPrimary)
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