package com.clarkstoro.android_kotlin_examples.JSONArrayList

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.clarkstoro.android_kotlin_examples.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_jsonarraylist.view.*
import okhttp3.*
import java.io.IOException


class JSONArrayListFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view : View = inflater.inflate(R.layout.fragment_jsonarraylist, container, false)


        view.myRecyclerViewJSON.setLayoutManager( LinearLayoutManager(context));

        //val adapter = ListAdapter(users,requireContext())

        fetchJSON()

        return view
    }

    fun fetchJSON(){

        val url = "https://reqres.in/api/users?page=2"

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body()?.string()

                val gson = GsonBuilder().create()

                val user = gson.fromJson(body, Users::class.java)

                activity?.runOnUiThread {
                    val adapter =
                        ListAdapterJSON(user, requireContext())
                    view?.myRecyclerViewJSON?.adapter = adapter
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Could not retrieve JSON")
            }
        })
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
