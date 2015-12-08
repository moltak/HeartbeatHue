package net.moltak.heartbeathue.logic.color

import net.moltak.heartbeathue.logic.BulbColor
import java.util.*

/**
 * Created by moltak on 15. 12. 8..
 */
class CieOppositeXyColorCreator(bulbCount: Int = 3, stageCount: Int = 20, modelNumber: String) : SpecialColorCreator {
    override val bulbCount: Int
    override val stageCount: Int
    val modelName: String

    init {
        this.bulbCount = bulbCount
        this.modelName = modelNumber
        this.stageCount = stageCount
    }

    val MAX_COLOR = 255

    val rand = Random()

    override fun create(stage: Int): Array<BulbColor> {
        val min = createMinimumValue(stage)

        val r = createRandomValueGreaterThanMin(min, rand)
        val g = createRandomValueGreaterThanMin(min, rand)
        val b = createRandomValueGreaterThanMin(min, rand)

        val hueStages = Array(bulbCount, { BulbColor(r, g, b)})
        return hueStages
    }

    private fun createMinimumValue(stage: Int): Int {
        return ((MAX_COLOR / stage) - (MAX_COLOR / stageCount))
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