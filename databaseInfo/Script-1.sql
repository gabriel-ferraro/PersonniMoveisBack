select * from vw_product_info ;
select * from vw_element_info ;

select * from product p ;
select * from product_element pe ;
select * from product_option po ;

--view product_info (mostra elementos de um produto)
CREATE VIEW vw_product_info AS
SELECT 
    p.product_id,
    p.product_name,
    p.is_product_editable,
    p.product_total_value,
    pe.product_element_id,
    pe.element_name,
    pe.element_value
FROM 
    product AS p
LEFT JOIN 
    product_element AS pe ON p.product_id = pe.product_id;

-- view_element_info mostra as opções de um determinado elemento de um produto.
CREATE VIEW vw_element_info AS
SELECT 
    p.product_id,
    p.product_name,
    p.is_product_editable,
    p.product_total_value,
    pe.product_element_id,
    pe.element_name,
    pe.element_value,
    po.product_option_id,
    po.option_type,
    po.option_name
FROM 
    product AS p
LEFT JOIN 
    product_element AS pe ON p.product_id = pe.product_id
LEFT JOIN 
    product_option AS po ON pe.product_element_id = po.product_element_id;