package net.moltak.heartbeathue.logic.color

import net.moltak.heartbeathue.logic.HueStage

/**
 * Created by moltak on 15. 12. 8..
 */
class CieOppositeXyColorCreator : SpecialColorCreator {
    override var hueCount: Int = 0
        get() = field
        set(value) {
            field = value
        }

    override fun create(stage: Int): Array<HueStage> {
        return Array(hueCount, { HueStage(0, 0, 0)})
    }
}