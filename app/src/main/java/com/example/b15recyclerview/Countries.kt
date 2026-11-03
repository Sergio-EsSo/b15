package com.example.b15recyclerview

class Countries (val nombre: String, val id: String, val capital: String){

    companion object{

        //mutable para añadir o quitar elementos
        val nameList: MutableList<Countries> = mutableListOf(
            Countries("España", "es", "Madrid"),
            Countries("Reino Unido", "gb", "Londres"),
            Countries("Mongolia", "mn", "Ulán Bator"),
            Countries("China", "cn", "Pekín"),
            Countries("India", "in", "Nueva Delhi"),
            Countries("Egipto", "eg", "El Cairo"),
            Countries("Grecia", "gr", "Atenas"),
            Countries("Francia", "fr", "París"),
            Countries("Finlandia", "fi", "Helsinki"),
            Countries("Rusia", "ru", "Moscú"),
            Countries("México", "mx", "Ciudad de México"),
            Countries("Perú", "pe", "Lima"),
            Countries("Canadá", "ca", "Ottawa"),
            Countries("Italia", "it", "Roma"),
            Countries("Alemania", "de", "Berlín"),
            Countries("Nigeria", "ng", "Abuya"),
            Countries("Congo", "cg", "Brazzaville"),
            Countries("Japón", "jp", "Tokio"),
            Countries("Corea del Sur", "kr", "Seúl"),
            Countries("Sudán", "sd", "Jartum"),
            Countries("Etiopía", "et", "Adís Abeba"),
            Countries("Líbano", "lb", "Beirut"),
            Countries("Bielorrusia", "by", "Minsk")
        )
    }
}