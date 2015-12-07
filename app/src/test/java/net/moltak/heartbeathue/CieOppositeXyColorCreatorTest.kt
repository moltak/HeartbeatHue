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
        assertTrue(colorCreator.create(0) is Array<HueStage>)
    }

    @Test
    fun creatorShouldReturnSameHueCountWithLevelCreator() {
        val levelCreator = LevelCreator(colorCreator = CieOppositeXyColorCreator())
        assertEquals(levelCreator.hueCount, levelCreator.specialcolorCreator.hueCount)
    }

    @Test
    fun shouldHas255ColorAtFirstStage() {
        val levelCreator = LevelCreator(colorCreator = CieOppositeXyColorCreator())
        assertTrue { levelCreator.hues[0].stages[0].R == 255
                || levelCreator.hues[0].stages[0].G == 255
                || levelCreator.hues[0].stages[0].B == 255}
    }
}