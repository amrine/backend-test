-- Table CLIENTS
CREATE TABLE customers (
                          id BIGINT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          is_vip BOOLEAN NOT NULL DEFAULT false
);
CREATE SEQUENCE customers_seq START 1 INCREMENT 1;


-- Table ORDERS
CREATE TABLE orders (
                        id BIGINT PRIMARY KEY,
                        customer_id BIGINT NOT NULL,
                        total DOUBLE PRECISION NOT NULL,
                        CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);
CREATE SEQUENCE orders_seq START 1 INCREMENT 1;


-- Table ITEMS (liée à ORDERS via une table de liaison)
CREATE TABLE items(
                      id BIGSERIAL PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      price DOUBLE PRECISION NOT NULL
);
CREATE SEQUENCE items_seq START 1 INCREMENT 1;

-- Table de liaison ORDERS_ITEMS
CREATE TABLE orders_items (
                             order_id BIGINT NOT NULL,
                             item_id BIGINT NOT NULL,
                             PRIMARY KEY (order_id, item_id),
                             FOREIGN KEY (order_id) REFERENCES orders(id),
                             FOREIGN KEY (item_id) REFERENCES items(id)
);
