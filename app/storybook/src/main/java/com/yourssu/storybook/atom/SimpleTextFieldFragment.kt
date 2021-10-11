package com.yourssu.storybook.atom

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yourssu.storybook.databinding.FragmentSimpleTextFieldBinding

class SimpleTextFieldFragment : Fragment() {

    lateinit var binding: FragmentSimpleTextFieldBinding
    private val viewModel: SimpleTextFieldViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance() = SimpleTextFieldFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSimpleTextFieldBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initView()

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {

    }
}