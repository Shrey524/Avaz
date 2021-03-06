package com.example.avaz.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.avaz.R
import com.example.avaz.models.Data
import kotlinx.android.synthetic.main.view_holder.view.*

class RecyclerViewAdapter(context: Context, list: List<Data>) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {
	private val context: Context = context
	var list: List<Data> = list

	private inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
		var image: ImageView = itemView.findViewById(R.id.recycler_image)
		var dish: TextView = itemView.findViewById(R.id.recycler_text)
		var selected: ImageView = itemView.findViewById(R.id.selected)

		fun bind(position: Int) {
			dish.text = list[position].name
			image.setImageResource(list[position].img_dish)
		}

		fun click(position: Int){
			itemView.recycler_image.setOnClickListener {
				if(selected.visibility == View.INVISIBLE){
					selected.visibility = View.VISIBLE
					list[position].selected = true
				}else{
					selected.visibility = View.INVISIBLE
					list[position].selected = false
				}
			}
			}
		}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		val view: View = LayoutInflater.from(context).inflate(R.layout.view_holder, parent, false)
		return UserViewHolder(view)
	}


	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		(holder as UserViewHolder).bind(position)
		holder.click(position)
	}

	override fun getItemCount(): Int {
		return list.size
	}
}
