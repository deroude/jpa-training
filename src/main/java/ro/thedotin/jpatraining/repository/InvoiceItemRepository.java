package ro.thedotin.jpatraining.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.thedotin.jpatraining.domain.InvoiceItem;

import java.util.UUID;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, UUID> {
}
