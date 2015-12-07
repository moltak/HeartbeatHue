package net.moltak.heartbeathue.logic.color

import net.moltak.heartbeathue.logic.HueStage
import java.util.*

/**
 * Created by engeng on 12/6/15.
 */
class InverseExponencialColorCreator : SpecialColorCreator {
    override var hueCount: Int = 0
        get() = field
        set(value) {
            field = value
        }

    val MAX_COLOR = 255

    override fun create(stage: Int): Array<HueStage> {
        val rand = Random()
        rand.setSeed(Date().time)

        val a = rand.nextInt(MAX_COLOR)
        val r = rand.nextInt(MAX_COLOR)
        val g = rand.nextInt(MAX_COLOR)
        val b = rand.nextInt(MAX_COLOR)

        var hueStages = Array(hueCount, { HueStage(a, r, g, b)})
        hueStages[r % hueCount] = createSpecialHueColor(a, b, g, r, stage)
        return hueStages
    }

    private fun createSpecialHueColor(a: Int, b: Int, g: Int, r: Int, stage: Int): HueStage {
        // create special color
        // n = color - color * (1/stage^2), inverse exponential,
        var exponential: Double = 1.0 / (stage * stage)
        if (exponential == 1.0) {
            // 처음 값이 무조건 1이 나오는데 이때 편차가 너무 크므로 조금 줄임.
            exponential = 0.8
        }

        //println("Stage $stage   -->  $exponential")
        val specialHue = HueStage(
                (a - a * exponential).toInt(),
                (r - r * exponential).toInt(),
                (g - g * exponential).toInt(),
                (b - b * exponential).toInt())
        return specialHue
    }
}