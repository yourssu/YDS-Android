package com.yourssu.storybook

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.system.language.*
import com.yourssu.design.undercarriage.size.dpToIntPx
import com.yourssu.storybook.DetailActivity.Companion.navigateToDetail
import com.yourssu.storybook.atom.*
import com.yourssu.storybook.component.*
import com.yourssu.storybook.foundation.*

class MainFragment : BaseFragment() {
    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    /** vvvvvv 추가시 여기에만 선언하면 됨 vvvvvv */

    private val foundationList = listOf<Pair<String, Class<*>>>(
        "BasicColor" to BaseColorFragment::class.java,
        "SemanticColor" to SemanticColorFragment::class.java,
        "Vibration" to VibrationFragment::class.java,
        "Icon" to IconFragment::class.java,
        "Typography" to TypographyFragment::class.java,
    )
    private val atomList = listOf<Pair<String, Class<*>>>(
        "Text" to TextFragment::class.java,
        "ProfileImageView" to ProfileImageViewFragment::class.java,
        "Badge" to BadgeFragment::class.java,
        "BoxButton" to BoxButtonFragment::class.java,
        "Divider" to DividerFragment::class.java,
        "IconView" to IconViewFragment::class.java,
        "PlainButton" to PlainButtonFragment::class.java,
        "Checkbox" to CheckboxFragment::class.java,
        "Toggle" to ToggleFragment::class.java,
        "SimpleTextField" to SimpleTextFieldFragment::class.java,
        "SuffixTextField" to SuffixTextFieldFragment::class.java,
        "PasswordTextField" to PasswordTextFieldFragment::class.java,
        "SearchTextField" to SearchTextFieldFragment::class.java,
        "ListItem" to ListItemFragment::class.java,
        "ListToggleItem" to ListToggleItemFragment::class.java,
    )
    private val componentList = listOf<Pair<String, Class<*>>>(
        "Toast" to ToastFragment::class.java,
        "TopBar" to TopBarFragment::class.java,
        "SingleTitleTopBar" to SingleTitleTopBarFragment::class.java,
        "DoubleTitleTopBar" to DoubleTitleTopBarFragment::class.java,
        "SearchTopBar" to SearchTopBarFragment::class.java,
        "BottomBar" to BottomBarFragment::class.java,
        "TabBar" to TabBarFragment::class.java,
        "Tooltip" to TooltipFragment::class.java,
        "List" to ListFragment::class.java,
    )

    /* 위의 리스트의 합 */
    private val totalItemList = listOf<Pair<String, List<Pair<String, Class<*>>>>>(
        "1. Foundation" to foundationList,
        "2. Atom" to atomList,
        "3. Component" to componentList
    )

    /** ^^^^^^ 추가시 여기에만 선언하면 됨 ^^^^^^^ */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = requireContext().verticalLayout {
        singleTitleTopBar {
            title = "StoryBook"
            thirdIcon = Icon.ic_warningcircle_line
            thirdButtonListener = View.OnClickListener {
                bottomSheet {
                    list {
                        subHeader = "Setting"

                        listItem {
                            text = "Current Version : ${BuildConfig.VERSION_NAME}"
                        }
                        listItem {
                            text = "Privacy policy"
                            rightIcon = Icon.ic_arrow_right_line
                            setOnClickListener {
                                val intent = Intent(
                                    Intent.ACTION_VIEW, Uri.parse(
                                        context.getString(
                                            R.string.link_privacy_policy
                                        )
                                    )
                                )
                                startActivity(intent)
                            }
                        }
                    }
                }
            }
        }
        scrollView {
            verticalLayout {
                totalItemList.forEach {
                    text {
                        text = it.first
                        typo = Typo.SubTitle3

                        backgroundColor(R.color.dimNormal)
                        setLayout(
                            width = MATCH_PARENT,
                            leftPaddingPx = context.dpToIntPx(16f),
                            topPaddingPx = context.dpToIntPx(2f),
                            bottomPaddingPx = context.dpToIntPx(2f)
                        )
                    }
                    it.second.forEach { foundation ->
                        text {
                            text = foundation.first
                            typo = Typo.Body1
                            setOnClickListener {
                                requireContext().navigateToDetail(
                                    foundation.first,
                                    foundation.second
                                )
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
