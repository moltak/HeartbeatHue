package net.moltak.heartbeathue.logic

import com.philips.lighting.hue.sdk.PHAccessPoint
import com.philips.lighting.hue.sdk.PHSDKListener
import com.philips.lighting.model.PHBridge
import com.philips.lighting.model.PHHueParsingError

/**
 * Created by engeng on 12/1/15.
 */
open class HueSimpleListener : PHSDKListener {

    override fun onCacheUpdated(list: List<Int>, phBridge: PHBridge) { }

    override fun onBridgeConnected(phBridge: PHBridge, s: String) { }

    override fun onAuthenticationRequired(phAccessPoint: PHAccessPoint) { }

    override fun onAccessPointsFound(list: List<PHAccessPoint>) { }

    override fun onError(i: Int, s: String) { }

    override fun onConnectionResumed(phBridge: PHBridge) { }

    override fun onConnectionLost(phAccessPoint: PHAccessPoint) { }

    override fun onParsingErrors(list: List<PHHueParsingError>) { }
}
