CREATE TABLE products (
    id              BIGSERIAL       NOT NULL,
    name            VARCHAR(255)    NOT NULL,
    description     VARCHAR(255)        NOT NULL,
    price           DECIMAL(10, 2)  NOT NULL DEFAULT 0,
    stock           INTEGER         NOT NULL DEFAULT 0,
    category_id     BIGINT          NOT NULL,
    CONSTRAINT pk_product
        PRIMARY KEY (id),
    CONSTRAINT fk_product_category
        FOREIGN KEY (category_id)
        REFERENCES categories (id)
);
