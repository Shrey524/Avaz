package com.example.avaz.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.avaz.*
import com.example.avaz.adapters.RecyclerView2Adapter
import com.example.avaz.adapters.RecyclerViewAdapter
import com.example.avaz.models.Data
import com.example.avaz.models.SortedApiData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

	private lateinit var adapter: RecyclerViewAdapter
	private lateinit var adapter2: RecyclerView2Adapter

	private val sharedPrefFile = "sharedPreference"

	// Creating a List for default dishes
	private fun createDefaultList(): List<Data> {
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

	//Fetching data from the popUp Activity
	private fun createNewList(sharedPreferences: SharedPreferences): ArrayList<SortedApiData> {

		var arrayList : ArrayList<SortedApiData> = ArrayList()

		val check = sharedPreferences.getBoolean("check", true)
		if(!check){
			// receiving the List of Data
			val bundle = intent.extras
			arrayList = bundle!!.getParcelableArrayList<Parcelable>("popUpList") as ArrayList<SortedApiData>
			Log.v("check running",arrayList.toString())
			runOnUiThread { adapter.notifyDataSetChanged()
				dishes2.visibility = View.VISIBLE
				adapter2 = RecyclerView2Adapter(this, arrayList)
				val mLayoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
				dishes2.layoutManager = mLayoutManager2
				dishes2.itemAnimator = DefaultItemAnimator()
				dishes2.setHasFixedSize(true)
				dishes2.adapter = adapter}
		}
		return arrayList
	}


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		// hiding actionBar
		var actionBar: ActionBar? = supportActionBar
		actionBar?.hide()

		//creating a default list
		var recyclerDataList1 : List<Data> = createDefaultList()

		// sharedPrefs
		val sharedPreferences: SharedPreferences = this.getSharedPreferences(
			sharedPrefFile,
			Context.MODE_PRIVATE
		)

		// declaring default RecyclerView
		adapter = RecyclerViewAdapter(this, recyclerDataList1)
		val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
		dishes.layoutManager = mLayoutManager
		dishes.itemAnimator = DefaultItemAnimator()
		dishes.setHasFixedSize(true)
		dishes.adapter = adapter

		// declaring New RecyclerView
		var list : ArrayList<SortedApiData> = createNewList(sharedPreferences)


		// onClickListener for popUp activity
		add.setOnClickListener {
			val intent = Intent(this, PopPage::class.java)
			startActivity(intent)
		}

		// onClickListener for final activity
		continue_button.setOnClickListener {

			//creating a final list for the final activity
			val selectedList: ArrayList<String> = ArrayList<String>()

			for(item in recyclerDataList1 ){
				if(item.selected){
					selectedList.add(item.name)
				}
			}

			for(item in list ){
				if(item.selected){
					selectedList.add(item.name)
				}
			}

			if(selectedList.size>=3){
				if(selectedList.size<6){

					Toast.makeText(this@MainActivity, "Welcome", Toast.LENGTH_LONG).show()

					// passing ParcelableArrayList to the final arrayList
					val intent = Intent(this, FinalPage::class.java)
					val bundle = Bundle()
					bundle.putStringArrayList("mylist", selectedList)
					intent.putExtras(bundle)
					startActivity(intent)

				}else{
					Toast.makeText(
						this@MainActivity,
						"You can't select more than 5 dishes",
						Toast.LENGTH_LONG
					).show()
				}
			}else{
				Toast.makeText(
					this@MainActivity,
					"You have to select more than 2 dishes",
					Toast.LENGTH_LONG
				).show()
			}
		}
	}

	override fun onStop() {
		super.onStop()
		// updating shared prefs when activity stops
		val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
		val editor: SharedPreferences.Editor = sharedPreferences.edit()
		var check: Boolean = true
		Log.v("list_check", check.toString())
		editor.putBoolean("check", check)
		editor.apply()
		editor.commit()
	}
}