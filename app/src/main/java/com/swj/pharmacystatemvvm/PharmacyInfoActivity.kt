package com.swj.pharmacystatemvvm

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.TextView

import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.UiSettings
import com.naver.maps.map.overlay.Marker

import java.io.IOException
import java.util.Locale


class PharmacyInfoActivity : AppCompatActivity(), OnMapReadyCallback {
    var tvName: TextView? = null
    var tvTel: TextView? = null
    var tvAddr: TextView? = null
    var tvMondayOpen: TextView? = null
    var tvMondayEnd: TextView? = null
    var tvTuesdayOpen: TextView? = null
    var tvTuesdayEnd: TextView? = null
    var tvWednesdayOpen: TextView? = null
    var tvWednesdayEnd: TextView? = null
    var tvThursdayOpen: TextView? = null
    var tvThursdayEnd: TextView? = null
    var tvFridayOpen: TextView? = null
    var tvFridayEnd: TextView? = null
    var tvSaturdayOpen: TextView? = null
    var tvSaturdayEnd: TextView? = null
    var tvSundayOpen: TextView? = null
    var tvSundayEnd: TextView? = null
    var tvHolidayOpen: TextView? = null
    var tvHolidayEnd: TextView? = null
    var mapView: MapView? = null
    var naverMap: NaverMap? = null
    var gpsLatitude = 0.0 // 위도
    var gpsLongitude = 0.0 // 경도
    private fun back() {
        super.onBackPressed()
        finish()
    }

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacy_info)

        /*Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_backspace);
        toolbar.setOnClickListener(view -> back());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("약국 상세");*/findViewById<View>(R.id.ac_img_btn).setOnClickListener(
            View.OnClickListener { view: View? -> back() })
        tvName = findViewById<TextView>(R.id.tv_name)
        tvTel = findViewById<TextView>(R.id.tv_tel)
        tvAddr = findViewById<TextView>(R.id.tv_addr)
        tvMondayOpen = findViewById<TextView>(R.id.tv_monday_open)
        tvMondayEnd = findViewById<TextView>(R.id.tv_monday_end)
        tvTuesdayOpen = findViewById<TextView>(R.id.tv_tuesday_open)
        tvTuesdayEnd = findViewById<TextView>(R.id.tv_tuesday_end)
        tvWednesdayOpen = findViewById<TextView>(R.id.tv_wednesday_open)
        tvWednesdayEnd = findViewById<TextView>(R.id.tv_wednesday_end)
        tvThursdayOpen = findViewById<TextView>(R.id.tv_thursday_open)
        tvThursdayEnd = findViewById<TextView>(R.id.tv_thursday_end)
        tvFridayOpen = findViewById<TextView>(R.id.tv_friday_open)
        tvFridayEnd = findViewById<TextView>(R.id.tv_friday_end)
        tvSaturdayOpen = findViewById<TextView>(R.id.tv_saturday_open)
        tvSaturdayEnd = findViewById<TextView>(R.id.tv_saturday_end)
        tvSundayOpen = findViewById<TextView>(R.id.tv_sunday_open)
        tvSundayEnd = findViewById<TextView>(R.id.tv_sunday_end)
        tvHolidayOpen = findViewById<TextView>(R.id.tv_holiday_open)
        tvHolidayEnd = findViewById<TextView>(R.id.tv_holiday_end)
        mapView = findViewById(R.id.mapview)
        mapView!!.onCreate(savedInstanceState)
        mapView!!.getMapAsync(this)
        val intent: Intent = getIntent()
        val item = intent.getSerializableExtra("item") as PharmacyItem
        tvName!!.setText(item.name)
        tvTel!!.setText(item.tel)
        tvAddr!!.setText(item.roadAddr)
        if (item.roadAddr == null) tvAddr!!.setText(item.lotNoAddr)

        // 월요일
        if (item.mondayOpen != null) {
            tvMondayOpen!!.setText(item.mondayOpen)
            tvMondayEnd!!.setText(item.mondayEnd)
        } else {
            tvMondayOpen!!.setText("휴무")
            findViewById<View>(R.id.tv_mon).setVisibility(View.GONE)
            tvMondayEnd!!.setVisibility(View.GONE)
        }


        // 화요일
        if (item.tuesdayOpen != null) {
            tvTuesdayOpen!!.setText(item.tuesdayOpen)
            tvTuesdayEnd!!.setText(item.tuesdayEnd)
        } else {
            tvTuesdayOpen!!.setText("휴무")
            findViewById<View>(R.id.tv_tues).setVisibility(View.GONE)
            tvTuesdayEnd!!.setVisibility(View.GONE)
        }


        // 수요일
        if (item.wednesdayOpen != null) {
            tvWednesdayOpen!!.setText(item.wednesdayOpen)
            tvWednesdayEnd!!.setText(item.wednesdayEnd)
        } else {
            tvWednesdayOpen!!.setText("휴무")
            findViewById<View>(R.id.tv_wedn).setVisibility(View.GONE)
            tvWednesdayEnd!!.setVisibility(View.GONE)
        }


        // 목요일
        if (item.thursdayOpen != null) {
            tvThursdayOpen!!.setText(item.thursdayOpen)
            tvThursdayEnd!!.setText(item.thursdayEnd)
        } else {
            tvThursdayOpen!!.setText("휴무")
            findViewById<View>(R.id.tv_thur).setVisibility(View.GONE)
            tvThursdayEnd!!.setVisibility(View.GONE)
        }


        // 금요일
        if (item.fridayOpen != null) {
            tvFridayOpen!!.setText(item.fridayOpen)
            tvFridayEnd!!.setText(item.fridayEnd)
        } else {
            tvFridayOpen!!.setText("휴무")
            findViewById<View>(R.id.tv_fri).setVisibility(View.GONE)
            tvFridayEnd!!.setVisibility(View.GONE)
        }


        // 토요일
        if (item.saturdayOpen != null) {
            tvSaturdayOpen!!.setText(item.saturdayOpen)
            tvSaturdayEnd!!.setText(item.saturdayEnd)
        } else {
            tvSaturdayOpen!!.setText("휴무")
            findViewById<View>(R.id.tv_sat).setVisibility(View.GONE)
            tvSaturdayEnd!!.setVisibility(View.GONE)
        }


        // 일요일
        if (item.sundayOpen != null) {
            tvSundayOpen!!.setText(item.sundayOpen)
            tvSundayEnd!!.setText(item.sundayEnd)
        } else {
            tvSundayOpen!!.setText("휴무")
            findViewById<View>(R.id.tv_sun).setVisibility(View.GONE)
            tvSundayEnd!!.setVisibility(View.GONE)
        }


        // 공휴일
        if (item.holidayOpen != null) {
            tvHolidayOpen!!.setText(item.holidayOpen)
            tvHolidayEnd!!.setText(item.holidayEnd)
        } else {
            tvHolidayOpen!!.setText("휴무")
            findViewById<View>(R.id.tv_holi).setVisibility(View.GONE)
            tvHolidayEnd!!.setVisibility(View.GONE)
        }
        gpsLatitude = item.gpsLatitude
        gpsLongitude = item.gpsLongitude

        // api로 긁어온 데이터에 위도 경도가 없었다면.. 초기값 0.0일 것이므로...
        if (gpsLatitude == 0.0 && gpsLongitude == 0.0) {
            val geocoder = Geocoder(this, Locale.KOREA)
            var addr = item.roadAddr
            if (item.roadAddr == null) addr = item.lotNoAddr
            try {
                val addresses: List<Address> = geocoder.getFromLocationName(addr!!, 3)!!
                gpsLatitude = addresses[0].latitude
                gpsLongitude = addresses[0].longitude
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }

        // naver map
        val naverClientId: String = getString(R.string.NAVER_CLIENT_ID) // id 가져오기
        NaverMapSdk.getInstance(this).setClient( //id 등록
            NaverMapSdk.NaverCloudPlatformClient(naverClientId)
        )
    }

    override fun onMapReady(naverMap: NaverMap) {
        naverMap.setMapType(NaverMap.MapType.Basic)

        //건물 표시
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true)
        naverMap.setIndoorEnabled(false)

        //카메라 세팅
        val cameraPosition = CameraPosition(LatLng(gpsLatitude, gpsLongitude), 17.5, 0.0, 0.0)
        naverMap.setCameraPosition(cameraPosition)
        this.naverMap = naverMap
        val uiSettings: UiSettings = naverMap.getUiSettings()

        //네이버맵 UI 설정. 로고 클릭은 반드시 true 값으로 해야함(정책사항)
        uiSettings.setCompassEnabled(true) // 나침반 보이기
        uiSettings.setLocationButtonEnabled(true) // 현재 위치 버튼 보이기
        uiSettings.setLogoClickEnabled(true) // 네이버 로고 클릭
        uiSettings.setScrollGesturesEnabled(true) // 스크롤 제스처
        uiSettings.setZoomControlEnabled(true) // 줌 컨트롤 보이기
        uiSettings.setRotateGesturesEnabled(true) // 화면 회전 제스처
        val marker = Marker()
        marker.setPosition(LatLng(gpsLatitude, gpsLongitude))
        marker.setMap(naverMap)
    }

    protected override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    protected override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    protected override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    protected override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    protected override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    protected override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }
}