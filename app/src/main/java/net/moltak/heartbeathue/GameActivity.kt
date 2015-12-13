package net.moltak.heartbeathue

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import butterknife.ButterKnife
import butterknife.OnClick
import net.moltak.heartbeathue.library.bindView
import net.moltak.heartbeathue.logic.Bulb
import net.moltak.heartbeathue.logic.HueController
import net.moltak.heartbeathue.logic.LevelCreator
import net.moltak.heartbeathue.logic.color.CieOppositeXyColorCreator
import net.moltak.heartbeathue.logic.color.PartialColorBlindnessCreator
import net.moltak.heartbeathue.logic.color.SpecialColorCreator

public class GameActivity : AppCompatActivity() {

    private var hueController: HueController? = null
    private var hueCount = 0
    var levelCreator: LevelCreator? = null

    private val textView: TextView by bindView(R.id.textView)
    private val button1: Button by bindView(R.id.button1)
    private val button2: Button by bindView(R.id.button2)
    private val button3: Button by bindView(R.id.button3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        ButterKnife.bind(this)

        hueController = (application as HueApplication).hueController
        levelCreator = LevelCreator(createLevelCreator())
        hueController?.levelCreator = levelCreator
    }

    private fun createLevelCreator() : SpecialColorCreator {
        when(intent.getIntExtra("mode", 0)) {
            0 -> return CieOppositeXyColorCreator()
            else -> return PartialColorBlindnessCreator()
        }
    }

    @OnClick(R.id.buttonChangeColor)
    public fun onChangeColorButtonClicked() {
        if (hueController?.changeTheColor(levelCreator!!.stages[hueCount]) ?: false) {
            textView.text = "Stage: -> ${hueCount + 1}, color changed!"
        } else {
            textView.text = "Stage: -> ${hueCount + 1}, color fail!"
        }

        changeButtonColor(levelCreator!!.stages[hueCount])

        if (hueCount == levelCreator!!.stageCount - 1) hueCount = 0
        else hueCount ++

    }

    private fun changeButtonColor(bulb: Bulb) {
        button1.setBackgroundColor(bulb.bulbs[0].toInt() ?: android.R.color.black)
        button2.setBackgroundColor(bulb.bulbs[1].toInt() ?: android.R.color.black)
        button3.setBackgroundColor(bulb.bulbs[2].toInt() ?: android.R.color.black)
    }
}