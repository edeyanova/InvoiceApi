package com.example.invoiceapi.repositories

import com.example.invoiceapi.entities.Invoice
import org.springframework.data.jpa.repository.JpaRepository

interface InvoiceRepository extends JpaRepository<Invoice, Long> {}
