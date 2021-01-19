package ipca.project.clickerracer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class PlayerCar {
    var x = 0
    var y = 0
    var carSpeed = 0

    var accelerating = false
    var maxY = 0
    var minY = 0
    private final val MIN_SPEED = 0

    var bitmap : Bitmap

    constructor(context: Context, width: Int, height: Int) {
        x = 0
        y=  5000
        carSpeed = 0
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pepegadriving)
        maxY = height - bitmap.height
        minY = 0
    }

    fun update(){
        if(accelerating){
            carSpeed += 5
        }
        else{
            carSpeed -= 2
        }

        if(carSpeed < MIN_SPEED) carSpeed = MIN_SPEED

        y -= carSpeed

        if(y > maxY) y = maxY
        if(y < minY) y = minY
    }
}