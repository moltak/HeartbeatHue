package net.moltak.heartbeathue

import net.moltak.heartbeathue.logic.LevelCreator
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

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
    fun levelObjectHasHad10Stages() {
        var levelCreator = LevelCreator()
        assertEquals(0, levelCreator.getStages().get(0).R)
    }
}