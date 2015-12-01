package net.moltak.heartbeathue

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.OnClick
import net.moltak.heartbeathue.logic.HueController
import net.moltak.heartbeathue.logic.HueSharedPreferences
import net.moltak.heartbeathue.logic.HueSimpleListener
import net.moltak.heartbeathue.logic.LevelCreator

public class MainActivity : AppCompatActivity() {

    private var hueController: HueController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        hueController = HueController(HueSharedPreferences.getInstance(this), listener, LevelCreator(10, 20))
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

    @OnClick(R.id.buttonChangeColor)
    public fun onChangeColorButtonClicked() {
        hueController?.changeTheColor()
        Toast.makeText(this, "aaa", Toast.LENGTH_LONG).show()
    }
}