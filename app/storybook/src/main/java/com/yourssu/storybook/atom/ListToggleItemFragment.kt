package com.yourssu.storybook.atom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yourssu.storybook.BaseFragment
import com.yourssu.storybook.databinding.FragmentListToggleItemBinding

class ListToggleItemFragment : BaseFragment() {

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

    override fun onPortrait() {
        super.onPortrait()
        viewModel.isLandscape.value = false
    }

    override fun onLandscape() {
        super.onLandscape()
        viewModel.isLandscape.value = true
    }
}