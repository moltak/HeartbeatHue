package net.moltak.heartbeathue

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import net.moltak.heartbeathue.library.bindView

/**
 * Created by engeng on 12/17/15.
 */
class ColorBlindnessResultActivity : AppCompatActivity() {

    val imageView: ImageView by bindView(R.id.imageView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_blindess_result)

        val result = intent.getStringExtra("result")
        when (result) {
            "normal" -> imageView.setImageResource(R.drawable.img_normal)
            "color_blindness" -> imageView.setImageResource(R.drawable.img_color_blindness)
        }
    }
}