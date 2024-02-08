package com.swj.pharmacystatemvvm

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object MainBindingAdapter {
    @BindingAdapter("itemList")
    @JvmStatic
    fun setItemList(view:RecyclerView, items:Any) {
        view.adapter = PharmacyAdapter(view.context, items as ArrayList<PharmacyItem>)
    }
}