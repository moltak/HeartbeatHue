package net.moltak.heartbeathue

import net.moltak.heartbeathue.logic.LevelCreator
import net.moltak.heartbeathue.logic.color.TimeAttackModeColorCreator
import org.junit.Test

/**
 * Created by moltak on 15. 12. 13..
 */
class GameRefereeTest {

    @Test
    public fun testGameMode() {
        val levelCreator = LevelCreator(TimeAttackModeColorCreator())
    }
}