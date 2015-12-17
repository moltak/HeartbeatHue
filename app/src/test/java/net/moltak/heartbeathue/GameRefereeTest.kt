package net.moltak.heartbeathue

import net.moltak.heartbeathue.logic.LevelCreator
import net.moltak.heartbeathue.logic.color.TimeAttackModeColorCreator
import net.moltak.heartbeathue.logic.game.GameReferee
import org.junit.Test
import kotlin.test.assertTrue

/**
 * Created by moltak on 15. 12. 13..
 */
class GameRefereeTest {

    @Test
    public fun testGameMode() {
        val levelCreator = LevelCreator(TimeAttackModeColorCreator())
        val gameReferee = GameReferee(levelCreator)

        for (stage in 0..levelCreator.stageCount - 1) {
            val selectedColor = findSingleColor(levelCreator, stage)
            println("Stage: ${stage + 1} --> selctedColor: $selectedColor")
//            assertTrue(gameReferee.refereeing(selectedColor, stage))
        }
    }

    private fun findSingleColor(levelCreator: LevelCreator, stage: Int): Int {
        val bubls = levelCreator.stages[stage]

        for (i in 0..bubls.bulbs.size - 1) {
            var sameCount = 0

            for (j in 0..bubls.bulbs.size - 1) {
                if (i == j) continue

                if (bubls.bulbs[i].toInt() == bubls.bulbs[j].toInt()) {
                    sameCount ++
                }

                if (sameCount == bubls.bulbs.size - 2) {
                    break;
                }
            }

            if (sameCount == 0) {
                return i
            }
        }

        return -1
    }
}