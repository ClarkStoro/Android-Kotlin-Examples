package com.clarkstoro.android_kotlin_examples.FirebaseAuth

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

import com.clarkstoro.android_kotlin_examples.R
import com.google.firebase.auth.FirebaseUser
import org.w3c.dom.Text

class FirebaseAuth : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var txtResult : TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    fun checkUserLogged(){
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }//end checkUserLogged

    fun updateUI(currentUser: FirebaseUser?){
        if(currentUser != null){
            txtResult?.text = "User logged as " + currentUser.email
        }else{
            txtResult?.text = "User not logged"
        }

    }//end updateUI

    fun loginUser(email : String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity!!) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                    val edtEmail = activity?.findViewById(R.id.edtEmail) as EditText
                    val edtPassword = activity?.findViewById(R.id.edtPassword) as EditText
                    edtEmail.setText("")
                    edtPassword.setText("")
                    view?.requestFocus()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }//end loginUser

    fun createAccount(email : String, password : String){

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity!!) { task ->

            if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(context, "Account created.", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    updateUI(user)
                    val edtEmail = activity?.findViewById(R.id.edtEmail) as EditText
                    val edtPassword = activity?.findViewById(R.id.edtPassword) as EditText
                    edtEmail.setText("")
                    edtPassword.setText("")
                    view?.requestFocus()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }//end createAccount

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v : View = inflater.inflate(R.layout.fragment_firebase_auth, container, false)
        txtResult = v?.findViewById<TextView>(R.id.txtResult)

        // Initialize Firebase Auth
        auth = com.google.firebase.auth.FirebaseAuth.getInstance()

        checkUserLogged()

        val btnLogin = v?.findViewById(R.id.btnLogin) as Button
        btnLogin.setOnClickListener { v ->
            if(auth.currentUser == null) {
                val edtEmail = activity?.findViewById(R.id.edtEmail) as EditText
                val edtPassword = activity?.findViewById(R.id.edtPassword) as EditText
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                if((email != "")&&(password != "")){
                    loginUser(email, password)
                }else{
                    Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(context, "You have to logout before", Toast.LENGTH_SHORT).show()
            }
        }

        val btnCreateAccount = v?.findViewById(R.id.btnCreateAccount) as Button
        btnCreateAccount.setOnClickListener { v ->
            if (auth.currentUser == null) {
                val edtEmail = activity?.findViewById(R.id.edtEmail) as EditText
                val edtPassword = activity?.findViewById(R.id.edtPassword) as EditText
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                if((email != "")&&(password != "")){
                    createAccount(email, password)
                }else{
                    Toast.makeText(context, "Registration failed.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "You have to logout before", Toast.LENGTH_SHORT).show()
            }
        }

        val btnLogout = v?.findViewById(R.id.btnLogout) as Button
        btnLogout.setOnClickListener { v ->
            if(auth.currentUser != null) {
                FirebaseAuth.getInstance().signOut()
                updateUI(null)
            }
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
