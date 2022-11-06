package com.example.stage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class AnnonceActivity : AppCompatActivity() {

    lateinit var desciptionEdt:TextView
    lateinit var titleEdt:TextView
    lateinit var dateEdt:TextView
    lateinit var durationEdt:TextView
    lateinit var locationEdt:TextView
    lateinit var backBtn:View
    lateinit var subBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_annonce)
        desciptionEdt = findViewById(R.id.description)
        titleEdt = findViewById(R.id.title)
        dateEdt = findViewById(R.id.start_date)
        durationEdt = findViewById(R.id.duration)
        locationEdt = findViewById(R.id.location)

        titleEdt.text = intent.getStringExtra("title")
        desciptionEdt.text = intent.getStringExtra("description")
        dateEdt.text = intent.getStringExtra("date")
        locationEdt.text = intent.getStringExtra("location")
        durationEdt.text = intent.getStringExtra("duration")+" mois"

        backBtn = findViewById(R.id.back)
        subBtn = findViewById(R.id.btn_postuler)

        backBtn.setOnClickListener{
            startActivity(Intent(this, MainActivityLogin::class.java))
        }
    }
}