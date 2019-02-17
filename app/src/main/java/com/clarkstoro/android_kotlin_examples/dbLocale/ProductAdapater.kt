package com.clarkstoro.android_kotlin_examples.dbLocale

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.clarkstoro.android_kotlin_examples.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_db_locale.view.*
import kotlinx.android.synthetic.main.products_list_item.view.*
import com.clarkstoro.android_kotlin_examples.dbLocale.dbLocaleFragment.Companion.oldIDProduct

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

        //Update row
        holder.itemView.setOnClickListener{view ->

            var edtProductIDProduct = holder.itemView.rootView.findViewById(R.id.edtProductIDProduct) as EditText
            var edtProductName = holder.itemView.rootView.findViewById(R.id.edtProductName) as EditText
            var edtProductDescription = holder.itemView.rootView.findViewById(R.id.edtProductDescription) as EditText

            edtProductIDProduct.setText("" + products.productsList.get(position).IDProduct)
            edtProductName.setText("" + products.productsList.get(position).name)
            edtProductDescription.setText("" + products.productsList.get(position).description)

            oldIDProduct = products.productsList.get(position).IDProduct
        }

        //Delete row
        holder.itemView.setOnLongClickListener{view ->
            val snackbar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT)
            val dbOperations = DBOperations(context!!, holder.itemView.rootView) //In order to update the recyclerview you need to pass the rootView or anything will happen
            val result = dbOperations.deleteData(products.productsList.get(position).IDProduct)

            if(result) {
                snackbar.setAction("UNDO", View.OnClickListener { v ->
                    val IDProduct = products.productsList.get(position).IDProduct
                    val nameProduct = products.productsList.get(position).name
                    val descriptionProduct = products.productsList.get(position).description

                    dbOperations.insertData(IDProduct.toString(), nameProduct, descriptionProduct)
                })
                snackbar.setText("Product deleted").show()
            }else{
                 snackbar.setText("Error, could not delete the product").show()
            }

            true
        }

        holder.itemView.txtProductName.text = products.productsList.get(position).name
        holder.itemView.txtProductDescription.text= "" + products.productsList.get(position).description
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view)