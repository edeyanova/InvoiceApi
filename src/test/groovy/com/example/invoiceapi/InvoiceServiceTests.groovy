package com.example.invoiceapi

import com.example.invoiceapi.entities.Buyer
import com.example.invoiceapi.entities.Invoice
import com.example.invoiceapi.entities.Item
import com.example.invoiceapi.entities.Supplier
import com.example.invoiceapi.exceptions.InvoiceNotFoundException
import com.example.invoiceapi.repositories.BuyerRepository
import com.example.invoiceapi.repositories.InvoiceRepository
import com.example.invoiceapi.repositories.ItemRepository
import com.example.invoiceapi.repositories.SupplierRepository
import com.example.invoiceapi.services.InvoiceService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

import java.time.LocalDate

import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.anyList
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertThrows
import static org.mockito.Mockito.*

@SpringBootTest
class InvoiceServiceTests {
    @Autowired
    InvoiceService invoiceService

    @MockBean
    InvoiceRepository invoiceRepository

    @MockBean
    BuyerRepository buyerRepository

    @MockBean
    SupplierRepository supplierRepository

    @MockBean
    ItemRepository itemRepository

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    void shouldReturnAllInvoices() {
        List<Invoice> invoices = [
                new Invoice(id: 1, number: "001"),
                new Invoice(id: 2, number: "002")
        ]
        when(invoiceRepository.findAll()).thenReturn(invoices)

        List<Invoice> result = invoiceService.getAllInvoices(null, null)

        assertThat(result).isEqualTo(invoices)
    }

    @Test
    void shouldReturnInvoiceById() {
        Long invoiceId = 1L
        Invoice invoice = new Invoice(id: invoiceId, number: "001")
        Optional<Invoice> optionalInvoice = Optional.of(invoice)

        when(invoiceRepository.findById(invoiceId)).thenReturn(optionalInvoice)

        Invoice result = invoiceService.getInvoiceById(invoiceId)

        assertThat(result).isEqualTo(invoice)
    }

    @Test
    void shouldThrowExceptionWhenInvoiceNotFound() {
        when(invoiceRepository.findById(1L)).thenReturn(Optional.empty())

        InvoiceNotFoundException exception = assertThrows(InvoiceNotFoundException.class, {
            invoiceService.getInvoiceById(1L)
        })
        assertEquals("Invoice not found with id: 1", exception.getMessage())
    }

    @Test
    void shouldCreateInvoice() {
        Buyer buyer = new Buyer(id: null, name: "ABC Company")
        Supplier supplier = new Supplier(id: null, name: "XYZ Supplier")

        Item item1 = new Item(
                itemId: null, name: "Item 1",
                quantity: 2, amount: new BigDecimal("10.0")
        )
        Item item2 = new Item(
                itemId: null, name: "Item 2",
                quantity: 3, amount: new BigDecimal("20.0")
        )
        Invoice invoice = new Invoice(
                id: null, number: "001", buyer: buyer,
                supplier: supplier, items: [item1, item2]
        )

        Buyer savedBuyer = new Buyer(id: 1, name: "ABC Company")
        Supplier savedSupplier = new Supplier(id: 1, name: "XYZ Supplier")
        Item savedItem1 = new Item(
                itemId: 1, name: "Item 1", quantity: 2,
                amount: new BigDecimal("10.0")
        )
        Item savedItem2 = new Item(
                itemId: 2, name: "Item 2", quantity: 3,
                amount: new BigDecimal("20.0")
        )
        List<Item> savedItems = [savedItem1, savedItem2]
        Invoice savedInvoice = new Invoice(
                id: 1, number: "001", buyer: savedBuyer,
                supplier: savedSupplier, items: savedItems
        )

        when(buyerRepository.save(any(Buyer))).thenReturn(savedBuyer)
        when(supplierRepository.save(any(Supplier))).thenReturn(savedSupplier)
        when(itemRepository.saveAll(anyList())).thenReturn(savedItems)
        when(invoiceRepository.save(any(Invoice))).thenReturn(savedInvoice)

        Invoice result = invoiceService.createInvoice(invoice)

        assertThat(result).isEqualTo(savedInvoice)
        verify(buyerRepository).save(buyer)
        verify(supplierRepository).save(supplier)
        verify(itemRepository).saveAll(argThat { items ->
            items.size() == 2 &&
            items[0].name == "Item 1" && items[0].quantity == 2 && items[0].amount == new BigDecimal("10.0") &&
            items[1].name == "Item 2" && items[1].quantity == 3 && items[1].amount == new BigDecimal("20.0")
        })
        verify(invoiceRepository).save(invoice)
    }

    @Test
    void shouldUpdateInvoice() {
        Invoice invoice = new Invoice()
        invoice.setId(1L)
        invoice.setNumber("001")
        invoice.setInvoiceDate(LocalDate.now())
        invoice.setDueDate(LocalDate.now().plusDays(30))

        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice))
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(invoice)

        Invoice anotherInvoice = invoice
        anotherInvoice.setNumber("002")

        Invoice updatedInvoice = invoiceService.updateInvoice(1L, anotherInvoice)

        verify(invoiceRepository, times(1)).save(anotherInvoice)

        assertEquals(anotherInvoice, updatedInvoice)
    }

    @Test
    void shouldDeleteInvoiceById() {
        when(invoiceRepository.existsById(anyLong())).thenReturn(true)

        invoiceService.deleteInvoice(1L)

        verify(invoiceRepository, times(1)).deleteById(1L)
    }

    @Test
    void shouldThrowExceptionWhenDeletingNotExistingInvoice() {
        when(invoiceRepository.findById(1L)).thenReturn(Optional.empty())

        InvoiceNotFoundException exception = assertThrows(InvoiceNotFoundException.class, {
            invoiceService.deleteInvoice(1L)
        })
        assertEquals("Invoice not found with id: 1", exception.getMessage())
    }
}
