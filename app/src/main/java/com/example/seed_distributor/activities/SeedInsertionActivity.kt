package com.example.seed_distributor.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.seed_distributor.models.SeedModel
import com.example.seed_distributor.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SeedInsertionActivity : AppCompatActivity() {

    private lateinit var sshopNumber: EditText
    private lateinit var sseedName: EditText
    private lateinit var sprice: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seed_insert_seed)

        sshopNumber = findViewById(R.id.sshopNumber)
        sseedName = findViewById(R.id.sseedName)
        sprice = findViewById(R.id.sprice)
        btnSaveData = findViewById(R.id.btnSave)



        dbRef = FirebaseDatabase.getInstance().getReference("Seed")

        btnSaveData.setOnClickListener {
            saveSeedData()
        }
    }

    private fun saveSeedData() {

        //getting values
        val shopNumber = sshopNumber.text.toString()
        val seedName = sseedName.text.toString()
        val price = sprice.text.toString()



        if (shopNumber.isEmpty()) {
            sshopNumber.error = "Please enter shopNumber"
        }
        if (seedName.isEmpty()) {
            sseedName.error = "Please enter seed name"
        }
        if (price.isEmpty()) {
            sprice.error = "Please enter price"
        }

        val sId = dbRef.push().key!!

        val seed = SeedModel(sId, shopNumber , seedName, price)

        dbRef.child(sId).setValue(seed)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                sshopNumber.text.clear()

                sseedName.text.clear()

                sprice.text.clear()




            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}