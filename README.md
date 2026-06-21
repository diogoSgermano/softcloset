# softcloset
Projeto idealizado referente ao seminário da matéria de Teste de Software II, do curso Bacharelado em Engenharia de Software, onde serão realizados diferentes testes de software por outras equipes

## Como rodar (rápido)

Pré-requisitos:

- Java 21
- Maven
- MySQL
- Node.js + npm

## Criar banco de dados MySQL `softcloset`

Cole os comandos abaixo no terminal (substitua senhas e usuários conforme necessário). Assumimos MySQL em `localhost:3306`.

```bash
# cria apenas o banco
mysql -u root -p -h 127.0.0.1 -P 3306 -e "CREATE DATABASE softcloset CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
```
```bash
# cria banco + usuário (substitua SENHA_AQUI)
mysql -u root -p -h 127.0.0.1 -P 3306 -e "CREATE DATABASE softcloset CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci; CREATE USER 'softuser'@'localhost' IDENTIFIED BY 'SENHA_AQUI'; GRANT ALL PRIVILEGES ON softcloset.* TO 'softuser'@'localhost'; FLUSH PRIVILEGES;"
```

Depois de criar o banco, edite `backend/src/main/resources/application.properties` e coloque seu usuário e senha nos campos:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/softcloset?useSSL=false&serverTimezone=UTC
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
```

1) Rodar o backend (porta 8080):

```bash
cd backend
mvn spring-boot:run
```

2) Rodar o frontend (porta 3000):

```bash
cd frontend
npm install
npm start
```

O frontend consome a API em `http://localhost:8080/api/produtos`.

Para desenvolvimento, reinicie os servidores quando fizer mudanças importantes no backend.
