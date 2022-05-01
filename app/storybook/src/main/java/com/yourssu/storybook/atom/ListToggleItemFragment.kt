package com.yourssu.storybook.atom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yourssu.storybook.databinding.FragmentListToggleItemBinding

class ListToggleItemFragment : Fragment() {

    private lateinit var binding: FragmentListToggleItemBinding

    companion object {
        fun newInstance() = ListToggleItemFragment()
    }

    private val viewModel: ListToggleItemViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListToggleItemBinding.inflate(LayoutInflater.from(context), container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}