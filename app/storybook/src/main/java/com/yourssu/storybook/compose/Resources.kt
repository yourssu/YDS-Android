package com.yourssu.storybook.compose

import com.yourssu.design.system.compose.foundation.YdsTextStyle
import com.yourssu.design.system.compose.foundation.YdsTypography
import com.yourssu.design.system.foundation.Icon
import kotlin.reflect.full.declaredMemberProperties

/**
 * 참고:
 * @see com.yourssu.storybook.ResourcesUnitTest
 */
val typographies: Map<String, YdsTextStyle> by lazy {
    val thisRef = YdsTypography()
    YdsTypography::class.declaredMemberProperties.associate { property ->
        property.name to property.get(thisRef) as YdsTextStyle
    }
}

val icons: Map<String, Int> by lazy {
    val iconList = Icon.getList()
    iconList.map { Icon.getName(it) }.zip(iconList).toMap()
}
