package com.anwesh.uiprojects.squarerotatestepview

/**
 * Created by anweshmishra on 05/11/18.
 */

import android.view.View
import android.view.MotionEvent
import android.content.Context
import android.app.Activity
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color
import android.graphics.PointF
import android.util.Log

val nodes : Int = 5
val lines : Int = 4
val color : Int = Color.parseColor("#673AB7")
val scGap : Float = 0.05f
val sizeFactor : Int = 3
val strokeFactor : Float = 60f

fun Int.getInverse() : Float = 1f / this

fun Float.scaleFactor() : Float = Math.floor(this / 0.5).toFloat()

fun Float.updateScale(dir : Float) : Float = dir * scGap * ((1 - scaleFactor())/lines + scaleFactor())

fun Float.divideScale(n : Int, i : Int) : Float = Math.min(n.getInverse(), Math.max(0f, this - i * n.getInverse())) * n

fun Canvas.getGapSizePoint(n : Int, sf : Int) : PointF {
    val gap : Float = width.toFloat() / (n + 1)
    return PointF(gap, gap / sf)
}

fun Canvas.drawInMiddle(x : Float, cb : () -> Unit ) {
    save()
    translate(x, height.toFloat()/2)
    cb()
    restore()
}

fun Canvas.setPaintProps(paint : Paint, color : Int, strokeFactor : Float) {
    paint.strokeWidth = Math.min(width, height) / strokeFactor
    paint.strokeCap = Paint.Cap.ROUND
    paint.color = color
}

fun Canvas.drawSRSNode(i : Int, scale : Float, paint : Paint) {
    val gapSizePoint : PointF = getGapSizePoint(nodes, sizeFactor)
    val gap : Float = gapSizePoint.x
    val size : Float = gapSizePoint.y
    val deg : Float = 360f / lines
    setPaintProps(paint, color, strokeFactor)
    drawInMiddle(gap * (i + 1)) {
        val sc1 : Float = scale.divideScale(2, 0)
        val sc2 : Float = scale.divideScale(2, 1)
        rotate(90f * sc2)
        for (j in 0..(lines - 1)) {
            val sc : Float = sc1.divideScale(lines, j)
            save()
            rotate(deg * j)
            translate(0f, size/2)
            drawLine(size, 0f, size - 2 * size * sc, 0f, paint)
            restore()
        }
    }
}

class SquareRotateStepView(ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    data class State(var scale : Float = 0f, var prevScale : Float = 0f, var dir : Float = 0f) {

        fun update(cb : (Float) -> Unit) {
            val deltaScale : Float = scale.updateScale(dir)
            scale += deltaScale
            Log.d("change in scale", "${Math.abs(deltaScale)}")
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }
}