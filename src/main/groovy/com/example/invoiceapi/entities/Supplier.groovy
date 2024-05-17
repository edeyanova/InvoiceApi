package com.example.invoiceapi.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotEmpty

@Entity
class Supplier {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @NotEmpty(message = "Supplier name must not be empty")
    String name

    Supplier(String name) {
        this.name = name
    }

    Supplier() {}
}
