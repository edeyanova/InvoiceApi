package com.example.invoiceapi.services

import com.example.invoiceapi.entities.Buyer
import com.example.invoiceapi.entities.Invoice
import com.example.invoiceapi.entities.Item
import com.example.invoiceapi.entities.Supplier
import com.example.invoiceapi.exceptions.InvalidInputException
import com.example.invoiceapi.exceptions.InvoiceNotFoundException
import com.example.invoiceapi.repositories.BuyerRepository
import com.example.invoiceapi.repositories.InvoiceRepository
import com.example.invoiceapi.repositories.ItemRepository
import com.example.invoiceapi.repositories.SupplierRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

/**
 * Service class responsible for handling operations related to invoices.
 */
@Service
class InvoiceService {

    private final InvoiceRepository invoiceRepository
    private final BuyerRepository buyerRepository
    private final SupplierRepository supplierRepository
    private final ItemRepository itemRepository

    InvoiceService(InvoiceRepository invoiceRepository, BuyerRepository buyerRepository,
                   SupplierRepository supplierRepository, ItemRepository itemRepository) {
        this.invoiceRepository = invoiceRepository
        this.buyerRepository = buyerRepository
        this.supplierRepository = supplierRepository
        this.itemRepository = itemRepository
    }

    /**
     * Retrieves all invoices with optional sorting.
     *
     * @param sortBy The field to sort by.
     * @param direction The sort direction (asc or desc).
     * @return A list of all invoices.
     */
    @Transactional
    List<Invoice> getAllInvoices(String sortBy, String direction) {
        if (sortBy == null || sortBy.isEmpty()) {
            return invoiceRepository.findAll()
        }

        Sort sort
        try {
            sort = Sort.by(Sort.Direction.fromString(direction), sortBy)
        } catch (IllegalArgumentException ignored) {
            throw new InvalidInputException("Invalid sort direction: " + direction)
        }

        return invoiceRepository.findAll(sort)
    }

    /**
     * Retrieves an invoice by its ID.
     *
     * @param id the ID of the invoice to retrieve
     * @return the invoice with the given ID
     * @throws InvoiceNotFoundException if no invoice with the given ID is found
     */
    @Transactional
    Invoice getInvoiceById(Long id) {
        return getExistingInvoice(id)
    }

    /**
     * Creates a new invoice.
     *
     * @param invoice the invoice to create
     * @return the created invoice
     */
    @Transactional
    Invoice createInvoice(Invoice invoice) {
        saveBuyerIfNecessary(invoice)
        saveSupplierIfNecessary(invoice)
        saveItemsIfNecessary(invoice)

        return invoiceRepository.save(invoice)
    }

    /**
     * Updates an existing invoice with the provided ID
     * using the details from the updated invoice object.
     *
     * @param id The ID of the invoice to be updated.
     * @param invoice The updated invoice object containing the new details.
     * @return The updated invoice object.
     * @throws InvoiceNotFoundException If the invoice with the provided ID is not found.
     */
    @Transactional
    Invoice updateInvoice(Long id, Invoice invoice) {
        Invoice existingInvoice = getExistingInvoice(id)

        updateInvoiceDetails(existingInvoice, invoice)
        updateBuyer(existingInvoice, invoice)
        updateSupplier(existingInvoice, invoice)
        updateItems(existingInvoice, invoice)

        return invoiceRepository.save(existingInvoice)
    }

    /**
     * Deletes an invoice by its ID.
     *
     * @param id the ID of the invoice to delete
     * @throws InvoiceNotFoundException if no invoice with the given ID is found
     */
    @Transactional
    void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new InvoiceNotFoundException(id)
        }
        invoiceRepository.deleteById(id)
    }

    private Invoice getExistingInvoice(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow {
                    new InvoiceNotFoundException(id)
                }
    }

    private void saveBuyerIfNecessary(Invoice invoice) {
        if (invoice.buyer.id == null) {
            invoice.buyer = buyerRepository.save(invoice.buyer)
        }
    }

    private void saveSupplierIfNecessary(Invoice invoice) {
        if (invoice.supplier.id == null) {
            invoice.supplier = supplierRepository.save(invoice.supplier)
        }
    }

    private void saveItemsIfNecessary(Invoice invoice) {
        for (Item item : invoice.items) {
            item.invoice = invoice
        }
        invoice.items = itemRepository.saveAll(invoice.items)
    }

    private static void updateInvoiceDetails(Invoice existingInvoice, Invoice updatedInvoice) {
        existingInvoice.number = updatedInvoice.number
        existingInvoice.invoiceDate = updatedInvoice.invoiceDate
        existingInvoice.dueDate = updatedInvoice.dueDate
    }

    private void updateBuyer(Invoice existingInvoice, Invoice updatedInvoice) {
        Buyer updatedInvoiceBuyer = updatedInvoice.buyer

        if (updatedInvoiceBuyer != null) {
            // Indicates an existing buyer
            if (updatedInvoiceBuyer.id != null) {
                Buyer existingBuyer = buyerRepository.findById(updatedInvoiceBuyer.id)
                        .orElseThrow {
                            new IllegalArgumentException("Invalid Buyer ID: " + updatedInvoiceBuyer.id)
                        }
                existingBuyer.name = updatedInvoiceBuyer.name
                existingInvoice.buyer = buyerRepository.save(existingBuyer)
            } else {
                existingInvoice.buyer = buyerRepository.save(updatedInvoiceBuyer)
            }
        }
    }

    private void updateSupplier(Invoice existingInvoice, Invoice updatedInvoice) {
        Supplier updatedInvoiceSupplier = updatedInvoice.supplier

        if (updatedInvoiceSupplier != null) {
            // Indicates an existing supplier
            if (updatedInvoiceSupplier.id != null) {
                Supplier existingSupplier = supplierRepository.findById(updatedInvoiceSupplier.id)
                        .orElseThrow {
                            new IllegalArgumentException("Invalid Supplier ID: " + updatedInvoiceSupplier.id)
                        }
                existingSupplier.name = updatedInvoiceSupplier.name
                existingInvoice.supplier = supplierRepository.save(existingSupplier)
            } else {
                existingInvoice.supplier = supplierRepository.save(updatedInvoiceSupplier)
            }
        }
    }

    private void updateItems(Invoice existingInvoice, Invoice updatedInvoice) {
        if (updatedInvoice.items != null) {
            // Delete existing items associated with the invoice from the database
            itemRepository.deleteAll(existingInvoice.items)

            // Clear existing items list of the invoice object
            existingInvoice.items.clear()

            for (Item item : updatedInvoice.items) {
                // Reset item ID to null (indicating a new item)
                item.itemId = null
                item.invoice = existingInvoice
                existingInvoice.items.add(item)
            }
            itemRepository.saveAll(existingInvoice.items)
        }
    }
}
