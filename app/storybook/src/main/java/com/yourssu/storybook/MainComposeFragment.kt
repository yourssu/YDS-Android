package com.yourssu.storybook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.compose.rememberNavController
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.base.Surface
import com.yourssu.storybook.compose.Navigation

class MainComposeFragment : BaseFragment() {
    companion object {
        @JvmStatic
        fun newInstance() = MainComposeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            YdsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = YdsTheme.colors.bgNormal,
                ) {
                    Navigation(
                        navController = rememberNavController(),
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}
