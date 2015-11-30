package net.moltak.heartbeathue.logic

import java.util.*

/**
 * Created by engeng on 11/26/15.
 */
class LevelCreator {
    val list: MutableList<HueStage> = ArrayList()

    constructor() {
        // n = color - color * (1/stage^2)
        list.add(HueStage(0, 0, 0))
    }

    fun getStages(): List<HueStage> {
        return list
    }
}

