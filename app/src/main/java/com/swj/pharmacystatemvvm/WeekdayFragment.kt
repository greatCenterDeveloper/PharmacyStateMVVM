package com.swj.pharmacystatemvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class WeekdayFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weekday, container, false)
    }

    var items: ArrayList<PharmacyItem>? = null

    //ArrayList<PharmacyItem> items;
    var currentItems = ArrayList<PharmacyItem>()
    var ivPrevPage: ImageView? = null
    var ivNextPage: ImageView? = null
    var tvCurrentPage: TextView? = null
    var recyclerView: RecyclerView? = null
    var adapter: PharmacyAdapter? = null
    var currentPage = 1
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerview_weekday)
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
        if (activity != null) items = (activity as MainActivity).items
        (activity as MainActivity).currentSelectedBottomNavigationViewItem = "weekday"

        /*for(int i=0; i<items.size(); i++) {
            if(currentItems.size() >= 10) break;
            currentItems.add(items.get(i));
        }*/
    }

    fun searchName(query: String, mainActivityitems: ArrayList<PharmacyItem>) {
        items = mainActivityitems
        currentPage = 1
        tvCurrentPage?.text = currentPage.toString()
        val tempList = ArrayList<PharmacyItem>()
        var num = 1
        for (i in items!!.indices) {
            if (items!![i].name.contains(query)) {
                items!![i].num = num
                tempList.add(items!![i])
                num++
            }
        }
        items = tempList
        currentItems.clear()
        for (i in items!!.indices) {
            if (i == 10) break
            currentItems.add(items!![i])
        }
        adapter?.notifyDataSetChanged()
    }

    fun searchSigungu(sigungu: String, mainActivityitems: ArrayList<PharmacyItem>) {
        items = mainActivityitems
        currentPage = 1
        tvCurrentPage?.text = currentPage.toString()
        val tempList = ArrayList<PharmacyItem>()
        var num = 1
        for (i in items!!.indices) {
            if (items!![i].sigungu == sigungu) {
                items!![i].num = num
                tempList.add(items!![i])
                num++
            }
        }
        items = tempList
        currentItems.clear()
        for (i in items!!.indices) {
            if (i == 10) break
            currentItems.add(items!![i])
        }
        adapter?.notifyDataSetChanged()
    }

    fun setItems(mainActivityitems: ArrayList<PharmacyItem>) {
        items = mainActivityitems
        currentPage = 1
        tvCurrentPage?.text = currentPage.toString()
        for (i in currentPage * 10 - 10 until currentPage * 10) {
            val item = items!![i]
            //Log.i("weekday setItems", "" + item.num + ", " + item.name +", " + item.hashCode() + ", " + Thread.currentThread().getName());
            currentItems.add(item)
        }
        requireActivity().runOnUiThread { if (adapter != null) adapter?.notifyDataSetChanged() }

        /*
        currentItems.clear();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(adapter != null) adapter.notifyDataSetChanged();
            }
        });
        for(int i=(currentPage*10 - 10); i<(currentPage*10); i++) {
            PharmacyItem item = items.get(i);
            //item.num = i+1;
            Log.i("weekday setItems", "" + item.num + ", " + item.name +", " + item.hashCode() + ", " + Thread.currentThread().getName());
            currentItems.add(item);
        }*/

        /*for(PharmacyItem item : currentItems) {
            Log.i("weekday runOnUiThread", "" + item.num + ", " + item.name + ", " + item.hashCode());
        }*/


//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
        /*                for(PharmacyItem item : currentItems) {
                    Log.i("weekday runOnUiThread", "" + item.num + ", " + item.name + ", " + item.hashCode());
                }
                if(adapter != null) {
                    //Toast.makeText(getActivity(), "sssssssss", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }
                else Log.i("weekday runOnUiThread", "null");*/
//            }
//        });
    }

    private fun prevPage() {
        if (currentPage > 1) currentPage-- else return
        tvCurrentPage?.text = currentPage.toString()
        currentItems.clear()
        for (i in currentPage * 10 - 10 until currentPage * 10) {
            items!![i].num = i + 1
            currentItems.add(items!![i])
        }
        adapter?.notifyDataSetChanged()
    }

    private fun nextPage() {
        var pageMax = items!!.size / 10 + 1
        if (items!!.size % 10 == 0) pageMax = items!!.size / 10
        var maxPage = 1
        if (currentPage == pageMax) return
        if (currentPage < pageMax) {
            currentPage++
            maxPage = currentPage * 10
            if (currentPage == pageMax) maxPage = items!!.size
        }
        currentItems.clear()
        for (i in currentPage * 10 - 10 until maxPage) {
            items!![i].num = i + 1
            currentItems.add(items!![i])
        }
        tvCurrentPage?.text = currentPage.toString()
        adapter?.notifyDataSetChanged()
    }
}