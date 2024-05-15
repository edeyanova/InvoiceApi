package com.example.invoiceapi.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long itemId
    String name
    int quantity
    BigDecimal amount

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    @JsonIgnoreProperties("items")
    Invoice invoice
}
