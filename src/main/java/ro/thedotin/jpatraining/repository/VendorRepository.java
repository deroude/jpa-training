package ro.thedotin.jpatraining.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.thedotin.jpatraining.domain.Vendor;

import java.util.UUID;

public interface VendorRepository extends JpaRepository<Vendor, UUID> {


}
