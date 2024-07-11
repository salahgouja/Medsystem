package com.salah.service;

import com.salah.entity.Invoice;
import com.salah.entity.User;
import com.salah.exception.UserNotFoundException;
import com.salah.repository.InvoiceRepository;
import com.salah.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor

public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(Long invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    public void createInvoice(Invoice invoice) {
        User patient = userRepository.findById(invoice.getPatient().getId())
                .orElseThrow(() -> new UserNotFoundException("Patient not found"));

        invoice.setCreatedAt(new Date());
        invoice.setPatient(patient);
        invoiceRepository.save(invoice);
    }

    public void updateInvoice(Long invoiceId, Invoice updatedInvoice) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        // Check if patient exists
        User patient = userRepository.findById(updatedInvoice.getPatient().getId())
                .orElseThrow(() -> new UserNotFoundException("Patient not found"));

        invoice.setPatient(patient);
        invoice.setTotalAmount(updatedInvoice.getTotalAmount());
        invoice.setItem(updatedInvoice.getItem());
        invoice.setItemPrice(updatedInvoice.getItemPrice());
        invoice.setQuantity(updatedInvoice.getQuantity());
        invoice.setPaid(updatedInvoice.isPaid());

        invoiceRepository.save(invoice);
    }


    public void deleteInvoice(Long invoiceId) {
        invoiceRepository.deleteById(invoiceId);
    }
}
