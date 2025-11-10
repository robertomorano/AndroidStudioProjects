package com.example.contactagenda.data.repositories

import com.example.contactagenda.domain.entities.Contact

object contactRepository {
    val list = mutableListOf<Contact>(
        Contact(1, "Juan García", "611123456", "H"),
        Contact(2, "María López", "678456123", "M"),
        Contact(3, "Raúl Cimas", "644789456", "H"),
        Contact(4, "Ana Morantes", "693882147", "M"),
        Contact(5, "Luis Fernández", "622334455", "H"),
        Contact(6, "Carmen Torres", "633998877", "M"),
        Contact(7, "Javier Alonso", "699445566", "H"),
        Contact(8, "Laura Serrano", "655112233", "M"),
        Contact(9, "Pablo Martín", "677889900", "H"),
        Contact(10, "Elena Cruz", "664223344", "M"),
        Contact(11, "Sergio Ruiz", "612778899", "H"),
        Contact(12, "Isabel Navarro", "688991122", "M"),
        Contact(13, "Andrés Molina", "671223344", "H"),
        Contact(14, "Rocío Romero", "690556677", "M"),
        Contact(15, "Víctor Castillo", "646778899", "H")
    )
    fun getAllContacts(): List<Contact> {
        return list
    }

}