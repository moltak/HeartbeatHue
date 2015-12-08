package net.moltak.heartbeathue.util

import com.philips.lighting.hue.sdk.utilities.PHUtilities
import com.philips.lighting.hue.sdk.utilities.impl.Color
import net.moltak.heartbeathue.logic.BulbColor

/**
 * Created by engeng on 12/6/15.
 */
class ColorConverter() {
    fun toXY(R: Int, G: Int, B: Int, modelNumber: String): FloatArray {
        return PHUtilities.calculateXYFromRGB(R, G, B, modelNumber);
    }

    fun toRGB(newXY: FloatArray, modelNumber: String): BulbColor {
        val color = PHUtilities.colorFromXY(newXY, modelNumber)
        return BulbColor(Color.red(color), Color.green(color), Color.blue(color))
    }
}
