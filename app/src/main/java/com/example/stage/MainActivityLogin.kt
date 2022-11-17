package com.example.stage
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


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

        setCurrentFragment(HomeFragment())
        // assigning ID of the toolbar to a variable
        /**********************************************************/
        val callback = object : ActionMode.Callback {

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                menuInflater.inflate(R.menu.top_app_bar, menu)
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
                    Log.e("home","reselected")
                }
                R.id.company -> setCurrentFragment(companyFragment)
                R.id.folder -> setCurrentFragment(folderFragment)
                R.id.user -> setCurrentFragment(userFragment)

            }
            true
        }

    }

    override fun onBackPressed() {

        if(bottomNavigationView.selectedItemId == R.id.home) {
            AlertDialog.Builder(this)
                .setMessage("voulez vous quitter l'application?")
                .setCancelable(false)
                .setPositiveButton("oui"
                ) { _, _ -> finish() }
                .setNegativeButton("non", null)
                .show()
        }else bottomNavigationView.selectedItemId = R.id.home

    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}