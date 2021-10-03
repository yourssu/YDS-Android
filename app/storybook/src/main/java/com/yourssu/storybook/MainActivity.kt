package com.yourssu.storybook

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.yourssu.design.system.component.Toast.Companion.toast
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.system.language.*
import com.yourssu.design.undercarriage.size.dpToIntPx
import com.yourssu.storybook.DetailActivity.Companion.navigateToDetail
import com.yourssu.storybook.atom.*
import com.yourssu.storybook.transform.ActivityAnimType

class MainActivity : BaseActivity() {

    /** 추가시 여기에만 선언하면 됨 */
    private val foundationList = listOf<Pair<String, Class<*>>>(
        "SemanticColor" to Fragment::class.java,
        "Vibration" to Fragment::class.java
    )
    private val atomList = listOf<Pair<String, Class<*>>> (
        "Text" to TextFragment::class.java,
        "ProfileImageView" to ProfileImageViewFragment::class.java,
        "Badge" to BadgeFragment::class.java,
        "BoxButton" to BoxButtonFragment::class.java,
        "Divider" to DividerFragment::class.java,
        "IconView" to IconViewFragment::class.java,
        "PlainButton" to Fragment::class.java,
        "Checkbox" to Fragment::class.java,
        "SimpleTextField" to Fragment::class.java,
        "SuffixTextField" to Fragment::class.java,
        "PasswordTextField" to Fragment::class.java,
        "SearchTextField" to Fragment::class.java
    )
    private val componentList = listOf<Pair<String, Class<*>>>(
        "Toast" to Fragment::class.java,
        "TopBar" to Fragment::class.java,
        "SingleTitleTopBar" to Fragment::class.java,
        "DoubleTitleTopBar" to Fragment::class.java,
        "BottomBar" to Fragment::class.java
    )
    /** 추가시 여기에만 선언하면 됨 */

    override var animationType: ActivityAnimType = ActivityAnimType.STAY

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

                        setOnClickListener {
                            toast(this.text.toString())
                        }
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

                            backgroundColor(R.color.dimNormal)
                            setLayout(
                                width = MATCH_PARENT,
                                leftPaddingPx = context.dpToIntPx(16f),
                                topPaddingPx = context.dpToIntPx(2f),
                                bottomPaddingPx = context.dpToIntPx(2f)
                            )
                        }
                        foundationList.forEach { foundation ->
                            text {
                                text = foundation.first
                                typo = Typo.Body1
                                setOnClickListener {
                                    navigateToDetail(foundation.first, foundation.second)
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
                        text {
                            text = "2. Atom"
                            typo = Typo.SubTitle3

                            backgroundColor(R.color.dimNormal)
                            setLayout(
                                width = MATCH_PARENT,
                                leftPaddingPx = context.dpToIntPx(16f),
                                topPaddingPx = context.dpToIntPx(2f),
                                bottomPaddingPx = context.dpToIntPx(2f)
                            )
                        }
                        atomList.forEach { atom ->
                            text {
                                text = atom.first
                                typo = Typo.Body1
                                setOnClickListener {
                                    navigateToDetail(atom.first, atom.second)
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
                        text {
                            text = "3. Component"
                            typo = Typo.SubTitle3

                            backgroundColor(R.color.dimNormal)
                            setLayout(
                                width = MATCH_PARENT,
                                leftPaddingPx = context.dpToIntPx(16f),
                                topPaddingPx = context.dpToIntPx(2f),
                                bottomPaddingPx = context.dpToIntPx(2f)
                            )
                        }
                        componentList.forEach { component ->
                            text {
                                text = component.first
                                typo = Typo.Body1

                                setOnClickListener {
                                    navigateToDetail(component.first, component.second)
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
    }
}