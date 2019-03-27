package com.clarkstoro.android_kotlin_examples.FirebaseData

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView

import com.clarkstoro.android_kotlin_examples.R
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_firebase_data.*
import kotlinx.android.synthetic.main.fragment_firebase_data.view.*
import org.w3c.dom.Text

class FirebaseData : Fragment() {

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun getDataOnce(){
        val db = FirebaseFirestore.getInstance()
        val docRefOnce = db.collection("users").document("Q64ozx7sCNFkGre7TbVA") //id of a user
        //Get data once
        docRefOnce.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    //Success
                    //get data
                    var snap = document.data

                    var txtFirstNameFirebaseData = activity?.findViewById(R.id.txtFirstNameOnceFirebaseData) as TextView
                    var txtLastNameFirebaseData = activity?.findViewById(R.id.txtLastNameOnceFirebaseData) as TextView
                    var progressBar = activity?.findViewById(R.id.progressBarDataOnce) as ProgressBar
                    //Set text
                    txtFirstNameFirebaseData.setText("First name: " + snap?.get("First name"))
                    txtLastNameFirebaseData.setText("Last name: " + snap?.get("Last name"))

                    progressBar.visibility = View.GONE //hide progress bar

                } else {
                    //No such document
                }
            }
            .addOnFailureListener { exception ->
                //Error
            }
    }//end getDataOnce

    fun getDataRealtime(){
        val db = FirebaseFirestore.getInstance()
        val docRefRealtime = db.collection("users").document("Ej9YGVqyjzMHsW2Lppnd") //id of a user
        //Get realtime data
        docRefRealtime.addSnapshotListener(EventListener<DocumentSnapshot> { snapshot, e ->

            if (e != null) {
                //Failed
                return@EventListener
            }

            if (snapshot != null && snapshot.exists()) {
                var snap = snapshot.data
                var txtFirstNameFirebaseData = activity?.findViewById(R.id.txtFirstNameRealtimeFirebaseData) as TextView
                var txtLastNameFirebaseData = activity?.findViewById(R.id.txtLastNameRealtimeFirebaseData) as TextView
                var progressBar = activity?.findViewById(R.id.progressBarDataRealtime) as ProgressBar
                txtFirstNameFirebaseData.setText("First name: " + snap?.get("First name"))
                txtLastNameFirebaseData.setText("Last name: " + snap?.get("Last name"))
                progressBar.visibility = View.GONE //hide progress bar
            } else {
                //Current data null
            }
        })
    }//end getDataRealtime

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_firebase_data, container, false)

        getDataOnce()
        getDataRealtime()

        return v
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
