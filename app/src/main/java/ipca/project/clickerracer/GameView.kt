package ipca.project.clickerracer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.icu.lang.UCharacter
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.annotation.RequiresApi
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

abstract class CountDownTimer

class GameView : SurfaceView, Runnable {

    var playing = false
    var gameThread : Thread? = null

    var surfaceHolder : SurfaceHolder? = null
    var canvas : Canvas? = null
    var paint : Paint = Paint()

    lateinit var playerCar : PlayerCar
    var textSpeed : Int = 0

    //Database
    val database = Firebase.database
    val mySpeed = database.getReference("message")

    //Timer
    //var timeLeft : Int = 30000
    //var timeEnded = false

    private fun init(context: Context?, width: Int, height: Int){
        playerCar = PlayerCar(context!!, width, height)
        surfaceHolder = holder
    }

    constructor(context: Context?, width: Int, height: Int) : super(context){
        init(context, width, height)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        init(context, 0, 0)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        init(context, 0, 0)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes){
        init(context, 0, 0)
    }

    override fun run() {
        while (playing){
            update()
            draw()
            control()
        }
    }

    private fun update(){
        playerCar.update()

        textSpeed = playerCar.carSpeed

        mySpeed.setValue("$textSpeed")

        //timeLeft -= 1000
        //if(timeLeft == 0) timeEnded = true
    }

    private fun draw(){
        surfaceHolder?.let {
            if(it.surface.isValid){
                canvas = surfaceHolder?.lockCanvas()
                canvas?.drawColor(Color.GREEN)

                paint.color = Color.WHITE

                canvas?.drawBitmap(playerCar.bitmap, playerCar.x.toFloat(), playerCar.y.toFloat(), paint)

                paint.color = Color.BLACK
                paint.textSize = 60f
                canvas?.drawText("Speed: $textSpeed", 0f, 100f, paint)

                //if(timeEnded){
                //    paint.color = Color.BLACK
                //    paint.textSize = 150f
                //    canvas?.drawText("Total Speed: $textSpeed", 150f, 500f, paint)
                //}
                surfaceHolder?.unlockCanvasAndPost(canvas)
            }
        }
    }

    private fun control(){
        Thread.sleep(17L)
    }

    fun pause(){
        playing = false
        gameThread?.join()
    }

    fun resume(){
        playing = true
        gameThread = Thread(this)
        gameThread!!.start()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN ->{
                playerCar.carSpeed += 5
                playerCar.accelerating = true
            }
            MotionEvent.ACTION_UP -> {
                playerCar.carSpeed -= 3
                playerCar.accelerating = false
            }
        }
        return true
    }
}