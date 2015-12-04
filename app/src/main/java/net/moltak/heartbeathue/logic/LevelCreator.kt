package net.moltak.heartbeathue.logic

import java.util.*

/**
 * Created by engeng on 11/26/15.
 */
class LevelCreator(hueCount: Int = 3, stageCount: Int = 20) {
    private val hues: MutableList<Hues> = ArrayList()
    private val hueCount: Int
    private val stageCount: Int

    init {
        this.hueCount = hueCount
        this.stageCount = stageCount

        val rand = Random()
        rand.setSeed(Date().time)

        for (i in 1..stageCount) {
            val a = rand.nextInt(255)
            val r = rand.nextInt(255)
            val g = rand.nextInt(255)
            val b = rand.nextInt(255)

            var hueStages = Array(hueCount, { i -> HueStage(a, r, g, b)})
            hueStages[r % hueCount] = createInverseExponentialColor(hueStages[0], i)

            hues.add(Hues(hueStages))

            //println("${hueStages[0].toString()}  ${hueStages[1].toString()} ${hueStages[2].toString()}")
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

    fun getHues(): List<Hues> {
        return hues
    }

    fun getHueCount(): Int {
        return hueCount
    }
}