package com.salah.controller;

import com.salah.entity.Invoice;
import com.salah.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @GetMapping("/{invoiceId}")
    public Invoice getInvoiceById(@PathVariable Long invoiceId) {
        return invoiceService.getInvoiceById(invoiceId);
    }

    @PostMapping
    public void createInvoice(@RequestBody Invoice invoice) {
        invoiceService.createInvoice(invoice);
    }

    @PutMapping("/{invoiceId}")
    public void updateInvoice(@PathVariable Long invoiceId, @RequestBody Invoice updatedInvoice) {
        invoiceService.updateInvoice(invoiceId, updatedInvoice);
    }

    @DeleteMapping("/{invoiceId}")
    public void deleteInvoice(@PathVariable Long invoiceId) {
        invoiceService.deleteInvoice(invoiceId);
    }
}
