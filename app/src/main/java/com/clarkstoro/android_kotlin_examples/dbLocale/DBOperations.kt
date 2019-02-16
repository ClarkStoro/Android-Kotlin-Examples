package com.clarkstoro.android_kotlin_examples.dbLocale

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_db_locale.view.*

class DBOperations(val context: Context, val view: View) {

    fun insertData(IDProduct: String, nameProduct: String, descriptionProduct: String){
        if((nameProduct != null)&&(nameProduct.length > 0)&&(descriptionProduct != null)&&(descriptionProduct.length > 0)) {
            var product = Product(IDProduct, nameProduct, descriptionProduct)
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
        }
    }//end insertData

    /*
    fun deleteData(id: Int){

        var db = DatabaseHandler(context!!)
        var result = db.deleteData(id)

        if(result){
            Snackbar.make(view, "Product deleted", Snackbar.LENGTH_SHORT)
        }else{
            Snackbar.make(view, "Fail, could not delete product", Snackbar.LENGTH_SHORT)
        }


    }//end deleteData by id
    */

    //Delete products with the IDProduct given
    fun deleteData(IDProduct: String){
        var db = DatabaseHandler(context!!)
        var result = db.deleteData(IDProduct)

        if(result){
            displayAllData()
            //Snackbar.make(view, "Product deleted", Snackbar.LENGTH_SHORT).show()
        }else{
            //Snackbar.make(view, "Fail, could not delete product", Snackbar.LENGTH_SHORT).show()
        }
    }//end deleteData by IDProduct



    //Retrieve and display on RecyclerView data
    fun displayAllData(){
        view?.dbLocaleRecyclerView?.setLayoutManager( LinearLayoutManager(context));
        var db = DatabaseHandler(context!!)
        //Get data from SQLite
        var products = db.retrieveAllData()

        val adapter = ProductAdapater(products, context)
        //Set the adapter to the list
        view?.dbLocaleRecyclerView?.adapter = adapter
    }//end displayAllData

    fun deleteAllData(){

    }//end deleteAllData






}