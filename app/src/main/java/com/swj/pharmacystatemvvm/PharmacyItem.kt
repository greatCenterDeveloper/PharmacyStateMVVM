package com.swj.pharmacystatemvvm

import java.io.Serializable

class PharmacyItem : Serializable {
    var num = 0 // 순서
    var sigungu: String = ""            // 시군구
    var name: String = ""               // 약국 이름
    var tel: String = ""                // 약국 전화번호
    var mondayOpen: String = ""         //  월요일 시작 시간
    var mondayEnd: String = ""          // 월요일 마감 시간
    var tuesdayOpen: String = ""        // 화요일 시작 시간
    var tuesdayEnd: String = ""         // 화요일 마감 시간
    var wednesdayOpen: String = ""      // 수요일 시작 시간
    var wednesdayEnd: String = ""       // 수요일 마감 시간
    var thursdayOpen: String = ""       // 목요일 시작 시간
    var thursdayEnd: String = ""        // 목요일 마감 시간
    var fridayOpen: String = ""         // 금요일 시작 시간
    var fridayEnd: String = ""          // 금요일 마감 시간
    var saturdayOpen: String = ""       // 토요일 시작 시간
    var saturdayEnd: String = ""        // 토요일 마감 시간
    var sundayOpen: String = ""         // 일요일 시작 시간
    var sundayEnd: String = ""          // 일요일 마감 시간
    var holidayOpen: String = ""        // 공휴일 시작 시간
    var holidayEnd: String = ""         // 공휴일 마감 시간
    var lotNoAddr: String = ""          // 약국 일반 주소 (도로명 주소가 비어있을 경우 사용)
    var roadAddr: String = ""           // 약국 도로명 주소
    var businessDay: String = ""        // 영업일 : 평일, 주말, 공휴일
    var gpsLatitude = 0.0               // 위도
    var gpsLongitude = 0.0              // 경도

    constructor()
    constructor(item: PharmacyItem) {
        num = item.num
        sigungu = item.sigungu
        name = item.name
        tel = item.tel
        mondayOpen = item.mondayOpen
        mondayEnd = item.mondayEnd
        tuesdayOpen = item.tuesdayOpen
        tuesdayEnd = item.tuesdayEnd
        wednesdayOpen = item.wednesdayOpen
        wednesdayEnd = item.wednesdayEnd
        thursdayOpen = item.thursdayOpen
        thursdayEnd = item.thursdayEnd
        fridayOpen = item.fridayOpen
        fridayEnd = item.fridayEnd
        saturdayOpen = item.saturdayOpen
        saturdayEnd = item.saturdayEnd
        sundayOpen = item.sundayOpen
        sundayEnd = item.sundayEnd
        holidayOpen = item.holidayOpen
        holidayEnd = item.holidayEnd
        lotNoAddr = item.lotNoAddr
        roadAddr = item.roadAddr
        businessDay = item.businessDay
        gpsLatitude = item.gpsLatitude
        gpsLongitude = item.gpsLongitude
    }
}