package net.moltak.heartbeathue.logic

import com.philips.lighting.hue.listener.PHLightListener
import com.philips.lighting.model.PHBridgeResource
import com.philips.lighting.model.PHHueError
import com.philips.lighting.model.PHLight

/**
 * Created by moltak on 15. 12. 3..
 */

open class HueLightSimpleListener : PHLightListener {
    override fun onError(code: Int, msg: String?) { }

    override fun onStateUpdate(p0: MutableMap<String, String>?, p1: MutableList<PHHueError>?) { }

    override fun onSuccess() { }

    override fun onReceivingLights(p0: MutableList<PHBridgeResource>?) { }

    override fun onReceivingLightDetails(p0: PHLight?) { }

    override fun onSearchComplete() { }
}