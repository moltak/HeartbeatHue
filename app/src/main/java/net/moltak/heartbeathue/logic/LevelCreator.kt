package net.moltak.heartbeathue.logic

import java.util.*

/**
 * Created by engeng on 11/26/15.
 */
class LevelCreator {
    private val hues: MutableList<Hues> = ArrayList()

    constructor() {
        val rand = Random()
        rand.setSeed(Date().time)

        for (i in 1..20) {
            var r = rand.nextInt(255)
            var g = rand.nextInt(255)
            var b = rand.nextInt(255)

            var hueStages = Array(3, { i -> HueStage(r, g, b)})
            hueStages[r % 3] = createInverseExponentialColor(hueStages[0], i)

            hues.add(Hues(hueStages))
        }
    }

    private fun createInverseExponentialColor(hueStage: HueStage, stage: Int) : HueStage {
        val r = hueStage.R
        val g = hueStage.G
        val b = hueStage.B

        // n = color - color * (1/stage^2), inverse exponential,
        val exponential = 1 / (stage * stage)
        return HueStage(r - r * exponential, g - g * exponential, b - b * exponential)
    }

    fun getHues(): List<Hues> {
        return hues
    }
}