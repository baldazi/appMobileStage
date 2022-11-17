package com.example.stage

import android.content.Intent
import android.icu.text.CaseMap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class AnnonceActivity : AppCompatActivity() {

    private lateinit var desciptionEdt:TextView
    private lateinit var titleEdt:TextView
    private lateinit var dateEdt:TextView
    private lateinit var durationEdt:TextView
    private lateinit var locationEdt:TextView
    private lateinit var backBtn:View
    private lateinit var subBtn: Button
    lateinit var email: String
    lateinit var title: String
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

        title = intent.getStringExtra("title").toString()
        email = intent.getStringExtra("email").toString()

        backBtn = findViewById(R.id.back)
        subBtn = findViewById(R.id.btn_postuler)

        backBtn.setOnClickListener{
            startActivity(Intent(this, MainActivityLogin::class.java))
        }

        subBtn.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse(
                    "mailto:$email?subject=candidature%20à%20$title&" +
                            "body=Bonjour,%0d%0a%0d%0aJe%20souhaite%20postuler%20à%20cette%20offre...%0d%0a%0d%0aMerci.")
            }
            startActivity(Intent.createChooser(emailIntent, "envoyer un mail"))

        }
    }
}