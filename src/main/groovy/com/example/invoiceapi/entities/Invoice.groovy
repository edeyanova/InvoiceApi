package com.example.invoiceapi.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

import java.time.LocalDate

@Entity
class Invoice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String number

    LocalDate invoiceDate
    LocalDate dueDate

    @ManyToOne
    Buyer buyer

    @ManyToOne
    Supplier supplier

    @OneToMany(mappedBy = "invoice")
    List<Item> items = []
}
