package net.moltak.heartbeathue

import net.moltak.heartbeathue.logic.LevelCreator
import org.junit.Test
import kotlin.test.*

/**
 * Created by engeng on 11/26/15.
 */
class LevelUnitTest {

    @Test
    @Throws(Exception::class)
    fun levelObjectHasBeenCreated() {
        val levelCreator = LevelCreator()
        assertNotNull(levelCreator)
    }

    @Test
    @Throws(Exception::class)
    fun levelObjectHasHad20Stages() {
        var levelCreator = LevelCreator()
        assertEquals(20, levelCreator.hues.size)
    }

    @Test
    @Throws(Exception::class)
    fun stagesHaveADifferentColors() {
        var levelCreator = LevelCreator(3, 20)
        assertTrue(levelCreator.hues[0].stages[0].R != levelCreator.hues[1].stages[1].R)
    }

    @Test
    @Throws(Exception::class)
    fun stagHas2SameColors1DifferentColor() {
        var levelCreator = LevelCreator()

        val hues = levelCreator.hues[10]
        val hue0 = hues.stages[0]
        val hue1 = hues.stages[1]
        val hue2 = hues.stages[2]

        assertFalse((hue0.R == hue1.R) && (hue1.R == hue2.R) && (hue2.R == hue0.R))
    }

    @Test
    @Throws(Exception::class)
    fun stageHas4ChannelColor() {
        val levelCreator = LevelCreator()

        val hues = levelCreator.hues[10]
        assertNotEquals(0, hues.stages[0].A)
        assertNotEquals(0, hues.stages[0].R)
        assertNotEquals(0, hues.stages[0].G)
        assertNotEquals(0, hues.stages[0].B)
    }
}