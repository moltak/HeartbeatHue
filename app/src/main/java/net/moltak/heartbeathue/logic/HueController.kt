package net.moltak.heartbeathue.logic

import android.text.TextUtils
import android.util.Log
import com.philips.lighting.hue.sdk.PHAccessPoint
import com.philips.lighting.hue.sdk.PHBridgeSearchManager
import com.philips.lighting.hue.sdk.PHHueSDK
import com.philips.lighting.hue.sdk.PHSDKListener
import com.philips.lighting.hue.sdk.utilities.impl.Color
import com.philips.lighting.model.*
import net.moltak.heartbeathue.util.ColorConverter

/**
 * Created by engeng on 12/1/15.
 */
class HueController(sharedPreferences: HueSharedPreferences, phdSdkPHSDKListener: PHSDKListener) {
    val phHueSDK: PHHueSDK
    val sharedPreferences: HueSharedPreferences
    val listener: PHSDKListener
    val colorConverter = ColorConverter()

    var levelCreator: LevelCreator? = null

    private val TAG = "HUE_TAG"

    init {
        this.sharedPreferences = sharedPreferences
        this.listener = phdSdkPHSDKListener

        phHueSDK = PHHueSDK.create()
        phHueSDK.appName = "HeartbeatHue"
        phHueSDK.deviceName = android.os.Build.MODEL
    }

    fun connectToLastAccessPoint(): Boolean {
        phHueSDK.notificationManager.registerSDKListener(simpleListener)

        val userName = sharedPreferences.getUserName()
        val lastIpAddress = sharedPreferences.getLastConnectedIpAddress()

        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(lastIpAddress)) {
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
        phHueSDK.notificationManager.unregisterSDKListener(simpleListener);
        phHueSDK.disableAllHeartbeat();
    }

    fun changeTheColor(bulb: Bulb): Boolean {
        val bridge = phHueSDK.selectedBridge
        val resource = bridge?.resourceCache?.allLights ?: return false

        for (i in 0..levelCreator!!.bulbCount - 1) {
            val lightState = convertRGBtoCIE(bulb.bulbs[i], resource[i].modelNumber)
            lightState.colorMode = PHLight.PHLightColorMode.COLORMODE_XY
            lightState.isOn = true
            lightState.brightness = (Color.brightness(bulb.bulbs[i].toInt()!!) * 255).toInt()
            if (lightState.brightness >= 255) lightState.brightness = 254
            bridge.updateLightState(bridge.resourceCache.allLights[i], lightState, simpleLightListener)

            Log.d(TAG, "   $i RGB -> ${bulb.bulbs[i].toInt()}, x = ${lightState.x}, y = ${lightState.y}, " +
                    "${lightState.validateState()}, brightness = ${lightState.brightness}")
        }

        return true
    }

    private fun convertRGBtoCIE(stage: BulbColor, modelNumber: String): PHLightState {
        val xy = colorConverter.toXY(stage.R, stage.G, stage.B, modelNumber);
        val lightState = PHLightState()
        lightState.x = xy[0]
        lightState.y = xy[1]
        return lightState
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

        override fun onBridgeConnected(phBridge: PHBridge, userName: String) {
            phHueSDK.selectedBridge = phBridge
            phHueSDK.enableHeartbeat(phBridge, PHHueSDK.HB_INTERVAL.toLong())
            phHueSDK.lastHeartbeat.put(phBridge.resourceCache.bridgeConfiguration.ipAddress, System.currentTimeMillis())
            sharedPreferences.setLastConnectedIPAddress(phBridge.resourceCache.bridgeConfiguration.ipAddress)
            sharedPreferences.setUsername(userName)

            listener.onBridgeConnected(phBridge, userName)
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