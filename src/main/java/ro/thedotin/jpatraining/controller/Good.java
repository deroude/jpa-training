package ro.thedotin.jpatraining.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.thedotin.jpatraining.domain.Invoice;
import ro.thedotin.jpatraining.domain.InvoiceItem;
import ro.thedotin.jpatraining.domain.Status;
import ro.thedotin.jpatraining.domain.views.InvoiceView;
import ro.thedotin.jpatraining.repository.InvoiceItemRepository;
import ro.thedotin.jpatraining.repository.InvoiceRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/good")
public class Good implements Api {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;

    @Autowired
    public Good(InvoiceRepository invoiceRepository, InvoiceItemRepository invoiceItemRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceItemRepository = invoiceItemRepository;
    }

    @Override
    @GetMapping("/invoice/due")
    public ResponseEntity<Page<Invoice>> getDueInvoices(Pageable pageSpec) {
        return ResponseEntity.ok(this.invoiceRepository.getInvoicesWithItemsByStatus(Status.DUE, pageSpec));
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
    @JsonView(InvoiceView.ShortView.class)
    @PatchMapping("/invoice/paid")
    public ResponseEntity<List<Invoice>> setInvoicesPaid(@RequestBody List<UUID> ids) {
        this.invoiceRepository.updateInvoiceStatus(Status.PAID, ids);
        return ResponseEntity.ok(this.invoiceRepository.findAllById(ids));
    }
}
