package com.example.invoiceapi.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
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

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("invoice")
    List<Item> items = new ArrayList<>()
}
