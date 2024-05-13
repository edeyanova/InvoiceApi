package com.example.invoiceapi.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Buyer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String name
}
