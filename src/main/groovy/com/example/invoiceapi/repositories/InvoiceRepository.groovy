package com.example.invoiceapi.repositories

import com.example.invoiceapi.entities.Invoice
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository

import java.time.LocalDate

interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findAllByInvoiceDateBetween(LocalDate startDate, LocalDate endDate, Sort sort)
    List<Invoice> findAllByInvoiceDateAfter(LocalDate startDate, Sort sort)
    List<Invoice> findAllByInvoiceDateBefore(LocalDate endDate, Sort sort)
}
