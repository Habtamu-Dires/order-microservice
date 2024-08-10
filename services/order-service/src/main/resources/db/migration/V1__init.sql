CREATE SEQUENCE If NOT EXISTS order_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS t_order (
    id BIGINT DEFAULT nextval('order_seq') PRIMARY KEY,
    order_number varchar(255) ,
    sku_code varchar(255),
    price DECIMAL(10,2),
    quantity INTEGER

);