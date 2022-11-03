package com.example.stage
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONArray

class MainActivityLogin : AppCompatActivity() {

    lateinit var bottomNavigationView : BottomNavigationView

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_login)

        /*****/
        val homeFragment = HomeFragment()
        val companyFragment = CompanyFragment()
        val folderFragment = FolderFragment()
        val userFragment = UserFragment()

        setCurrentFragment(homeFragment)
        // assigning ID of the toolbar to a variable
        /**********************************************************/
        val callback = object : ActionMode.Callback {

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                menuInflater.inflate(R.menu.contextual_action_bar, menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                return when (item?.itemId) {
                    R.id.home -> {
                        true
                    }
                    R.id.search -> {
                        // Handle delete icon press
                        true
                    }
                    else -> false
                }
            }

            override fun onDestroyActionMode(mode: ActionMode?) {

            }
        }

        //val actionMde = startSupportActionMode(callback)
        //actionMode?.title = "1 selected"
        /*********************************************************/
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.home -> {
                    setCurrentFragment(homeFragment)
                }
                R.id.company -> setCurrentFragment(companyFragment)
                R.id.folder -> setCurrentFragment(folderFragment)
                R.id.user -> setCurrentFragment(userFragment)

            }
            true
        }

        bottomNavigationView.setOnNavigationItemReselectedListener{

            when (it.itemId) {
                R.id.home -> {
                    setCurrentFragment(homeFragment)
                    Log.e("err","resel")
                }
                R.id.company -> setCurrentFragment(companyFragment)
                R.id.folder -> setCurrentFragment(folderFragment)
                R.id.user -> setCurrentFragment(userFragment)

            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}