package net.moltak.heartbeathue.logic

import net.moltak.heartbeathue.logic.color.SpecialColorCreator
import java.util.*

/**
 * Created by engeng on 11/26/15.
 * <img src="http://www.developers.meethue.com/sites/default/files/gamut_0.png" />
 * http://www.developers.meethue.com/documentation/supported-lights
 */
class LevelCreator(colorCreator: SpecialColorCreator) {
    val stages: MutableList<Bulb> = ArrayList()
    val bulbCount: Int
    val stageCount: Int
    val specialColorCreator: SpecialColorCreator

    init {
        this.bulbCount = colorCreator.bulbCount
        this.stageCount = colorCreator.stageCount
        this.specialColorCreator = colorCreator

        for (i in 1..this.stageCount) {
            var bulbs = colorCreator.create(i)
            stages.add(Bulb(bulbs))
        }
    }
}