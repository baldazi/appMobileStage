package com.example.stage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // on below line we are creating a variable for shared preferences.
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        //TODO
        // on below line we are initializing our shared preferences variable.
        sharedPreferences = activity?.getSharedPreferences("user", Context.MODE_PRIVATE)!!
        //todo


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("ddkk","ok")
        view?.findViewById<TextView>(R.id.us_firstname).text = sharedPreferences.getString("firstname", "").toString()
        view?.findViewById<TextView>(R.id.us_lastname).text = sharedPreferences.getString("lastname", "").toString()
        view?.findViewById<TextView>(R.id.us_email).text = sharedPreferences.getString("email", "").toString()
        view?.findViewById<TextView>(R.id.us_school).text = sharedPreferences.getString("school", "").toString()
        //view?.findViewById<TextView>(R.id.us_level).text = sharedPreferences.getInt("level", 0).toString()
        //view?.findViewById<TextView>(R.id.us_firstname).text = sharedPreferences.getString("firstname", "").toString()
        view?.findViewById<Button>(R.id.idBtnLogOut)?.setOnClickListener {

            // on below line we are creating a variable for
            // editor of shared preferences and initializing it.
            Log.e("ddlm","logg")
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            val i = Intent(activity, MainActivity::class.java)
            startActivity(i)
            //finish()
        }
    }
}