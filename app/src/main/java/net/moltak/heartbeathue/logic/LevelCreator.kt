package net.moltak.heartbeathue.logic

import net.moltak.heartbeathue.logic.color.SpecialColorCreator
import java.util.*

/**
 * Created by engeng on 11/26/15.
 * <img src="http://www.developers.meethue.com/sites/default/files/gamut_0.png" />
 * http://www.developers.meethue.com/documentation/supported-lights
 */
class LevelCreator(bulbCount: Int = 3, stageCount: Int = 20, colorCreator: SpecialColorCreator) {
    val stages: MutableList<Bulb> = ArrayList()
    val bulbCount: Int
    val stageCount: Int
    val specialColorCreator: SpecialColorCreator

    init {
        this.bulbCount = bulbCount
        this.stageCount = stageCount
        this.specialColorCreator = colorCreator
        this.specialColorCreator.bulbCount = bulbCount

        for (i in 1..stageCount) {
            var bulbs = colorCreator.create(i)
            stages.add(Bulb(bulbs))

            println("Stage $i -> ${bulbs[0].toString()}  ${bulbs[1].toString()} ${bulbs[2].toString()}")
        }
    }
}