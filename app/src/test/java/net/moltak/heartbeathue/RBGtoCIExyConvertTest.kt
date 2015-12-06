package net.moltak.heartbeathue

import net.moltak.heartbeathue.util.ColorConverter
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by engeng on 12/6/15.
 */
class RBGtoCIExyConvertTest {
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
    }
}