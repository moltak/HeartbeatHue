package net.moltak.heartbeathue.logic.game

import net.moltak.heartbeathue.logic.LevelCreator
import net.moltak.heartbeathue.logic.color.PartialColorBlindnessCreator

/**
 * Created by moltak on 15. 12. 13..
 */
class GameReferee(levelCreator: LevelCreator) {
    val levelCreator: LevelCreator
    val blindnessReferee = ColorBlindnessReferee()
    var colorBlindnessResult: ColorBlindnessReferee.ColorBlindType? = null

    init {
        this.levelCreator = levelCreator
    }

    fun refereeing(selectedIndex: Int, stage: Int) : Result {
        if (levelCreator.specialColorCreator is PartialColorBlindnessCreator) {
            return partialColorBlindnessTestReferee(selectedIndex, stage)
        } else {
            return gameReferee(selectedIndex, stage)
        }
    }

    private fun partialColorBlindnessTestReferee(selectedIndex: Int, stage: Int): Result {
        blindnessReferee.choice(selectedIndex)

        if (stage == levelCreator.stageCount - 1) {
            colorBlindnessResult = blindnessReferee.testResult
            return Result.COMPLETE
        }

        return Result.NEXT
    }

    private fun gameReferee(selectedIndex: Int, stage: Int): Result {
        val bulb = levelCreator.stages[stage]

        for (i in 0..levelCreator.bulbCount - 1) {
            if (i == selectedIndex) continue

            if (bulb.bulbs[i].toInt() == bulb.bulbs[selectedIndex].toInt()) {
                return Result.GAME_OVER
            }
        }

        if (stage == levelCreator.stageCount - 1) {
            return Result.COMPLETE
        }

        return Result.NEXT
    }

    enum class Result {
        GAME_OVER, NEXT, COMPLETE
    }
}