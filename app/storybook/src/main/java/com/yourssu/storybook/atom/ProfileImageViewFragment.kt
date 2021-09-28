package com.yourssu.storybook.atom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
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

        return binding.root
    }
}