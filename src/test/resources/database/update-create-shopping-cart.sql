-- Clear existing data to avoid primary key conflicts
DELETE FROM books_categories WHERE book_id = 1;
DELETE FROM cart_items WHERE shopping_cart_id = 1;
DELETE FROM shopping_carts WHERE id = 1;
DELETE FROM books WHERE id = 1;

-- Reinsert data
INSERT INTO books (id, title, author, isbn, price)
VALUES (1, 'The Hitchhiker''s Guide to the Galaxy', 'Douglas Adams', '9780345391803', 8.99);

INSERT INTO shopping_carts (id, user_id, is_deleted) VALUES (1, 1, false);

INSERT INTO cart_items (id, shopping_cart_id, book_id, quantity) VALUES (1, 1, 1, 1);
