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

        for (i in 1..levelCreator.stageCount) {
            val MINVALUE : Int = ((255 / i) - (255 / levelCreator.stageCount))
            println("Stage $i -> MinValue = $MINVALUE")

            val stages = levelCreator.hues[i - 1].stages
            assert(stages[0].R > MINVALUE || stages[0].G > MINVALUE || stages[0].B > MINVALUE)
            assert(stages[1].R > MINVALUE || stages[1].G > MINVALUE || stages[1].B > MINVALUE)
            assert(stages[2].R > MINVALUE || stages[2].G > MINVALUE || stages[2].B > MINVALUE)
        }
    }

    @Test
    fun shouldHasOppositeValue() {

    }

}