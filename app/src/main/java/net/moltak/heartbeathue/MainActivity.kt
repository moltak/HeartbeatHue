package net.moltak.heartbeathue

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import net.moltak.heartbeathue.logic.HueController
import net.moltak.heartbeathue.logic.HueSharedPreferences
import net.moltak.heartbeathue.logic.HueSimpleListener

public class MainActivity : AppCompatActivity() {

    private var hueController: HueController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hueController = HueController(HueSharedPreferences.getInstance(this), listener)
        if (hueController?.connectToLastAccessPoint() == false) {
            hueController?.searchBridge()
        }
    }

    override fun onDestroy() {
        hueController?.disconnect()
        super.onDestroy()
    }

    private val listener = object : HueSimpleListener() {

    }
}