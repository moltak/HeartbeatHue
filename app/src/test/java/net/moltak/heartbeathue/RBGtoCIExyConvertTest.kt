package net.moltak.heartbeathue

import net.moltak.heartbeathue.logic.LevelCreator
import net.moltak.heartbeathue.logic.color.InverseExponencialColorCreator
import net.moltak.heartbeathue.util.ColorConverter
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Created by engeng on 12/6/15.
 * http://www.developers.meethue.com/documentation/core-concepts
 *
 *
 * http://www.developers.meethue.com/documentation/supported-lights
 * Color	Hue	    x	        y
 * Red	     0	  0.675	  0.322
 * Green	100	  0.409	  0.518
 * Blue	    184	  0.167	  0.04
 */
class RBGtoCIExyConvertTest {

    val LESS_X = 0.167f
    val BIGGER_X = 0.675f

    val LESS_Y = 0.04f
    val BIGGER_Y = 0.518f

    @Test
    public fun shouldTheColorIsInX08Y08() {
        // http://www.developers.meethue.com/documentation/hue-xy-values 에 있는 Gamut2 와 같아야함.
        val modelNumber = "LCT001"
        val converter = ColorConverter()
        var xy = converter.toXY(239, 247, 255, modelNumber)
        assertEquals(xy[0], 0.3092f)
        assertEquals(xy[1], 0.321f)

        xy = converter.toXY(188, 183, 107, modelNumber)
        assertEquals(xy[0], 0.4004f)
        assertEquals(xy[1], 0.4331f)

        xy = converter.toXY(0, 255, 0, modelNumber)
        assertEquals(xy[0], 0.408f)
        assertEquals(xy[1], 0.517f)
    }

    @Test
    public fun shouldReturnNo0() {
        for (testCount in 0..10) {
            stageColorToCIECovertTest()
        }
    }

    private fun stageColorToCIECovertTest() {
        val levelCreator = LevelCreator(colorCreator = InverseExponencialColorCreator(3))
        val converter = ColorConverter()

        for (i in 0..levelCreator.bulbCount - 1) {
            for (j in 0..levelCreator.stages[i].bulbs.size - 1) {
                val stage = levelCreator.stages[i].bulbs[j]
                val xy = converter.toXY(stage.R, stage.G, stage.B, "LCT001")

                assertTrue(xy[0] >= LESS_X, "The x is must be bigger than $LESS_X but x is ${xy[0]} i=$i, j=$j")
                assertTrue(xy[0] <= BIGGER_X, "The x is must be smaller than $BIGGER_X but x is ${xy[0]} i=$i, j=$j")

                assertTrue(xy[1] >= LESS_Y, "The y is must be smaller than $LESS_Y but x is ${xy[1]} i=$i, j=$j")
                assertTrue(xy[1] <= BIGGER_Y, "The y is must be bigger than $BIGGER_Y but x is ${xy[1]} i=$i, j=$j")
            }
        }
    }
}