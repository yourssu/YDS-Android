package com.yourssu.storybook.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.yourssu.design.system.component.Toast.Companion.toast
import com.yourssu.storybook.databinding.FragmentSearchTopBarBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchTopBarFragment : Fragment() {

    private lateinit var binding: FragmentSearchTopBarBinding
    private val viewModel: SearchTopBarViewModel by viewModels()
    private lateinit var collectEventJob: Job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchTopBarBinding.inflate(LayoutInflater.from(context), container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        collectEventJob = lifecycleScope.launch {
            viewModel.eventFlow.collect { handleEvent(it) }
        }
    }

    override fun onStop() {
        super.onStop()
        collectEventJob.cancel()
    }

    private fun handleEvent(event: SearchTopBarViewModel.Event) = when(event) {
        SearchTopBarViewModel.Event.LeftArrowButtonClicked -> showButtonClickedToast()
        is SearchTopBarViewModel.Event.KeyboardAction -> showEditorActionToast(event.actionId)
    }

    private fun showButtonClickedToast() {
        toast("Left arrow button was clicked.")
    }

    private fun showEditorActionToast(actionId: Int) {
        toast("Editor action event has occurred. actionId = $actionId")
    }
}