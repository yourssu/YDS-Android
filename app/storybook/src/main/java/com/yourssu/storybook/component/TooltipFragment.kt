package com.yourssu.storybook.component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yourssu.design.system.atom.Picker
import com.yourssu.design.system.atom.ToolTip
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.system.language.bottomSheet
import com.yourssu.design.system.language.picker
import com.yourssu.design.system.language.setLayout
import com.yourssu.design.system.language.text
import com.yourssu.design.undercarriage.size.dpToIntPx
import com.yourssu.storybook.databinding.FragmentTooltipBinding

class TooltipFragment : Fragment() {
    private lateinit var binding: FragmentTooltipBinding
    private val viewModel: TooltipViewModel by viewModels()


    val hopelocationList = listOf<Pair<String, Int>>(
        "on the reference view" to ToolTip.ABOVE,
        "under the reference view" to ToolTip.BELOW,
        "to the right of the reference view" to ToolTip.RIGHT_SIDE,
        "to the left of the reference view" to ToolTip.LEFT_SIDE,
        "random" to -1
    )
    val locationList = listOf<String>(
        hopelocationList[0].first,
        hopelocationList[1].first,
        hopelocationList[2].first,
        hopelocationList[3].first,
        hopelocationList[4].first
    )


    private val onHopeLocationValueChangeListener = object : Picker.OnValueChangeListener {
        override fun onValueChange(
            firstValue: String,
            secondValue: String,
            thirdValue: String,
            totalValue: String,
        ) {
            viewModel.hopeLocation.value = firstValue
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTooltipBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        context?.let {
            activity?.let { it1 ->
                viewModel.tooltipBuilders =
                    ToolTip.Builder(context = it, it1.windowManager, inflater)
            }
        }

        initView()

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = TooltipFragment()
    }

    private fun initView() {
        binding.hopeLocationSelect.setOnClickListener {
            bottomSheet {
                text {
                    text = "hopeLocation"
                    typo = Typo.SubTitle2

                    setLayout(leftMarginPx = context.dpToIntPx(16f))
                }
                picker {
                    setFirstRow(locationList)
                    setFirstRowPosition(locationList.indexOf(viewModel.hopeLocation.value))
                    this.onValueChangeListener = onHopeLocationValueChangeListener
                }
            }
        }
    }
}