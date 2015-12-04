package net.moltak.heartbeathue

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import butterknife.ButterKnife
import butterknife.OnClick
import com.philips.lighting.hue.sdk.PHAccessPoint
import com.philips.lighting.model.PHBridge
import net.moltak.heartbeathue.library.bindView
import net.moltak.heartbeathue.logic.HueController
import net.moltak.heartbeathue.logic.HueSharedPreferences
import net.moltak.heartbeathue.logic.HueSimpleListener
import net.moltak.heartbeathue.logic.LevelCreator

public class MainActivity : AppCompatActivity() {

    private var hueController: HueController? = null

    private val levelCreator = LevelCreator()
    private val textView: TextView by bindView(R.id.textView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        hueController = HueController(HueSharedPreferences.getInstance(this), listener, LevelCreator(10, 20))
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
            changeText("onConnectionResumed")
        }

        override fun onConnectionLost(phAccessPoint: PHAccessPoint) {
            changeText("onConnectionLost")
        }

        override fun onBridgeConnected(phBridge: PHBridge, s: String) {
            changeText("onBridgeConnected")
        }

        override fun onCacheUpdated(list: List<Int>, phBridge: PHBridge) {
            changeText("onCacheUpdated")
        }
    }

    private fun changeText(text: String) {
        runOnUiThread { textView.text = text }
    }

    @OnClick(R.id.buttonChangeColor)
    public fun onChangeColorButtonClicked() {
        if (hueController?.changeTheColor(levelCreator.getHues()[10]) ?: false) {
            textView.text = "color changed!"
        } else {
            textView.text = "fail!"
        }
    }
}