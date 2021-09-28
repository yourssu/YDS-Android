package com.yourssu.yds_ui_tester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.yourssu.design.system.atom.Picker
import com.yourssu.design.system.atom.PlainButton
import com.yourssu.design.system.atom.Toggle
import com.yourssu.design.system.foundation.Icon
import com.yourssu.yds_ui_tester.databinding.ActivityPlainButtonBinding

class PlainButtonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlainButtonBinding
    val sizeString = listOf("Medium", "Small", "Large")
    val iconString = listOf("아이콘 없음", "ic_ground_line", "ic_home_line", "ic_board_line")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_plain_button)
        binding.activity = this

        binding.plainButton.setOnClickListener {
            Log.d(this::class.simpleName, "PlainButton Clicked!")
            Toast.makeText(this, "PlainButton Clicked!", Toast.LENGTH_SHORT)
                .show()
        }

        binding.toggleIsDisabled.setOnSelectedListener(object : Toggle.SelectedListener {
            override fun onSelected(boolean: Boolean) {
                binding.plainButton.isDisabled = boolean
            }
        })

        binding.toggleIsWarned.setOnSelectedListener(object : Toggle.SelectedListener {
            override fun onSelected(boolean: Boolean) {
                binding.plainButton.isWarned = boolean
            }
        })

        binding.toggleIsPointed.setOnSelectedListener(object : Toggle.SelectedListener {
            override fun onSelected(boolean: Boolean) {
                binding.plainButton.isPointed = boolean
            }
        })

        binding.pickerSize.onValueChangeListener = object : Picker.OnValueChangeListener {
            override fun onValueChange(
                firstValue: String,
                secondValue: String,
                thirdValue: String,
                totalValue: String
            ) {
                when (firstValue) {
                    "Large" -> binding.plainButton.size = PlainButton.LARGE
                    "Medium" -> binding.plainButton.size = PlainButton.MEDIUM
                    "Small" -> binding.plainButton.size = PlainButton.SMALL
                }
            }
        }

        binding.pickerLeftIcon.onValueChangeListener = object : Picker.OnValueChangeListener {
            override fun onValueChange(
                firstValue: String,
                secondValue: String,
                thirdValue: String,
                totalValue: String
            ) {
                when (firstValue) {
                    "아이콘 없음" -> binding.plainButton.leftIcon = null
                    "ic_ground_line" -> binding.plainButton.leftIcon = Icon.ic_ground_line
                    "ic_home_line" -> binding.plainButton.leftIcon = Icon.ic_home_line
                    "ic_board_line" -> binding.plainButton.leftIcon = Icon.ic_board_line
                }
            }
        }

        binding.pickerRightIcon.onValueChangeListener = object : Picker.OnValueChangeListener {
            override fun onValueChange(
                firstValue: String,
                secondValue: String,
                thirdValue: String,
                totalValue: String
            ) {
                when (firstValue) {
                    "아이콘 없음" -> binding.plainButton.rightIcon = null
                    "ic_ground_line" -> binding.plainButton.rightIcon = Icon.ic_ground_line
                    "ic_home_line" -> binding.plainButton.rightIcon = Icon.ic_home_line
                    "ic_board_line" -> binding.plainButton.rightIcon = Icon.ic_board_line
                }
            }
        }
    }

    fun setPlainButtonText() {
        with(binding) {
            plainButton.text = textFieldButtonText.text.toString()
        }
    }
}