package com.example.invoiceapi.services

import com.example.invoiceapi.entities.Buyer
import com.example.invoiceapi.entities.Invoice
import com.example.invoiceapi.entities.Item
import com.example.invoiceapi.entities.Supplier
import com.example.invoiceapi.exceptions.InvoiceNotFoundException
import com.example.invoiceapi.repositories.BuyerRepository
import com.example.invoiceapi.repositories.InvoiceRepository
import com.example.invoiceapi.repositories.ItemRepository
import com.example.invoiceapi.repositories.SupplierRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository

    @Autowired
    BuyerRepository buyerRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    ItemRepository itemRepository;

    @Transactional
    List<Invoice> getAllInvoices() {
        invoiceRepository.findAll()
    }

    @Transactional
    Invoice getInvoiceById(Long id) {
        invoiceRepository.findById(id)
                .orElseThrow { new InvoiceNotFoundException(id) }
    }

    @Transactional
    Invoice createInvoice(Invoice invoice) {
        if (invoice.buyer != null && invoice.buyer.id == null) {
            invoice.buyer = buyerRepository.save(invoice.buyer)
        }
        if (invoice.supplier != null && invoice.supplier.id == null) {
            invoice.supplier = supplierRepository.save(invoice.supplier)
        }
        if (invoice.getItems() != null) {
            for (Item item : invoice.getItems()) {
                item.invoice = invoice  // Set the back-reference
            }
            invoice.items = itemRepository.saveAll(invoice.items)
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
                Buyer existingBuyer = buyerRepository.findById(invoice.buyer.id)
                        .orElseThrow { new IllegalArgumentException("Invalid Buyer ID: " + invoice.buyer.id) }
                existingBuyer.name = invoice.buyer.name
                existingInvoice.buyer = buyerRepository.save(existingBuyer)
            } else {
                existingInvoice.buyer = buyerRepository.save(invoice.buyer)
            }
        }

        if (invoice.supplier != null) {
            if (invoice.supplier.id != null) {
                Supplier existingSupplier = supplierRepository.findById(invoice.supplier.id)
                        .orElseThrow { new IllegalArgumentException("Invalid Supplier ID: " + invoice.supplier.id) }
                existingSupplier.name = invoice.supplier.name
                existingInvoice.supplier = supplierRepository.save(existingSupplier)
            } else {
                existingInvoice.supplier = supplierRepository.save(invoice.supplier)
            }
        }

        if (invoice.items != null) {
            itemRepository.deleteAll(existingInvoice.items)

            existingInvoice.items.clear()

            for (Item item : invoice.items) {
                item.itemId = null
                item.invoice = existingInvoice
                existingInvoice.items.add(item)
            }
            itemRepository.saveAll(existingInvoice.items)
        }

        return invoiceRepository.save(existingInvoice)
    }

    @Transactional
    void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new InvoiceNotFoundException(id)
        }
        invoiceRepository.deleteById(id)
    }
}
