# 🎬 Cinema Management API

API RESTful completa para gerenciamento de cinemas, filmes e sessões, construída com Java 25, Spring Boot 4.1 e arquitetura segura de nível de produção.

---

## 🚀 Tecnologias Utilizadas

* **Linguagem:** Java 25
* **Framework:** Spring Boot 4.1 (Spring Web, Spring Data JPA, Spring Security)
* **Banco de Dados:** MySQL 9.7 (containerizado via Docker)
* **Segurança:** Autenticação Stateless via JWT + Criptografia BCrypt + Role-Based Access Control (RBAC)
* **Documentação:** OpenAPI 3 / Swagger UI
* **Testes:** JUnit 5, Mockito e MockMvc
* **Gerenciador de Dependências:** Maven

---

## 🔒 Segurança e Permissões

A API possui controle de acesso baseado em perfis (*Roles*):

* **`ROLE_USER`**: Pode visualizar a lista e detalhes de filmes e sessões.
* **`ROLE_ADMIN`**: Possui permissão total para criar, atualizar e deletar filmes e sessões.

---

## 🛠️ Como Executar o Projeto

### Pré-requisitos
* Java 25
* Docker & Docker Compose
* Maven

### Passos
1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/KauaBaptista/cinema-api.git](https://github.com/KauaBaptista/cinema-api.git)
   cd cinema-api
2. **Inicie o banco de dados MySQL via Docker:**
   ```bash
   docker compose up -d
3. **Execute a aplicação:**
   ```bash
   ./mvnw spring-boot:run
4. **Acesse a documentação interativa (Swagger UI):**
   ```bash
   http://localhost:8080/swagger-ui.html
5. **Executando os Testes Automatizados**
   ```bash
   ./mvnw test