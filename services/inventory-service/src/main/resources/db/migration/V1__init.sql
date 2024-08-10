CREATE SEQUENCE IF NOT EXISTS inventory_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS t_inventory (
    id BIGINT DEFAULT nextval('inventory_seq') PRIMARY KEY,
    sku_code varchar(255),
    quantity INT
);