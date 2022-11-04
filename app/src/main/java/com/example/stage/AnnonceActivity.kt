package com.example.stage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class AnnonceActivity : AppCompatActivity() {

    lateinit var desciptionEdt:TextView
    lateinit var titleEdt:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_annonce)
        desciptionEdt = findViewById(R.id.description)
        titleEdt = findViewById(R.id.title)
        titleEdt.text = intent.getStringExtra("title")
        desciptionEdt.text = intent.getStringExtra("description")
    }
}