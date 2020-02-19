package ro.thedotin.jpatraining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.thedotin.jpatraining.domain.Invoice;
import ro.thedotin.jpatraining.domain.InvoiceItem;
import ro.thedotin.jpatraining.domain.Status;
import ro.thedotin.jpatraining.repository.InvoiceItemRepository;
import ro.thedotin.jpatraining.repository.InvoiceRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bad")
public class Bad implements Api {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;

    @Autowired
    public Bad(InvoiceRepository invoiceRepository, InvoiceItemRepository invoiceItemRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceItemRepository = invoiceItemRepository;
    }

    @Override
    @GetMapping("/invoice/due")
    public ResponseEntity<Page<Invoice>> getDueInvoices(Pageable pageSpec) {
        return ResponseEntity.ok(this.invoiceRepository.findByStatus(Status.DUE, pageSpec));
    }

    @Override
    @GetMapping("/invoice/history")
    public ResponseEntity<Page<Invoice>> getInvoiceHistory(ZonedDateTime tstart, ZonedDateTime tend, Pageable pageSpec) {
        return null;
    }

    @Override
    @GetMapping("/item/top")
    public ResponseEntity<Page<InvoiceItem>> getTopPurchasedItems(ZonedDateTime tstart, ZonedDateTime tend) {
        return null;
    }

    @Override
    @PatchMapping("/invoice/paid")
    public ResponseEntity<List<Invoice>> setInvoicesPaid(@RequestBody List<UUID> ids) {
        List<Invoice> affected = this.invoiceRepository.findAllById(ids);
        affected.forEach(inv -> inv.setStatus(Status.PAID));
        this.invoiceRepository.saveAll(affected);
        return ResponseEntity.ok(affected);
    }
}
