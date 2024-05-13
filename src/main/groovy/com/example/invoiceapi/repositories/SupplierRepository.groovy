package com.example.invoiceapi.repositories

import com.example.invoiceapi.entities.Supplier
import org.springframework.data.jpa.repository.JpaRepository

interface SupplierRepository extends JpaRepository<Supplier, Long> {}
