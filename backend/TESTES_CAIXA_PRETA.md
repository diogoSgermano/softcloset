# 🧪 Guia de Testes de Caixa Preta - SoftCloset API

Os testes de caixa preta avaliam o comportamento externo do sistema (endpoints HTTP, retornos e status codes) sem a necessidade de conhecer o código-fonte interno. 

Abaixo está o passo a passo com exatamente **10 casos de teste (CT)** fundamentais para validar a API utilizando comandos `cURL` (executáveis no terminal).

---

## 🚀 Preparação

1. Certifique-se de que a aplicação backend está rodando localmente (geralmente em `http://localhost:8080`).
2. Abra o terminal (Bash, PowerShell ou Prompt de Comando).

---

## 📋 Cenários de Teste

### CT-01: Criar um Produto Válido (Sucesso)
* **Objetivo:** Validar a criação de um novo produto com dados corretos.
* **Ação:** Enviar requisição POST para `/api/produtos` com payload válido.
* **Comando:**
  ```bash
  curl -X POST http://localhost:8080/api/produtos \
    -H "Content-Type: application/json" \
    -d '{
      "nome": "Calça Jeans Slim",
      "marca": "Levi’s",
      "classificacao": "NOVO",
      "categoria": "CALCAS",
      "descricao": "Calça jeans masculina corte slim fit azul escuro",
      "preco": 199.90,
      "imagemUrl": "https://imagens.softcloset.com/calca-slim.jpg",
      "tamanhos": [
        {"tamanho": "M", "quantidade": 15},
        {"tamanho": "G", "quantidade": 10}
      ]
    }'
  ```
* **Resultado Esperado:** 
  - HTTP Status: `201 Created`
  - Body: JSON com o produto criado contendo um ID gerado (ex: `1`) e as variantes associadas.

---

### CT-02: Criar Produto com Dados Inválidos (Validação/Falha)
* **Objetivo:** Verificar se a API barra requisições com dados em formatos incorretos ou ausentes.
* **Ação:** Enviar POST para `/api/produtos` com preço negativo e nome em branco.
* **Comando:**
  ```bash
  curl -X POST http://localhost:8080/api/produtos \
    -H "Content-Type: application/json" \
    -d '{
      "nome": "",
      "marca": "Nike",
      "classificacao": "NOVO",
      "categoria": "CAMISAS",
      "descricao": "Preço Inválido",
      "preco": -50.00
    }'
  ```
* **Resultado Esperado:**
  - HTTP Status: `400 Bad Request`
  - Body: Mensagens de erro de validação (ex: nome não pode estar vazio, preço deve ser positivo).

---

### CT-03: Listar Todos os Produtos (Versão Light)
* **Objetivo:** Validar a listagem geral de produtos e o payload enxuto (sem variantes).
* **Ação:** Enviar requisição GET para `/api/produtos`.
* **Comando:**
  ```bash
  curl -X GET http://localhost:8080/api/produtos
  ```
* **Resultado Esperado:**
  - HTTP Status: `200 OK`
  - Body: Uma lista (Array JSON) de produtos. Os tamanhos/variantes de cada produto devem vir como uma lista vazia `[]` para otimizar a performance.

---

### CT-04: Buscar Produto por ID Existente (Detalhado)
* **Objetivo:** Validar que a busca direta traz o detalhamento completo, incluindo variantes.
* **Ação:** Enviar requisição GET para `/api/produtos/1` (ou o ID criado no CT-01).
* **Comando:**
  ```bash
  curl -X GET http://localhost:8080/api/produtos/1
  ```
* **Resultado Esperado:**
  - HTTP Status: `200 OK`
  - Body: Objeto JSON completo do produto com suas respectivas variantes (`tamanhos`) preenchidas (ex: M e G).

---

### CT-05: Buscar Produto por ID Inexistente (Falha)
* **Objetivo:** Verificar a resposta do sistema para buscas de recursos inexistentes.
* **Ação:** Enviar requisição GET para `/api/produtos/9999`.
* **Comando:**
  ```bash
  curl -X GET http://localhost:8080/api/produtos/9999
  ```
* **Resultado Esperado:**
  - HTTP Status: `404 Not Found`

---

### CT-06: Atualizar Produto Existente (Sucesso)
* **Objetivo:** Validar a alteração de dados de um produto já cadastrado.
* **Ação:** Enviar PUT para `/api/produtos/1` alterando o nome e o preço.
* **Comando:**
  ```bash
  curl -X PUT http://localhost:8080/api/produtos/1 \
    -H "Content-Type: application/json" \
    -d '{
      "nome": "Calça Jeans Slim Premium",
      "marca": "Levi’s",
      "classificacao": "DESTAQUE",
      "categoria": "CALCAS",
      "descricao": "Calça jeans slim premium lavagem especial",
      "preco": 249.90,
      "imagemUrl": "https://imagens.softcloset.com/calca-slim.jpg"
    }'
  ```
* **Resultado Esperado:**
  - HTTP Status: `200 OK`
  - Body: JSON com as alterações aplicadas (nome alterado para "Calça Jeans Slim Premium" e preço 249.90).

---

### CT-07: Buscar Produtos por Nome (Search)
* **Objetivo:** Validar a funcionalidade de filtro de busca textual de produtos.
* **Ação:** Enviar GET para `/api/produtos/search?q=Jeans`.
* **Comando:**
  ```bash
  curl -X GET "http://localhost:8080/api/produtos/search?q=Jeans"
  ```
* **Resultado Esperado:**
  - HTTP Status: `200 OK`
  - Body: Uma lista contendo apenas os produtos que tenham "Jeans" no nome (como o produto do CT-06).

---

### CT-08: Criar Variante de Tamanho para Produto Existente
* **Objetivo:** Validar a adição de novas grades de estoque a um produto existente.
* **Ação:** Enviar POST para `/api/produto-variantes/produto/1` com o novo tamanho (ex: GG).
* **Comando:**
  ```bash
  curl -X POST http://localhost:8080/api/produto-variantes/produto/1 \
    -H "Content-Type: application/json" \
    -d '{
      "tamanho": "GG",
      "quantidade": 8
    }'
  ```
* **Resultado Esperado:**
  - HTTP Status: `201 Created`
  - Body: JSON contendo a variante criada com seu próprio ID e a associação com o produto 1.

---

### CT-09: Tentar Criar Variante para Produto Inexistente (Falha)
* **Objetivo:** Validar integridade referencial ao adicionar variante a um produto fantasma.
* **Ação:** Enviar POST para `/api/produto-variantes/produto/9999` com dados de tamanho.
* **Comando:**
  ```bash
  curl -X POST http://localhost:8080/api/produto-variantes/produto/9999 \
    -H "Content-Type: application/json" \
    -d '{
      "tamanho": "M",
      "quantidade": 10
    }'
  ```
* **Resultado Esperado:**
  - HTTP Status: `404 Not Found`

---

### CT-10: Deletar Produto Existente (Sucesso)
* **Objetivo:** Validar a exclusão física (ou lógica) do produto do catálogo.
* **Ação:** Enviar DELETE para `/api/produtos/1`.
* **Comando:**
  ```bash
  curl -X DELETE http://localhost:8080/api/produtos/1
  ```
* **Resultado Esperado:**
  - HTTP Status: `204 No Content`
  - *Verificação Extra:* Se você fizer um `GET /api/produtos/1` logo em seguida, deve receber `404 Not Found`.

---

## 💡 Dicas de Execução
- Para visualizar as respostas com formatação amigável, você pode canalizar o comando para a ferramenta `jq` (se instalada no sistema):
  ```bash
  curl -X GET http://localhost:8080/api/produtos | jq
  ```
- O cabeçalho `-H "Content-Type: application/json"` é obrigatório para todas as requisições `POST` e `PUT` para que o Spring Boot interprete o corpo da requisição corretamente.
