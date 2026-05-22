-- ─── Books ────────────────────────────────────────────────────────────────────
INSERT IGNORE INTO books (name, author, publisher, collection_address, qty, price, borrowing_copies) VALUES
('The Great Gatsby',      'F. Scott Fitzgerald', 'Scribner',        'Shelf A1', 5, 299.0, 3),
('To Kill a Mockingbird', 'Harper Lee',          'J.B. Lippincott', 'Shelf A2', 4, 249.0, 2),
('Clean Code',            'Robert C. Martin',   'Prentice Hall',   'Shelf B1', 6, 599.0, 4),
('Effective Java',        'Joshua Bloch',       'Addison-Wesley',  'Shelf B2', 3, 699.0, 2),
('Spring in Action',      'Craig Walls',        'Manning',         'Shelf B3', 4, 799.0, 3);

-- ─── Admin ────────────────────────────────────────────────────────────────────
INSERT IGNORE INTO admins (name, email, phone_number) VALUES
('Admin User', 'admin@library.com', '9999999999');

-- ─── Members ──────────────────────────────────────────────────────────────────
INSERT IGNORE INTO members (name, email, phone_number) VALUES
('Alice',   'alice@gmail.com',    '9876543210'),
('Bob',     'bob@gmail.com',      '9123456780'),
('Charlie', 'charlie@gmail.com',  '9000011111');