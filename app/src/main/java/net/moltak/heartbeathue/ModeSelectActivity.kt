package net.moltak.heartbeathue

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.OnClick

/**
 * Created by moltak on 15. 12. 12..
 */
class ModeSelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode_select);
        ButterKnife.bind(this)
    }

    @OnClick(R.id.buttonStage)
    public fun onStageButtonClicked() {
    }

    @OnClick(R.id.buttonTimeAttack)
    public fun onTimeAttackButtonClicked() {
    }

    @OnClick(R.id.buttonColorBlindness)
    public fun onColorBlindnessButtonClicked() {
    }
}