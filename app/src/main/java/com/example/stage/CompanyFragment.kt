package com.example.stage

import adapter.AnnonceAdapter
import adapter.EntrepriseAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.appbar.MaterialToolbar
import data.Annonce
import data.Entreprise
import org.json.JSONArray

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [CompanyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CompanyFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var entrepriseAdapter: EntrepriseAdapter
    private  lateinit var recyclerView: RecyclerView
    private  var entrepriseList:ArrayList<Entreprise> = arrayListOf()
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    private lateinit var loadingPB: ProgressBar
    private lateinit var nestedSV: NestedScrollView
    private lateinit var topNavigationView : MaterialToolbar
    private var fin = false
    private var count:Int = 0

    private val url = "https://obscure-sierra-87499.herokuapp.com/company"

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
        return inflater.inflate(R.layout.fragment_company, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CompanyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CompanyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topNavigationView = view?.findViewById<MaterialToolbar>(R.id.topAppBar)!!
        topNavigationView.setOnMenuItemClickListener{

            when (it.itemId) {
                R.id.search -> {
                    val intent = Intent(activity, SearchActivity::class.java)
                    this.startActivity(intent)
                }
            }
            true
        }
        /*****************************************************************/
        Log.e("count", count.toString())
        super.onViewCreated(view, savedInstanceState)
        //newAnnonceList =  arrayListOf()
        shimmerFrameLayout = view.findViewById(R.id.shimmer_entreprise)
        shimmerFrameLayout.startShimmer()
        loadingPB = view.findViewById(R.id.entreprise_loading)
        nestedSV = view.findViewById(R.id.entreprise_nested)
        nestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener{
                v:NestedScrollView, _:Int, scrollY:Int, _:Int, _:Int ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                loadingPB.visibility = View.VISIBLE
                if (!fin) {
                    count += 5
                    dataInitialize(view, count)
                }else{
                    loadingPB.visibility = View.GONE
                }
            }
        })

        dataInitialize(view, count)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun dataInitialize(view: View, offset:Int){
        val queue: RequestQueue = Volley.newRequestQueue(activity)
        val req = StringRequest(
            Request.Method.GET, "$url/$offset",
            { response ->

                val data = response.toString()
                val jsonArray = JSONArray(data)
                fin = jsonArray.length() == 0
                Log.e("err", jsonArray.length().toString())
                for (i in 0 until jsonArray.length()){
                    val jObject = jsonArray.getJSONObject(i)
                    val itemName = jObject.getString("name")
                    val itemSeat = jObject.getString("seat")
                    val itemSect = jObject.getString("activity")
                    val entreprise = Entreprise(itemName, itemSeat, itemSect)
                    entrepriseList.add(entreprise)
                    //Log.e("array", jObject.toString())
                }

                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.visibility = View.GONE

                val layoutManager = LinearLayoutManager(context)
                recyclerView = view.findViewById(R.id.home_item_list)
                recyclerView.visibility = View.VISIBLE
                recyclerView.layoutManager = layoutManager
                recyclerView.setHasFixedSize(true)
                entrepriseAdapter = EntrepriseAdapter(entrepriseList)
                recyclerView.adapter = entrepriseAdapter
                entrepriseAdapter.notifyDataSetChanged()

            }, {err-> Log.e("er", err.toString()) })
        queue.add(req)
    }
}