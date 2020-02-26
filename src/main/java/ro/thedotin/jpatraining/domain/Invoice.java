package ro.thedotin.jpatraining.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import ro.thedotin.jpatraining.domain.views.InvoiceView;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Invoice {

    @Id
    @JsonView(InvoiceView.ShortView.class)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor")
    private Vendor vendor;

    @Basic
    @JsonView(InvoiceView.ShortView.class)
    private Double total;

    @Basic
    @JsonView(InvoiceView.ShortView.class)
    private Double vat;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @JsonView(InvoiceView.ShortView.class)
    private Currency currency;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @JsonView(InvoiceView.ShortView.class)
    private Status status;

    @Column
    @JsonView(InvoiceView.ShortView.class)
    private ZonedDateTime issuedDate;

    @Column
    @JsonView(InvoiceView.ShortView.class)
    private ZonedDateTime dueDate;

    @OneToMany(targetEntity = InvoiceItem.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice")
    private List<InvoiceItem> items;
}
