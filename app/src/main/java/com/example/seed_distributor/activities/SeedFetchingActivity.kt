package com.example.seed_distributor.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seed_distributor.adapters.SeedAdapter
import com.example.seed_distributor.models.SeedModel
import com.google.firebase.database.*
import com.example.seed_distributor.R

class SeedFetchingActivity : AppCompatActivity() {

    private lateinit var seedRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var seedList: ArrayList<SeedModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seedlist_fetching)

        seedRecyclerView = findViewById(R.id.rvSeed)
        seedRecyclerView.layoutManager = LinearLayoutManager(this)
        seedRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        seedList = arrayListOf<SeedModel>()

        getSeedData()

    }

    private fun getSeedData() {

        seedRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Seed")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                seedList.clear()
                if (snapshot.exists()){
                    for (seedSnap in snapshot.children){
                        val seedData = seedSnap.getValue(SeedModel::class.java)
                        seedList.add(seedData!!)
                    }
                    val mAdapter = SeedAdapter(seedList)
                    seedRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : SeedAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@SeedFetchingActivity, SeedDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("sId", seedList[position].sId)
                            intent.putExtra("shopNumber", seedList[position].shopNumber)
                            intent.putExtra("seedName", seedList[position].seedName)
                            intent.putExtra("price", seedList[position].price)


                            startActivity(intent)
                        }

                    })

                    seedRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}