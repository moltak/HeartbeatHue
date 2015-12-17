package net.moltak.heartbeathue

import net.moltak.heartbeathue.logic.BulbColor
import net.moltak.heartbeathue.logic.LevelCreator
import net.moltak.heartbeathue.logic.color.InverseExponencialColorCreator
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Created by moltak on 15. 12. 8..
 */
class StageModeColorCreatorTest {

    @Test
    fun createorShouldReturnHueStages() {
        val colorCreator = InverseExponencialColorCreator(3, 20)
        assertTrue(colorCreator.create(1) is Array<BulbColor>)
    }

    @Test
    fun creatorShouldReturnSameHueCountWithLevelCreator() {
        val levelCreator = LevelCreator(colorCreator = InverseExponencialColorCreator(3, 20))
        assertEquals(levelCreator.bulbCount, (levelCreator.specialColorCreator as InverseExponencialColorCreator).bulbCount)
    }

    @Test
    fun shouldHasMinValue() {
        val levelCreator = LevelCreator(colorCreator = InverseExponencialColorCreator(3, 20))

        for (i in 1..levelCreator.stageCount) {
            val MINVALUE : Int = ((255 / i) - (255 / levelCreator.stageCount))
            println("Stage $i -> MinValue = $MINVALUE")

            val bulb = levelCreator.stages[i - 1].bulbs
            assert(bulb[0].R > MINVALUE || bulb[0].G > MINVALUE || bulb[0].B > MINVALUE)
            assert(bulb[1].R > MINVALUE || bulb[1].G > MINVALUE || bulb[1].B > MINVALUE)
            assert(bulb[2].R > MINVALUE || bulb[2].G > MINVALUE || bulb[2].B > MINVALUE)
        }
    }

    @Test
    fun shouldHasOppositeValue() {
        val levelCreator = LevelCreator(colorCreator = InverseExponencialColorCreator())
    }
}