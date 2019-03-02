package com.clarkstoro.android_kotlin_examples.FirebaseList

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_firebase_list.view.*

class FirebaseOperations(val context: Context, val view: View){

    var citiesList = mutableListOf<City>()

    //READ DATA -----------------------------------------------------------------------------
    fun cityAdded(document : DocumentSnapshot){
        val id = document?.id.toString() //get document value
        val cityName = document?.get("Name").toString()
        val cityStateName = document?.get("State").toString()
        val population = document?.get("Population")

        val city = City(id, cityName, cityStateName, population as Long)
        citiesList?.add(city)

        updateView()

    }//end cityAdd

    fun cityModified(document: DocumentSnapshot){
        val id = document?.id.toString() //get document value
        val cityName = document?.get("Name").toString()
        val cityStateName = document?.get("State").toString()
        val population = document?.get("Population")

        val city = City(id, cityName, cityStateName, population as Long) //create new City with value updated

        var c = citiesList.first { city -> city.id == id } //find the city by id
        val index = citiesList.indexOf(c) //get the index of the city to update
        citiesList.set(index, city) //update list

        updateView()
    }//end modifyCity

    fun cityRemoved(document: DocumentSnapshot){
        val id = document?.id.toString() //get document value

        var c = citiesList.first { city -> city.id == id } //find the city by id
        val index = citiesList.indexOf(c) //get the index of the city to remove

        citiesList.removeAt(index)//remove city from the list

        updateView()
    }//end removeCity

    fun updateView(){
        val cities = Cities(citiesList!!)
        val adapter = FirebaseListAdapter(cities, context)

        view?.recyclerFirebaseList?.setLayoutManager( LinearLayoutManager(context))
        view?.recyclerFirebaseList?.adapter = adapter
    }//end updateView



    //WRITE DATA -------------------------------------------------------------------------------------------------------

    //You can use this method to create new city (document) or overwrite an existing one
    fun addCity(cityID: String, cityName : String, stateName : String, cityPopulation: Long?) {
        val db = FirebaseFirestore.getInstance()
        val city = HashMap<String, Any?>()
        city["Name"] = cityName
        city["State"] = stateName
        city["Population"] = cityPopulation

        db.collection("cities").document(cityID).set(city)
    }//end addCity


    fun deleteCity(id : String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("cities").document(id)
            .delete()
            .addOnSuccessListener {Log.d(TAG, "DocumentSnapshot successfully deleted!")}
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e); }
    }//end deleteCity


    //Update only the name field on a document
    fun updateCityName(cityID: String, cityName : String){
        val db = FirebaseFirestore.getInstance()
        val updateNameRef = db.collection("cities").document(cityID)

        // Set the "Name" field of the city selected
        updateNameRef.update("Name", cityName)
    }//end updateCity

    fun updateState(cityID: String, stateName: String){
        val db = FirebaseFirestore.getInstance()
        val updateNameRef = db.collection("cities").document(cityID)

        // Set the "Name" field of the city selected
        updateNameRef.update("State", stateName)
    }//end updateState

    fun updatePopulation(cityID: String, population: String){
        val db = FirebaseFirestore.getInstance()
        val updateNameRef = db.collection("cities").document(cityID)

        // Set the "Name" field of the city selected
        updateNameRef.update("Population", population)
    }//end updatePopulation

}