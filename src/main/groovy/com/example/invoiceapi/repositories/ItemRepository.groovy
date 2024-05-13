package com.example.invoiceapi.repositories

import com.example.invoiceapi.entities.Item
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository extends JpaRepository<Item, Long> {}
