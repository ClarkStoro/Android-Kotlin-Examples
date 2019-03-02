package com.clarkstoro.android_kotlin_examples.FirebaseList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.clarkstoro.android_kotlin_examples.ArrayList.ViewHolder
import com.clarkstoro.android_kotlin_examples.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.cities_firebase_list_item.view.*
import kotlinx.android.synthetic.main.fragment_firebase_list.view.*

class FirebaseListAdapter (val cities : Cities, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.cities_firebase_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val id = cities.data.get(position).id
        val name = cities.data.get(position).name
        val state = cities.data.get(position).state
        val population = cities.data.get(position).population

        holder.itemView.txtCityName.text = "Name: "+ name
        holder.itemView.txtCityStateName.text = "State: " + state
        holder.itemView.txtCityPopulation.text = "Population: " + population


        holder.itemView.setOnClickListener{view ->

            val edtCityID = holder.itemView.rootView.findViewById(R.id.edtCityID) as EditText
            val edtCityName = holder.itemView.rootView.findViewById(R.id.edtCityName) as EditText
            val edtStateName = holder.itemView.rootView.findViewById(R.id.edtStateName) as EditText
            val edtPopulation = holder.itemView.rootView.findViewById(R.id.edtPopulation) as EditText

            edtCityID.setText("" + id)
            edtCityName.setText("" + name)
            edtStateName.setText("" + state)
            edtPopulation.setText("" + population)
        }


        //Delete city
        holder.itemView.setOnLongClickListener{view ->
            val firebaseOp = FirebaseOperations(context, view)
            firebaseOp.deleteCity(id)
            Snackbar.make(view, "City deleted", Snackbar.LENGTH_SHORT)
                .setAction("UNDO", View.OnClickListener { v ->

            firebaseOp.addCity(id, name, state, population)
            }).show()
            true
        }

    }

    override fun getItemCount(): Int {
        return cities.data.count()
    }

}