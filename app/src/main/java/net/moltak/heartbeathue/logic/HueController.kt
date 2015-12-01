package net.moltak.heartbeathue.logic

import android.util.Log
import com.philips.lighting.hue.sdk.PHAccessPoint
import com.philips.lighting.hue.sdk.PHBridgeSearchManager
import com.philips.lighting.hue.sdk.PHHueSDK
import com.philips.lighting.hue.sdk.PHSDKListener
import com.philips.lighting.model.PHBridge
import com.philips.lighting.model.PHHueParsingError
import com.philips.lighting.model.PHLightState

/**
 * Created by engeng on 12/1/15.
 */
class HueController(sharedPreferences: HueSharedPreferences, phdSdkPHSDKListener: PHSDKListener) {
    private val phHueSDK: PHHueSDK
    private val sharedPreferences: HueSharedPreferences
    private val listener: PHSDKListener

    private val TAG = "HUE_TAG"
    private val USERNAME = "HeartbeatHue"

    init {
        this.sharedPreferences = sharedPreferences
        this.listener = phdSdkPHSDKListener

        phHueSDK = PHHueSDK.create()
        phHueSDK.appName = "HeartbeatHue"
        phHueSDK.deviceName = android.os.Build.MODEL
    }

    fun connectToLastAccessPoint(): Boolean {
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

    fun changeTheColor(hues: Hues) {
        val bridge = phHueSDK.selectedBridge
        val size = bridge?.resourceCache?.allLights?.size ?: return

        for (i in 0..size) {
            if (i == 3) break // I had have 3 hues.

            val lightState = PHLightState()
            lightState.hue = hues.hues[i].toInt()
            bridge.updateLightState(bridge.resourceCache.allLights[i], lightState);
        }
    }

    fun searchBridge() {
        val sm: PHBridgeSearchManager = phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE) as PHBridgeSearchManager;
        sm.search(true, true);
    }

    private fun connectToAccessPoints(accessPoint: PHAccessPoint) {
        val connectedBridge: PHBridge = phHueSDK.selectedBridge

        val connectedIP = connectedBridge.resourceCache.bridgeConfiguration.ipAddress;
        if (connectedIP != null) {   // We are already connected here:-
            phHueSDK.disableHeartbeat(connectedBridge);
            phHueSDK.disconnect(connectedBridge);
        }

        phHueSDK.connect(accessPoint);
    }

    private val simpleListener = object : HueSimpleListener() {
        override fun onAccessPointsFound(list: List<PHAccessPoint>) {
            if (list.size > 0) connectToAccessPoints(list[0])
            listener.onAccessPointsFound(list)
        }

        override fun onCacheUpdated(list: List<Int>, phBridge: PHBridge) {
            Log.w(TAG, "On CacheUpdated");

            listener.onCacheUpdated(list, phBridge)
        }

        override fun onBridgeConnected(phBridge: PHBridge, s: String) {
            phHueSDK.selectedBridge = phBridge;
            phHueSDK.enableHeartbeat(phBridge, PHHueSDK.HB_INTERVAL.toLong());
            phHueSDK.lastHeartbeat.put(phBridge.resourceCache.bridgeConfiguration.ipAddress, System.currentTimeMillis());
            sharedPreferences.setLastConnectedIPAddress(phBridge.resourceCache.bridgeConfiguration.ipAddress);
            sharedPreferences.setUsername(USERNAME);

            listener.onBridgeConnected(phBridge, s)
        }

        override fun onAuthenticationRequired(phAccessPoint: PHAccessPoint) {
            phHueSDK.startPushlinkAuthentication(phAccessPoint);

            listener.onAuthenticationRequired(phAccessPoint)
        }

        override fun onConnectionResumed(phBridge: PHBridge) {
            Log.v(TAG, "onConnectionResumed ${phBridge.resourceCache.bridgeConfiguration.ipAddress}");

            phHueSDK.lastHeartbeat.put(phBridge.resourceCache.bridgeConfiguration.ipAddress, System.currentTimeMillis());
            for (i in 0..phHueSDK.disconnectedAccessPoint.size) {
                if (phHueSDK.disconnectedAccessPoint[i].ipAddress.equals(phBridge.resourceCache.bridgeConfiguration.ipAddress)) {
                    phHueSDK.disconnectedAccessPoint.removeAt(i);
                }
            }

            listener.onConnectionResumed(phBridge)
        }

        override fun onConnectionLost(phAccessPoint: PHAccessPoint) {
            Log.v(TAG, "onConnectionLost: ${phAccessPoint.ipAddress}");
            if (!phHueSDK.disconnectedAccessPoint.contains(phAccessPoint)) {
                phHueSDK.disconnectedAccessPoint.add(phAccessPoint);
            }

            listener.onConnectionLost(phAccessPoint)
        }

        override fun onError(i: Int, s: String) {
            listener.onError(i, s)
        }

        override fun onParsingErrors(list: List<PHHueParsingError>) {
            for (parsingError in list) {
                Log.e(TAG, "ParsingError: ${parsingError.message}");
            }

            listener.onParsingErrors(list)
        }
    }
}