package com.swj.pharmacystatemvvm

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.swj.pharmacystatemvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var searchView: SearchView
    var fragments = arrayOf<Fragment>(WeekdayFragment(), WeekendFragment(), HolidayFragment())
    var items = ArrayList<PharmacyItem>()
    var weekEndItems = ArrayList<PharmacyItem>()
    var holidayItems = ArrayList<PharmacyItem>()
    var progressBar: ProgressBar? = null
    var currentSelectedBottomNavigationViewItem = "없음"
    val binding:ActivityMainBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = MainViewModel(this)

        binding.toolbar.setNavigationIcon(R.drawable.ic_pharmacy)
        setSupportActionBar(binding.toolbar)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.container_fragment, fragments[0])
            .commit()

        progressBar = binding.progressbar
        pharmacyThread(items)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option, menu)
        val menuItem = menu.findItem(R.id.menu_search)
        searchView = menuItem.actionView as SearchView
        searchView.queryHint = "약국 명을 입력 하세요."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
        searchView.setOnCloseListener {
            if (currentSelectedBottomNavigationViewItem == "weekday")
                (fragments[0] as WeekdayFragment).setItems(items)
            else if (currentSelectedBottomNavigationViewItem == "weekend")
                (fragments[1] as WeekendFragment).setItems(weekEndItems)
            else if (currentSelectedBottomNavigationViewItem == "holiday")
                (fragments[2] as HolidayFragment).setItems(holidayItems)

            //searchView.onActionViewCollapsed();
            false
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun pharmacyThread(items: ArrayList<PharmacyItem>) {
        object : Thread() {
            override fun run() {
                binding.vm.setItems(items)

                runOnUiThread {
                    progressBar?.visibility = View.GONE
                    progressBar = null

                    binding.ivMedicine.visibility = View.GONE
                    binding.appbar.visibility = View.VISIBLE
                    binding.layoutPharmacy.visibility = View.VISIBLE
                }
            }
        }.start()
    }
} // MainActivity class..
