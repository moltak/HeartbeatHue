package net.moltak.heartbeathue

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders
import com.philips.lighting.hue.sdk.PHAccessPoint
import com.philips.lighting.hue.sdk.PHMessageType
import com.philips.lighting.model.PHBridge
import com.philips.lighting.model.PHHueError
import net.moltak.heartbeathue.library.bindView
import net.moltak.heartbeathue.logic.HueController
import net.moltak.heartbeathue.logic.HueSharedPreferences
import net.moltak.heartbeathue.logic.HueSimpleListener

/**
 * Created by moltak on 15. 12. 12..
 */
class ModeSelectActivity : AppCompatActivity() {

    private val textView: TextView by bindView(R.id.textView)
    private val circularFillableLoaders: CircularFillableLoaders by bindView(R.id.circularFillableLoaders)
    private var hueController: HueController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode_select);
        ButterKnife.bind(this)

        hueController = HueController(HueSharedPreferences.getInstance(this), listener)
        (application as HueApplication).hueController = hueController
        if (hueController?.connectToLastAccessPoint() == false) {
            startActivity(Intent(this, PHHomeActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        changeConnectedUi()
    }

    private fun changeConnectedUi() {
        if (hueController!!.isConnect) {
            circularFillableLoaders.setProgress(95)
            circularFillableLoaders.setAmplitudeRatio(0.06f)
            circularFillableLoaders.setImageResource(R.drawable.philips_hue_color_light_on)
        } else {
            circularFillableLoaders.setProgress(50)
            circularFillableLoaders.setImageResource(R.drawable.philips_hue_color_light)
        }
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
            }
        }
    }

    private fun changeText(text: String) {
        runOnUiThread { textView.text = text }
    }

    override fun onDestroy() {
        hueController?.disconnect()
        super.onDestroy()
    }

    @OnClick(R.id.buttonStage)
    public fun onStageButtonClicked() {
        startGameActivity(0)
    }

    @OnClick(R.id.buttonTimeAttack)
    public fun onTimeAttackButtonClicked() {
        startGameActivity(1)
    }

    @OnClick(R.id.buttonColorBlindness)
    public fun onColorBlindnessButtonClicked() {
        startGameActivity(2)
    }

    private fun startGameActivity(mode: Int) {
        val i = Intent(this, GameActivity::class.java)
        i.putExtra("mode", mode)
        startActivity(i)
    }
}