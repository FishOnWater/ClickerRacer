package ipca.project.clickerracer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonPlay = findViewById<ImageButton>(R.id.imageButtonPlay)
        imageButtonPlay.setOnClickListener{
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            startActivity(intent)
        }
    }
}