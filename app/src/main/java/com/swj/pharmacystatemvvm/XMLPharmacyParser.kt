package com.swj.pharmacystatemvvm

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL

object XMLPharmacyParser {
    fun setPharmacyList(items: ArrayList<PharmacyItem>) {
        /*new Thread(){
            @Override
            public void run() {
                for(int i=1; i<=5; i++) loopAPI(items, i);
                Log.i("pharmacyThread", String.valueOf(items.size()));
            }
        }.start();*/
        for (i in 1..5) loopAPI(items, i)
    }

    private fun loopAPI(items: ArrayList<PharmacyItem>, pIndex: Int) {
        try {
            val stringUrl = "https://openapi.gg.go.kr/ParmacyInfo" +
                    "?KEY=47daf33f4f0a456b86b3d139372a72d5" +
                    "&Type=xml" +
                    "&pIndex=" + pIndex +
                    "&pSize=1000"
            val url = URL(stringUrl)
            val xpp = XmlPullParserFactory.newInstance().newPullParser()
            xpp.setInput(InputStreamReader(url.openStream()))
            var eventType = xpp.eventType
            val item = PharmacyItem()
            var num = 1
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        val tagName = xpp.name
                        when (tagName) {
                            "row" -> {
                                item.num = num
                                num++
                            }

                            "SIGUN_NM" -> {
                                xpp.next()
                                item.sigungu = xpp.text
                            }

                            "INST_NM" -> {
                                xpp.next()
                                item.name = xpp.text
                            }

                            "REPRSNT_TELNO" -> {
                                xpp.next()
                                item.tel = xpp.text
                            }

                            "MON_END_TREAT_TM" -> {
                                xpp.next()
                                if (xpp.text != null) item.mondayEnd = xpp.text
                            }

                            "TUES_END_TREAT_TM" -> {
                                xpp.next()
                                if (xpp.text != null) item.tuesdayEnd = xpp.text
                            }

                            "WED_END_TREAT_TM" -> {
                                xpp.next()
                                if (xpp.text != null) item.wednesdayEnd = xpp.text
                            }

                            "THUR_END_TREAT_TM" -> {
                                xpp.next()
                                if (xpp.text != null) item.thursdayEnd = xpp.text
                            }

                            "FRI_END_TREAT_TM" -> {
                                xpp.next()
                                if (xpp.text != null) item.fridayEnd = xpp.text
                            }

                            "SAT_END_TREAT_TM" -> {
                                xpp.next()
                                if (xpp.text != null) item.saturdayEnd = xpp.text
                            }

                            "SUN_END_TREAT_TM" -> {
                                xpp.next()
                                if (xpp.text != null) item.sundayEnd = xpp.text
                            }

                            "HOLIDAY_END_TREAT_TM" -> {
                                xpp.next()
                                if (xpp.text != null) item.holidayEnd = xpp.text
                            }

                            "MON_BEGIN_TREAT_TM" -> {
                                xpp.next()
                                if (xpp.text != null) item.mondayOpen = xpp.text
                            }

                            "TUES_BEGIN_TREAT_TM" -> {
                                xpp.next()
                                if (xpp.text != null) item.tuesdayOpen = xpp.text
                            }

                            "WED_BEGIN_TREAT_TM" -> {
                                xpp.next()
                                if (xpp.text != null) item.wednesdayOpen = xpp.text
                            }

                            "THUR_BEGIN_TREAT_TM" -> {
                                xpp.next()
                                if (xpp.text != null) item.thursdayOpen = xpp.text
                            }

                            "FRI_BEGIN_TREAT_TM" -> {
                                xpp.next()
                                if (xpp.text != null) item.fridayOpen = xpp.text
                            }

                            "SAT_BEGIN_TREAT_TM" -> {
                                xpp.next()
                                if (xpp.text != null) item.saturdayOpen = xpp.text
                            }

                            "SUN_BEGIN_TREAT_TM" -> {
                                xpp.next()
                                if (xpp.text != null) item.sundayOpen = xpp.text
                            }

                            "HOLIDAY_BEGIN_TREAT_TM" -> {
                                xpp.next()
                                if (xpp.text != null) item.holidayOpen = xpp.text
                            }

                            "REFINE_LOTNO_ADDR" -> {
                                xpp.next()
                                if (xpp.text != null) item.lotNoAddr = xpp.text
                            }

                            "REFINE_ROADNM_ADDR" -> {
                                xpp.next()
                                if (xpp.text != null) item.roadAddr = xpp.text
                            }

                            "REFINE_WGS84_LOGT" -> {
                                xpp.next()
                                if (xpp.text != null) item.gpsLongitude =
                                    xpp.text.toDouble() else if (xpp.text == null) item.gpsLongitude =
                                    0.0
                            }

                            "REFINE_WGS84_LAT" -> {
                                xpp.next()
                                if (xpp.text != null) item.gpsLatitude =
                                    xpp.text.toDouble() else if (xpp.text == null) item.gpsLatitude =
                                    0.0
                            }
                        }
                    }

                    XmlPullParser.END_TAG -> {
                        val tagName2 = xpp.name
                        if (tagName2 == "row") {
                            val businessDay = getBusinessDay(item)
                            item.businessDay = businessDay
                            items.add(item)
                        }
                    }
                }
                eventType = xpp.next()
            }
        } catch (e: MalformedURLException) {
            throw RuntimeException(e)
        } catch (e: XmlPullParserException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    private fun getBusinessDay(item: PharmacyItem): String {
        var businessDay = ""
        if (item.mondayOpen != "" &&
            item.tuesdayOpen != "" &&
            item.wednesdayOpen != "" &&
            item.thursdayOpen != "" &&
            item.fridayOpen != "") businessDay =
            "평일, "
        if (!businessDay.contains("평일")) {
            if (item.mondayOpen != "") businessDay += "월, "
            if (item.tuesdayOpen != "") businessDay += "화, "
            if (item.wednesdayOpen != "") businessDay += "수, "
            if (item.thursdayOpen != "") businessDay += "목, "
            if (item.fridayOpen != "") businessDay += "금, "
        }
        if (item.saturdayOpen != "" &&
            item.sundayOpen != "")
                businessDay += "주말, "
        if (!businessDay.contains("주말")) {
            if (item.saturdayOpen != "") businessDay += "토, "
            if (item.sundayOpen != "") businessDay += "일요일, "
        }
        if (item.holidayOpen != "") businessDay += "공휴일"

        // 마지막에 붙어있는 [, ] 제거
        if (businessDay.endsWith(", ")) businessDay =
            businessDay.substring(0, businessDay.length - 2)
        if (businessDay == "평일, 주말, 공휴일") businessDay = "365일 연중무휴"
        return businessDay
    }
}