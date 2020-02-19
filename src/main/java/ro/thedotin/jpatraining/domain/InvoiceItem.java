package ro.thedotin.jpatraining.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Basic(optional = false)
    private Double unitValue;

    @Basic(optional = false)
    private Double quantity;

    @Basic(optional = false)
    private String unit;

    @Basic(optional = false)
    private String description;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "invoice")
    private Invoice invoice;
}
