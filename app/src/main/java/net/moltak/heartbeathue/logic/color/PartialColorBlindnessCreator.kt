package net.moltak.heartbeathue.logic.color

import net.moltak.heartbeathue.logic.BulbColor

/**
 * Created by moltak on 15. 12. 11..
 */
class PartialColorBlindnessCreator : SpecialColorCreator {
    override val bulbCount: Int = 3
    override val stageCount: Int = 24

    // stage가 1부터 들어옴.
    override fun create(stage: Int): Array<BulbColor> {
        return getBulbColor(stage - 1)
    }

    private fun getBulbColor(stage: Int): Array<BulbColor> {
        val array = Array(3, { BulbColor(0, 0, 0) })
        when (stage) {
            0 -> {
                array[0] = BulbColor(0xFF0000)
                array[1] = BulbColor(0x8F9022)
                array[2] = BulbColor(0xB93B00)
            }
            1 -> {
                array[0] = BulbColor(0xFF0000)
                array[1] = BulbColor(0xBA7E0B)
                array[2] = BulbColor(0xE6210B)
            }
            2 -> {
                array[0] = BulbColor(0xFF0000)
                array[1] = BulbColor(0xA6A536)
                array[2] = BulbColor(0xD73000)
            }
            3 -> {
                array[0] = BulbColor(0xFF0000)
                array[1] = BulbColor(0xB06609)
                array[2] = BulbColor(0xE61C0C)
            }
            4 -> {
                array[0] = BulbColor(0xFF0000)
                array[1] = BulbColor(0xE60000)
                array[2] = BulbColor(0xE40D0E)
            }
            5 -> {
                array[0] = BulbColor(0xFF0000)
                array[1] = BulbColor(0xEF0000)
                array[2] = BulbColor(0xF10D0F)
            }
            6 -> {
                array[0] = BulbColor(0xFF0000)
                array[1] = BulbColor(0x4D4D4D)
                array[2] = BulbColor(0x0068FE)
            }
            7 -> {
                array[0] = BulbColor(0xFF0000)
                array[1] = BulbColor(0x723E3D)
                array[2] = BulbColor(0xD9191A)
            }
            8 -> {
                array[0] = BulbColor(0x00FF00)
                array[1] = BulbColor(0x6F6F4A)
                array[2] = BulbColor(0xDB0064)
            }
            9 -> {
                array[0] = BulbColor(0x00FF00)
                array[1] = BulbColor(0x458131)
                array[2] = BulbColor(0x1ADC15)
            }
            10 -> {
                array[0] = BulbColor(0x00FF00)
                array[1] = BulbColor(0x595A4D)
                array[2] = BulbColor(0x810281)
            }
            11 -> {
                array[0] = BulbColor(0x00FF00)
                array[1] = BulbColor(0x4F993A)
                array[2] = BulbColor(0x18E415)
            }
            12 -> {
                array[0] = BulbColor(0x00FF00)
                array[1] = BulbColor(0x127574)
                array[2] = BulbColor(0x1C00DB)
            }
            13 -> {
                array[0] = BulbColor(0x00FF00)
                array[1] = BulbColor(0x0E9648)
                array[2] = BulbColor(0x10E219)
            }
            14 -> {
                array[0] = BulbColor(0x00FF00)
                array[1] = BulbColor(0x969696)
                array[2] = BulbColor(0xFE34FE)
            }
            15 -> {
                array[0] = BulbColor(0x00FF00)
                array[1] = BulbColor(0x77AB76)
                array[2] = BulbColor(0x21E423)
            }
            16 -> {
                array[0] = BulbColor(0x0000FF)
                array[1] = BulbColor(0x000092)
                array[2] = BulbColor(0x0020E1)
            }
            17 -> {
                array[0] = BulbColor(0x0000FF)
                array[1] = BulbColor(0x0000C3)
                array[2] = BulbColor(0x0D0FEC)
            }
            18 -> {
                array[0] = BulbColor(0x0000FF)
                array[1] = BulbColor(0x00007D)
                array[2] = BulbColor(0x0026DC)
            }
            19 -> {
                array[0] = BulbColor(0x0000FF)
                array[1] = BulbColor(0x0000BC)
                array[2] = BulbColor(0x0D0FED)
            }
            20 -> {
                array[0] = BulbColor(0x0000FF)
                array[1] = BulbColor(0x078B8B)
                array[2] = BulbColor(0x01FE25)
            }
            21 -> {
                array[0] = BulbColor(0x0000FF)
                array[1] = BulbColor(0x026AB6)
                array[2] = BulbColor(0x0D1BE7)
            }
            22 -> {
                array[0] = BulbColor(0x0000FF)
                array[1] = BulbColor(0x1D1D1D)
                array[2] = BulbColor(0x910000)
            }
            23 -> {
                array[0] = BulbColor(0x0000FF)
                array[1] = BulbColor(0x17174C)
                array[2] = BulbColor(0x1212D3)
            }
        }

        return array
    }
}