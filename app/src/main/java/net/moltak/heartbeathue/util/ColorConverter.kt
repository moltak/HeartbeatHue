package net.moltak.heartbeathue.util

import com.philips.lighting.hue.sdk.utilities.PHUtilities

/**
 * Created by engeng on 12/6/15.
 */
class ColorConverter() {
    fun toXY(R: Int, G: Int, B: Int, modelNumber: String): FloatArray {
        return PHUtilities.calculateXYFromRGB(R, G, B, modelNumber);
    }
}
