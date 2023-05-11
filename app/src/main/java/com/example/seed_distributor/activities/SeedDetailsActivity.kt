package com.example.seed_distributor.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.seed_distributor.R
import com.example.seed_distributor.models.SeedModel
import com.google.firebase.database.FirebaseDatabase

class SeedDetailsActivity : AppCompatActivity() {

    private lateinit var tvsId: TextView
    private lateinit var tvshopNumber: TextView
    private lateinit var tvseedName: TextView
    private lateinit var tvprice: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seed_update)

        initView()
        setValuesToViews()




    btnUpdate.setOnClickListener {
        openUpdateDialog(
            intent.getStringExtra("sId").toString(),
            intent.getStringExtra("shopNumber").toString()
        )
    }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("sId").toString()
            )
        }
    }


    private fun initView() {
        tvsId = findViewById(R.id.tvsId)
        tvshopNumber = findViewById(R.id.tvshopNumber)
        tvseedName = findViewById(R.id.tvseedName)
        tvprice = findViewById(R.id.tvprice)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvsId.text = intent.getStringExtra("sId")
        tvshopNumber.text = intent.getStringExtra("shopNumber")
        tvseedName.text = intent.getStringExtra("seedName")
        tvprice.text = intent.getStringExtra("price")

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Seed").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Seed data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, SeedFetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        sId: String,
        shopNumber: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.seed_update_dialog, null)

        mDialog.setView(mDialogView)

        val sshopNumber = mDialogView.findViewById<EditText>(R.id.sshopNumber)
        val sseedName = mDialogView.findViewById<EditText>(R.id.sseedName)
        val sprice = mDialogView.findViewById<EditText>(R.id.sprice)


        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        sshopNumber.setText(intent.getStringExtra("shopNumber").toString())
        sseedName.setText(intent.getStringExtra("seedName").toString())
        sprice.setText(intent.getStringExtra("price").toString())


        mDialog.setTitle("Updating $shopNumber Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateSeedData(
                sId,
                sshopNumber.text.toString(),
                sseedName.text.toString(),
                sprice.text.toString()

            )

            Toast.makeText(applicationContext, "Seed Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvshopNumber.text = sshopNumber.text.toString()
            tvseedName.text = sseedName.text.toString()
            tvprice.text = sprice.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateSeedData(
        id: String,
        shopNumber: String,
        seedname: String,
        price: String,


    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Seed").child(id)
        val empInfo = SeedModel(id, shopNumber,seedname, price)
        dbRef.setValue(empInfo)
    }



}