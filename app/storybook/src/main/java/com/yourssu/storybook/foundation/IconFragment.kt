package com.yourssu.storybook.foundation

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.system.language.*
import com.yourssu.design.undercarriage.size.dpToIntPx

class IconFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return context?.scrollView {
            verticalLayout {
                Icon.getList().forEach { iconResource ->
                    horizontalLayout {
                        gravity = Gravity.CENTER_VERTICAL
                        setLayout(
                            height = context.dpToIntPx(50f),
                        )
                        imageView {
                            setImageResource(Icon.getIconDrawable(iconResource))
                            setLayout(
                                width = WRAP_CONTENT,
                                height = WRAP_CONTENT,
                                leftMarginPx = context.dpToIntPx(30f),
                                rightMarginPx = context.dpToIntPx(30f),
                            )
                        }

                        text {
                            text = Icon.getName(iconResource)
                            typo = Typo.Body1
                        }
                    }
                }
            }
        }
    }
}