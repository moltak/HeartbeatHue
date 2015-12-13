package net.moltak.heartbeathue.logic.color

import net.moltak.heartbeathue.logic.BulbColor

/**
 * Created by moltak on 15. 12. 13..
 */
class TimeAttackModeColorCreator(bulbCount: Int = 3, stageCount: Int = 3, fakeStage: Int = 15) : SpecialColorCreator {
    override val bulbCount: Int
    override val stageCount: Int
    val stageColorCreator = StageModeColorCreator()
    val fakeStage: Int

    init {
        this.bulbCount = bulbCount
        this.stageCount = stageCount
        this.fakeStage = fakeStage
    }

    // stage는 1부터 시작.
    override fun create(stage: Int): Array<BulbColor> {
        if (stage == 1) {
            return stageColorCreator.create(stage)
        }

        return stageColorCreator.create(fakeStage)
    }
}