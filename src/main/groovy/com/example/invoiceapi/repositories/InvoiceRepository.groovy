package com.example.invoiceapi.repositories

import com.example.invoiceapi.entities.Invoice
import jakarta.persistence.criteria.CriteriaBuilder
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository

import java.time.LocalDate

interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findAllByInvoiceDateBetween(LocalDate startDate, LocalDate endDate, Sort sort)
    List<Invoice> findAllByInvoiceDateBetweenAndNumber(LocalDate startDate, LocalDate endDate, String number, Sort sort)
    List<Invoice> findAllByInvoiceDateBeforeAndNumber(LocalDate endDate, String number, Sort sort)
    List<Invoice> findAllByInvoiceDateAfter(LocalDate startDate, Sort sort)
    List<Invoice> findAllByInvoiceDateAfterAndNumber(LocalDate startDate, String number, Sort sort)
    List<Invoice> findAllByNumber(String number, Sort sort)
    List<Invoice> findAllByInvoiceDateBefore(LocalDate endDate, Sort sort)
}
