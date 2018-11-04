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

val nodes : Int = 5
val lines : Int = 4
val color : Int = Color.parseColor("#673AB7")
val scGap : Float = 0.05f

fun Int.getInverse() : Float = 1f / this

fun Float.scaleFactor() : Float = Math.floor(this / 0.5).toFloat()

fun Float.updateScale(dir : Float) : Float = dir * scGap * ((1 - scaleFactor())/lines + scaleFactor())

