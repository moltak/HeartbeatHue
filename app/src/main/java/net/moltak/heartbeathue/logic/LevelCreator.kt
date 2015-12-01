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
            val r = rand.nextInt(255)
            val g = rand.nextInt(255)
            val b = rand.nextInt(255)

            var hueStages = Array(3, { i -> HueStage(r, g, b)})
            hueStages[r % 3] = createInverseExponentialColor(hueStages[0], i.toDouble())

            hues.add(Hues(hueStages))

            //println("${hueStages[0].toString()}  ${hueStages[1].toString()} ${hueStages[2].toString()}")
        }
    }

    private fun createInverseExponentialColor(hueStage: HueStage, stage: Double) : HueStage {
        val r = hueStage.R
        val g = hueStage.G
        val b = hueStage.B

        // n = color - color * (1/stage^2), inverse exponential,
        var exponential = 1 / (stage * stage)
        if (exponential == 1.0) {
            exponential = 0.8
        }

        println("Stage $stage   -->  $exponential")
        return HueStage(
                (r - r * exponential).toInt(),
                (g - g * exponential).toInt(),
                (b - b * exponential).toInt())
    }

    fun getHues(): List<Hues> {
        return hues
    }
}