package net.moltak.heartbeathue.logic.color

import net.moltak.heartbeathue.logic.BulbColor

/**
 * Created by engeng on 12/6/15.
 */
interface SpecialColorCreator {
    var bulbCount: Int

    public fun create(stage: Int): Array<BulbColor>
}