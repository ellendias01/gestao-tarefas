# 📝 Sistema de Gerenciamento de Tarefas

API RESTful completa para gerenciamento de tarefas do dia a dia, desenvolvida com Spring Boot e PostgreSQL.

![Java](https://img.shields.io/badge/Java-21-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18-blue.svg)
![License](https://img.shields.io/badge/License-MIT-green.svg)

## ✨ Funcionalidades

- ✅ **Criar tarefa** - Adicione novas tarefas com nome, descrição, status e observações
- ✅ **Listar tarefas** - Visualize todas as tarefas cadastradas
- ✅ **Buscar por ID** - Encontre uma tarefa específica
- ✅ **Atualizar tarefa** - Modifique os dados de uma tarefa existente
- ✅ **Deletar tarefa** - Remova tarefas do sistema
- ✅ **Health Check** - Endpoint para verificar o status da API

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 21** - Linguagem de programação
- **Spring Boot 3.1.5** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Web** - API RESTful
- **Spring Validation** - Validação de dados
- **PostgreSQL 18** - Banco de dados relacional
- **Hibernate** - ORM para mapeamento objeto-relacional
- **Maven** - Gerenciador de dependências

### Frontend
- **HTML5/CSS3** - Interface do usuário
- **JavaScript** - Consumo da API e interatividade
- **Fetch API** - Requisições HTTP assíncronas

### Documentação
- **Swagger/OpenAPI** - Documentação interativa da API

## 📋 Pré-requisitos

- [Java 21+](https://adoptium.net/)
- [Maven 3.6+](https://maven.apache.org/)
- [PostgreSQL 18+](https://www.postgresql.org/)

## 🚀 Como Executar o Projeto

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/gestao-tarefas.git
cd gestao-tarefas
```
### 2. Configure o banco de dados PostgreSQL
Crie um banco de dados:

    CREATE DATABASE gestao_tarefas;
    
### 3. Configure as credenciais
Edite o arquivo src/main/resources/application.properties:

    spring.datasource.url=jdbc:postgresql://localhost:5432/gestao_tarefas
    spring.datasource.username=postgres
    spring.datasource.password=SUA_SENHA_AQUI

### 4. Execute a aplicação

    # Com Maven Wrapper (Windows)
    .\mvnw.cmd spring-boot:run
    
    # Com Maven Wrapper (Linux/Mac)
    ./mvnw spring-boot:run
    
    # Ou com Maven instalado
    mvn spring-boot:run

### 5. Acesse a aplicação 🌐

| Serviço      | URL                                                                                    |
| ------------ | -------------------------------------------------------------------------------------- |
| API          | [http://localhost:8080/api](http://localhost:8080/api)                                 |
| Swagger      | [http://localhost:8080/api/swagger-ui.html](http://localhost:8080/api/swagger-ui.html) |
| Health Check | [http://localhost:8080/api/health](http://localhost:8080/api/health)                   |

### 📡 Endpoints

| Método | Endpoint          | Descrição      |
| ------ | ----------------- | -------------- |
| GET    | /api/tarefas      | Listar tarefas |
| GET    | /api/tarefas/{id} | Buscar por ID  |
| POST   | /api/tarefas      | Criar tarefa   |
| PUT    | /api/tarefas/{id} | Atualizar      |
| DELETE | /api/tarefas/{id} | Deletar        |
| GET    | /api/health       | Status da API  |

### 📦 Exemplo de Requisição
Criar tarefa (Post)

    curl -X POST http://localhost:8080/api/tarefas \
    -H "Content-Type: application/json" \
    -d '{
      "nome": "Estudar Spring Boot",
      "descricao": "Revisar API REST",
      "status": "PENDENTE"
    }'
Listar todas as tarefas (GET)

    curl http://localhost:8080/api/tarefas
    
Atualizar uma tarefa (PUT)

    curl -X PUT http://localhost:8080/api/tarefas/1 \
      -H "Content-Type: application/json" \
      -d '{
        "nome": "Estudar Spring Boot Avançado",
        "descricao": "Estudar Security e JWT",
        "status": "EM_ANDAMENTO"
      }'
Deletar uma tarefa (DELETE)

    curl -X DELETE http://localhost:8080/api/tarefas/1
    
Verificar saúde da API (GET)

    curl http://localhost:8080/api/health

### 📊 Modelo da Entidade

| Campo           | Tipo          |
| --------------- | ------------- |
| id              | Long          |
| nome            | String        |
| descricao       | String        |
| status          | Enum          |
| observacoes     | String        |
| dataCriacao     | LocalDateTime |
| dataAtualizacao | LocalDateTime |

### 📁 Estrutura do Projeto

    gestao-tarefas/
     ├── controller/
     ├── service/
     ├── repository/
     ├── model/
     ├── exception/
     ├── resources/
     │    ├── application.properties
     │    └── db/migration
     ├── frontend/
     └── pom.xml

### 🧪 Testes
    .\mvnw.cmd test

Exemplo de teste unitário

    @Test
    void criarTarefa_ComSucesso() {
        Tarefa tarefa = new Tarefa();
        tarefa.setNome("Teste");
        tarefa.setStatus(Tarefa.StatusTarefa.PENDENTE);
        
        when(tarefaRepository.save(any())).thenReturn(tarefa);
        
        Tarefa resultado = tarefaService.criarTarefa(tarefa);
        
        assertNotNull(resultado);
        assertEquals("Teste", resultado.getNome());
    }

## 📊 Logs da Aplicação
A aplicação gera logs detalhados de todas as operações:

    2026-04-22 13:16:42.530 [http-nio-8080-exec-5] INFO  - 🌐 [API] POST /tarefas
    2026-04-22 13:16:42.532 [http-nio-8080-exec-5] INFO  - 📝 SOLICITAÇÃO: Criar nova tarefa - Nome: Teste
    2026-04-22 13:16:42.717 [http-nio-8080-exec-5] INFO  - ✅ SUCESSO: Tarefa criada - ID: 2
    2026-04-22 13:16:42.736 [http-nio-8080-exec-5] INFO  - 🌐 [API] Retornando status 201 CREATED

### 🤝 Contribuição
Contribuições são bem-vindas! Siga os passos:

Fork o projeto

* Crie sua branch (git checkout -b feature/AmazingFeature)
* Commit suas mudanças (git commit -m 'Add some AmazingFeature')
* Push para a branch (git push origin feature/AmazingFeature)
* Abra um Pull Request

### 📄 Licença
Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

### 👨‍💻 Autores
Ellen Dias Farias <br>
Jhonatan Dias Farias

### 🙏 Agradecimentos
Spring Boot Team pela excelente framework

PostgreSQL pela robustez do banco de dados

Todos os contribuidores e usuários do projeto

