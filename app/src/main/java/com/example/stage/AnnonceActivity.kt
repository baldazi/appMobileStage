package com.example.stage

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        subBtn.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                data = Uri.parse("mailto:")
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, "recipient@email.com")
                putExtra(Intent.EXTRA_SUBJECT, "Subject of Email")
                putExtra(Intent.EXTRA_TEXT, "content of Email")
            }
            if (intent.resolveActivity(this!!.packageManager) != null) {
                //intent.setPackage("com.google.android.gm")
                startActivity(intent)
            } else {
                Log.d("TAG", "No app available to send email.")
            }
        }
    }
}