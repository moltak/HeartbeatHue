package net.moltak.heartbeathue

import net.moltak.heartbeathue.logic.HueStage
import net.moltak.heartbeathue.logic.LevelCreator
import net.moltak.heartbeathue.logic.color.CieOppositeXyColorCreator
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Created by moltak on 15. 12. 8..
 */
class CieOppositeXyColorCreatorTest {

    @Test
    fun createorShouldReturnHueStages() {
        val colorCreator = CieOppositeXyColorCreator()
        assertTrue(colorCreator.create(1) is Array<HueStage>)
    }

    @Test
    fun creatorShouldReturnSameHueCountWithLevelCreator() {
        val levelCreator = LevelCreator(colorCreator = CieOppositeXyColorCreator())
        assertEquals(levelCreator.hueCount, levelCreator.specialcolorCreator.hueCount)
    }

    @Test
    fun shouldHasMinValue() {
        val levelCreator = LevelCreator(colorCreator = CieOppositeXyColorCreator())
        val stages = levelCreator.hues[0].stages

        for (i in 1..levelCreator.stageCount) {
            val MINVALUE : Int = ((255 / i) - (255 / levelCreator.stageCount))
            println("MinValue = $MINVALUE")
            assert(stages[0].R > MINVALUE || stages[0].G > MINVALUE || stages[0].B > MINVALUE)
        }
    }
}