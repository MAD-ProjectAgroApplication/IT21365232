package com.example.seed_distributor.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.example.seed_distributor.R

class MainActivity2 : AppCompatActivity() {



    private lateinit var btnFetchDataI: ImageView
    private lateinit var btnInsertDataI: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)



        btnInsertDataI = findViewById(R.id.btnInsertDataI)
        btnFetchDataI = findViewById(R.id.btnFetchDataI)

        btnInsertDataI.setOnClickListener {
            val intent = Intent(this, SeedInsertionActivity::class.java)
            startActivity(intent)
        }

        btnFetchDataI.setOnClickListener {
            val intent = Intent(this, SeedFetchingActivity::class.java)
            startActivity(intent)
        }




    }
}