package com.example.invoiceapi.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class Buyer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String name

//    @OneToMany(mappedBy = "buyer")
//    List<Invoice> invoices = []

    Buyer(String name) {
        this.name = name;
    }

    Buyer() {}
}
