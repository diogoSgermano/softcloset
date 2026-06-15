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
