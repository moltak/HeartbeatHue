package net.moltak.heartbeathue

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.philips.lighting.hue.sdk.PHHueSDK
import com.philips.lighting.model.PHBridge
import net.moltak.heartbeathue.logic.HueSharedPreferences

public class MainActivity : AppCompatActivity() {

    private var phHueSdk: PHHueSDK? = null
    private var hueSharedPreference : HueSharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        phHueSdk = PHHueSDK.create();
        hueSharedPreference = HueSharedPreferences.getInstance(this)
    }

    override fun onDestroy() {
        var bridge : PHBridge? = phHueSdk?.selectedBridge

        if (bridge != null) {
            if ((phHueSdk as PHHueSDK).isHeartbeatEnabled(bridge)) {
                (phHueSdk as PHHueSDK).disableHeartbeat(bridge)
            }

            (phHueSdk as PHHueSDK).disconnect(bridge)
        }

        super.onDestroy()
    }
}
