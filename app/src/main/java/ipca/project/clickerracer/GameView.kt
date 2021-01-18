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

class GameView : SurfaceView, Runnable {

    var playing = false
    var gameThread : Thread? = null

    var surfaceHolder : SurfaceHolder? = null
    var canvas : Canvas? = null
    var paint : Paint = Paint()

    lateinit var playerCar: PlayerCar

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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
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

        //Implement
    }

    private fun draw(){
        surfaceHolder?.let {
            if(it.surface.isValid){
                canvas = surfaceHolder?.lockCanvas()
                canvas?.drawColor(Color.GREEN)

                paint.color = Color.WHITE

                canvas?.drawBitmap(playerCar.bitmap, playerCar.x.toFloat(), playerCar.y.toFloat(), paint)
                surfaceHolder?.unlockCanvasAndPost(canvas)
            }
        }
        //Implement
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
            MotionEvent.ACTION_UP ->{
                playerCar.accelerating = false
            }
            MotionEvent.ACTION_DOWN ->{
                playerCar.accelerating = true
            }
        }
        return false
    }
}