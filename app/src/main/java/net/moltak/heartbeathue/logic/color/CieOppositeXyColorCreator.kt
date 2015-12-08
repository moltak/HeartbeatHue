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
    val totalStage = 20
    val rand = Random()

    init {
        rand.setSeed(Date().time)
    }

    override fun create(stage: Int): Array<HueStage> {
        val min = createMinimumValue(stage)

        val r = createRandomValueGreaterThanMin(min, rand)
        val g = createRandomValueGreaterThanMin(min, rand)
        val b = createRandomValueGreaterThanMin(min, rand)

        val hueStages = Array(hueCount, { HueStage(r, g, b)})
        return hueStages
    }

    private fun createMinimumValue(stage: Int): Int {
        return ((MAX_COLOR / stage) - (MAX_COLOR / totalStage))
    }

    private fun createRandomValueGreaterThanMin(min: Int, rand: Random): Int {
        while (true) {
            val v = rand.nextInt(MAX_COLOR)
            if (v >= min) {
                return v
            }
        }
    }
}