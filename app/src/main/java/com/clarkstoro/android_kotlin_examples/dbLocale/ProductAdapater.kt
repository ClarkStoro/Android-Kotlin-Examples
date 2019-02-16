package com.clarkstoro.android_kotlin_examples.dbLocale

import android.app.PendingIntent
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.clarkstoro.android_kotlin_examples.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.products_list_item.view.*

class ProductAdapater (val products : Products, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.products_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return products.productsList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.setOnLongClickListener{view ->

            val dbOperations = DBOperations(context, holder.itemView.rootView) //In order to update the recyclerview you need to pass the rootView or anything will happen
            dbOperations.deleteData(products.productsList.get(position).IDProduct)

            true
        }

        holder.itemView.txtProductName.text = products.productsList.get(position).name
        holder.itemView.txtProductDescription.text= "" + products.productsList.get(position).description
    }


}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view)