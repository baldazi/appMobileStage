package com.example.stage

import adapter.AnnonceAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import data.Annonce
import listener.OnAnnonceClickListener
import org.json.JSONArray
import java.time.LocalDate

class SearchActivity : AppCompatActivity(), OnAnnonceClickListener {
    private lateinit var annonceAdapter: AnnonceAdapter
    private  lateinit var recyclerView: RecyclerView
    private  lateinit var annonceList:ArrayList<Annonce>

    lateinit var title : ArrayList<String>
    private var fin = false

    private val url = "https://obscure-sierra-87499.herokuapp.com/search/"
    private lateinit var backBtn: View
    private lateinit var searchEt: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        backBtn = findViewById(R.id.back)
        searchEt = findViewById(R.id.search_in)
        searchEt.requestFocus()
        searchEt.setOnEditorActionListener(TextView.OnEditorActionListener{ _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH ) {
                val key = searchEt.text.toString()
                closeKeyboard()
                Log.e("search", key.toString())
                dataInitialize(key)
                return@OnEditorActionListener true
            }
            false
        })
        backBtn.setOnClickListener{
            finish()
        }
    }

    @SuppressLint("NotifyDataSetChanged", "NewApi")
    private fun dataInitialize(key:String){
        annonceList = arrayListOf()
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val req = StringRequest(
            Request.Method.GET, "$url$key",
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
                    val itemDateString = itemDate.dayOfMonth.toString()+"/"+itemDate.monthValue+"/"+itemDate.year
                    val annonce = Annonce(itemTitle, itemLocation, itemCompany, itemDescription, itemDuration, itemDateString,itemCompanyEmail)
                    annonceList.add(annonce)
                    //Log.e("array", jObject.toString())
                }

                val layoutManager = LinearLayoutManager(this)
                recyclerView = findViewById(R.id.home_item_list)
                recyclerView.visibility = View.VISIBLE
                recyclerView.layoutManager = layoutManager
                recyclerView.setHasFixedSize(true)
                annonceAdapter = AnnonceAdapter(annonceList, this)
                recyclerView.adapter = annonceAdapter
                annonceAdapter.notifyDataSetChanged()

            }, {err-> Log.e("er", err.toString()) })
        queue.add(req)
    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val manager: InputMethodManager = getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            manager
                .hideSoftInputFromWindow(
                    view.windowToken, 0
                )
        }
    }

    override fun onAnnonceClicked(position: Int) {
        val intent = Intent(this, AnnonceActivity::class.java)
        intent.putExtra("title", annonceList[position].title)
        intent.putExtra("description", annonceList[position].description)
        intent.putExtra("date", annonceList[position].initDate)
        intent.putExtra("duration", annonceList[position].duration.toString())
        intent.putExtra("location", annonceList[position].location)
        intent.putExtra("email", annonceList[position].email)
        this.startActivity(intent)
    }
}