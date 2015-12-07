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
    val specialcolorCreator: SpecialColorCreator

    init {
        this.hueCount = hueCount
        this.stageCount = stageCount
        this.specialcolorCreator = colorCreator
        this.specialcolorCreator.hueCount = hueCount

        for (i in 0..stageCount - 1) {
            var hueStages = colorCreator.create(i)
            hues.add(Hues(hueStages))

            println("${hueStages[0].toString()}  ${hueStages[1].toString()} ${hueStages[2].toString()}")
        }
    }
}