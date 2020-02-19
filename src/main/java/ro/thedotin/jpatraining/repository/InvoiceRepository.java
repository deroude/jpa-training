package ro.thedotin.jpatraining.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.thedotin.jpatraining.domain.Invoice;
import ro.thedotin.jpatraining.domain.Status;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
    Page<Invoice> findByStatus(Status status, Pageable pageSpec);

    @Query(value = "select i from Invoice i join fetch i.items join fetch i.vendor where i.status=:status",
            countQuery = "select count(i) from Invoice i")
    Page<Invoice> getInvoicesWithItemsByStatus(@Param("status") Status status, Pageable pageSpec);

    @Transactional
    @Modifying
    @Query("update Invoice set status=:status where id in :ids")
    void updateInvoiceStatus(@Param("status") Status status, @Param("ids") List<UUID> ids);
}
