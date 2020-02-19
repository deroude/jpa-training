INSERT INTO vendor(name)
VALUES ('ChocoChicken'),
       ('UglyToys');

WITH v AS (SELECT id FROM vendor WHERE name = 'ChocoChicken'),
     i AS (INSERT INTO invoice (vendor, issued_date, due_date, currency, vat, total, status)
         SELECT v.id, '2019-01-01 20:00:00Z', '2019-01-11 20:00:00Z', 'USD', 16, 100, 'DUE' FROM v RETURNING id)
INSERT
INTO invoice_item(invoice, description, unit, unit_value, quantity)
SELECT i.id, 'Choco fried chicken', 'pcs', 12, 200
FROM i
UNION ALL
SELECT i.id, 'Choco chicken sandwitch', 'pcs', 10, 300
FROM i
UNION ALL
SELECT i.id, 'Choco chicken soup', 'lbs', 1.2, 100
FROM i;

WITH v AS (SELECT id FROM vendor WHERE name = 'ChocoChicken'),
     i AS (INSERT INTO invoice (vendor, issued_date, due_date, currency, vat, total, status)
         SELECT v.id, '2019-01-03 20:00:00Z', '2019-01-13 20:00:00Z', 'USD', 32, 200, 'DUE' FROM v RETURNING id)
INSERT
INTO invoice_item(invoice, description, unit, unit_value, quantity)
SELECT i.id, 'Choco fried chicken', 'pcs', 32, 200
FROM i
UNION ALL
SELECT i.id, 'Choco chicken sandwitch', 'pcs', 18, 300
FROM i
UNION ALL
SELECT i.id, 'Choco chicken soup', 'lbs', 3, 100
FROM i;

WITH v AS (SELECT id FROM vendor WHERE name = 'ChocoChicken'),
     i AS (INSERT INTO invoice (vendor, issued_date, due_date, currency, vat, total, status)
         SELECT v.id, '2019-01-05 20:00:00Z', '2019-01-15 20:00:00Z', 'USD', 17, 110, 'DUE' FROM v RETURNING id)
INSERT
INTO invoice_item(invoice, description, unit, unit_value, quantity)
SELECT i.id, 'Choco fried chicken', 'pcs', 2, 200
FROM i
UNION ALL
SELECT i.id, 'Choco chicken sandwitch', 'pcs', 30, 300
FROM i
UNION ALL
SELECT i.id, 'Choco chicken soup', 'lbs', 0.6, 100
FROM i;

WITH v AS (SELECT id FROM vendor WHERE name = 'UglyToys'),
     i AS (INSERT INTO invoice (vendor, issued_date, due_date, currency, vat, total, status)
         SELECT v.id, '2019-01-11 20:00:00Z', '2019-01-21 20:00:00Z', 'USD', 17, 110, 'DUE' FROM v RETURNING id)
INSERT
INTO invoice_item(invoice, description, unit, unit_value, quantity)
SELECT i.id, 'Limp Teddy bear', 'pcs', 7, 40
FROM i
UNION ALL
SELECT i.id, 'Bieber face robot', 'pcs', 30, 70
FROM i
UNION ALL
SELECT i.id, 'Off note guitar', 'pcs', 7, 20
FROM i;

WITH v AS (SELECT id FROM vendor WHERE name = 'UglyToys'),
     i AS (INSERT INTO invoice (vendor, issued_date, due_date, currency, vat, total, status)
         SELECT v.id, '2019-02-05 20:00:00Z', '2019-02-15 20:00:00Z', 'USD', 17, 110, 'DUE' FROM v RETURNING id)
INSERT
INTO invoice_item(invoice, description, unit, unit_value, quantity)
SELECT i.id, 'Flammable Fire truck', 'pcs', 8, 20
FROM i
UNION ALL
SELECT i.id, 'Headless chicken', 'pcs', 30, 28.99
FROM i
UNION ALL
SELECT i.id, 'Brown lemmings', 'batch', 3, 99.99
FROM i;

WITH v AS (SELECT id FROM vendor WHERE name = 'UglyToys'),
     i AS (INSERT INTO invoice (vendor, issued_date, due_date, currency, vat, total, status)
         SELECT v.id, '2019-02-08 20:00:00Z', '2019-02-18 20:00:00Z', 'USD', 17, 110, 'DUE' FROM v RETURNING id)
INSERT
INTO invoice_item(invoice, description, unit, unit_value, quantity)
SELECT i.id, 'Creepy balaclava', 'pcs', 20, 3.99
FROM i
UNION ALL
SELECT i.id, 'Headless chicken', 'pcs', 8, 28.99
FROM i
UNION ALL
SELECT i.id, 'Endless supply of barf', 'lbs', 8, 0.99
FROM i;