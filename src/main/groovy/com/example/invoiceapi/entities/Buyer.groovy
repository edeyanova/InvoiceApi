package com.example.invoiceapi.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotEmpty

@Entity
class Buyer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @NotEmpty(message = "Invoice name must not be empty")
    String name

    Buyer(String name) {
        this.name = name
    }

    Buyer() {}
}
