package net.moltak.heartbeathue.logic.color

import net.moltak.heartbeathue.logic.HueStage

/**
 * Created by engeng on 12/6/15.
 */
class InverseExponencialColorCreator : SpecialColorCreator {
    override fun create(hueStage: HueStage, stage: Int): HueStage {
        val a = hueStage.A
        val r = hueStage.R
        val g = hueStage.G
        val b = hueStage.B

        // n = color - color * (1/stage^2), inverse exponential,
        var exponential : Double = 1.0 / (stage * stage)
        if (exponential == 1.0) { // 처음 값이 무조건 1이 나오는데 이때 편차가 너무 크므로 조금 줄임.
            exponential = 0.8
        }

        //println("Stage $stage   -->  $exponential")
        return HueStage(
                (a - a * exponential).toInt(),
                (r - r * exponential).toInt(),
                (g - g * exponential).toInt(),
                (b - b * exponential).toInt())
    }
}