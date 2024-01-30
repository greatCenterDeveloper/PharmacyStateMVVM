package com.swj.pharmacystatemvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class HolidayFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_holiday, container, false)
    }

    var items = ArrayList<PharmacyItem>()
    var currentItems = ArrayList<PharmacyItem>()
    var ivPrevPage: ImageView? = null
    var ivNextPage: ImageView? = null
    var tvCurrentPage: TextView? = null
    var recyclerView: RecyclerView? = null
    var adapter: PharmacyAdapter? = null
    var currentPage = 1
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerview_holiday)
        ivPrevPage = view.findViewById(R.id.iv_prev_page)
        ivNextPage = view.findViewById(R.id.iv_next_page)
        tvCurrentPage = view.findViewById(R.id.tv_current_page)
        ivPrevPage?.setOnClickListener(View.OnClickListener { view1: View? -> prevPage() })
        ivNextPage?.setOnClickListener(View.OnClickListener { view1: View? -> nextPage() })

        // 여기에 대량의 데이터를 추가해서 보여줘야 하므로.. currentItems는 멤버 변수에 넣어야 한다.
        adapter = PharmacyAdapter(requireActivity(), currentItems)
        recyclerView?.setAdapter(adapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity != null) items = (activity as MainActivity).holidayItems
        (activity as MainActivity).currentSelectedBottomNavigationViewItem = "holiday"
        val num = 1
        for (i in items.indices) {
            if (currentItems.size >= 10) break
            currentItems.add(items[i])
        }
    }

    fun searchName(query: String, mainActivityitems: ArrayList<PharmacyItem>) {
        items = mainActivityitems
        currentPage = 1
        tvCurrentPage?.text = currentPage.toString()
        val tempList = ArrayList<PharmacyItem>()
        var num = 1
        for (i in items.indices) {
            if (items[i].name.contains(query)) {
                items[i].num = num
                tempList.add(items[i])
                num++
            }
        }
        items = tempList
        currentItems.clear()
        for (i in items.indices) {
            if (i == 10) break
            currentItems.add(items[i])
        }
        adapter?.notifyDataSetChanged()
    }

    fun searchSigungu(sigungu: String, mainActivityitems: ArrayList<PharmacyItem>) {
        items = mainActivityitems
        currentPage = 1
        tvCurrentPage?.text = currentPage.toString()
        val tempList = ArrayList<PharmacyItem>()
        var num = 1
        for (i in items.indices) {
            if (items[i].sigungu == sigungu) {
                items[i].num = num
                tempList.add(items[i])
                num++
            }
        }
        items = tempList
        currentItems.clear()
        for (i in items.indices) {
            if (i == 10) break
            currentItems.add(items[i])
        }
        adapter?.notifyDataSetChanged()
    }

    fun setItems(mainActivityitems: ArrayList<PharmacyItem>) {
        items = mainActivityitems
        currentPage = 1
        tvCurrentPage?.text = currentPage.toString()
        currentItems.clear()
        for (i in currentPage * 10 - 10 until currentPage * 10) {
            items[i].num = i + 1
            currentItems.add(items[i])
        }

//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
        /*if(adapter != null) */ adapter?.notifyDataSetChanged()
        //            }
//        });
    }

    private fun prevPage() {
        if (currentPage > 1) currentPage-- else {
            //ivPrevPage.setClickable(false);
            return
        }

        //ivPrevPage.setClickable(true);
        tvCurrentPage?.text = currentPage.toString()
        currentItems.clear()
        for (i in currentPage * 10 - 10 until currentPage * 10) {
            items[i].num = i + 1
            currentItems.add(items[i])
        }
        adapter?.notifyDataSetChanged()
    }

    private fun nextPage() {
        var pageMax = items.size / 10 + 1
        if (items.size % 10 == 0) pageMax = items.size / 10
        var maxPage = 1
        if (currentPage == pageMax) {
            //ivNextPage.setClickable(false);
            return
        }

        //ivNextPage.setClickable(true);
        if (currentPage < pageMax) {
            currentPage++
            maxPage = currentPage * 10
            if (currentPage == pageMax) maxPage = items.size
        }
        currentItems.clear()
        for (i in currentPage * 10 - 10 until maxPage) {
            items[i].num = i + 1
            currentItems.add(items[i])
        }
        tvCurrentPage?.text = currentPage.toString()
        adapter?.notifyDataSetChanged()
    }
}