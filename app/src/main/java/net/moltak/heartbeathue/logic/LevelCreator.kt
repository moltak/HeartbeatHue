package net.moltak.heartbeathue.logic

import java.util.*

/**
 * Created by engeng on 11/26/15.
 */
class LevelCreator {
    private val list: MutableList<Hues> = ArrayList()

    constructor() {
        // n = color - color * (1/stage^2)
        var i = 0
        val rand = Random()
        rand.setSeed(Date().time)

        while (i < 20) {
            var hues = Array(3, { i -> HueStage(0, 0, 0)})

            val r = rand.nextInt(255)
            val g = rand.nextInt(255)
            val b = rand.nextInt(255)

            var j = 0
            while (j < 3) {
                hues[j] = HueStage(r, g, b)
                j ++
            }

            list.add(Hues(hues))
            i ++
        }
    }

    fun getHues(): List<Hues> {
        return list
    }
}

