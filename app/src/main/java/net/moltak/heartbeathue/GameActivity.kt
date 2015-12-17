package net.moltak.heartbeathue

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import butterknife.ButterKnife
import net.moltak.heartbeathue.library.bindView
import net.moltak.heartbeathue.logic.Bulb
import net.moltak.heartbeathue.logic.HueController
import net.moltak.heartbeathue.logic.LevelCreator
import net.moltak.heartbeathue.logic.color.PartialColorBlindnessCreator
import net.moltak.heartbeathue.logic.color.SpecialColorCreator
import net.moltak.heartbeathue.logic.color.StageModeColorCreator
import net.moltak.heartbeathue.logic.color.TimeAttackModeColorCreator
import net.moltak.heartbeathue.logic.game.ColorBlindnessReferee
import net.moltak.heartbeathue.logic.game.GameReferee

public class GameActivity : AppCompatActivity() {

    private var hueController: HueController? = null
    private var stage = 0
    var levelCreator: LevelCreator? = null
    var gameReferee: GameReferee? = null

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
        gameReferee = GameReferee(levelCreator as LevelCreator)

        button1.setOnClickListener(buttonSelect)
        button2.setOnClickListener(buttonSelect)
        button3.setOnClickListener(buttonSelect)

        nextStage()
    }

    private fun createLevelCreator() : SpecialColorCreator {
        when(intent.getIntExtra("mode", 0)) {
            0 -> {
                textView.text = "Stage mode"
                return StageModeColorCreator()
            }
            1 -> {
                textView.text = "Time Attack mode"
                return TimeAttackModeColorCreator()
            }
            else -> {
                textView.text = "Color Blindness mode"
                return PartialColorBlindnessCreator()
            }
        }
    }

    private fun changeButtonColor(bulb: Bulb) {
        button1.setBackgroundColor(bulb.bulbs[0].toInt() ?: android.R.color.black)
        button2.setBackgroundColor(bulb.bulbs[1].toInt() ?: android.R.color.black)
        button3.setBackgroundColor(bulb.bulbs[2].toInt() ?: android.R.color.black)
    }

    val buttonSelect = View.OnClickListener { v ->
        var selected = selectedIndex(v)

        when (gameReferee?.refereeing(selected, stage - 1)!!) {
            GameReferee.Result.NEXT -> nextStage()
            GameReferee.Result.COMPLETE -> {
                showResult()
            }

            GameReferee.Result.GAME_OVER -> {
                showGameOver()
                finish()
            }
        }
    }

    private fun selectedIndex(v: View?): Int {
        when (v!!.id) {
            R.id.button1 -> return 0
            R.id.button2 -> return 1
            else -> return 2
        }
    }

    private fun nextStage() {
        if (hueController?.changeTheColor(levelCreator!!.stages[stage]) ?: false) {
            textView.text = "Stage: -> ${stage + 1}, color changed!"
        } else {
            textView.text = "Stage: -> ${stage + 1}, color fail!"
        }

        changeButtonColor(levelCreator!!.stages[stage])

        if (stage == levelCreator!!.stageCount - 1) stage = 0
        else stage++
    }

    private fun showGameOver() {
        startActivity(Intent(this, GameOverActivity::class.java))
    }

    private fun showResult() {
        if (levelCreator!!.specialColorCreator is PartialColorBlindnessCreator) {
            showColorBlindnessTestResult()
        } else {
            showGameModeResult()
        }
    }

    private fun showGameModeResult() {
    }

    private fun showColorBlindnessTestResult() {
        val i = Intent(this, ColorBlindnessResultActivity::class.java)

        when (gameReferee!!.colorBlindnessResult) {
            ColorBlindnessReferee.ColorBlindType.NORMAL -> { // 정상
                i.putExtra("result", "normal")
                return
            }
            ColorBlindnessReferee.ColorBlindType.PROTANOPIA, // 제1색맹 : 적색맹
            ColorBlindnessReferee.ColorBlindType.DEUTERANOPIA, // 제2색맹 : 녹색맹
            ColorBlindnessReferee.ColorBlindType.ACHROMAOPSIA -> { // 전색맹
                i.putExtra("result", "color_blindness")
            }
        }

        startActivity(i)
        finish()
    }
}