package net.moltak.heartbeathue.logic.color

import net.moltak.heartbeathue.logic.BulbColor

/**
 * Created by moltak on 15. 12. 11..
 */
class PartialColorBlindnessCreator : SpecialColorCreator {
    override val bulbCount: Int = 3
    override val stageCount: Int = 9

    // stage가 1부터 들어옴.
    override fun create(stage: Int): Array<BulbColor> {
        return getBulbColor(stage - 1)
    }

    private fun getBulbColor(stage: Int): Array<BulbColor> {
        val array = Array(3, { BulbColor(0, 0, 0) })
        when (stage) {
            0 -> {
                array[0] = BulbColor(0xFF0000)
                array[1] = BulbColor(0x4D4D4D)
                array[2] = BulbColor(0x0068FE)
            }
            1 -> {
                array[0] = BulbColor(0x723E3D)
                array[1] = BulbColor(0xD9191A)
                array[2] = BulbColor(0xFF0000)
            }
            2 -> {
                array[0] = BulbColor(0x00FF00)
                array[1] = BulbColor(0x21E423)
                array[2] = BulbColor(0x77AB76)
            }
            3 -> {
                array[0] = BulbColor(0x0000FF)
                array[1] = BulbColor(0x078B8B)
                array[2] = BulbColor(0x01FE25)
            }
            4 -> {
                array[0] = BulbColor(0x8F9022)
                array[1] = BulbColor(0xB93B00)
                array[2] = BulbColor(0xFF0000)
            }
            5 -> {
                array[0] = BulbColor(0xE6210B)
                array[1] = BulbColor(0xFF0000)
                array[2] = BulbColor(0xBA7E0B)
            }
            6 -> {
                array[0] = BulbColor(0xFF0000)
                array[1] = BulbColor(0xA6A536)
                array[2] = BulbColor(0xD73000)
            }
            7 -> {
                array[0] = BulbColor(0xB06609)
                array[1] = BulbColor(0xFF0000)
                array[2] = BulbColor(0xE61C0C)
            }
            8 -> {
                array[0] = BulbColor(0x00FF00)
                array[1] = BulbColor(0x4F993A)
                array[2] = BulbColor(0x18E415)
            }
        }

        return array
    }
}