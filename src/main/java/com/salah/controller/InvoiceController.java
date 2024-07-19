package com.salah.controller;

import com.salah.entity.Invoice;
import com.salah.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/invoices")
@PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTION','PATIENT')")

public class InvoiceController {

    private final InvoiceService invoiceService;


    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin:read', 'doctor:read' , 'reception:read', 'patient:read')")

    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @GetMapping("/{invoiceId}")
    @PreAuthorize("hasAnyAuthority('admin:read', 'doctor:read' , 'reception:read', 'patient:read')")

    public Invoice getInvoiceById(@PathVariable Long invoiceId) {
        return invoiceService.getInvoiceById(invoiceId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin:create', 'doctor:create' , 'reception:create')")

    public void createInvoice(@RequestBody Invoice invoice) {
        invoiceService.createInvoice(invoice);
    }

    @PutMapping("/{invoiceId}")
    @PreAuthorize("hasAnyAuthority('admin:update', 'doctor:update' , 'reception:update')")

    public void updateInvoice(@PathVariable Long invoiceId, @RequestBody Invoice updatedInvoice) {
        invoiceService.updateInvoice(invoiceId, updatedInvoice);
    }

    @DeleteMapping("/{invoiceId}")
    @PreAuthorize("hasAnyAuthority('admin:delete', 'doctor:delete')")

    public void deleteInvoice(@PathVariable Long invoiceId) {
        invoiceService.deleteInvoice(invoiceId);
    }
}
