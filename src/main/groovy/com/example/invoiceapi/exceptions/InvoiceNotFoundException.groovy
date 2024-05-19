package com.example.invoiceapi.exceptions

class InvoiceNotFoundException extends RuntimeException {
    private final Long id

    InvoiceNotFoundException(Long id) {
        super("Invoice not found with id: $id")
        this.id = id
    }

    Long getId() {
        return id
    }
}
