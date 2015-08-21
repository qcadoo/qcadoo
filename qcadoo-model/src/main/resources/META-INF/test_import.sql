drop table if exists products_productlistdto;
create or replace view products_productlistdto as SELECT id, number, name, changeableName, enum, quantity, category, readOnly FROM products_product;

