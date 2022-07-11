package com.yourssu.storybook.foundation

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yourssu.design.system.atom.ProfileImageView
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.system.foundation.semanticColors
import com.yourssu.design.system.language.*
import com.yourssu.design.undercarriage.size.dpToIntPx
import com.yourssu.storybook.BaseFragment
import com.yourssu.storybook.R

class SemanticColorFragment: BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return context?.scrollView {
            verticalLayout {
                semanticColors.forEach { colorCategory ->
                    text {
                        text = colorCategory.groupName
                        typo = Typo.SubTitle3

                        backgroundColor(R.color.dimNormal)
                        setLayout(
                            width = MATCH_PARENT,
                            leftPaddingPx = context.dpToIntPx(16f),
                            topPaddingPx = context.dpToIntPx(2f),
                            bottomPaddingPx = context.dpToIntPx(2f)
                        )
                    }
                    colorCategory.colorList.forEach { basicColor ->
                        horizontalLayout {
                            profileImageView {
                                setImageResource(basicColor.color)
                                size = ProfileImageView.Medium
                                gravity = Gravity.CENTER_VERTICAL

                                setLayout(
                                    leftMarginPx = context.dpToIntPx(16f),
                                    rightMarginPx = context.dpToIntPx(16f),
                                )
                            }
                            verticalLayout {
                                text {
                                    text = basicColor.colorName
                                    typo = Typo.SubTitle2

                                    setLayout(
                                        width = MATCH_PARENT,
                                        topPaddingPx = context.dpToIntPx(16f)
                                    )
                                }
                                text {
                                    text = basicColor.colorValue
                                    typo = Typo.Body2
                                    textColor(R.color.textDisabled)

                                    setLayout(
                                        width = MATCH_PARENT,
                                        topPaddingPx = context.dpToIntPx(4f),
                                        bottomPaddingPx = context.dpToIntPx(16f)
                                    )
                                }
                            }
                        }
                        divider {
                            setLayout(leftMarginPx = context.dpToIntPx(16f))
                        }
                    }
                }
            }
        }
    }
}