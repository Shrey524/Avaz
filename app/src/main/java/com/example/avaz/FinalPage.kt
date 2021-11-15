package com.example.avaz

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_final_page.*

class FinalPage : AppCompatActivity() {

    // binding data to the views
    private fun bindData(list: ArrayList<Data>){
        dish1.text = list[0].name
        dish2.text = list[1].name
        dish3.text = list[2].name
        if(list.size >= 4){
            dish4.visibility = View.VISIBLE
            dish4.text = list[3].name
            if (list.size == 5){
                dish5.visibility = View.VISIBLE
                dish5.text = list[4].name
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_page)

        // hiding actionBar
        var actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        // receiving the List of Data
        val bundle = intent.extras
        val arrayList = bundle!!.getParcelableArrayList<Parcelable>("mylist") as ArrayList<Data>

        // binding data to the views
        bindData(arrayList)
    }
}