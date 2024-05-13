package com.example.invoiceapi.controllers

import com.example.invoiceapi.entities.Invoice
import com.example.invoiceapi.exceptions.InvoiceNotFoundException
import com.example.invoiceapi.services.InvoiceService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/invoices")
class InvoiceController {
    InvoiceService invoiceService

    InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService
    }

    @GetMapping
    List<Invoice> getAllInvoices() {
        invoiceService.getAllInvoices()
    }

    @GetMapping("/{id}")
    Invoice getInvoiceById(@PathVariable("id") Long id) {
        try {
            invoiceService.getInvoiceById(id)
        } catch (InvoiceNotFoundException e) {
            throw e
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Invoice createInvoice(@RequestBody Invoice invoice) {
        invoiceService.createInvoice(invoice)
    }

    @PutMapping("/{id}")
    Invoice updateInvoice(@PathVariable("id") Long id, @RequestBody Invoice invoice) {
        invoiceService.updateInvoice(id, invoice)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteInvoice(@PathVariable("id") Long id) {
        invoiceService.deleteInvoice(id)
    }
}
