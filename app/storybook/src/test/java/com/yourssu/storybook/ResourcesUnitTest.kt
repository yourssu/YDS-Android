package com.yourssu.storybook

import com.yourssu.design.system.compose.foundation.YdsTypography
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.reflect.full.declaredMemberProperties

class ResourcesUnitTest {

    val typoPropertyNames = listOf(
        "body1", "body2", "button0", "button1", "button2", "button3", "button4", "caption0", "caption1", "caption2", "display1", "display2",
        "subTitle1", "subTitle2", "subTitle3", "title1", "title2", "title3"
    )

    @Test
    fun `reflection properties test`() {
        val properties = YdsTypography::class.declaredMemberProperties
        val instance = YdsTypography()

        assertEquals(
            properties.map { it.name },
            typoPropertyNames
        )
        assertEquals(properties.first().get(instance), instance.body1)
    }
}