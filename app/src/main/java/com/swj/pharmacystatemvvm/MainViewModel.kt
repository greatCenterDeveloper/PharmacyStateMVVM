package com.swj.pharmacystatemvvm

import android.content.Context
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment

class MainViewModel(val context: Context) {
    var items = ArrayList<PharmacyItem>()
    val fragments = arrayOf<Fragment>(WeekdayFragment(), WeekendFragment(), HolidayFragment())
    val weekEndItems = ArrayList<PharmacyItem>()
    val holidayItems = ArrayList<PharmacyItem>()
    val sigunguList: ArrayList<String> = ArrayList() // AutoCompleteTextView 에 들어가는 시군구 리스트

    fun setItems(items:ArrayList<PharmacyItem>) {
        XMLPharmacyParser.setPharmacyList(items)
        (fragments[0] as WeekdayFragment).setItems(items)

        this.items = items

        var num = 1
        for (i in items.indices) {
            if (items[i].businessDay.contains("주말") ||
                items[i].businessDay.contains("연중무휴") ||
                items[i].businessDay.contains("토") ||
                items[i].businessDay.contains("일요일")
            ) {
                val item = PharmacyItem(items[i])
                item.num = num
                num++
                weekEndItems.add(item)
            }
        }
        num = 1
        for (i in items.indices) {
            if (items[i].businessDay.contains("공휴일") ||
                items[i].businessDay.contains("연중무휴")
            ) {
                val item = PharmacyItem(items[i])
                item.num = num
                num++
                holidayItems.add(item)
            }
        }

        for (item in items) {
            if (!sigunguList.contains(item.sigungu)) sigunguList.add(item.sigungu)
        }
        sigunguList.add(0, "선택안함")
        val sigunguAdapter = ArrayAdapter(
            context, android.R.layout.simple_list_item_1,
            sigunguList
        )
        (context as MainActivity).binding.acTvSigungu.setAdapter(sigunguAdapter)
    }


    fun acTvSigunguSelected(adapterView: AdapterView<ArrayAdapter<String>>, view: View, i: Int, l: Long) {
        if ((context as MainActivity).currentSelectedBottomNavigationViewItem == "weekday") {
            (fragments[0] as WeekdayFragment).searchSigungu(sigunguList[i], items)
            if (i == 0) (fragments[0] as WeekdayFragment).setItems(items)
        } else if (context.currentSelectedBottomNavigationViewItem == "weekend") {
            (fragments[1] as WeekendFragment).searchSigungu(sigunguList[i], weekEndItems)
            if (i == 0) (fragments[1] as WeekendFragment).setItems(weekEndItems)
        } else if (context.currentSelectedBottomNavigationViewItem == "holiday") {
            (fragments[2] as HolidayFragment).searchSigungu(sigunguList[i], holidayItems)
            if (i == 0) (fragments[2] as HolidayFragment).setItems(holidayItems)
        }
    }


    fun bnvItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.bnv_weekday)
            (context as MainActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_fragment, fragments[0])
                .commit()
        else if (item.itemId == R.id.bnv_weekend)
            (context as MainActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_fragment, fragments[1])
                .commit()
        else if (item.itemId == R.id.bnv_holiday)
            (context as MainActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_fragment, fragments[2])
                .commit()
        return true
    }
}