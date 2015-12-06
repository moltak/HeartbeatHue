package net.moltak.heartbeathue.logic

import net.moltak.heartbeathue.logic.color.SpecialColorCreator
import java.util.*

/**
 * Created by engeng on 11/26/15.
 * <img src="http://www.developers.meethue.com/sites/default/files/gamut_0.png" />
 * http://www.developers.meethue.com/documentation/supported-lights
 */
class LevelCreator(hueCount: Int = 3, stageCount: Int = 20, colorCreator: SpecialColorCreator) {
    val hues: MutableList<Hues> = ArrayList()
    val hueCount: Int
    val stageCount: Int
    val colorCreator: SpecialColorCreator
    private val MAX_COLOR = 255

    init {
        this.hueCount = hueCount
        this.stageCount = stageCount
        this.colorCreator = colorCreator

        val rand = Random()
        rand.setSeed(Date().time)

        for (i in 1..stageCount) {
            val a = rand.nextInt(MAX_COLOR)
            val r = rand.nextInt(MAX_COLOR)
            val g = rand.nextInt(MAX_COLOR)
            val b = rand.nextInt(MAX_COLOR)

            var hueStages = Array(hueCount, { HueStage(a, r, g, b)})
            hueStages[r % hueCount] = colorCreator.create(hueStages[0], i)

            hues.add(Hues(hueStages))

            println("${hueStages[0].toString()}  ${hueStages[1].toString()} ${hueStages[2].toString()}")
        }
    }
}