package com.example.avaz.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.avaz.R
import kotlinx.android.synthetic.main.activity_final_page.*

class FinalPage : AppCompatActivity() {

    // binding data to the views
    private fun bindData(list: ArrayList<String>){
        dish1.text = list[0]
        dish2.text = list[1]
        dish3.text = list[2]
        if(list.size >= 4){
            dish4.visibility = View.VISIBLE
            dish4.text = list[3]
            if (list.size == 5){
                dish5.visibility = View.VISIBLE
                dish5.text = list[4]
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
        val arrayList = bundle!!.getStringArrayList("mylist")

        // binding data to the views
        if (arrayList != null) {
            bindData(arrayList)
        }
    }
}