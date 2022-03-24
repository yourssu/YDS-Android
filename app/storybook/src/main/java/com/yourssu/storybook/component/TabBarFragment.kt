package com.yourssu.storybook.component

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.yourssu.design.system.atom.Picker
import com.yourssu.design.system.atom.Text
import com.yourssu.design.system.component.TabBar
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.system.language.bottomSheet
import com.yourssu.design.system.language.picker
import com.yourssu.design.system.language.setLayout
import com.yourssu.design.system.language.text
import com.yourssu.design.util.TabBarMediator
import com.yourssu.design.undercarriage.size.dpToIntPx
import com.yourssu.storybook.R
import com.yourssu.storybook.databinding.FragmentTabBarBinding

class TabBarFragment : Fragment() {

    private lateinit var binding: FragmentTabBarBinding
    private val viewModel: TabBarViewModel by viewModels()
    private val tabModeList = listOf("scrollable", "fixed")
    private lateinit var collectionAdapter: CollectionAdapter
    private lateinit var viewPager: ViewPager2
    private var tabBarMediator: TabBarMediator? = null
    private val onTabModeValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            viewModel.tabMode.value = firstValue
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTabBarBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        observeViewModel()

        initView()

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.tabMode.observe(viewLifecycleOwner) { mode ->
            if (mode == tabModeList[SCROLLABLE]) {
                binding.tabBar.tabMode = TabBar.MODE_SCROLLABLE
            } else {
                binding.tabBar.tabMode = TabBar.MODE_FIXED
            }

            collectionAdapter = CollectionAdapter(childFragmentManager, lifecycle, mode)
            viewPager = binding.pager
            viewPager.adapter = collectionAdapter

            tabBarMediator?.detach()
            tabBarMediator = TabBarMediator(binding.tabBar, viewPager) { tab, position ->
                tab.text = "Tab ${position + 1}"
            }
            tabBarMediator?.attach()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.tabModeSelect.setOnClickListener {
            bottomSheet {
                text {
                    text = "tabMode"
                    typo = Typo.SubTitle2

                    setLayout(leftMarginPx = context.dpToIntPx(16f))
                }
                picker {
                    setFirstRow(tabModeList)
                    setFirstRowPosition(tabModeList.indexOf(viewModel.tabMode.value))
                    this.onValueChangeListener = onTabModeValueChangeListener
                }
            }
        }
    }

    class CollectionAdapter(
        fm: FragmentManager,
        lifecycle: Lifecycle,
        private val tabMode: String
    ) :
        FragmentStateAdapter(fm, lifecycle) {
        override fun getItemCount(): Int =
            if (tabMode == "scrollable") SCROLLABLE_MODE_TAB_COUNT else FIXED_MODE_TAB_COUNT

        override fun createFragment(position: Int): Fragment {
            val fragment = DemoFragment()
            fragment.arguments = Bundle().apply {
                putString(ARG_OBJECT, "Tab ${position + 1}")
            }
            return fragment
        }


    }

    class DemoFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_tab_bar_demo, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
                val textView: Text = view.findViewById(R.id.text_demo)
                textView.text = getString(ARG_OBJECT) ?: ""
            }
        }
    }


    companion object {
        private const val SCROLLABLE = 0
        private const val FIXED = 1
        private const val ARG_OBJECT = "ARG_OBJECT"
        private const val SCROLLABLE_MODE_TAB_COUNT = 6
        private const val FIXED_MODE_TAB_COUNT = 4

        @JvmStatic
        fun newInstance() = TabBarFragment()
    }
}