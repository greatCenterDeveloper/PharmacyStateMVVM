package com.swj.pharmacystatemvvm

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.swj.pharmacystatemvvm.PharmacyAdapter.VH

class PharmacyAdapter(var context: Context, var items: ArrayList<PharmacyItem>) :
    Adapter<VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.tvNum.text = item.num.toString()
        holder.tvNum.tag = item
        holder.tvPharmacyName.text = item.name
        holder.tvPharmacyAddress.text = item.roadAddr
        if (item.roadAddr == null) holder.tvPharmacyAddress.text = item.lotNoAddr
        holder.tvPharmacyBusinessDay.text = item.businessDay
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class VH(itemView: View) : ViewHolder(itemView) {
        var tvNum: TextView
        var tvPharmacyName: TextView
        var tvPharmacyAddress: TextView
        var tvPharmacyBusinessDay: TextView

        init {
            tvNum = itemView.findViewById(R.id.tv_num)
            tvPharmacyName = itemView.findViewById(R.id.tv_pharmacy_name)
            tvPharmacyAddress = itemView.findViewById(R.id.tv_pharmacy_address)
            tvPharmacyBusinessDay = itemView.findViewById(R.id.tv_pharmacy_business_day)
            itemView.setOnClickListener {
//                val intent = Intent(context, PharmacyInfoActivity::class.java)
//                val item = tvNum.tag as PharmacyItem
//                intent.putExtra("item", item)
//                context.startActivity(intent)
            }
        }
    }
}