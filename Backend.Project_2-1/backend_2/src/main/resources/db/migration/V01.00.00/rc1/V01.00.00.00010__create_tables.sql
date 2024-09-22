CREATE TABLE addresses (
    address_id BIGSERIAL PRIMARY KEY,
    country    VARCHAR NOT NULL,
    city       VARCHAR NOT NULL,
    street     VARCHAR NOT NULL
);


CREATE TABLE suppliers (
    supplier_id  BIGSERIAL PRIMARY KEY,
    name         VARCHAR     NOT NULL,
    address_id   BIGINT      NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    CONSTRAINT fk_suppliers_address_id FOREIGN KEY (address_id)
        REFERENCES addresses(address_id)
);

CREATE INDEX i_suppliers_address_id ON suppliers(address_id);


CREATE TABLE images (
    image_id UUID PRIMARY KEY,
    image    BYTEA NOT NULL
);


CREATE TABLE products (
    product_id       BIGSERIAL PRIMARY KEY,
    name             VARCHAR    NOT NULL,
    category         VARCHAR    NOT NULL,
    price            NUMERIC(2) NOT NULL,
    available_stock  BIGINT,
    last_update_date DATE,
    supplier_id      BIGINT     NOT NULL,
    image_id         UUID       NOT NULL,
    CONSTRAINT fk_products_supplier_id FOREIGN KEY (supplier_id)
        REFERENCES suppliers(supplier_id),
    CONSTRAINT fk_products_image_id FOREIGN KEY (image_id)
        REFERENCES images(image_id)
);

CREATE INDEX i_products_supplier_id ON products(supplier_id);


CREATE TABLE clients (
    client_id         BIGSERIAL PRIMARY KEY,
    client_name       VARCHAR                  NOT NULL,
    client_surname    VARCHAR,
    birthday          DATE                     NOT NULL,
    gender            VARCHAR DEFAULT 'female' NOT NULL,
    registration_date DATE                     NOT NULL,
    address_id        BIGINT                   NOT NULL,
    CONSTRAINT fk_clients_address_id FOREIGN KEY (address_id)
        REFERENCES addresses(address_id),
    CONSTRAINT ch_gender CHECK ( gender IN ('female', 'male'))
);

CREATE INDEX i_clients_address_id ON clients(address_id);
CREATE INDEX ui_clients_client_name_and_client_surname ON clients(client_name, client_surname);