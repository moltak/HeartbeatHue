package net.moltak.heartbeathue.logic.color

import net.moltak.heartbeathue.logic.BulbColor
import net.moltak.heartbeathue.util.ColorConverter
import java.util.*

/**
 * Created by moltak on 15. 12. 8..
 */
class CieOppositeXyColorCreator(bulbCount: Int = 3, stageCount: Int = 20, modelNumber: String) : SpecialColorCreator {
    override val bulbCount: Int
    override val stageCount: Int
    val modelNumber: String
    val colorConverter = ColorConverter()
    val gamutCenterX = 0.21f
    val gamutCenterY = 0.29f

    init {
        this.bulbCount = bulbCount
        this.modelNumber = modelNumber
        this.stageCount = stageCount
    }

    val MAX_COLOR = 255

    val rand = Random()

    override fun create(stage: Int): Array<BulbColor> {
        val min = createMinimumValue(stage)

        val r = createRandomValueGreaterThanMin(min, rand)
        val g = createRandomValueGreaterThanMin(min, rand)
        val b = createRandomValueGreaterThanMin(min, rand)

        val bulbs = Array(bulbCount, { BulbColor(r, g, b)})
        bulbs[r % bulbCount] = createSpecialBulbColor(bulbs[0], stage)
        return bulbs
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

    private fun createSpecialBulbColor(bulbColor: BulbColor, stage: Int): BulbColor {
        val xy = colorConverter.toXY(bulbColor.R, bulbColor.G, bulbColor.B, modelNumber)
        val x2 = (gamutCenterX * 2) - (xy[0] / stage)
        val y2 = (gamutCenterY * 2) - (xy[1] / stage)
        val newXY = FloatArray(2)
        newXY[0] = x2
        newXY[1] = y2

        return colorConverter.toRGB(newXY, modelNumber)
    }
}