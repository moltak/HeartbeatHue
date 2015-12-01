package net.moltak.heartbeathue.logic

import com.philips.lighting.hue.sdk.PHAccessPoint
import com.philips.lighting.hue.sdk.PHBridgeSearchManager
import com.philips.lighting.hue.sdk.PHHueSDK
import com.philips.lighting.hue.sdk.PHSDKListener

/**
 * Created by engeng on 12/1/15.
 */
class HueController(sharedPreferences: HueSharedPreferences, phdSdkPHSDKListener: PHSDKListener) {
    private val phHueSDK: PHHueSDK
    private val sharedPreferences: HueSharedPreferences
    private val listener: PHSDKListener

    init {
        this.sharedPreferences = sharedPreferences
        this.listener = phdSdkPHSDKListener

        phHueSDK = PHHueSDK.create()
        phHueSDK.appName = "HeartbeatHue"
        phHueSDK.deviceName = android.os.Build.MODEL
        phHueSDK.notificationManager.registerSDKListener(phdSdkPHSDKListener)
    }

    fun connect(): Boolean {
        if (sharedPreferences.lastConnectedIPAddress.length != 0
                && sharedPreferences.username.length != 0) {

            val lastAccessPoint = PHAccessPoint();
            lastAccessPoint.ipAddress = sharedPreferences.lastConnectedIPAddress;
            lastAccessPoint.username = sharedPreferences.username;

            if (!phHueSDK.isAccessPointConnected(lastAccessPoint)) {
                phHueSDK.connect(lastAccessPoint);
                return true
            }
        }

        return false
    }

    fun disconnect() {
        phHueSDK.notificationManager.unregisterSDKListener(listener);
        phHueSDK.disableAllHeartbeat();
    }

    fun searchBridge() {
        val sm: PHBridgeSearchManager = phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE) as PHBridgeSearchManager;
        sm.search(true, true);
    }
}