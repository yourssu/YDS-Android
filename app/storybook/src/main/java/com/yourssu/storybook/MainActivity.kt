package com.yourssu.storybook

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.system.language.*
import com.yourssu.design.undercarriage.size.dpToIntPx

class MainActivity : AppCompatActivity() {

    /** 추가시 여기에만 선언하면 됨 */
    private val foundation = listOf<String>(
        "SemanticColor",
        "Typography"
    )
    private val atom = listOf<String>(
        "Text",
        "Toggle",
        "ProfileImageView",
        "Badge",
        "BoxButton",
        "BottomSheet",
        "Divider",
        "IconView",
        "Picker",
        "PlainButton",
        "Checkbox",
        "SimpleTextField",
        "SuffixTextField",
        "PasswordTextField",
        "SearchTextField"
    )
    private val component = listOf<String>(
        "Toast",
        "TopBar",
        "SingleTitleTopBar",
        "DoubleTitleTopBar",
        "BottomBar"
    )
    /** 추가시 여기에만 선언하면 됨 */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        setContentView {
            verticalLayout {
                horizontalLayout { // TODO change to SingleTitleTopBar
                    text {
                        text = "StoryBook"
                        typo = Typo.Title2
                        textColor(R.color.textPrimary)
                        setLayout(
                            leftMarginPx = context.dpToIntPx(16f),
                            topMarginPx = context.dpToIntPx(8f),
                            bottomMarginPx = context.dpToIntPx(8f)
                        )
                    }
                }
                scrollView {
                    verticalLayout {
                        text {
                            text = "1. Foundation"
                            typo = Typo.SubTitle3
                            textColor(R.color.textPrimary)
                            backgroundColor(R.color.dimNormal)
                            setLayout(
                                width = MATCH_PARENT,
                                leftPaddingPx = context.dpToIntPx(16f),
                                topPaddingPx = context.dpToIntPx(2f),
                                bottomPaddingPx = context.dpToIntPx(2f)
                            )
                        }
                        foundation.forEach {
                            text {
                                text = it
                                typo = Typo.Body1
                                textColor(R.color.textPrimary)
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
                        text {
                            text = "2. Atom"
                            typo = Typo.SubTitle3
                            textColor(R.color.textPrimary)
                            backgroundColor(R.color.dimNormal)
                            setLayout(
                                width = MATCH_PARENT,
                                leftPaddingPx = context.dpToIntPx(16f),
                                topPaddingPx = context.dpToIntPx(2f),
                                bottomPaddingPx = context.dpToIntPx(2f)
                            )
                        }
                        atom.forEach {
                            text {
                                text = it
                                typo = Typo.Body1
                                textColor(R.color.textPrimary)
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
                        text {
                            text = "3. Component"
                            typo = Typo.SubTitle3
                            textColor(R.color.textPrimary)
                            backgroundColor(R.color.dimNormal)
                            setLayout(
                                width = MATCH_PARENT,
                                leftPaddingPx = context.dpToIntPx(16f),
                                topPaddingPx = context.dpToIntPx(2f),
                                bottomPaddingPx = context.dpToIntPx(2f)
                            )
                        }
                        component.forEach {
                            text {
                                text = it
                                typo = Typo.Body1
                                textColor(R.color.textPrimary)
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
    }
}