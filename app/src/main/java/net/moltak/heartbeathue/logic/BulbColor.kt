package net.moltak.heartbeathue.logic

import com.philips.lighting.hue.sdk.utilities.impl.Color

data class BulbColor(val R: Int, val G: Int, val B: Int) {
    constructor(rgb: Int) : this(Color.red(rgb), Color.green(rgb), Color.blue(rgb)) {}

    fun toInt(): Int? {
        return Color.argb(255, R, G, B).toInt()
    }
}

