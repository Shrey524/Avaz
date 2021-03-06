package com.example.avaz.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.avaz.*
import com.example.avaz.Database.DatabaseHelper
import com.example.avaz.adapters.RecyclerView2Adapter
import com.example.avaz.adapters.RecyclerViewAdapter
import com.example.avaz.models.Data
import com.example.avaz.models.SortedApiData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

	private lateinit var adapter: RecyclerViewAdapter
	private lateinit var adapter2: RecyclerView2Adapter

	val databaseHandler: DatabaseHelper = DatabaseHelper(this)

	var recycler2List : ArrayList<SortedApiData> = ArrayList()

	//creating a default list
	var recyclerDataList1 : List<Data> = createDefaultList()

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

	//Fetching data from the popUp Activity and creating the second recyclerView
	private fun createRecyclerView2(): ArrayList<SortedApiData> {

		var arrayList : ArrayList<SortedApiData> = ArrayList()


		if(databaseHandler.viewDishes().isNotEmpty()){
			// receiving the List of Data
			arrayList = databaseHandler.viewDishes() as ArrayList<SortedApiData>

			for(items in arrayList){
				recycler2List.add(items)
			}

				dishes2.visibility = View.VISIBLE
				adapter2 = RecyclerView2Adapter(this, recycler2List)
				val mLayoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
				dishes2.layoutManager = mLayoutManager2
				dishes2.itemAnimator = DefaultItemAnimator()
				dishes2.setHasFixedSize(true)
				dishes2.adapter = adapter2
		}
		return arrayList
	}


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		// hiding actionBar
		var actionBar: ActionBar? = supportActionBar
		actionBar?.hide()


		// declaring default RecyclerView
		adapter = RecyclerViewAdapter(this, recyclerDataList1)
		val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
		dishes.layoutManager = mLayoutManager
		dishes.itemAnimator = DefaultItemAnimator()
		dishes.setHasFixedSize(true)
		dishes.adapter = adapter

		//  Creating the 2nd RecyclerView
		var list : ArrayList<SortedApiData> = createRecyclerView2()


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

					// passing ArrayList to the final arrayList
					val intent = Intent(this, FinalPage::class.java)
					val bundle = Bundle()
					bundle.putStringArrayList("mylist", selectedList)
					intent.putExtras(bundle)
					startActivity(intent)

				}else{
					Toast.makeText(this@MainActivity, "You can't select more than 5 dishes", Toast.LENGTH_LONG).show()
				}
			}else{
				Toast.makeText(this@MainActivity, "You have to select more than 2 dishes", Toast.LENGTH_LONG).show()
			}
		}
	}

}