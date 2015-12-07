package net.moltak.heartbeathue.logic.color

import net.moltak.heartbeathue.logic.HueStage
import java.util.*

/**
 * Created by moltak on 15. 12. 8..
 */
class CieOppositeXyColorCreator : SpecialColorCreator {
    override var hueCount: Int = 0
        get() = field
        set(value) {
            field = value
        }

    val MAX_COLOR = 255

    override fun create(stage: Int): Array<HueStage> {
        val rand = Random()
        rand.setSeed(Date().time)

        val r = rand.nextInt(MAX_COLOR)
        val g = rand.nextInt(MAX_COLOR)
        val b = rand.nextInt(MAX_COLOR)

        val hueStages = Array(hueCount, { HueStage(r, g, b)})

    }

    private fun createMinimumValue(stage: Int): Int {
        return (MAX_COLOR * (1 / stage.toFloat())).toInt()
    }
}