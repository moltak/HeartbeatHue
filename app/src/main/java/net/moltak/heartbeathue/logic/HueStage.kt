package net.moltak.heartbeathue.logic

import android.graphics.Color

data class HueStage(val R: Int, val G: Int, val B: Int) {
    fun toInt(): Int? {
        return Color.argb(255, R, G, B).toInt()
    }
}

