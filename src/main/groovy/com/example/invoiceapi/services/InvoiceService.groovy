package com.example.invoiceapi.services

import com.example.invoiceapi.entities.Invoice
import com.example.invoiceapi.repositories.InvoiceRepository
import jakarta.transaction.Transactional
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.stereotype.Service

@Service
class InvoiceService {
    final InvoiceRepository invoiceRepository

    InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository
    }

    @Transactional
    List<Invoice> getAllInvoices() {
        invoiceRepository.findAll()
    }

    @Transactional
    Invoice getInvoiceById(Long id) {
        invoiceRepository.findById(id).orElseThrow { new ChangeSetPersister.NotFoundException("Invoice not found with id: $id") }
    }

    @Transactional
    Invoice createInvoice(Invoice invoice) {
        invoiceRepository.save(invoice)
    }

    @Transactional
    Invoice updateInvoice(Long id, Invoice invoice) {
        Invoice existingInvoice = invoiceRepository.findById(id).orElseThrow { new ChangeSetPersister.NotFoundException("Invoice not found with id: $id") }
        existingInvoice.number = invoice.number
        existingInvoice.invoiceDate = invoice.invoiceDate
        existingInvoice.dueDate = invoice.dueDate
        existingInvoice.buyer = invoice.buyer
        existingInvoice.supplier = invoice.supplier
        existingInvoice.items = invoice.items
        invoiceRepository.save(existingInvoice)
    }

    @Transactional
    void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id)
    }
}
