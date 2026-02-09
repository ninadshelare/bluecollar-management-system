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



