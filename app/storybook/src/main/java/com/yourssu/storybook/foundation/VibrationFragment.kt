package com.yourssu.storybook.foundation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yourssu.design.system.component.Toast.Companion.shortToast
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.system.language.*
import com.yourssu.design.system.rule.Vibration
import com.yourssu.design.system.rule.vibe
import com.yourssu.design.undercarriage.size.dpToIntPx

class VibrationFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return context?.scrollView {
            verticalLayout {
                Vibration.values().forEach { vibration ->
                    text {
                        text = vibration.name
                        typo = Typo.Body1
                        setOnClickListener {
                            shortToast(
                                vibration.name + "\n" +
                                        "Timings : [${vibration.timings.joinToString()}]" + "\n" +
                                        "Amplitudes: [${vibration.amplitudes.joinToString()}]"
                            )
                            context.vibe(vibration)
                        }
                        setLayout(
                            width = MATCH_PARENT,
                            leftPaddingPx = context.dpToIntPx(16f),
                            topPaddingPx = context.dpToIntPx(16f),
                            bottomPaddingPx = context.dpToIntPx(16f)
                        )
                    }
                    divider {
                        setLayout(leftMarginPx = context.dpToIntPx(16f))
                    }
                }
            }
        }
    }
}