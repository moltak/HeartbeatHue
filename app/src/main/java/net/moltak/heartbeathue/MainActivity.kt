package net.moltak.heartbeathue

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import butterknife.ButterKnife
import butterknife.OnClick
import com.philips.lighting.hue.sdk.PHAccessPoint
import com.philips.lighting.hue.sdk.PHMessageType
import com.philips.lighting.model.PHBridge
import com.philips.lighting.model.PHHueError
import net.moltak.heartbeathue.library.bindView
import net.moltak.heartbeathue.logic.HueController
import net.moltak.heartbeathue.logic.HueSharedPreferences
import net.moltak.heartbeathue.logic.HueSimpleListener
import net.moltak.heartbeathue.logic.LevelCreator
import net.moltak.heartbeathue.logic.color.InverseExponencialColorCreator

public class MainActivity : AppCompatActivity() {

    private var hueController: HueController? = null
    private var hueCount = 0

    private val levelCreator = LevelCreator(colorCreator = InverseExponencialColorCreator(3, 20))
    private val textView: TextView by bindView(R.id.textView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
            textView.text = "fail!"
        }

        if (hueCount == levelCreator.stageCount - 1) hueCount = 0
        else hueCount ++
    }
}