package com.yourssu.storybook.foundation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.system.language.*
import com.yourssu.design.undercarriage.size.dpToIntPx
import com.yourssu.storybook.R
import kotlin.math.roundToInt

class TypographyFragment: Fragment() {
    companion object {
        const val EXAMPLE_TEXT =
            "계절이 지나가는 하늘에는 가을로 가득 차 있습니다.\n" +
            "나는 아무 걱정도 없이 가을 속의 별들을 다 헬 듯 합니다.\n" +
            "가슴 속에 하나 둘 새겨지는 별을 이제 다 못 헤는 것은 쉬이 아침이 오는 까닭이요, " +
            "내일 밤이 남은 까닭이요, 아직 나의 청춘이 다하지 않은 까닭입니다."
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return context?.scrollView {
            verticalLayout {
                Typo.getList().forEach { typoResource ->
                    text {
                        text = Typo.getName(typoResource)
                        typo = Typo.Body2
                        background = ResourcesCompat.getDrawable(resources, R.color.bgSelected, null)
                        setLayout(
                            width = MATCH_PARENT,
                            leftPaddingPx = context.dpToIntPx(16f),
                            rightPaddingPx = context.dpToIntPx(16f),
                            topPaddingPx = context.dpToIntPx(16f),
                            bottomPaddingPx = context.dpToIntPx(16f),
                        )
                    }

                    text {
                        text = EXAMPLE_TEXT
                        typo = typoResource
                        setLayout(
                            width = MATCH_PARENT,
                            leftPaddingPx = context.dpToIntPx(16f),
                            rightPaddingPx = context.dpToIntPx(16f),
                            topPaddingPx = context.dpToIntPx(16f),
                            bottomPaddingPx = context.dpToIntPx(16f),
                        )
                    }

                    verticalLayout {
                        background = ResourcesCompat.getDrawable(resources, R.color.bgSelected, null)
                        setLayout(
                            width = MATCH_PARENT,
                            leftMarginPx = context.dpToIntPx(16f),
                            rightMarginPx = context.dpToIntPx(16f),
                            topMarginPx = context.dpToIntPx(16f),
                            bottomMarginPx = context.dpToIntPx(16f),
                            leftPaddingPx = context.dpToIntPx(16f),
                            rightPaddingPx = context.dpToIntPx(16f),
                            topPaddingPx = context.dpToIntPx(16f),
                            bottomPaddingPx = context.dpToIntPx(16f),
                        )
                        text {
                            text = "textSize: ${getTextSize(context, typoResource)}"
                            typo = Typo.Button0
                            setLayout(
                                bottomMarginPx = context.dpToIntPx(8f),
                            )
                        }
                        text {
                            text = "lineHeight: ${getLineHeight(typoResource)}"
                            typo = Typo.Button0
                        }
                    }
                    divider {
                        setLayout(leftMarginPx = context.dpToIntPx(16f))
                    }
                }
            }
        }
    }

    private fun getTextSize(context: Context, typoResource: Int): String {
        return Typo.getStyleTextSize(context, Typo.getStyle(typoResource))?.dropLast(3) ?: ""
    }

    private fun getLineHeight(typoResource: Int): String {
        val dimension = resources.getDimension(Typo.getLineHeight(typoResource)) // xml -> dimension
        val dp = dimension / resources.displayMetrics.density // dimension -> dp
        val roundDP = (dp * 10).roundToInt() / 10 // 소수점 첫번째 까지 반올림하기 위해서 10 배 곱하고 반올림 다시 나누기 10 해서 원래값 찾기
        return roundDP.toString()
    }
}