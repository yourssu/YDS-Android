package com.yourssu.storybook.compose

import com.yourssu.design.system.compose.foundation.YdsTextStyle
import com.yourssu.design.system.compose.foundation.YdsTypography
import com.yourssu.design.system.foundation.Icon

// TODO: 다 채우기
val typographies: Map<String, YdsTextStyle> by lazy {
    YdsTypography().let {
        mapOf(
            "title1" to it.title1,
            "title2" to it.title2,
            "title3" to it.title3,
        )
    }
}

val icons: Map<String, Int> by lazy {
    val iconList = Icon.getList()
    iconList
        .map { Icon.getName(it) }
        .zip(
            iconList.map { Icon.getIconDrawable(it) }
        ).toMap()
}
