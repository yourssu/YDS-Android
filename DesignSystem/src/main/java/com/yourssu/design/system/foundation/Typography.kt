package com.yourssu.design.system.foundation

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import androidx.annotation.DimenRes
import androidx.annotation.IntDef
import androidx.annotation.StyleRes
import com.yourssu.design.R
import com.yourssu.design.system.foundation.Typo.Body1
import com.yourssu.design.system.foundation.Typo.Body2
import com.yourssu.design.system.foundation.Typo.Button0
import com.yourssu.design.system.foundation.Typo.Button1
import com.yourssu.design.system.foundation.Typo.Button2
import com.yourssu.design.system.foundation.Typo.Button3
import com.yourssu.design.system.foundation.Typo.Button4
import com.yourssu.design.system.foundation.Typo.Caption0
import com.yourssu.design.system.foundation.Typo.Caption1
import com.yourssu.design.system.foundation.Typo.Caption2
import com.yourssu.design.system.foundation.Typo.Display1
import com.yourssu.design.system.foundation.Typo.Display2
import com.yourssu.design.system.foundation.Typo.SubTitle1
import com.yourssu.design.system.foundation.Typo.SubTitle2
import com.yourssu.design.system.foundation.Typo.SubTitle3
import com.yourssu.design.system.foundation.Typo.Title1
import com.yourssu.design.system.foundation.Typo.Title2
import com.yourssu.design.system.foundation.Typo.Title3


@Retention(AnnotationRetention.SOURCE)
@IntDef(value = [Title1, Title2, Title3, SubTitle1, SubTitle2, SubTitle3, Body1, Body2, Button0, Button1, Button2, Button3, Button4, Caption0, Caption1, Caption2, Display1, Display2])
annotation class Typography

object Typo {
    const val Title1 = 0
    const val Title2 = 1
    const val Title3 = 2
    const val SubTitle1 = 10
    const val SubTitle2 = 11
    const val SubTitle3 = 12
    const val Body1 = 20
    const val Body2 = 21
    const val Button0 = 30
    const val Button1 = 31
    const val Button2 = 32
    const val Button3 = 33
    const val Button4 = 34
    const val Caption0 = 40
    const val Caption1 = 41
    const val Caption2 = 42
    const val Display1 = 50
    const val Display2 = 51

    @StyleRes
    fun getStyle(@Typography typo: Int): Int = when (typo) {
        Title1 -> R.style.Text_Title1
        Title2 -> R.style.Text_Title2
        Title3 -> R.style.Text_Title3
        SubTitle1 -> R.style.Text_SubTitle1
        SubTitle2 -> R.style.Text_SubTitle2
        SubTitle3 -> R.style.Text_SubTitle3
        Body1 -> R.style.Text_Body1
        Body2 -> R.style.Text_Body2
        Button0 -> R.style.Text_Button0
        Button1 -> R.style.Text_Button1
        Button2 -> R.style.Text_Button2
        Button3 -> R.style.Text_Button3
        Button4 -> R.style.Text_Button4
        Caption0 -> R.style.Text_Caption0
        Caption1 -> R.style.Text_Caption1
        Caption2 -> R.style.Text_Caption2
        Display1 -> R.style.Text_Display1
        Display2 -> R.style.Text_Display2
        else -> R.style.Text_Title1
    }

    val attrs = intArrayOf(android.R.attr.textSize, android.R.attr.fontFamily)

    fun getStyleTextSize(context: Context, @StyleRes style: Int): String? {
        val attrs = intArrayOf(android.R.attr.textSize)

        val ta: TypedArray = context.obtainStyledAttributes(style, attrs)

        val textSize = ta.getString(0)

        ta.recycle()

        return textSize
    }

    @DimenRes
    fun getLineHeight(@Typography typo: Int) = when (typo) {
        Title1 -> R.dimen.title1_line_height
        Title2 -> R.dimen.title2_line_height
        Title3 -> R.dimen.title3_line_height
        SubTitle1 -> R.dimen.subtitle1_line_height
        SubTitle2 -> R.dimen.subtitle2_line_height
        SubTitle3 -> R.dimen.subtitle3_line_height
        Body1 -> R.dimen.body1_line_height
        Body2 -> R.dimen.body2_line_height
        Button0 -> R.dimen.button0_line_height
        Button1 -> R.dimen.button1_line_height
        Button2 -> R.dimen.button2_line_height
        Button3 -> R.dimen.button3_line_height
        Button4 -> R.dimen.button4_line_height
        Caption0 -> R.dimen.caption0_line_height
        Caption1 -> R.dimen.caption1_line_height
        Caption2 -> R.dimen.caption2_line_height
        Display1 -> R.dimen.display1_line_height
        Display2 -> R.dimen.display2_line_height
        else -> R.dimen.title1_line_height
    }

    fun getList(): List<Int> {
        return listOf(
            Title1, Title2, Title3, SubTitle1, SubTitle2, SubTitle3, Body1, Body2, Button0, Button1, Button2, Button3, Button4, Caption0, Caption1, Caption2, Display1, Display2
        )
    }

    fun getName(@Typography value: Int): String {
        return when (value) {
            Title1 -> "Title1"
            Title2 -> "Title2"
            Title3 -> "Title3"
            SubTitle1 -> "SubTitle1"
            SubTitle2 -> "SubTitle2"
            SubTitle3 -> "SubTitle3"
            Body1 -> "Body1"
            Body2 -> "Body2"
            Button0 -> "Button0"
            Button1 -> "Button1"
            Button2 -> "Button2"
            Button3 -> "Button3"
            Button4 -> "Button4"
            Caption0 -> "Caption0"
            Caption1 -> "Caption1"
            Caption2 -> "Caption2"
            Display1 -> "Display1"
            Display2 -> "Display2"
            else -> "Title1"
        }
    }

    fun getValueByName(value: String): Int {
        return when (value) {
            "Title1" -> Title1
            "Title2" -> Title2
            "Title3" -> Title3
            "SubTitle1" -> SubTitle1
            "SubTitle2" -> SubTitle2
            "SubTitle3" -> SubTitle3
            "Body1" -> Body1
            "Body2" -> Body2
            "Button0" -> Button0
            "Button1" -> Button1
            "Button2" -> Button2
            "Button3" -> Button3
            "Button4" -> Button4
            "Caption0" -> Caption0
            "Caption1" -> Caption1
            "Caption2" -> Caption2
            "Display1" -> Display1
            "Display2" -> Display2
            else -> Title1
        }
    }
}