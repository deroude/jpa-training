CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table vendor
(
    id   uuid         NOT NULL DEFAULT uuid_generate_v1(),
    name varchar(120) NOT NULL,
    CONSTRAINT vendor_pk PRIMARY KEY (id)
);

create table invoice
(
    id          uuid        NOT NULL DEFAULT uuid_generate_v1(),
    vendor      uuid        NOT NULL,
    vat         float,
    total       float,
    currency    varchar(3),
    status      varchar(20),
    issued_date timestamptz NOT NULL,
    due_date    timestamptz NOT NULL,
    CONSTRAINT invoice_pk PRIMARY KEY (id),
    CONSTRAINT invoice_vendor_fk FOREIGN KEY (vendor) REFERENCES vendor (id) ON DELETE CASCADE
);

CREATE INDEX idx_invoice_issued ON invoice (issued_date DESC);
CREATE INDEX idx_invoice_due ON invoice (due_date ASC);

create table invoice_item
(
    id          uuid NOT NULL DEFAULT uuid_generate_v1(),
    invoice     uuid NOT NULL,
    description varchar(500),
    quantity    float,
    unit        varchar(20),
    unit_value  float,
    currency    varchar(3),
    CONSTRAINT invoice_item_pk PRIMARY KEY (id),
    CONSTRAINT invoice_item_invoice_fk FOREIGN KEY (invoice) REFERENCES invoice (id) ON DELETE CASCADE
);