package net.moltak.heartbeathue.logic.color

import net.moltak.heartbeathue.logic.BulbColor
import net.moltak.heartbeathue.util.ColorConverter
import java.util.*

/**
 * Created by moltak on 15. 12. 8..
 * @link http://www.developers.meethue.com/documentation/supported-lights#colorTemperatureLight
 */
class StageModeColorCreator(bulbCount: Int = 3, stageCount: Int = 20, modelNumber: String = "LCT001") : SpecialColorCreator {
    override val bulbCount: Int
    override val stageCount: Int
    val modelNumber: String
    val colorConverter = ColorConverter()

    // white: 0.3227,0.329
    // 0.3944,0.3093
    val gamutCenterX = 0.3944f //0.21f
    val gamutCenterY = 0.3093f //0.29f

    init {
        this.bulbCount = bulbCount
        this.modelNumber = modelNumber
        this.stageCount = stageCount
    }

    val MAX_COLOR = 255

    val rand = Random()

    // stage는 1부터 시작.
    override fun create(stage: Int): Array<BulbColor> {
        if (stage == 1) {
            return createCalibrationBulb()
        }

        val min = createMinimumValue(stage)

        val r = createRandomValueGreaterThanMin(min, rand)
        val g = createRandomValueGreaterThanMin(min, rand)
        val b = createRandomValueGreaterThanMin(min, rand)

        val bulbs = Array(bulbCount, { BulbColor(r, g, b)})
        bulbs[r % bulbCount] = createSpecialBulbColor(bulbs[0], stage)
        return bulbs
    }

    private fun createCalibrationBulb(): Array<BulbColor> {
        val bulbs = Array(bulbCount, { BulbColor(0, 0, 0)})
        bulbs[0] = BulbColor(255, 0, 0)
        bulbs[1] = BulbColor(0, 255, 0)
        bulbs[2] = BulbColor(0, 0, 255)
        return bulbs
    }

    private fun createMinimumValue(stage: Int): Int {
        return ((MAX_COLOR / stage) - (MAX_COLOR / stageCount))
    }

    private fun createRandomValueGreaterThanMin(min: Int, rand: Random): Int {
        while (true) {
            val v = rand.nextInt(MAX_COLOR)
            if (v >= min) {
                return v
            }
        }
    }

    //    private fun createSpecialBulbColor(bulbColor: BulbColor, stage: Int): BulbColor {
    //        val xy = colorConverter.toXY(bulbColor.R, bulbColor.G, bulbColor.B, modelNumber)
    //        val x2 = (gamutCenterX * 2) - (xy[0] / stage)
    //        val y2 = (gamutCenterY * 2) - (xy[1] / stage)
    //        val newXY = FloatArray(2)
    //        newXY[0] = x2
    //        newXY[1] = y2
    //
    //        return colorConverter.toRGB(newXY, modelNumber)
    //    }

    private fun createSpecialBulbColor(bulb: BulbColor, stage: Int): BulbColor {
        var rgb = IntArray(3)
        rgb[0] = bulb.R
        rgb[1] = bulb.G
        rgb[2] = bulb.B
        // create special color
        // n = color - color * (1/stage^2), inverse exponential,
        var exponential: Double = 1.0 / (stage * stage)
        if (exponential == 1.0) {
            // 처음 값이 무조건 1이 나오는데 이때 편차가 너무 크므로 조금 줄임.
            exponential = 0.8
        }

        if (stage / stageCount >= 0.3f) { // 3개 다 섞기
            rgb = shuffle3(rgb)
        } else if (stage / stageCount >= 0.6f) { // 2개 섞기
            rgb = shuffle2(rgb)
        } else if (stage / stageCount >= 0.8f){ // 1개 값 변경
            rgb = shuffle1(rgb)
        }

        return BulbColor(
                (rgb[0] - rgb[0] * exponential).toInt(),
                (rgb[1] - rgb[1] * exponential).toInt(),
                (rgb[2] - rgb[2] * exponential).toInt())
    }

    private fun shuffle3(rgb: IntArray): IntArray {
        var rgbTemp = IntArray(3)
        rgbTemp[0] = rgb[1]
        rgbTemp[1] = rgb[2]
        rgbTemp[2] = rgb[0]
        return rgbTemp
    }

    private fun shuffle2(rgb: IntArray): IntArray {
        var rgbTemp = IntArray(3)
        rgbTemp[0] = rgb[0]
        rgbTemp[1] = rgb[2]
        rgbTemp[2] = rgb[1]
        return rgbTemp
    }

    private fun shuffle1(rgb: IntArray): IntArray {
        var rgbTemp = IntArray(3)
        rgbTemp[0] = rgb[0]
        rgbTemp[1] = rgb[1]
        rgbTemp[2] = rgb[2]
        rgbTemp[2] = ((rgbTemp[0] + rgbTemp[1] + rgbTemp[2]).toDouble() % MAX_COLOR).toInt()
        return rgbTemp
    }
}