package com.example.stage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class RegisterActivity : AppCompatActivity() {
    lateinit var logBtn:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        logBtn = findViewById(R.id.id_connect)
        logBtn.setOnClickListener(){
            startActivity(Intent(this@RegisterActivity,MainActivity::class.java))
        }
    }
}