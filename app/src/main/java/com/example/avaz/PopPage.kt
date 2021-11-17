package com.example.avaz

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_pop_page.*


class PopPage : AppCompatActivity() {

	private lateinit var adapter: PopUpAdapter

	private val sharedPrefFile = "sharedPreference"

	private fun createList(): List<Data> {
		val data = Data("apple", R.drawable.apple, false)
		val data1 = Data("burger", R.drawable.burger, false)
		val data2 = Data("cake", R.drawable.cake, false)
		val data3 = Data("dimsum", R.drawable.dimsum, false)
		val data4 = Data("dosa", R.drawable.dosa, false)
		val data5 = Data("ice_cream", R.drawable.ice_cream, false)
		val data6 = Data("pancake", R.drawable.pancake, false)
		val data7 = Data("pizza", R.drawable.pizza, false)
		val data8 = Data("soup", R.drawable.soup, false)
		val data9 = Data("sushi", R.drawable.sushi, false)

		val dataList: ArrayList<Data> = ArrayList()
		dataList.add(data)
		dataList.add(data1)
		dataList.add(data2)
		dataList.add(data3)
		dataList.add(data4)
		dataList.add(data5)
		dataList.add(data6)
		dataList.add(data7)
		dataList.add(data8)
		dataList.add(data9)

		return dataList
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_pop_page)

		var actionBar: ActionBar? = supportActionBar
		actionBar?.hide()

		// setting layout params for the popUp Activity
		val dm : DisplayMetrics = DisplayMetrics()
		windowManager.defaultDisplay.getMetrics(dm)

		val width : Int = dm.widthPixels
		val height : Int = dm.heightPixels

		window.setLayout(width * 1, ((height * .8).toInt()))
		val params : WindowManager.LayoutParams = window.attributes
		params.gravity = Gravity.BOTTOM
		params.x = 20
		params.y = 0
		window.attributes = params

		//recyclerView
		var recyclerDataList1 : List<Data> = createList()
		adapter = PopUpAdapter(this, recyclerDataList1)
		popUprecycler.layoutManager = GridLayoutManager(applicationContext,2)
		popUprecycler.itemAnimator = DefaultItemAnimator()
		popUprecycler.setHasFixedSize(true)
		popUprecycler.adapter = adapter

		Save.setOnClickListener {

			val selectedList: ArrayList<Data> = ArrayList<Data>()

			for (item in recyclerDataList1) {
				if (item.selected) {
					selectedList.add(item)
				}
			}

			//
			val intent = Intent(this, MainActivity::class.java)
			val bundle = Bundle()
			bundle.putParcelableArrayList("popUpList", selectedList)
			intent.putExtras(bundle)

			//sending size of list
			val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
			val editor: SharedPreferences.Editor =  sharedPreferences.edit()
			if(selectedList.size >= 1){
				var check : Boolean = false
				Log.v("list_check",check.toString())
				editor.putBoolean("check",check)
				editor.apply()
				editor.commit()
			}
			startActivity(intent)

		}
	}

}