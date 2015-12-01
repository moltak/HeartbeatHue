package net.moltak.heartbeathue.logic

import com.philips.lighting.hue.sdk.PHAccessPoint
import com.philips.lighting.hue.sdk.PHBridgeSearchManager
import com.philips.lighting.hue.sdk.PHHueSDK
import com.philips.lighting.hue.sdk.PHSDKListener
import com.philips.lighting.model.PHBridge
import com.philips.lighting.model.PHHueParsingError

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
    }

    fun connect(): Boolean {
        phHueSDK.notificationManager.registerSDKListener(simpleListener)

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

    private val simpleListener = object : HueSimpleListener() {
        override fun onAccessPointsFound(list: List<PHAccessPoint>) {
            listener.onAccessPointsFound(list)
        }

        override fun onCacheUpdated(list: List<Int>, phBridge: PHBridge) {
            listener.onCacheUpdated(list, phBridge)
        }

        override fun onBridgeConnected(phBridge: PHBridge, s: String) {
            listener.onBridgeConnected(phBridge, s)
        }

        override fun onAuthenticationRequired(phAccessPoint: PHAccessPoint) {
            listener.onAuthenticationRequired(phAccessPoint)
        }

        override fun onConnectionResumed(phBridge: PHBridge) {
            listener.onConnectionResumed(phBridge)
        }

        override fun onConnectionLost(phAccessPoint: PHAccessPoint) {
            listener.onConnectionLost(phAccessPoint)
        }

        override fun onError(i: Int, s: String) {
            listener.onError(i, s)
        }

        override fun onParsingErrors(list: List<PHHueParsingError>) {
            listener.onParsingErrors(list)
        }
    }
}