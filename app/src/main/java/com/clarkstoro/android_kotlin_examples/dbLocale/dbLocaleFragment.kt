package com.clarkstoro.android_kotlin_examples.dbLocale

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager

import com.clarkstoro.android_kotlin_examples.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_db_locale.view.*
import kotlinx.android.synthetic.main.products_list_item.view.*


class dbLocaleFragment : Fragment(){

    companion object {
        var oldIDProduct = 0
    }


    override fun onStart() {
        super.onStart()
    }

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater.inflate(R.layout.fragment_db_locale, container, false)

        var dbOperations = DBOperations(context!!, view)

        view.btnSubmit.setOnClickListener{view ->

            var edtIDProduct = activity?.findViewById(R.id.edtProductIDProduct) as EditText
            var edtNameProduct = activity?.findViewById(R.id.edtProductName) as EditText
            var edtDescriptionProduct = activity?.findViewById(R.id.edtProductDescription) as EditText

            var IDProductProduct = edtIDProduct.text.toString()
            var nameProduct = edtNameProduct.text.toString()
            var descriptionProduct = edtDescriptionProduct.text.toString()


           dbOperations.insertData(IDProductProduct, nameProduct, descriptionProduct)

        }//end onClickListener

        view.btnUpdate.setOnClickListener{view ->
            var edtIDProduct = activity?.findViewById(R.id.edtProductIDProduct) as EditText
            var edtNameProduct = activity?.findViewById(R.id.edtProductName) as EditText
            var edtDescriptionProduct = activity?.findViewById(R.id.edtProductDescription) as EditText

            var IDProductProduct = edtIDProduct.text.toString()
            var nameProduct = edtNameProduct.text.toString()
            var descriptionProduct = edtDescriptionProduct.text.toString()

            dbOperations.updateData(oldIDProduct, IDProductProduct, nameProduct, descriptionProduct)
        }

        view.btnDeleteAllData.setOnClickListener{view ->
            val products = dbOperations.getAllData()
            val result = dbOperations.deleteAllData()
            if(result){
                Snackbar.make(view, "All products deleted", Snackbar.LENGTH_SHORT)
                    .setAction("UNDO", View.OnClickListener { v ->
                        dbOperations.insertData(products)
                    })
                    .show()
            }
        }

        view?.dbLocaleRecyclerView?.setLayoutManager( LinearLayoutManager(context));
        dbOperations.displayAllData()

        return view
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
