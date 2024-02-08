package com.swj.pharmacystatemvvm

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.swj.pharmacystatemvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var searchView: SearchView
    val binding:ActivityMainBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = MainViewModel(this.application)
        binding.lifecycleOwner = this

        binding.toolbar.setNavigationIcon(R.drawable.ic_pharmacy)
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option, menu)
        val menuItem = menu.findItem(R.id.menu_search)
        searchView = menuItem.actionView as SearchView
        searchView.queryHint = "약국 입력"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean { return false }
        })
        searchView.setOnCloseListener {
            //
            false
        }
        return super.onCreateOptionsMenu(menu)
    }
} // MainActivity class..
