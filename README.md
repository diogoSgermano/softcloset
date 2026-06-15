# softcloset
Projeto idealizado referente ao seminário da matéria de Teste de Software II, do curso Bacharelado em Engenharia de Software, onde serão realizados diferentes testes de software por outras equipes

## Como rodar (rápido)

Pré-requisitos:

- Java 21
- Maven
- Node.js + npm

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

## Criar banco de dados MySQL `softcloset`

Cole um dos comandos abaixo no terminal (substitua senhas e usuários conforme necessário).

- Usando cliente MySQL (assumindo servidor em `localhost:3307` ou ajuste a porta):

```bash
# cria apenas o banco
mysql -u root -p -h 127.0.0.1 -P 3307 -e "CREATE DATABASE softcloset CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

# cria banco + usuário (substitua SENHA_AQUI)
mysql -u root -p -h 127.0.0.1 -P 3307 -e "CREATE DATABASE softcloset CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci; CREATE USER 'softuser'@'localhost' IDENTIFIED BY 'SENHA_AQUI'; GRANT ALL PRIVILEGES ON softcloset.* TO 'softuser'@'localhost'; FLUSH PRIVILEGES;"
```

- Usando Docker (inicia um container MySQL com o banco criado):

```bash
docker run --name softcloset-mysql \
	-e MYSQL_ROOT_PASSWORD=rootpwd \
	-e MYSQL_DATABASE=softcloset \
	-e MYSQL_USER=softuser \
	-e MYSQL_PASSWORD=suasenha \
	-p 3307:3306 \
	-d mysql:8.0
```

Depois de criar o banco, edite `backend/src/main/resources/application.properties` e coloque seu usuário e senha nos campos:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/softcloset?useSSL=false&serverTimezone=UTC
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
```

Se usar o container Docker com mapeamento de porta `-p 3307:3306`, ajuste a URL para usar a porta `3307`.
