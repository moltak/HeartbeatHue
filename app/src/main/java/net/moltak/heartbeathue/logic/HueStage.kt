package net.moltak.heartbeathue.logic

import android.graphics.Color

data class HueStage(val A: Int, val R: Int, val G: Int, val B: Int) {
    fun toInt(): Int? {
        return Color.argb(A, R, G, B).toInt()
    }
}

