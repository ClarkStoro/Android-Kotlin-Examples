package com.clarkstoro.android_kotlin_examples.FirebaseList

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.clarkstoro.android_kotlin_examples.R

import kotlinx.android.synthetic.main.fragment_firebase_list.view.*
import com.google.firebase.firestore.*


class FirebaseList : Fragment() {


    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun getDataOnce(){
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("cities")
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val documents = documentSnapshot.documents //get all documents

            //If you want to take all the fields in the documents you can use .toObject method as suggested on docs

            //If you want to take some of the fields manually
            val citiesList = mutableListOf<City>()
            for(city in documents){
                val id = city?.id.toString() //get document value
                val cityName = city?.get("Name").toString()
                val cityStateName = city?.get("State").toString()
                val population = city?.get("Population")

                val city = City(id, cityName, cityStateName, population as Long)
                citiesList?.add(city)
            }

            val cities = Cities(citiesList!!)
            val adapter = FirebaseListAdapter(cities, requireContext())

            view?.recyclerFirebaseList?.setLayoutManager( LinearLayoutManager(context));
            view?.recyclerFirebaseList?.adapter = adapter
        }
    }//end getDataOnce

    fun getDataRealtime(firebaseOp : FirebaseOperations){
        val db = FirebaseFirestore.getInstance()
        val docRefRealtime = db.collection("cities")
        docRefRealtime.addSnapshotListener(EventListener<QuerySnapshot> { snapshots, e ->

            if (e != null) {
                //Failed
                return@EventListener
            }

            if (snapshots != null && !snapshots.isEmpty) {
                for (dc in snapshots.getDocumentChanges()) {
                    when (dc.getType()) {
                        DocumentChange.Type.ADDED -> firebaseOp.cityAdded(dc.getDocument())
                        DocumentChange.Type.MODIFIED -> firebaseOp.cityModified(dc.getDocument())
                        DocumentChange.Type.REMOVED -> firebaseOp.cityRemoved(dc.getDocument())
                    }
                }
            } else {
                //Current data null
            }
        })
    }//end getDataRealtime

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v : View = inflater.inflate(R.layout.fragment_firebase_list, container, false)

        val firebaseOp = FirebaseOperations(requireContext(), v)

        //GET DATA ONCE
        //getDataOnce()

        //REALTIME RECYCLERVIEW UPDATES
        getDataRealtime(firebaseOp)


        //ADD A CITY
        v.btnAdd.setOnClickListener{v ->
            val edtID = activity?.findViewById(R.id.edtCityID) as EditText
            val edtCityName = activity?.findViewById(R.id.edtCityName) as EditText
            val edtStateName = activity?.findViewById(R.id.edtStateName) as EditText
            val edtPopulation = activity?.findViewById(R.id.edtPopulation) as EditText
            val cityID = edtID.text.toString()
            val cityName = edtCityName.text.toString()
            val stateName = edtStateName.text.toString()
            val cityPopulation = edtPopulation.text.toString().toLongOrNull()

            firebaseOp.addCity(cityID, cityName, stateName, cityPopulation)

            edtID.setText("")
            edtCityName.setText("")
            edtStateName.setText("")
            edtPopulation.setText("")
        }

        v.btnUpdateName.setOnClickListener{v ->
            val edtID = activity?.findViewById(R.id.edtCityID) as EditText
            val edtCityName = activity?.findViewById(R.id.edtCityName) as EditText
            val edtStateName = activity?.findViewById(R.id.edtStateName) as EditText
            val edtPopulation = activity?.findViewById(R.id.edtPopulation) as EditText
            val cityID = edtID.text.toString()
            val cityName = edtCityName.text.toString()
            firebaseOp.updateCityName(cityID, cityName)

            edtID.setText("")
            edtCityName.setText("")
            edtStateName.setText("")
            edtPopulation.setText("")
        }

        return v
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }


    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }
}
