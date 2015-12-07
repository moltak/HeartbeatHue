package net.moltak.heartbeathue.logic.color

import net.moltak.heartbeathue.logic.HueStage

/**
 * Created by engeng on 12/6/15.
 */
interface SpecialColorCreator {
    var hueCount: Int

    public fun create(stage: Int): Array<HueStage>
}