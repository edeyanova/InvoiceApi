package com.example.invoiceapi.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class Supplier {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String name

//    @OneToMany(mappedBy = "supplier")
//    List<Invoice> invoices = []

    Supplier(String name) {
        this.name = name;
    }

    Supplier() {}
}
