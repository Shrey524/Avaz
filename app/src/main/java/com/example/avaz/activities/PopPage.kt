package com.example.avaz.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.avaz.R
import com.example.avaz.adapters.PopUpAdapter
import com.example.avaz.models.ApiData
import com.example.avaz.models.IconsItem
import com.example.avaz.models.SortedApiData
import com.example.avaz.network.Client
import kotlinx.android.synthetic.main.activity_pop_page.*
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor


class PopPage : AppCompatActivity() {

	private lateinit var adapter: PopUpAdapter

	private val sharedPrefFile = "sharedPreference"

	private val BaseUrl:String = "https://api.thenounproject.com/collection/"

	var recyclerDataList : List<SortedApiData> = ArrayList()

	// Creating a list from the api call which we can use the recyclerView
	private fun createList(data: ApiData?): List<SortedApiData> {

		var imgList : List<IconsItem>? = data?.icons as List<IconsItem>?
		val dataList : ArrayList<SortedApiData> = ArrayList()
		if (imgList != null) {
			for(items in imgList){
				var img : String = items.previewUrl
				var name : String = items.term

				var sortedApiData : SortedApiData = SortedApiData(name, img, false)
				dataList.add(sortedApiData)
			}
		}
		return dataList
	}

	// function for apiCall which will create the recyclerView if there is a response
	private fun apiCall(search: String) {

		val consumer = OkHttpOAuthConsumer("9c6c751a78db41b9a8bec92ef28c7656", "3f302d7930a74c1db0304ce675d4d41b")
		consumer.setTokenWithSecret("","")

		val client1 = OkHttpClient.Builder()
			.addInterceptor(SigningInterceptor(consumer))
			.build()

		var retrofit : Retrofit = Retrofit.Builder()
			.baseUrl(BaseUrl)
			.client(client1)
			.addConverterFactory(GsonConverterFactory.create())
			.build()

		val api: Client = retrofit.create(Client::class.java)
		val call: retrofit2.Call<ApiData?>? = api.getResponse(search, 5, 0, 0)

		call?.enqueue(object : Callback<ApiData?> {
			override fun onFailure(call: retrofit2.Call<ApiData?>, t: Throwable?) {
				Toast.makeText(this@PopPage, "Failure in getting api response", Toast.LENGTH_LONG).show()
			}

			override fun onResponse(call: retrofit2.Call<ApiData?>, response: Response<ApiData?>) {
				val data: ApiData? = response.body()
				popUprecycler.visibility = View.VISIBLE

				//Creating the 2nd RecyclerView
				recyclerDataList = createList(data)
				adapter = PopUpAdapter(this@PopPage, recyclerDataList)
				popUprecycler.layoutManager = GridLayoutManager(applicationContext, 2)
				popUprecycler.itemAnimator = DefaultItemAnimator()
				popUprecycler.setHasFixedSize(true)
				popUprecycler.adapter = adapter

			}
		})
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_pop_page)


		//hiding actionBar
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


		// Searching dishes using retrofit
		searchbar.setOnClickListener {
			//recyclerView
			apiCall(searchbar.text.toString())
		}

		Save.setOnClickListener {

			// storing selected dishes into a new dish
			val selectedList: ArrayList<SortedApiData> = ArrayList()
			for (item in recyclerDataList) {
				if (item.selected) {
					selectedList.add(item)
				}
			}

			//updating selected check in the list back to false
			for(items in selectedList){
				items.selected = false
			}

			// passing the list of selected list back to mainActivity
			val intent = Intent(this, MainActivity::class.java)
			val bundle = Bundle()
			bundle.putParcelableArrayList("popUpList", selectedList)
			intent.putExtras(bundle)

			//updating shared prefs
			val sharedPreferences: SharedPreferences = this.getSharedPreferences(
				sharedPrefFile,
				Context.MODE_PRIVATE
			)
			val editor: SharedPreferences.Editor =  sharedPreferences.edit()
			if(selectedList.size >= 1){
				var check : Boolean = false
				Log.v("list_check", check.toString())
				editor.putBoolean("check", check)
				editor.apply()
				editor.commit()
			}
			//going back to the MainActivity
			startActivity(intent)

		}
	}

}