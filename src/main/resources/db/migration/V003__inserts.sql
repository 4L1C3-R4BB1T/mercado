INSERT INTO categories (name, description) VALUES
('Eletrônicos', 'Dispositivos, gadgets e acessórios'),
('Livros', 'Livros impressos e digitais'),
('Roupas', 'Roupas masculinas e femininas'),
('Esportes', 'Equipamentos e materiais esportivos'),
('Casa & Cozinha', 'Itens para casa e cozinha');

INSERT INTO products (name, description, price, stock, category_id) VALUES
('Smartphone', 'Smartphone modelo mais recente com 128GB de armazenamento', 699.99, 50, 1),
('Laptop', 'Laptop de 15 polegadas com 8GB de RAM e 256GB SSD', 899.99, 30, 1),
('Leitor de E-book', 'Leitor de e-book com tela de 6 polegadas', 129.99, 100, 2),
('Romance de Ficção', 'Romance de ficção mais vendido', 19.99, 200, 2),
('Camiseta', 'Camiseta de algodão com vários tamanhos', 9.99, 150, 3),
('Calça Jeans', 'Calça jeans com vários tamanhos', 39.99, 100, 3),
('Bola de Basquete', 'Bola de basquete tamanho e peso oficial', 29.99, 75, 4),
('Raquete de Tênis', 'Raquete de tênis profissional', 149.99, 25, 4),
('Liquidificador', 'Liquidificador de alta velocidade para smoothies', 89.99, 60, 5),
('Conjunto de Panelas', 'Conjunto de panelas antiaderente com 10 peças', 129.99, 40, 5);
