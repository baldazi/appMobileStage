package com.example.stage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.TextUtils.*
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    lateinit var logBtn:TextView
    lateinit var  regBtn:Button
    lateinit var  fsNameEdt:EditText
    lateinit var  lsNameEdt:EditText
    lateinit var  bornEdt:EditText
    lateinit var  emailEdt:EditText
    lateinit var  pwdEdt:EditText
    lateinit var  pwConfEdt:EditText
    lateinit var sharedPreferences: SharedPreferences
    val url = "https://obscure-sierra-87499.herokuapp.com/user/new/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        logBtn = findViewById(R.id.id_connect)
        logBtn.setOnClickListener(){
            startActivity(Intent(this@RegisterActivity,MainActivity::class.java))
        }

        fsNameEdt = findViewById(R.id.id_fst_name)
        lsNameEdt = findViewById(R.id.id_lst_name)
        bornEdt = findViewById(R.id.id_born)
        emailEdt = findViewById(R.id.id_email)
        pwdEdt = findViewById(R.id.id_password)
        pwConfEdt = findViewById(R.id.id_confirm)

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)

        regBtn = findViewById(R.id.id_btn_reg)
        regBtn.setOnClickListener(){
            if (isEmpty(fsNameEdt.text.toString()) || isEmpty(lsNameEdt.text.toString())
                || isEmpty(bornEdt.text.toString()) || isEmpty(pwConfEdt.text.toString())
                || isEmpty(emailEdt.text.toString()) || isEmpty(pwdEdt.text.toString())) {

                Toast.makeText(this, "renseignez tous les champs", Toast.LENGTH_SHORT).show()
            }
            if(pwdEdt.text.toString().length < 6){
                Toast.makeText(this, "le mot de passe doit contenir au moins 6 caractères", Toast.LENGTH_SHORT).show()
            }
            else if(pwdEdt.text.toString()  != pwConfEdt.text.toString()){
                Toast.makeText(this, "mots de passe non conformes", Toast.LENGTH_SHORT).show()
            }else {
                regUser(
                    fsNameEdt.text.toString(), lsNameEdt.text.toString(), bornEdt.text.toString(),
                    emailEdt.text.toString(), pwdEdt.text.toString()
                )
            }
        }
    }

    private fun regUser(fsName: String, lsName: String, born: String, email: String, pwd: String) {
        val queue = Volley.newRequestQueue(this@RegisterActivity)
        val request: StringRequest =
            object : StringRequest(Request.Method.POST, url, Response.Listener { response ->
                // on below line we are displaying a toast message as data updated.
                Log.e("TAG", "response: $response")
                Toast.makeText(this@RegisterActivity, "connexion..", Toast.LENGTH_SHORT).show()
                try {
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("email", email)
                        editor.putString("firstname", fsName)
                        editor.putString("lastname", lsName)
                        editor.putInt("id", 15)
                        editor.putInt("level", 0)
                        editor.putString("born", born)
                        editor.putString("school", "")
                        //TODO
                        editor.apply()
                        //TODO
                    Toast.makeText(this, "Merci de nous avoir rejoint", Toast.LENGTH_SHORT).show()
                        val i = Intent(this@RegisterActivity, MainActivityLogin::class.java)
                        startActivity(i)
                        finish()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error -> // displaying toast message on response failure.
                Log.e("tag", "deja utilisateur" + error.toString())
                Toast.makeText(this@RegisterActivity, "paramettres invalides", Toast.LENGTH_SHORT).show()
            }) {
                override fun getBodyContentType(): String {
                    return "application/json"
                }

                @Throws(AuthFailureError::class)
                override fun getBody(): ByteArray {
                    val params =  "firstname=$fsName&lastname=$lsName&born=$born&email=$email&password=$pwd"
                    return params.toByteArray()
                }

            }

        queue.add(request)
    }
}