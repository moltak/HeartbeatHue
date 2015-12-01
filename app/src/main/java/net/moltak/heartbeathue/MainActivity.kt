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
import java.util.*

public class MainActivity : AppCompatActivity() {

    private var hueController: HueController? = null
    private val levelCreator = LevelCreator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

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

    @OnClick(R.id.buttonChangeColor)
    public fun onChangeColorButtonClicked() {
        val r = Random(Date().time)
        hueController?.changeTheColor(levelCreator.getHues()[0])

        Toast.makeText(this, "aaa", Toast.LENGTH_LONG).show()
    }
}