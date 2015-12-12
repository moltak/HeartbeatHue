package net.moltak.heartbeathue

import net.moltak.heartbeathue.logic.color.PartialColorBlindnessCreator
import org.junit.Test

/**
 * Created by moltak on 15. 12. 11..
 */
class PartialColorBlindnessCreatorTest {
    @Test
    fun testCreate() {
        val cretor = PartialColorBlindnessCreator()
        for (i in 0..8) {
            cretor.create(i)
        }
    }
}