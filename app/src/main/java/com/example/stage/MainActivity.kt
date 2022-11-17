package com.example.stage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.stage.MainActivityLogin
import com.example.stage.R
import data.User
import org.json.JSONException
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    lateinit var emailEdt: EditText
    lateinit var pwdEdt: EditText
    lateinit var loginBtn: Button
    lateinit var regisertBtn: TextView
    var url = "https://obscure-sierra-87499.herokuapp.com/user"
    lateinit var sharedPreferences: SharedPreferences

    var user= User(0,"","","","","",0,"")
    var uEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        emailEdt = findViewById(R.id.idEdtEmail)
        pwdEdt = findViewById(R.id.idEdtPassword)
        loginBtn = findViewById(R.id.idBtnLogin)
        regisertBtn = findViewById(R.id.create_account)
        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        uEmail = sharedPreferences.getString("email", "").toString()

        loginBtn.setOnClickListener {
            if (TextUtils.isEmpty(emailEdt.text.toString()) || TextUtils.isEmpty(pwdEdt.text.toString())) {

                Toast.makeText(this, "email ou mot de passe non renseigné", Toast.LENGTH_SHORT).show();
            }
            loginUser(emailEdt.text.toString(), pwdEdt.text.toString())
        }
        //TODO
        regisertBtn.setOnClickListener{

            startActivity(Intent(this@MainActivity,RegisterActivity::class.java))
        }

    }

    override fun onStart() {
        super.onStart()
        if (uEmail != "") {

            val i = Intent(this@MainActivity, MainActivityLogin::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun loginUser(userEmail: String, password: String) {
        val queue = Volley.newRequestQueue(this@MainActivity)
        val request: StringRequest =
            @RequiresApi(Build.VERSION_CODES.O)
            object : StringRequest(Request.Method.POST, url, Response.Listener { response ->

                    Log.e("TAG", "response: $response")
                    Toast.makeText(this@MainActivity, "connecté", Toast.LENGTH_SHORT).show()
                    try {

                        val jsonObject = JSONObject(response)
                        Log.e("err",jsonObject.toString())
                        user.id = jsonObject.getInt("ID")
                        user.email = jsonObject.getString("email")
                        user.firstname = jsonObject.getString("firstname")
                        user.lastname = jsonObject.getString("lastname")
                        user.school = jsonObject.getString("school")
                        user.level = jsonObject.getInt("level")
                        user.born = jsonObject.getString("born")
                        user.password = ""
                        if( user.id == 0){
                            Toast.makeText(this, "utilisateur non trouvé", Toast.LENGTH_SHORT).show()
                        }else{
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString("email", user.email)
                            editor.putString("firstname", user.firstname)
                            editor.putString("lastname", user.lastname)
                            editor.putInt("id", user.id)
                            editor.putInt("level", user.level)
                            editor.putString("school", user.school)
                            val bdate = LocalDate.parse(user.born.substring(0,10))
                            val bdateFormated = bdate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                            editor.putString("born", bdateFormated.toString())
                            //TODO
                            editor.apply()
                            //TODO
                            val i = Intent(this@MainActivity, MainActivityLogin::class.java)
                            startActivity(i)
                            finish()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
            }, Response.ErrorListener { error ->
                Log.e("tag", "erreur : " + error.toString())
                Toast.makeText(this@MainActivity, "paramettres invalides", Toast.LENGTH_SHORT).show()
            }) {
                override fun getBodyContentType(): String {
                    return "application/json"
                }

                @Throws(AuthFailureError::class)
                override fun getBody(): ByteArray {
                    val params = HashMap<String, String>()
                    params.put("email",userEmail)
                    params.put("password", password)
                    return JSONObject(params as Map<*, *>?).toString().toByteArray()
                }

            }

        queue.add(request)
    }
}
