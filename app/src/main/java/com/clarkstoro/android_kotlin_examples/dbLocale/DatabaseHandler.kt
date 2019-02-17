package com.clarkstoro.android_kotlin_examples.dbLocale

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

val DATABASE_NAME = "DBProduct"
val TABLE_NAME = "Products"
val COL_ID = "id"
val COL_IDPRODUCT = "IDProduct"
val COL_NAME = "Name"
val COL_DESCRIPTION = "Description"

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1){
    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                            COL_IDPRODUCT + " INTEGER, " +
                            COL_NAME + " TEXT, " +
                            COL_DESCRIPTION + " TEXT );"

        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    //Insert a record in db
    fun insertData(product: Product) : Boolean{
        val db = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(COL_IDPRODUCT, product.IDProduct)
        contentValues.put(COL_NAME, product.name)
        contentValues.put(COL_DESCRIPTION, product.description)
        var result = db.insert(TABLE_NAME, null, contentValues)
        if(result == -1.toLong()){
            return false
        }else{
            println("Saved!")
            return true
        }
    }//end insertData

    fun updateData(product: Product, oldIDProduct: Int): Boolean {
        var result = false
        try {
            val db = this.writableDatabase
            var contentValues = ContentValues()
            contentValues.put(COL_IDPRODUCT, product.IDProduct)
            contentValues.put(COL_NAME, product.name)
            contentValues.put(COL_DESCRIPTION, product.description)
            db.update(TABLE_NAME, contentValues, COL_IDPRODUCT + " = ?", arrayOf(oldIDProduct.toString()))
            db.close()
            result = true
        }catch (e : Exception){
            println(e)
        }

        return result
    }

    //Get all data from db
    fun retrieveAllData() : Products {
        val query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_IDPRODUCT
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        val productsList = mutableListOf<Product>()

        if (cursor.moveToFirst()) {
            do {
                val id = Integer.parseInt(cursor.getString(0))
                val IDProduct = Integer.parseInt(cursor.getString(1))
                val name = cursor.getString(2)
                val description = cursor.getString(3)
                val product = Product(IDProduct, name, description)

                productsList?.add(product)

            }while(cursor.moveToNext())
        }

        cursor.close()

        var products = Products(productsList!!)
        db.close()

        return products
    }//end retrieveAllData

    //Delete records with the IDProduct passed, in case there are ultiple record with the same IDProduct every record will be delete!
    fun deleteData(IDProduct : Int) : Boolean{
        var result = false
        try {
            val db = this.writableDatabase
            db.delete(TABLE_NAME, COL_IDPRODUCT + " = ?", arrayOf(IDProduct.toString()))
            db.close()
            result = true
        }catch (e: Exception) {
            println(e)
        }

        return result
    }//end deleteData


    fun deleteAllData() : Boolean {
        var result = false
        try {
            val db = this.writableDatabase
            db.delete(TABLE_NAME, "1", null)
            db.close()
            result = true
        }catch (e : Exception){
            println(e)
        }

        return result
    }//end deleteAllData
}