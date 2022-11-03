package com.example.stage

import adapter.AnnonceAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import data.Annonce
import org.json.JSONArray

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var annonceAdapter: AnnonceAdapter
private  lateinit var recyclerView: RecyclerView
private  lateinit var newAnnonceList: ArrayList<Annonce>

lateinit var title : ArrayList<String>

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val url = "https://obscure-sierra-87499.herokuapp.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitialize(view)
    }

    private fun dataInitialize(view: View){
        newAnnonceList =  arrayListOf()
        val queue: RequestQueue = Volley.newRequestQueue(activity)
        val req = StringRequest(Request.Method.GET, url,
            { response ->
                val data = response.toString()
                val jsonArray = JSONArray(data)

                for (i in 0 until (jsonArray.length() - 1)){
                    val jObject = jsonArray.getJSONObject(i)
                    val itemTitle = jObject.getString("title")
                    val itemLocation = jObject.getString("location")
                    val annonce = Annonce(itemTitle, itemLocation)
                    newAnnonceList.add(annonce)
                    //Log.e("array", jObject.toString())
                }

                val layoutManager = LinearLayoutManager(context)
                recyclerView = view.findViewById(R.id.home_item_list)
                recyclerView.layoutManager = layoutManager
                recyclerView.setHasFixedSize(true)
                annonceAdapter = AnnonceAdapter(newAnnonceList)
                recyclerView.adapter = annonceAdapter
            }, {err-> Log.e("er", err.toString()) })
        queue.add(req)
    }
}