
# Projeto API de Gerenciamento de Pessoas e Tarefas

## Descrição

Este projeto é uma API RESTful desenvolvida em Java com Spring Boot para gerenciar pessoas e tarefas dentro de departamentos. Cada pessoa pode ser alocada em várias tarefas e as tarefas possuem atributos como prazo, duração, departamento e status de finalização.

---

## Funcionalidades

- **Gerenciar Pessoas**  
  - Criar, alterar e deletar pessoas  
  - Listar pessoas com nome, departamento e total de horas gastas em tarefas  

- **Gerenciar Tarefas**  
  - Criar tarefas  
  - Alocar pessoa em tarefa, verificando se pertencem ao mesmo departamento  
  - Finalizar tarefa  
  - Listar as 3 tarefas pendentes com os prazos mais antigos  

- **Consultas específicas**  
  - Buscar pessoas por nome e período, retornando a média de horas gastas por tarefa  
  - Listar departamentos com quantidade de pessoas e tarefas associadas  

---

## Tecnologias utilizadas

- Java 17+  
- Spring Boot  
- Spring Data JPA  
- PostgreSQL  
- Jakarta Persistence (JPA)  
- Lombok (para reduzir código boilerplate)  

---

## Estrutura do projeto

- **Entities**: representam as tabelas do banco (Pessoa, Tarefa)  
- **Repositories**: interfaces para acesso aos dados com Spring Data JPA  
- **Services**: lógica de negócio para pessoas, tarefas e departamentos  
- **Controllers**: endpoints REST para acesso externo  
- **DTOs**: objetos para transporte de dados nas respostas da API  

---

## Banco de dados

- PostgreSQL  
- Configurado via Docker para facilitar a instalação e execução  
- Utiliza UUID como chave primária para entidades  

---

## Como executar

1. Tenha o Docker instalado e rodando.  
2. Execute o PostgreSQL via Docker:




   ```bash
   docker run -d --name postgresdb -e POSTGRES_DB=meubanco -e POSTGRES_USER=meuusuario -e POSTGRES_PASSWORD=batata123 -p 5432:5432 postgres
````

3. Configure o arquivo `application.properties` com as credenciais do banco:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/meubanco
   spring.datasource.username=meuusuario
   spring.datasource.password=batata123
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
   ```

4. Execute a aplicação Spring Boot.

5. Use um cliente REST (Postman, Insomnia, curl) para consumir os endpoints.

---

## Endpoints principais

### Pessoas

| Método | Endpoint                  | Descrição                                                                                 |
| ------ | ------------------------- | ----------------------------------------------------------------------------------------- |
| POST   | `/pessoas`                | Adicionar nova pessoa                                                                     |
| PUT    | `/pessoas/{id}`           | Alterar pessoa existente                                                                  |
| DELETE | `/pessoas/{id}`           | Remover pessoa                                                                            |
| GET    | `/pessoas`                | Listar pessoas com total de horas gastas                                                  |
| GET    | `/pessoas/pessoas/gastos` | Buscar média de horas gastas por nome e período (params: `nome`, `dataInicio`, `dataFim`) |

### Tarefas

| Método | Endpoint                                | Descrição                                                 |
| ------ | --------------------------------------- | --------------------------------------------------------- |
| POST   | `/tarefas`                              | Adicionar nova tarefa                                     |
| PUT    | `/tarefas/alocar/{idTarefa}?idPessoa=`  | Alocar pessoa na tarefa                                   |
| PUT    | `/tarefas/tarefas/finalizar/{idTarefa}` | Finalizar tarefa                                          |
| GET    | `/tarefas/pendentes`                    | Listar 3 tarefas sem pessoa alocada com prazo mais antigo |

### Departamentos

| Método | Endpoint         | Descrição                                                |
| ------ | ---------------- | -------------------------------------------------------- |
| GET    | `/departamentos` | Listar departamentos com quantidade de pessoas e tarefas |

---

## Considerações finais

Este projeto foi desenvolvido para fins educacionais e demonstração de uma API RESTful usando Spring Boot com PostgreSQL, focando em boas práticas como camadas separadas, uso de DTOs e tratamento de exceções.
