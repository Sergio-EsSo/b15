package com.example.b15recyclerview

class Country {

    val nameList: MutableList<String> = mutableListOf(
        "Spain", "United Kingdom", "Mongolia",
        "China", "India", "Egypt", "Greece",
        "France", "Finland", "Russia", "Mexico",
        "Peru", "Canada", "Italy", "Germany",
        "Nigeria", "Congo", "Japan", "South Korea",
        "Sudan", "Ethiopia", "Lebanon", "Belarus"
    )

    fun getNameList(): List<String> {
        return nameList
    }
    fun addCountry(country: String){
        nameList.add(country)
    }
    fun killCountry(country: String){
        nameList.remove(country)
    }
    fun removeCountryByIndex(position: Int){
        if(position in nameList.indices){
            nameList.removeAt()
        }
    }

}