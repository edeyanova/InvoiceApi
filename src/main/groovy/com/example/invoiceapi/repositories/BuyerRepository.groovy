package com.example.invoiceapi.repositories

import com.example.invoiceapi.entities.Buyer
import org.springframework.data.jpa.repository.JpaRepository

interface BuyerRepository extends JpaRepository<Buyer, Long> {}
