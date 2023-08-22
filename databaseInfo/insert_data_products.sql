INSERT INTO product (product_name, is_product_editable, product_total_value)
VALUES
    ('Cadeira', true, 150.00),
    ('Banqueta', true, 80.00),
    ('Poltrona', true, 300.00),
    ('Mesa', true, 250.00),
    ('Sofá', true, 500.00);

INSERT INTO product_element (product_id, element_name, element_value)
VALUES
    (1, 'Encosto', 50.00),
    (1, 'Assento', 70.00),
    (2, 'Assento', 40.00),
    (3, 'Encosto', 100.00),
    (4, 'Tampo', 100.00),
    (5, 'Encosto', 200.00);

INSERT INTO product_option (product_element_id, option_type, option_name)
VALUES
    (1, 'CHECKBOX', 'Couro'),
    (1, 'CHECKBOX', 'Tecido'),
    (1, 'RANGE', 'Cor'),
    (1, 'RANGE', 'Acabamento'),
    (1, 'BOOLEAN', 'Acolchoado'),
    (2, 'CHECKBOX', 'Couro'),
    (2, 'CHECKBOX', 'Tecido'),
    (2, 'RANGE', 'Cor'),
    (2, 'RANGE', 'Acabamento'),
    (2, 'BOOLEAN', 'Acolchoado'),
    (3, 'CHECKBOX', 'Couro'),
    (3, 'CHECKBOX', 'Tecido'),
    (3, 'RANGE', 'Cor'),
    (3, 'RANGE', 'Acabamento'),
    (3, 'BOOLEAN', 'Acolchoado'),
    (4, 'CHECKBOX', 'Madeira'),
    (4, 'CHECKBOX', 'Vidro'),
    (4, 'RANGE', 'Cor'),
    (4, 'RANGE', 'Acabamento'),
    (4, 'BOOLEAN', 'Dobrável'),
    (5, 'CHECKBOX', 'Couro'),
    (5, 'CHECKBOX', 'Tecido'),
    (5, 'RANGE', 'Cor'),
    (5, 'RANGE', 'Acabamento'),
    (5, 'BOOLEAN', 'Reclinável');
