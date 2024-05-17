package com.example.invoiceapi.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

import java.time.LocalDate

@Entity
class Invoice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @NotEmpty(message = "Invoice number must not be empty")
    String number

    @NotNull(message = "Invoice date is required")
    LocalDate invoiceDate

    @NotNull(message = "Invoice date is required")
    LocalDate dueDate

    @ManyToOne
    @NotNull(message = "Buyer information is required")
    Buyer buyer

    @ManyToOne
    @NotNull(message = "Supplier information is required")
    Supplier supplier

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("invoice")
    @NotEmpty(message = "Invoice must contain at least one item")
    List<Item> items = new ArrayList<>()
}
