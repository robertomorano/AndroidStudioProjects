package com.example.traveljournal.data.repositories

import com.example.traveljournal.domain.entities.Travel

object travelRepository {
    val list = mutableListOf<Travel>(
        Travel( "Juan García", "611123456"),
        Travel("María López", "678456123"),
        Travel( "Raúl Cimas", "644789456"),
        Travel( "Ana Morantes", "693882147"),
        Travel( "Luis Fernández",  "H"),
        Travel( "Carmen Torres",  "M"),
        Travel( "Javier Alonso",  "H"),
        Travel( "Laura Serrano", "M"),
        Travel( "Pablo Martín",  "H"),
        Travel( "Elena Cruz",  "M"),
        Travel( "Sergio Ruiz", "612778899"),
        Travel( "Isabel Navarro", "688991122"),
        Travel( "Andrés Molina", "671223344"),
        Travel( "Rocío Romero", "690556677"),
        Travel( "Víctor Castillo", "646778899")
    )
    fun getAllTravels(): List<Travel> {
        return list
    }
    fun addTravels(newTravel: Travel){
        list.add(newTravel)
    }
}