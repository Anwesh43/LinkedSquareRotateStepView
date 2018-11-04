package com.anwesh.uiprojects.linkedsquarerotatestepview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.anwesh.uiprojects.squarerotatestepview.SquareRotateStepView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SquareRotateStepView.create(this)
    }
}
