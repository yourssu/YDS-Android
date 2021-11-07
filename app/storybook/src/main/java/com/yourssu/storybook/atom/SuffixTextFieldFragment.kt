package com.yourssu.storybook.atom

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yourssu.storybook.databinding.FragmentSuffixTextFieldBinding

class SuffixTextFieldFragment : Fragment() {

    lateinit var binding: FragmentSuffixTextFieldBinding
    private val viewModel: SuffixTextFieldViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance() = SuffixTextFieldFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSuffixTextFieldBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initView()

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {

    }
}