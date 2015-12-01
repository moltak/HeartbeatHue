package net.moltak.heartbeathue

import net.moltak.heartbeathue.logic.LevelCreator
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

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
        assertEquals(20, levelCreator.getHues().size)
    }

    @Test
    @Throws(Exception::class)
    fun stagesHaveADifferentColors() {
        var levelCreator = LevelCreator()
        assertTrue(levelCreator.getHues().get(0).hues.get(0).R != levelCreator.getHues().get(1).hues.get(1).R)
    }

    @Test
    @Throws(Exception::class)
    fun stagHas2SameColors1DifferentColor() {
        var levelCreator = LevelCreator()

        val hues = levelCreator.getHues()[0]
        val hue0 = hues.hues[0]
        val hue1 = hues.hues[1]
        val hue2 = hues.hues[2]

        assertFalse((hue0.R == hue1.R) && (hue1.R == hue2.R) && (hue2.R == hue0.R))
    }
}