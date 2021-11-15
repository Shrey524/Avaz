package com.example.avaz

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

class PopPage : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_pop_page)

		var actionBar: ActionBar? = supportActionBar
		actionBar?.hide()

		val dm : DisplayMetrics = DisplayMetrics()
		windowManager.defaultDisplay.getMetrics(dm)

		val width : Int = dm.widthPixels
		val height : Int = dm.heightPixels

		window.setLayout(width*1,((height*.8).toInt()))
		val params : WindowManager.LayoutParams = window.attributes
		params.gravity = Gravity.BOTTOM
		params.x = 20
		params.y = 0
		window.attributes = params
	}
}