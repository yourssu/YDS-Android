package com.yourssu.design.undercarriage.draw

import android.content.Context
import android.graphics.Path
import android.graphics.RectF
import com.yourssu.design.undercarriage.size.dpToPx


interface ClipRadius {
    var rect: RectF

    fun Context.getPath(radiusDp: Float,
                        topLeft: Boolean,
                        topRight: Boolean,
                        bottomRight: Boolean,
                        bottomLeft: Boolean): Path {

        val radiusPx = this.dpToPx(radiusDp)

        val path = Path()
        val radiusBack = FloatArray(8)

        if (topLeft) {
            radiusBack[0] = radiusPx
            radiusBack[1] = radiusPx
        }

        if (topRight) {
            radiusBack[2] = radiusPx
            radiusBack[3] = radiusPx
        }

        if (bottomRight) {
            radiusBack[4] = radiusPx
            radiusBack[5] = radiusPx
        }

        if (bottomLeft) {
            radiusBack[6] = radiusPx
            radiusBack[7] = radiusPx
        }

        path.addRoundRect(rect, radiusBack, Path.Direction.CW)

        return path
    }
}