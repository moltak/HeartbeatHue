package net.moltak.heartbeathue

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import butterknife.ButterKnife
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar
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
import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.emptyObservable
import rx.lang.kotlin.observable
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

public class GameActivity : AppCompatActivity() {

    private var hueController: HueController? = null
    private var stage = 0
    var levelCreator: LevelCreator? = null
    var gameReferee: GameReferee? = null
    var isDestory: Boolean = true

    private val button1: Button by bindView(R.id.button1)
    private val button2: Button by bindView(R.id.button2)
    private val button3: Button by bindView(R.id.button3)
    private val textViewCountDown: TextView by bindView(R.id.textViewCountDown)
    private val timeAttackProgress: RoundCornerProgressBar by bindView(R.id.timeAttackProgress)

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

        changeButtonColor(levelCreator!!.stages[stage])
        hueController?.changeTheColor(levelCreator!!.stages[stage])
    }

    override fun onDestroy() {
        isDestory = false
        super.onDestroy()
    }

    private fun createLevelCreator() : SpecialColorCreator {
        when(intent.getIntExtra("mode", 0)) {
            0 -> {
                supportActionBar.title = "Stage Mode"
                return StageModeColorCreator(stageCount = 10)
            }
            1 -> {
                initTimeAttackLayout()
                return TimeAttackModeColorCreator(stageCount = 10)
            }
            else -> {
                supportActionBar.title = "Color Blindness Mode"
                return PartialColorBlindnessCreator()
            }
        }
    }

    private fun initTimeAttackLayout() {
        findViewById(R.id.layoutProgress).visibility = View.VISIBLE
        timeAttackProgress.progressColor = Color.parseColor("#f44336")
        timeAttackProgress.setBackgroundColor(Color.parseColor("#808080"))
        timeAttackProgress.max = 60.0f
        timeAttackProgress.progress = timeAttackProgress.max
        timeAttackProgress.padding = 0
        timeAttackProgress.radius = 0
        textViewCountDown.text = "${timeAttackProgress.progress}/${timeAttackProgress.max}"
        supportActionBar.hide()

        startCountDown()
    }

    private fun startCountDown() {
        Thread(Runnable {
            while (isDestory) {
                Thread.sleep(100)
                runOnUiThread({
                    timeAttackProgress.progress -= 0.1f
                    textViewCountDown.text = "%.1f/${timeAttackProgress.max}".format(timeAttackProgress.progress)
                })

                if (timeAttackProgress.progress == 0f) {
                    showGameOver()
                    break
                }
            }
        }).start()
    }

    private fun changeButtonColor(bulb: Bulb) {
        button1.setBackgroundColor(bulb.bulbs[0].toInt() ?: android.R.color.black)
        button2.setBackgroundColor(bulb.bulbs[1].toInt() ?: android.R.color.black)
        button3.setBackgroundColor(bulb.bulbs[2].toInt() ?: android.R.color.black)
    }

    val buttonSelect = View.OnClickListener { v ->
        var selected = selectedIndex(v)

        when (gameReferee?.refereeing(selected, stage)!!) {
            GameReferee.Result.NEXT -> nextStage()
            GameReferee.Result.COMPLETE -> {
                hueController?.turnOff()
                showResult()
            }
            GameReferee.Result.GAME_OVER -> {
                hueController?.turnOff()
                showGameOver()
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
        if (stage == levelCreator!!.stageCount - 1) stage = 0
        else stage++

        hueController?.changeTheColor(levelCreator!!.stages[stage])
        changeButtonColor(levelCreator!!.stages[stage])
        if (levelCreator!!.specialColorCreator is StageModeColorCreator) {
            supportActionBar.title = "Stage: $stage"
        }
    }

    private fun showGameOver() {
        startActivity(Intent(this, GameOverActivity::class.java))
        finish()
    }

    private fun showResult() {
        if (levelCreator!!.specialColorCreator is PartialColorBlindnessCreator) {
            showColorBlindnessTestResult()
        } else {
            showClearActivity()
        }
    }

    private fun showClearActivity() {
        startActivity(Intent(this, GameClearActivity::class.java))
        finish()
    }

    private fun showColorBlindnessTestResult() {
        val i = Intent(this, ColorBlindnessResultActivity::class.java)

        when (gameReferee!!.colorBlindnessResult) {
            ColorBlindnessReferee.ColorBlindType.NORMAL -> { // 정상
                i.putExtra("result", "normal")
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