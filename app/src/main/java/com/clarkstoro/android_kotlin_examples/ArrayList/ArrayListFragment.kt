package com.clarkstoro.android_kotlin_examples.ArrayList

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.clarkstoro.android_kotlin_examples.R
import kotlinx.android.synthetic.main.fragment_array_list.view.*

class ArrayListFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view  : View = inflater.inflate(R.layout.fragment_array_list, container, false)


        //Create array of users
        val users = arrayListOf<String>("Anna", "Joel", "Mark", "Veronica", "Noel", "Hilary")
        view.myRecyclerView.setLayoutManager( LinearLayoutManager(context));
        val adapter = ListAdapter(users, requireContext())
        //Set the adapter to the list
        view.myRecyclerView.adapter = adapter


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
