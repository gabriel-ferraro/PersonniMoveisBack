-- A tabela cmp_category é referente ao produto de forma genérica (pode ser: cadeira, armário, qualquer produto...)
CREATE TABLE cmp_category (
    product_id SERIAL PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL,
    total_value NUMERIC(10, 2) NOT null,
    is_estimate_immediate BOOLEAN NOT NULL -- booleano para gerar orçamento e permitir a compra do produto imediatamente ou não
);

-- cmp_section
CREATE TABLE cmp_section (
	cmp_section_id SERIAL PRIMARY KEY,
	cmp_section_name VARCHAR(255) NOT NULL,
    cmp_category_product_id INTEGER REFERENCES cmp_category(product_id),
    cmp_section_x INTEGER,  -- Para indicar onde a imagem da seção vai ficar, por exemplo, enconsto fica em cima de assento que fica em cima de perna, então seria possível fazer (encosto:x0-y2,assento:x0-y1; pernas:x0-y0)
    cmp_section_y INTEGER,
    img BYTEA
);

-- cmp_element
CREATE TABLE cmp_element (
	cmp_element_id SERIAL PRIMARY KEY,
    cmp_section_id INTEGER REFERENCES cmp_section(cmp_section_id),
    element_name VARCHAR(255) NOT NULL,
    element_value NUMERIC(10, 2) NOT NULL
);

-- cmp_option
CREATE TABLE cmp_option (
    cmp_option_id SERIAL PRIMARY KEY,
    cmp_section_id INTEGER REFERENCES cmp_element(cmp_element_id),
    option_type VARCHAR(50) NOT NULL,
    option_name VARCHAR(255) NOT NULL
);