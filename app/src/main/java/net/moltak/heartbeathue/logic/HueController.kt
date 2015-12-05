package net.moltak.heartbeathue.logic

import android.util.Log
import com.philips.lighting.hue.sdk.*
import com.philips.lighting.hue.sdk.utilities.PHUtilities
import com.philips.lighting.model.*
import java.util.*

/**
 * Created by engeng on 12/1/15.
 */
class HueController(sharedPreferences: HueSharedPreferences, phdSdkPHSDKListener: PHSDKListener,
                    levelCreator: LevelCreator) {
    private val phHueSDK: PHHueSDK
    private val sharedPreferences: HueSharedPreferences
    private val listener: PHSDKListener
    private val levelCreator: LevelCreator

    private val TAG = "HUE_TAG"
    private val USERNAME = "HeartbeatHue"

    init {
        this.sharedPreferences = sharedPreferences
        this.listener = phdSdkPHSDKListener
        this.levelCreator = levelCreator

        phHueSDK = PHHueSDK.create()
        phHueSDK.appName = "HeartbeatHue"
        phHueSDK.deviceName = android.os.Build.MODEL
    }

    fun connectToLastAccessPoint(): Boolean {
        phHueSDK.notificationManager.registerSDKListener(simpleListener)

        val userName = sharedPreferences.getUserName()
        val lastIpAddress = sharedPreferences.getLastConnectedIpAddress()

        if (!userName.equals("") && !lastIpAddress.equals("")) {
            val lastAccessPoint = PHAccessPoint();
            lastAccessPoint.username = userName
            lastAccessPoint.ipAddress = lastIpAddress

            if (!phHueSDK.isAccessPointConnected(lastAccessPoint)) {
                phHueSDK.connect(lastAccessPoint)
                return true
            }
        }

        return false
    }

    fun disconnect() {
        phHueSDK.notificationManager.unregisterSDKListener(listener);
        phHueSDK.disableAllHeartbeat();
    }

    fun changeTheColor(hues: Hues): Boolean {
        val bridge = phHueSDK.selectedBridge
        val size = bridge?.resourceCache?.allLights?.size ?: return false

        val rand = Random()

        for (i in 0..size - 1) {
            val lightState = PHLightState()
            val xy = PHUtilities.calculateXYFromRGB(
                    hues.hues[i].R,
                    hues.hues[i].G,
                    hues.hues[i].B,
                    bridge.resourceCache.allLights[i].modelNumber)
//            lightState.x = xy[0]
//            lightState.y = xy[1]
            lightState.isOn = true

//            lightState.hue = rand.nextInt(65536)
//            lightState.hue = hues.hues[i].toHue()
            bridge.updateLightState(bridge.resourceCache.allLights[i], lightState, simpleLightListener)

            Log.d(TAG, "   $i -> ${lightState.hue}, x = ${xy[0]}, y = ${xy[1]}, ${lightState.validateState()}")
        }

        return true
    }

    fun searchBridge() {
        val sm: PHBridgeSearchManager = phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE) as PHBridgeSearchManager
        sm.search(true, true)
    }

    private fun connectToAccessPoints(accessPoint: PHAccessPoint) {
        val connectedBridge: PHBridge? = phHueSDK.selectedBridge

        val connectedIP = connectedBridge?.resourceCache?.bridgeConfiguration?.ipAddress ?: null
        if (connectedIP != null) {   // We are already connected here:-
            phHueSDK.disableHeartbeat(connectedBridge)
            phHueSDK.disconnect(connectedBridge)
        }

        phHueSDK.connect(accessPoint)
    }

    private val simpleListener = object : HueSimpleListener() {
        override fun onAccessPointsFound(list: List<PHAccessPoint>) {
            if (list.size > 0) connectToAccessPoints(list[0])
            listener.onAccessPointsFound(list)
        }

        override fun onCacheUpdated(list: List<Int>, phBridge: PHBridge) {
            Log.w(TAG, "On CacheUpdated")

            listener.onCacheUpdated(list, phBridge)
        }

        override fun onBridgeConnected(phBridge: PHBridge, s: String) {
            phHueSDK.selectedBridge = phBridge
            phHueSDK.enableHeartbeat(phBridge, PHHueSDK.HB_INTERVAL.toLong())
            phHueSDK.lastHeartbeat.put(phBridge.resourceCache.bridgeConfiguration.ipAddress, System.currentTimeMillis())
            sharedPreferences.setLastConnectedIPAddress(phBridge.resourceCache.bridgeConfiguration.ipAddress)
            sharedPreferences.setUsername(USERNAME)

            listener.onBridgeConnected(phBridge, s)
        }

        override fun onAuthenticationRequired(phAccessPoint: PHAccessPoint) {
            phHueSDK.startPushlinkAuthentication(phAccessPoint)

            listener.onAuthenticationRequired(phAccessPoint)
        }

        override fun onConnectionResumed(phBridge: PHBridge) {
            Log.v(TAG, "onConnectionResumed ${phBridge.resourceCache.bridgeConfiguration.ipAddress}")

            phHueSDK.lastHeartbeat.put(phBridge.resourceCache.bridgeConfiguration.ipAddress, System.currentTimeMillis())
            for (i in 0..phHueSDK.disconnectedAccessPoint.size - 1) {
                if (phHueSDK.disconnectedAccessPoint[i].ipAddress.equals(phBridge.resourceCache.bridgeConfiguration.ipAddress)) {
                    phHueSDK.disconnectedAccessPoint.removeAt(i)
                }
            }

            listener.onConnectionResumed(phBridge)
        }

        override fun onConnectionLost(phAccessPoint: PHAccessPoint) {
            Log.v(TAG, "onConnectionLost: ${phAccessPoint.ipAddress}")
            if (!phHueSDK.disconnectedAccessPoint.contains(phAccessPoint)) {
                phHueSDK.disconnectedAccessPoint.add(phAccessPoint)
            }

            listener.onConnectionLost(phAccessPoint)
        }

        override fun onError(code: Int, msg: String) {
            Log.e(TAG, "on Error Called : $code : $msg");
            listener.onError(code, msg)
        }

        override fun onParsingErrors(list: List<PHHueParsingError>) {
            for (parsingError in list) {
                Log.e(TAG, "ParsingError: ${parsingError.message}")
            }

            listener.onParsingErrors(list)
        }
    }

    private val simpleLightListener = object : HueLightSimpleListener() {
        override fun onError(code: Int, msg: String?) {
            Log.e(TAG, "LightListener -> $msg")
        }

        override fun onStateUpdate(p0: MutableMap<String, String>?, p1: MutableList<PHHueError>?) {
            Log.e(TAG, "LightListener -> onStateUpdate")
        }

        override fun onSuccess() {
            Log.i(TAG, "LightListener -> onSuccess")
        }

        override fun onReceivingLights(p0: MutableList<PHBridgeResource>?) {
        }

        override fun onReceivingLightDetails(p0: PHLight?) {
        }

        override fun onSearchComplete() {
        }
    }
}