package com.example.invoiceapi.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

@Entity
class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long itemId

    @NotEmpty(message = "Item name must not be empty")
    String name

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    int quantity

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    BigDecimal amount

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    @JsonIgnoreProperties("items")
    Invoice invoice
}
