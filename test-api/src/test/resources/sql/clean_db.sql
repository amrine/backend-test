DELETE FROM orders_items;
DELETE FROM orders;
DELETE FROM items;
DELETE FROM customers;

ALTER SEQUENCE customers_seq RESTART WITH 1;
ALTER SEQUENCE orders_seq RESTART WITH 1;
ALTER SEQUENCE items_seq RESTART WITH 1;
