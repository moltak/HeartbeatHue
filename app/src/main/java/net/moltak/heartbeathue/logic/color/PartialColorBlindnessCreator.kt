package net.moltak.heartbeathue.logic.color

import net.moltak.heartbeathue.logic.BulbColor

/**
 * Created by moltak on 15. 12. 11..
 */
class PartialColorBlindnessCreator : SpecialColorCreator {
    override val bulbCount: Int = 3
    override val stageCount: Int = 8

    // stage가 1부터 들어옴.
    override fun create(stage: Int): Array<BulbColor> {
        val bubls = Array(bulbCount, { BulbColor(0, 0, 0) })

        for (i in 0..bulbCount - 1) {
            val bulb = getBulbColor(i, stage - 1)
            bubls.set(i, bulb)
        }

        return bubls
    }

    private fun getBulbColor(type: Int, stage: Int): BulbColor {
        if (type == 0) {
            when (stage) {
                0 -> return BulbColor(0xFF0000, 0x8F9022, 0xB93B00)
                1 -> return BulbColor(0xFF0000, 0xBA7E0B, 0xE6210B)
                2 -> return BulbColor(0xFF0000, 0xA6A536, 0xD73000)
                3 -> return BulbColor(0xFF0000, 0xB06609, 0xE61C0C)
                4 -> return BulbColor(0xFF0000, 0xE60000, 0xE40D0E)
                5 -> return BulbColor(0xFF0000, 0xEF0000, 0xF10D0F)
                6 -> return BulbColor(0xFF0000, 0x4D4D4D, 0x0068FE)
                else -> return BulbColor(0xFF0000, 0x723E3D, 0xD9191A)
            }
        } else if (type == 1) {
            when (stage) {
                0 -> return BulbColor(0x00FF00, 0x6F6F4A, 0xDB0064)
                1 -> return BulbColor(0x00FF00, 0x458131, 0x1ADC15)
                2 -> return BulbColor(0x00FF00, 0x595A4D, 0x810281)
                3 -> return BulbColor(0x00FF00, 0x4F993A, 0x18E415)
                4 -> return BulbColor(0x00FF00, 0x127574, 0x1C00DB)
                5 -> return BulbColor(0x00FF00, 0x0E9648, 0x10E219)
                6 -> return BulbColor(0x00FF00, 0x969696, 0xFE34FE)
                else -> return BulbColor(0x00FF00, 0x77AB76, 0x21E423)
            }
        } else {
            when (stage) {
                0 -> return BulbColor(0x0000FF, 0x000092, 0x0020E1)
                1 -> return BulbColor(0x0000FF, 0x0000C3, 0x0D0FEC)
                2 -> return BulbColor(0x0000FF, 0x00007D, 0x0026DC)
                3 -> return BulbColor(0x0000FF, 0x0000BC, 0x0D0FED)
                4 -> return BulbColor(0x0000FF, 0x078B8B, 0x01FE25)
                5 -> return BulbColor(0x0000FF, 0x026AB6, 0x0D1BE7)
                6 -> return BulbColor(0x0000FF, 0x1D1D1D, 0x910000)
                else -> return BulbColor(0x0000FF, 0x17174C, 0x1212D3)
            }
        }
    }
}