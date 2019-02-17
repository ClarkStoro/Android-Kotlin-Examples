package com.clarkstoro.android_kotlin_examples.dbLocale

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_db_locale.view.*

class DBOperations(val context: Context, val view: View) {

    fun insertData(IDProduct: String, nameProduct: String, descriptionProduct: String){
        if((IDProduct != "")&&(nameProduct != null)&&(nameProduct.length > 0)&&(descriptionProduct != null)&&(descriptionProduct.length > 0)) {
            var product = Product(IDProduct.toInt(), nameProduct, descriptionProduct)
            var db = DatabaseHandler(context!!)
            var result = db.insertData(product)
            if(result){
                displayAllData()
                //Resetting text
                view?.edtProductIDProduct?.setText("")
                view?.edtProductName?.setText("")
                view?.edtProductDescription?.setText("")

            }else{
                Snackbar.make(view, "Fail, product not saved!", Snackbar.LENGTH_LONG).show()
            }
        }else{
            Snackbar.make(view, "Fill all the fields", Snackbar.LENGTH_LONG).show()
        }
    }//end insertData


    //Insert a list of products
    fun insertData(products: Products){
        var db = DatabaseHandler(context!!)
        for( product in products.productsList ){
            db.insertData(product)
            displayAllData()
        }
    }//end insertData

    fun updateData(oldIDProduct: Int, IDProduct: String, nameProduct: String, descriptionProduct: String){
        if((IDProduct != "")&&(nameProduct != null)&&(nameProduct.length > 0)&&(descriptionProduct != null)&&(descriptionProduct.length > 0)) {
            var product = Product(IDProduct.toInt(), nameProduct, descriptionProduct)
            var db = DatabaseHandler(context!!)
            var result = db.updateData(product, oldIDProduct)
            if(result){
                displayAllData()
                //Resetting text
                view?.edtProductIDProduct?.setText("")
                view?.edtProductName?.setText("")
                view?.edtProductDescription?.setText("")

                dbLocaleFragment.oldIDProduct = 0

                Snackbar.make(view, "Product updated", Snackbar.LENGTH_LONG).show()
            }else{
                Snackbar.make(view, "Fail, product not updated!", Snackbar.LENGTH_LONG).show()
            }
        }
    }//end updateData


    //Delete products with the IDProduct given
    fun deleteData(IDProduct: Int) : Boolean {
        var db = DatabaseHandler(context!!)
        var result = db.deleteData(IDProduct)

        if(result){
            displayAllData()
        }
        return result
    }//end deleteData by IDProduct

    fun getAllData() : Products{
        var db = DatabaseHandler(context!!)
        //Get data from SQLite
        var products = db.retrieveAllData()

        return products
    }//end getAllData

    //Retrieve and display on RecyclerView data
    fun displayAllData(){
        val products = getAllData()
        val adapter = ProductAdapater(products, context)
        //Set the adapter to the list
        view?.dbLocaleRecyclerView?.adapter = adapter
    }//end displayAllData

    fun deleteAllData() : Boolean {
        var db = DatabaseHandler(context!!)
        var result = db.deleteAllData()
        if(result){
            displayAllData()
        }
        return result
    }//end deleteAllData






}