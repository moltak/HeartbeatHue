package net.moltak.heartbeathue.logic.game

import net.moltak.heartbeathue.logic.LevelCreator
import net.moltak.heartbeathue.logic.color.PartialColorBlindnessCreator

/**
 * Created by moltak on 15. 12. 13..
 */
class GameReferee(levelCreator: LevelCreator) {
    val levelCreator: LevelCreator

    init {
        this.levelCreator = levelCreator
    }

    fun refereeing(selectedIndex: Int, stage: Int) : Boolean {
        if (levelCreator.specialColorCreator is PartialColorBlindnessCreator) {
            return partialReferee(selectedIndex, stage)
        } else {
            return referee(selectedIndex, stage)
        }
    }

    private fun partialReferee(selectedIndex: Int, stage: Int): Boolean {
        return false
    }

    private fun referee(selectedIndex: Int, stage: Int): Boolean {
        val bulb = levelCreator.stages[stage]

        for (i in 0..levelCreator.bulbCount - 1) {
            if (bulb.bulbs[i].toInt() != bulb.bulbs[selectedIndex].toInt()) {
                return false
            }
        }

        return true
    }
}