INSERT INTO addresses (country, city, street)
VALUES ('Россия', 'Москва', 'ул. Тверская, 12'),
       ('Россия', 'Санкт-Петербург', 'Невский проспект, 45'),
       ('Россия', 'Новосибирск', 'Красный проспект, 78'),
       ('Россия', 'Казань', 'ул. Баумана, 33');

INSERT INTO suppliers (name, address_id, phone_number)
VALUES ('МосТорг', 1, '+7 495 123-45-67'),
       ('ПитерОпт', 2, '+7 812 987-65-43'),
       ('СибирьТрейд', 3, '+7 383 654-32-10'),
       ('КазаньМаркет', 4, '+7 843 567-89-01');

INSERT INTO images (image_id, image)
VALUES
    ('550e8400-e29b-41d4-a716-446655440001', pg_read_binary_file('/images/desk.png')),
    ('550e8400-e29b-41d4-a716-446655440002', pg_read_binary_file('/images/laptops.png')),
    ('550e8400-e29b-41d4-a716-446655440003', pg_read_binary_file('/images/microwave-ovens.png')),
    ('550e8400-e29b-41d4-a716-446655440004', pg_read_binary_file('/images/washing-machine.png'));

INSERT INTO products (name, category, price, available_stock, last_update_date, supplier_id, image_id)
VALUES
    ('Компьютерный стол', 'Мебель', 7999.99, 150, '2025-01-10', 1, '550e8400-e29b-41d4-a716-446655440001'),
    ('Ноутбук', 'Электроника', 79999.99, 30, '2025-02-20', 2, '550e8400-e29b-41d4-a716-446655440002'),
    ('Микроволновая печь', 'Бытовая техника', 15999.99, 25, '2025-03-05', 3, '550e8400-e29b-41d4-a716-446655440003'),
    ('Стиральная машина', 'Бытовая техника', 24999.99, 20, '2025-03-10', 4, '550e8400-e29b-41d4-a716-446655440004');

INSERT INTO clients (client_name, client_surname, birthday, gender, registration_date, address_id)
VALUES ('Иван', 'Иванов', '1990-07-15', 'male', '2025-01-15', 1),
       ('Мария', 'Петрова', '1985-04-22', 'female', '2025-02-10', 2),
       ('Алексей', 'Сидоров', '2000-11-30', 'male', '2025-03-01', 3),
       ('Айнур', 'Хасанов', '1995-06-18', 'male', '2025-03-08', 4);