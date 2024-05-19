package com.example.invoiceapi.controllers

import com.example.invoiceapi.entities.Invoice
import com.example.invoiceapi.exceptions.InvalidInputException
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

/**
 * Controller class responsible for handling invoice-related HTTP requests.
 */
@RestController
@RequestMapping("/invoices")
class InvoiceController {
    InvoiceService invoiceService

    InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService
    }

    /**
     * Retrieves all invoices.
     *
     * @return A list of all invoices.
     */
    @GetMapping
    List<Invoice> getAllInvoices() {
        invoiceService.getAllInvoices()
    }

    /**
     * Retrieves an invoice by its ID.
     *
     * @param id The ID of the invoice to retrieve.
     * @return The invoice with the given ID.
     * @throws InvoiceNotFoundException If no invoice with the given ID is found.
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Invoice getInvoiceById(@PathVariable("id") Long id) {
        try {
            return invoiceService.getInvoiceById(id)
        } catch (InvoiceNotFoundException e) {
            throw e
        }
    }

    /**
     * Creates a new invoice.
     *
     * @param invoice The invoice to create.
     * @return The created invoice.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Invoice createInvoice(@RequestBody Invoice invoice) {
        validateInvoice(invoice)
        invoiceService.createInvoice(invoice)
    }

    /**
     * Updates an existing invoice.
     *
     * @param id      The ID of the invoice to update.
     * @param invoice The updated invoice data.
     * @return The updated invoice.
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Invoice updateInvoice(@PathVariable("id") Long id, @RequestBody Invoice invoice) {
        validateInvoice(invoice)
        return invoiceService.updateInvoice(id, invoice)
    }

    /**
     * Deletes an invoice by its ID.
     *
     * @param id The ID of the invoice to delete.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteInvoice(@PathVariable("id") Long id) {
        invoiceService.deleteInvoice(id)
    }

    private static void validateInvoice(Invoice invoice) {
        if (invoice.number == null || invoice.number.isEmpty()) {
            throw new InvalidInputException("Invoice number must not be empty")
        }
        if (invoice.buyer == null || invoice.buyer.name == null || invoice.buyer.name.isEmpty()) {
            throw new InvalidInputException("Buyer information is required")
        }
        if (invoice.supplier == null || invoice.supplier.name == null || invoice.supplier.name.isEmpty()) {
            throw new InvalidInputException("Supplier information is required")
        }
        if (invoice.items == null || invoice.items.isEmpty()) {
            throw new InvalidInputException("Invoice must contain at least one item")
        }
        if (invoice.invoiceDate == null) {
            throw new InvalidInputException("Invoice date is required")
        }
        if (invoice.dueDate == null) {
            throw new InvalidInputException("Due date is required")
        }
    }
}
