package com.example.seed_distributor.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.seed_distributor.R
import com.example.seed_distributor.models.SeedModel

class SeedAdapter(private val seedList: ArrayList<SeedModel>) :
    RecyclerView.Adapter<SeedAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.seed_list_item, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentSeed = seedList[position]
        holder.tvSeedName.text = currentSeed.seedName
        holder.tvPriceName.text = currentSeed.price
    }

    override fun getItemCount(): Int {
        return seedList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val tvSeedName : TextView = itemView.findViewById(R.id.tvSeedName)
        val tvPriceName : TextView = itemView.findViewById(R.id.tvPriceName)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}