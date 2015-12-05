package net.moltak.heartbeathue.logic

import java.util.*

/**
 * Created by engeng on 11/26/15.
 */
class LevelCreator(hueCount: Int = 3, stageCount: Int = 20) {
    val hues: MutableList<Hues> = ArrayList()
    val hueCount: Int
    val stageCount: Int
    private val MAX_COLOR = 255

    init {
        this.hueCount = hueCount
        this.stageCount = stageCount

        val rand = Random()
        rand.setSeed(Date().time)

        for (i in 1..stageCount) {
            val a = rand.nextInt(MAX_COLOR)
            val r = rand.nextInt(MAX_COLOR)
            val g = rand.nextInt(MAX_COLOR)
            val b = rand.nextInt(MAX_COLOR)

            var hueStages = Array(hueCount, { i -> HueStage(a, r, g, b)})
            hueStages[r % hueCount] = createInverseExponentialColor(hueStages[0], i)

            hues.add(Hues(hueStages))

            println("${hueStages[0].toString()}  ${hueStages[1].toString()} ${hueStages[2].toString()}")
        }
    }

    private fun createInverseExponentialColor(hueStage: HueStage, stage: Int) : HueStage {
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