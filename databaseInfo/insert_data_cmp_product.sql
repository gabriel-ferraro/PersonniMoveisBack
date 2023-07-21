--- CATEGORIA
INSERT INTO cmp_category (category_name, total_value, is_estimate_immediate)
VALUES
    ('Móvel Personalizado 1', 500.00, true),
    ('Móvel Personalizado 2', 700.00, true);
   
--- SEÇÕES
-- Inserir seção para o primeiro produto "Móvel Personalizado 1"
INSERT INTO cmp_section (cmp_category_product_id, cmp_section_name, cmp_section_x, cmp_section_y, img)
VALUES
    (1, 'Pernas', 0, 0, E'\\xabcdef1234567890'); -- Exemplo de dados em hexadecimal para a imagem

-- Inserir seção para o segundo produto "Móvel Personalizado 1"
INSERT INTO cmp_section (cmp_category_product_id, cmp_section_name, cmp_section_x, cmp_section_y, img)
VALUES
    (1, 'assento', 0, 1, E'\\x0987654321fedcba'); -- Exemplo de dados em hexadecimal para a imagem

-- Inserir seção para o terceiro produto "Móvel Personalizado 2"
INSERT INTO cmp_section (cmp_category_product_id, cmp_section_name, cmp_section_x, cmp_section_y, img)
VALUES
    (2, 'Encosto', 0, 2, E'\\x0123456789abcdef'); -- Exemplo de dados em hexadecimal para a imagem
    
--- ELEMENTOS
-- Inserir elemento para a primeira seção do primeiro produto "Móvel Personalizado 1"
INSERT INTO cmp_element (cmp_section_id, element_name, element_value)
VALUES
    (1, 'Assento', 100.00),
    (1, 'Encosto', 150.00);

-- Inserir elemento para a segunda seção do primeiro produto "Móvel Personalizado 1"
INSERT INTO cmp_element (cmp_section_id, element_name, element_value)
VALUES
    (2, 'Tampo', 200.00);

-- Inserir elemento para a primeira seção do segundo produto "Móvel Personalizado 1"
INSERT INTO cmp_element (cmp_section_id, element_name, element_value)
VALUES
    (3, 'Assento', 120.00),
    (3, 'Encosto', 180.00);
   
--- OPÇÕES
-- Inserir opções para o primeiro elemento da primeira seção do primeiro produto "Móvel Personalizado 1"
INSERT INTO cmp_option (cmp_section_id, option_type, option_name)
VALUES
    (1, 'CHECKBOX', 'Couro'),
    (1, 'CHECKBOX', 'Tecido'),
    (1, 'RANGE', 'Cor'),
    (1, 'RANGE', 'Acabamento'),
    (1, 'BOOLEAN', 'Acolchoado');

-- Inserir opções para o segundo elemento da primeira seção do primeiro produto "Móvel Personalizado 1"
INSERT INTO cmp_option (cmp_section_id, option_type, option_name)
VALUES
    (1, 'CHECKBOX', 'Madeira'),
    (1, 'RANGE', 'Cor');





