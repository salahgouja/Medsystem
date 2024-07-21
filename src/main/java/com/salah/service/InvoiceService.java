package com.salah.service;

import com.salah.entity.Invoice;
import com.salah.patient.Patient;
import com.salah.patient.PatientRepository;
import com.salah.exception.UserNotFoundException;
import com.salah.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final PatientRepository patientRepository;

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(Long invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    public void createInvoice(Invoice invoice) {
        Patient patient = patientRepository.findById(invoice.getPatient().getId())
                .orElseThrow(() -> new UserNotFoundException("Patient not found"));
        invoice.setPatient(patient);
        invoiceRepository.save(invoice);
    }

    public void updateInvoice(Long invoiceId, Invoice updatedInvoice) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        Patient patient = patientRepository.findById(updatedInvoice.getPatient().getId())
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
