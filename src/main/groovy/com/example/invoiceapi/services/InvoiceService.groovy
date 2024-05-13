package com.example.invoiceapi.services

import com.example.invoiceapi.entities.Invoice
import com.example.invoiceapi.exceptions.InvoiceNotFoundException
import com.example.invoiceapi.repositories.BuyerRepository
import com.example.invoiceapi.repositories.InvoiceRepository
import com.example.invoiceapi.repositories.SupplierRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.stereotype.Service

@Service
class InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository

    @Autowired
    BuyerRepository buyerRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Transactional
    List<Invoice> getAllInvoices() {
        invoiceRepository.findAll()
    }

    @Transactional
    Invoice getInvoiceById(Long id) {
        def invoice = invoiceRepository.findById(id)
        invoice.orElseThrow { new InvoiceNotFoundException(id) }
    }

    @Transactional
    Invoice createInvoice(Invoice invoice) {
        if (invoice.buyer !== null && invoice.buyer.id == null) {
            invoice.buyer = buyerRepository.save(invoice.buyer);
        }
        if (invoice.supplier != null && invoice.supplier.id == null) {
            invoice.supplier = supplierRepository.save(invoice.supplier)
        }
        return invoiceRepository.save(invoice)
    }

    @Transactional
    Invoice updateInvoice(Long id, Invoice invoice) {
        Invoice existingInvoice =
                invoiceRepository.findById(id)
                        .orElseThrow { new InvoiceNotFoundException(id) }

        existingInvoice.number = invoice.number
        existingInvoice.invoiceDate = invoice.invoiceDate
        existingInvoice.dueDate = invoice.dueDate

        if (invoice.buyer != null) {
            if (invoice.buyer.id != null) {
                existingInvoice.buyer = buyerRepository.findById(invoice.buyer.id)
                        .orElseThrow { new IllegalArgumentException("Invalid Buyer ID: " + invoice.buyer.id) }
            }
        } else {
            existingInvoice.buyer = buyerRepository.save(invoice.buyer)
        }

        if (invoice.supplier != null) {
            if (invoice.supplier.id != null) {
                existingInvoice.supplier = supplierRepository.findById(invoice.supplier.id)
                        .orElseThrow { new IllegalArgumentException("Invalid Supplier ID: " + invoice.supplier.id) }
            } else {
                existingInvoice.supplier = supplierRepository.save(invoice.supplier)
            }
        }

        existingInvoice.items = invoice.items
        invoiceRepository.save(existingInvoice)
    }

    @Transactional
    void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id)
    }
}
