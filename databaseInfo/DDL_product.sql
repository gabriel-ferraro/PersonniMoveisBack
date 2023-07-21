-- Tabela para produtos "prontos" e editáveis
CREATE TABLE product (
    product_id SERIAL PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    product_total_value NUMERIC(10, 2) NOT NULL,
    is_product_editable BOOLEAN NOT NULL
);

-- Elemento do produto (1 produto pode ter muitos elementos)
CREATE TABLE product_element (
    product_element_id SERIAL PRIMARY KEY,
    product_id INTEGER REFERENCES product(product_id),
    element_value NUMERIC(10, 2) NOT NULL,
    element_name VARCHAR(255) NOT NULL
);

-- Opção do elementos (1 elemento pode ter muitas opções)
CREATE TABLE product_option (
    product_option_id SERIAL PRIMARY KEY,
    product_element_id INTEGER REFERENCES product_element(product_element_id),
    option_type VARCHAR(50),
    option_name VARCHAR(255)
);

