package com.swj.pharmacystatemvvm

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.swj.pharmacystatemvvm.XMLPharmacyParser.setPharmacyList

class MainActivity : AppCompatActivity() {
    var searchView: SearchView? = null
    var acTvSigungu: AutoCompleteTextView? = null
    var sigunguList: ArrayList<String>? = null // AutoCompleteTextView 에 들어가는 시군구 리스트
    var fragments = arrayOf<Fragment>(WeekdayFragment(), WeekendFragment(), HolidayFragment())
    var bnv: BottomNavigationView? = null
    var items = ArrayList<PharmacyItem>()
    var weekEndItems = ArrayList<PharmacyItem>()
    var holidayItems = ArrayList<PharmacyItem>()
    var progressBar: ProgressBar? = null
    var currentSelectedBottomNavigationViewItem = "없음"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_pharmacy)
        setSupportActionBar(toolbar)
//        fragments[0] = WeekdayFragment()
//        fragments[1] = WeekendFragment()
//        fragments[2] = HolidayFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container_fragment, fragments[0])
            .commit()
        bnv = findViewById(R.id.bnv)
        bnv?.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            if (item.itemId == R.id.bnv_weekday) supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_fragment, fragments[0])
                .commit() else if (item.itemId == R.id.bnv_weekend) supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_fragment, fragments[1])
                .commit() else if (item.itemId == R.id.bnv_holiday) supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_fragment, fragments[2])
                .commit()
            true
        })
        acTvSigungu = findViewById(R.id.ac_tv_sigungu)
        acTvSigungu?.setOnItemClickListener(OnItemClickListener { adapterView, view, i, l ->
            if (currentSelectedBottomNavigationViewItem == "weekday") {
                (fragments[0] as WeekdayFragment).searchSigungu(sigunguList!![i], items)
                if (i == 0) (fragments[0] as WeekdayFragment).setItems(items)
            } else if (currentSelectedBottomNavigationViewItem == "weekend") {
                (fragments[1] as WeekendFragment).searchSigungu(sigunguList!![i], weekEndItems)
                if (i == 0) (fragments[1] as WeekendFragment).setItems(weekEndItems)
            } else if (currentSelectedBottomNavigationViewItem == "holiday") {
                (fragments[2] as HolidayFragment).searchSigungu(sigunguList!![i], holidayItems)
                if (i == 0) (fragments[2] as HolidayFragment).setItems(holidayItems)
            }
        })
        progressBar = findViewById(R.id.progressbar)
        pharmacyThread(items)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option, menu)
        val menuItem = menu.findItem(R.id.menu_search)
        searchView = menuItem.actionView as SearchView
        searchView?.queryHint = "약국 명을 입력 하세요."
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (currentSelectedBottomNavigationViewItem == "weekday") {
                    (fragments[0] as WeekdayFragment).searchName(query, items)
                } else if (currentSelectedBottomNavigationViewItem == "weekend") {
                    (fragments[1] as WeekendFragment).searchName(query, weekEndItems)
                } else if (currentSelectedBottomNavigationViewItem == "holiday") {
                    (fragments[2] as HolidayFragment).searchName(query, holidayItems)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        searchView!!.setOnCloseListener {
            if (currentSelectedBottomNavigationViewItem == "weekday") (fragments[0] as WeekdayFragment).setItems(
                items
            ) else if (currentSelectedBottomNavigationViewItem == "weekend") (fragments[1] as WeekendFragment).setItems(
                weekEndItems
            ) else if (currentSelectedBottomNavigationViewItem == "holiday") (fragments[2] as HolidayFragment).setItems(
                holidayItems
            )

            //searchView.onActionViewCollapsed();
            false
        }
        return super.onCreateOptionsMenu(menu)
    }

    fun pharmacyThread(items: ArrayList<PharmacyItem>) {
        object : Thread() {
            override fun run() {
                setPharmacyList(items)
                (fragments[0] as WeekdayFragment).setItems(items)
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
                runOnUiThread {
                    progressBar?.visibility = View.GONE
                    progressBar = null
                    findViewById<View>(R.id.iv_medicine).visibility =
                        View.GONE
                    findViewById<View>(R.id.appbar).visibility =
                        View.VISIBLE
                    findViewById<View>(R.id.layout_pharmacy).visibility =
                        View.VISIBLE
                    sigunguList = ArrayList()
                    for (item in items) {
                        if (!sigunguList!!.contains(item.sigungu)) sigunguList!!.add(item.sigungu)
                    }
                    sigunguList?.add(0, "선택안함")
                    val sigunguAdapter = ArrayAdapter(
                        this@MainActivity, android.R.layout.simple_list_item_1,
                        sigunguList!!
                    )
                    acTvSigungu?.setAdapter(sigunguAdapter)
                }
            }
        }.start()
    }
} // MainActivity class..
