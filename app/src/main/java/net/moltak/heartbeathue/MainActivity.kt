package net.moltak.heartbeathue

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import butterknife.ButterKnife
import butterknife.OnClick
import com.philips.lighting.hue.sdk.PHAccessPoint
import com.philips.lighting.hue.sdk.PHMessageType
import com.philips.lighting.model.PHBridge
import com.philips.lighting.model.PHHueError
import net.moltak.heartbeathue.library.bindView
import net.moltak.heartbeathue.logic.*
import net.moltak.heartbeathue.logic.color.CieOppositeXyColorCreator
import net.moltak.heartbeathue.logic.color.PartialColorBlindnessCreator

public class MainActivity : AppCompatActivity() {

    private var hueController: HueController? = null
    private var hueCount = 0

    private val levelCreator = LevelCreator(colorCreator = PartialColorBlindnessCreator())
    private val textView: TextView by bindView(R.id.textView)
    private val button1: Button by bindView(R.id.button1)
    private val button2: Button by bindView(R.id.button2)
    private val button3: Button by bindView(R.id.button3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stage)
        ButterKnife.bind(this)

        hueController = HueController(HueSharedPreferences.getInstance(this), listener, levelCreator)
        if (hueController?.connectToLastAccessPoint() == false) {
            hueController?.searchBridge()
        }
    }

    override fun onDestroy() {
        hueController?.disconnect()
        super.onDestroy()
    }

    private val listener = object : HueSimpleListener() {
        override fun onAuthenticationRequired(phAccessPoint: PHAccessPoint) {
            changeText("onAuthenticationRequired")
        }

        override fun onConnectionResumed(phBridge: PHBridge) {
//            changeText("onConnectionResumed")
        }

        override fun onConnectionLost(phAccessPoint: PHAccessPoint) {
            changeText("onConnectionLost")
        }

        override fun onBridgeConnected(phBridge: PHBridge, s: String) {
            changeText("onBridgeConnected")
        }

        override fun onCacheUpdated(list: List<Int>, phBridge: PHBridge) {
//            changeText("onCacheUpdated")
        }

        override fun onError(code: Int, msg: String) {
            when (code) {
                PHHueError.NO_CONNECTION -> changeText("On No Connection")
                PHHueError.AUTHENTICATION_FAILED,
                PHMessageType.PUSHLINK_AUTHENTICATION_FAILED -> changeText("Authentication Failed")
                PHHueError.BRIDGE_NOT_RESPONDING -> changeText("Bridge Not Responding...")
                PHMessageType.BRIDGE_NOT_FOUND -> changeText("Bridge Not Found")
                else -> changeText("on Error Called : $code : $msg")
            }
        }
    }

    private fun changeText(text: String) {
        runOnUiThread { textView.text = text }
    }

    @OnClick(R.id.buttonChangeColor)
    public fun onChangeColorButtonClicked() {
        if (hueController?.changeTheColor(levelCreator.stages[hueCount]) ?: false) {
            textView.text = "Stage: -> ${hueCount + 1}, color changed!"
        } else {
            textView.text = "Stage: -> ${hueCount + 1}, color fail!"
        }

        changeButtonColor(levelCreator.stages[hueCount])

        if (hueCount == levelCreator.stageCount - 1) hueCount = 0
        else hueCount ++

    }

    private fun changeButtonColor(bulb: Bulb) {
        button1.setBackgroundColor(bulb.bulbs[0].toInt() ?: android.R.color.black)
        button2.setBackgroundColor(bulb.bulbs[1].toInt() ?: android.R.color.black)
        button3.setBackgroundColor(bulb.bulbs[2].toInt() ?: android.R.color.black)
    }
}