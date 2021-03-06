package com.clarkstoro.android_kotlin_examples.ArrayList

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clarkstoro.android_kotlin_examples.R
import kotlinx.android.synthetic.main.users_list_item.view.*


class ListAdapter (val items : ArrayList<String>, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.users_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //holder?.txtName?.text = items.get(position)

        val nameOfUser = items.get(position)
        holder.itemView.txtProductName.text = (nameOfUser)

    }

    override fun getItemCount(): Int {
        return items.size
    }


}
class ViewHolder (view: View) : RecyclerView.ViewHolder(view)
