package net.moltak.heartbeathue

import net.moltak.heartbeathue.logic.LevelCreator
import net.moltak.heartbeathue.logic.color.InverseExponencialColorCreator
import org.junit.Test
import kotlin.test.*

/**
 * Created by engeng on 11/26/15.
 */
class LevelUnitTest {

    @Test
    @Throws(Exception::class)
    fun levelObjectHasBeenCreated() {
        val levelCreator = LevelCreator(colorCreator = InverseExponencialColorCreator(3))
        assertNotNull(levelCreator)
    }

    @Test
    @Throws(Exception::class)
    fun levelObjectHasHad20Stages() {
        var levelCreator = LevelCreator(colorCreator = InverseExponencialColorCreator(3))
        assertEquals(20, levelCreator.stages.size)
    }

    @Test
    @Throws(Exception::class)
    fun stagesHaveADifferentColors() {
        var levelCreator = LevelCreator(colorCreator = InverseExponencialColorCreator(3))
        assertTrue(levelCreator.stages[0].bulbs[0].R != levelCreator.stages[1].bulbs[1].R)
    }

    @Test
    @Throws(Exception::class)
    fun stagHas2SameColors1DifferentColor() {
        var levelCreator = LevelCreator(colorCreator = InverseExponencialColorCreator(3))

        val hues = levelCreator.stages[10]
        val hue0 = hues.bulbs[0]
        val hue1 = hues.bulbs[1]
        val hue2 = hues.bulbs[2]

        assertFalse((hue0.R == hue1.R) && (hue1.R == hue2.R) && (hue2.R == hue0.R))
    }

    @Test
    @Throws(Exception::class)
    fun stageHas4ChannelColor() {
        val levelCreator = LevelCreator(colorCreator = InverseExponencialColorCreator(3))

        val hues = levelCreator.stages[10]
        assertNotEquals(0, hues.bulbs[0].R)
        assertNotEquals(0, hues.bulbs[0].G)
        assertNotEquals(0, hues.bulbs[0].B)
    }

    @Test
    fun specialColorCreatorShouldHasHueCount() {
        val levelCreator = LevelCreator(colorCreator = InverseExponencialColorCreator(3))
        assertEquals(levelCreator.bulbCount,
                (levelCreator.specialColorCreator as InverseExponencialColorCreator).bulbCount)
    }
}