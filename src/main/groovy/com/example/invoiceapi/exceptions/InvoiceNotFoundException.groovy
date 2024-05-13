package com.example.invoiceapi.exceptions

class InvoiceNotFoundException extends RuntimeException {
    InvoiceNotFoundException(Long id) {
        super("Invoice not found with id: $id")
    }
}
