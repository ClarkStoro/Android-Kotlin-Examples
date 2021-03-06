package com.clarkstoro.android_kotlin_examples.JSONArrayList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clarkstoro.android_kotlin_examples.ArrayList.ViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.users_list_item_json.view.*


class ListAdapterJSON (val users : Users, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                com.clarkstoro.android_kotlin_examples.R.layout.users_list_item_json,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.txtNameJSON.text = users.data.get(position).first_name + " "+ users.data.get(position).last_name
        holder.itemView.txtIDJSON.text = "ID: " + users.data.get(position).id
        //Download the image from the correct URL and set it to the ImageView
        Picasso.get().load(users.data.get(position).avatar).into(holder.itemView.imgAvatar)

    }

    override fun getItemCount(): Int {
        return users.data.count()
    }

}
