package com.yourssu.storybook.component

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.yourssu.design.system.component.Toast.Companion.toast
import com.yourssu.design.system.language.*
import com.yourssu.storybook.databinding.FragmentToastBinding

class ToastFragment : Fragment() {

    lateinit var binding: FragmentToastBinding
    private val viewModel: ToastViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance() = ToastFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentToastBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initView()

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.toastButton.setOnClickListener {
            toast(viewModel.textString.value ?: "", if (viewModel.isLongTime.value == true) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
        }
    }
}