package ro.thedotin.jpatraining.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import ro.thedotin.jpatraining.domain.Invoice;
import ro.thedotin.jpatraining.domain.InvoiceItem;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface Api {
    ResponseEntity<Page<Invoice>> getDueInvoices(Pageable pageSpec);

    ResponseEntity<Page<Invoice>> getInvoiceHistory(ZonedDateTime tstart, ZonedDateTime tend, Pageable pageSpec);

    ResponseEntity<Page<InvoiceItem>> getTopPurchasedItems(ZonedDateTime tstart, ZonedDateTime tend);

    ResponseEntity<List<Invoice>> setInvoicesPaid(List<UUID> ids);
}
