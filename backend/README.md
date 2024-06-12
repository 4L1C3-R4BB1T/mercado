## ⚙️ Como executar o projeto

Clone esse repositório:

```bash
https://github.com/4L1C3-R4BB1T/mercado.git
```

Entre na pasta backend do projeto:

```bash
cd ./backend
```

Execute o container docker:

```bash
docker compose up -d
```

Execute o projeto:

```bash
./gradlew bootRun
```

Ou se preferir use extensões disponibilizadas pelas IDE's.


Para acessar a documentação Swagger:

```bash
http://localhost:8080/swagger-ui/index.html
```
---

## 🔗 API Endpoints

### 🏷️ Categorias

🟡 **```POST```** ::  Criar categoria

```bash
{host}/api/v1/categories
```

```json
/* Exemplo de Request */
{
    "name": "Beleza & Cuidados Pessoais",
    "description": "Produtos de beleza e cuidados pessoais"
}
```

🟢 **```GET```** :: Obter todas as categorias

```bash
{host}/api/v1/categories
```

```json
/* Exemplo de Response */
[
    {
        "id": 1,
        "name": "Eletrônicos",
        "description": "Dispositivos, gadgets e acessórios"
    },
    {
        "id": 2,
        "name": "Livros",
        "description": "Livros impressos e digitais"
    },
    {
        "id": 3,
        "name": "Roupas",
        "description": "Roupas masculinas e femininas"
    }
]
```

🟢 **```GET```** :: Obter categoria por id

```bash
{host}/api/v1/categories/{id:int}
```

```json
/* Exemplo de Response */
{
    "id": 1,
    "name": "Eletrônicos",
    "description": "Dispositivos, gadgets e acessórios"
}
```

🔵 **```PUT```** :: Atualizar categoria

```bash
{host}/api/v1/categories
```

```json
/* Exemplo de Request */
{
    "name": "Beleza & Cuidados Pessoais Atualizado",
    "description": "Produtos de beleza e cuidados pessoais"
}
```


🔴 **```DELETE```** :: Deletar categoria

```bash
{host}/api/v1/categories
```

---

### 📦 Produtos

🟡 **```POST```** :: Criar produto

```bash
{host}/api/v1/products
```

```json
/* Exemplo de Request */
{
    "name": "Creme Hidratante",
    "description": "Creme hidratante para pele seca",
    "price": 19.99,
    "stock": 200,
    "categoryId": 6
}
```

🟢 **```GET```** :: Obter todos os produtos

```bash
{host}/api/v1/products
```

```json
/* Exemplo de Response */
[
    {
        "id": 1,
        "name": "Smartphone",
        "description": "Smartphone modelo mais recente com 128GB de armazenamento",
        "price": 699.99,
        "stock": 50,
        "category": {
        "id": 1,
        "name": "Eletrônicos",
        "description": "Dispositivos, gadgets e acessórios"
        }
    },
    {
        "id": 2,
        "name": "Laptop",
        "description": "Laptop de 15 polegadas com 8GB de RAM e 256GB SSD",
        "price": 899.99,
        "stock": 30,
        "category": {
        "id": 1,
        "name": "Eletrônicos",
        "description": "Dispositivos, gadgets e acessórios"
        }
    },
    {
        "id": 3,
        "name": "Leitor de E-book",
        "description": "Leitor de e-book com tela de 6 polegadas",
        "price": 129.99,
        "stock": 100,
        "category": {
        "id": 2,
        "name": "Livros",
        "description": "Livros impressos e digitais"
        }
    }
]
```

🟢 **```GET```** :: Obter produto por id

```bash
{host}/api/v1/products/{id:int}
```

```json
/* Exemplo de Response */
{
    "id": 1,
    "name": "Smartphone",
    "description": "Smartphone modelo mais recente com 128GB de armazenamento",
    "price": 699.99,
    "stock": 50,
    "category": {
        "id": 1,
        "name": "Eletrônicos",
        "description": "Dispositivos, gadgets e acessórios"
    }
}
```

🔵 **```PUT```** :: Atualizar produto

```bash
{host}/api/v1/products
```

```json
/* Exemplo de Request */
{
    "name": "Creme Hidratante Atualizado",
    "description": "Creme hidratante para pele seca",
    "price": 19.99,
    "stock": 200,
    "categoryId": 6
}
```

🔴 **```DELETE```** :: Deletar produto

```bash
{host}/api/v1/products
```
