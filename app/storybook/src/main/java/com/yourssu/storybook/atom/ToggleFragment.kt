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
import com.yourssu.storybook.databinding.FragmentToggleBinding

class ToggleFragment : Fragment() {

    lateinit var binding: FragmentToggleBinding
    private val viewModel: ToggleViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance() = ToggleFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentToggleBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initView()

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {

    }
}