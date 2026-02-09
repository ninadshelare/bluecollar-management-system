sql schema

CREATE DATABASE

CREATE DATABASE bluecollar_management;
==================


BASH in COMMAND PROMPT

mysql -u root -p bluecollar_management < bluecollar_management_schema.sql

===========================

use bluecollar_management;

INSERT INTO service_category (description, name) VALUES
('Electrical repair services', 'ELECTRICIAN'),
('Plumbing services', 'PLUMBER'),
('Wood and furniture work', 'CARPENTER'),
('Painting services', 'PAINTER'),
('Daily wage labour work', 'LABOUR'),
('Household maid services', 'MAID');

ALTER TABLE work_request DROP FOREIGN KEY fk_work_request_customer;
ALTER TABLE work_request DROP FOREIGN KEY FKkbt2ocxwvw9oasir52mutluc4;
ALTER TABLE work_request DROP FOREIGN KEY FKm3rgp2r7i24qa5wjiq1pxiw6s;

SELECT *
FROM work_request wr
LEFT JOIN customer c ON wr.customer_id = c.id
WHERE c.id IS NULL;

DELETE FROM work_request
WHERE customer_id NOT IN (SELECT id FROM customer);

ALTER TABLE work_request
ADD CONSTRAINT fk_work_request_customer
FOREIGN KEY (customer_id)
REFERENCES customer(id)
ON DELETE CASCADE;




