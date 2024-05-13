package com.example.invoiceapi.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String name
    int quantity
    BigDecimal amount

    @ManyToOne
    Invoice invoice
}
