package com.example.stage

import adapter.AnnonceAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.FragmentTransaction
import com.facebook.shimmer.ShimmerFrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.appbar.MaterialToolbar
import data.Annonce
import listener.OnAnnonceClickListener
import org.json.JSONArray
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment(), OnAnnonceClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var annonceAdapter: AnnonceAdapter
    private  lateinit var recyclerView: RecyclerView
    private  var newAnnonceList:ArrayList<Annonce> = arrayListOf()
    private lateinit var shimmerFrameLayout:ShimmerFrameLayout
    private lateinit var loadingPB:ProgressBar
    private lateinit var endTv:TextView
    private lateinit var nestedSV:NestedScrollView
    private lateinit var topNavigationView : MaterialToolbar

    lateinit var title : ArrayList<String>
    private var fin = false
    private var count:Int = 0
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
        /******************************************************************/
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
        shimmerFrameLayout = view.findViewById(R.id.shimmer_annonce)
        shimmerFrameLayout.startShimmer()
        loadingPB = view.findViewById(R.id.annonce_loading)
        endTv = view.findViewById(R.id.annonce_end)
        nestedSV = view.findViewById(R.id.annonce_nested)
        nestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener{
                v:NestedScrollView, _:Int, scrollY:Int, _:Int, _:Int ->
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    loadingPB.visibility = View.VISIBLE
                    if (!fin) {
                        count += 5
                        dataInitialize(view, count)
                    }else{
                        loadingPB.visibility = View.GONE
                        endTv.visibility = View.VISIBLE
                    }
                }
        })

        dataInitialize(view, count)
    }

    @SuppressLint("NotifyDataSetChanged", "NewApi")
    private fun dataInitialize(view: View, offset:Int){
        val queue: RequestQueue = Volley.newRequestQueue(activity)
        val req = StringRequest(Request.Method.GET, "$url$offset",
            { response ->

                val data = response.toString()
                val jsonArray = JSONArray(data)
                fin = jsonArray==null||jsonArray.length()==0
                for (i in 0 until (jsonArray.length())){
                    val jObject = jsonArray.getJSONObject(i)
                    val itemTitle = jObject.getString("title")
                    val itemLocation = jObject.getString("location")
                    val itemCompany = jObject.getJSONObject("company").getString("name").toString()
                    val itemCompanyEmail = jObject.getJSONObject("company").getString("email").toString()
                    val itemDescription = jObject.getString("description")
                    val itemDuration = jObject.getInt("duration")
                    val itemDate = LocalDate.parse(jObject.getString("start_date").substring(0,10))
                    val itemDateString  = itemDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString()
                    val annonce = Annonce(itemTitle, itemLocation, itemCompany, itemDescription, itemDuration, itemDateString,itemCompanyEmail)
                    newAnnonceList.add(annonce)
                    //Log.e("array", jObject.toString())
                }

                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.visibility = View.GONE

                val layoutManager = LinearLayoutManager(context)
                recyclerView = view.findViewById(R.id.home_item_list)
                recyclerView.visibility = View.VISIBLE
                recyclerView.layoutManager = layoutManager
                recyclerView.setHasFixedSize(true)
                annonceAdapter = AnnonceAdapter(newAnnonceList, this)
                recyclerView.adapter = annonceAdapter
                annonceAdapter.notifyDataSetChanged()

            }, {err-> Log.e("er", err.toString()) })
        queue.add(req)
    }

    override fun onAnnonceClicked(position: Int) {
        val intent = Intent(activity, AnnonceActivity::class.java)
        intent.putExtra("title", newAnnonceList[position].title)
        intent.putExtra("description", newAnnonceList[position].description)
        intent.putExtra("date", newAnnonceList[position].initDate)
        intent.putExtra("duration", newAnnonceList[position].duration.toString())
        intent.putExtra("location", newAnnonceList[position].location)
        intent.putExtra("email", newAnnonceList[position].email)
        this.startActivity(intent)

    }

}