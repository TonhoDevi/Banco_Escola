# 🏫 Banco Escola - Sistema FUCTURA

Um sistema de gerenciamento de escola desenvolvido em Java que permite o cadastro e controle de alunos e cursos com persistência em banco de dados PostgreSQL.

## 📋 Características

- ✅ **Gerenciamento de Alunos**: Criar, listar, buscar, atualizar e deletar alunos
- ✅ **Gerenciamento de Cursos**: Criar, listar, buscar, atualizar e deletar cursos
- ✅ **Matrícula de Alunos**: Associar alunos a cursos
- ✅ **Interface CLI Intuitiva**: Menu interativo no terminal
- ✅ **Persistência em Banco de Dados**: PostgreSQL
- ✅ **Testes Unitários**: Testes com JUnit 4

## 🛠️ Tecnologias

- **Java**: Versão 17
- **Build Tool**: Maven 3.6+
- **Banco de Dados**: PostgreSQL 12+
- **Testes**: JUnit 4.11
- **IDE Recomendada**: IntelliJ IDEA ou VSCode

## 📦 Dependências

```xml
- PostgreSQL JDBC Driver 42.7.3
- JUnit 4.11 (teste)
```

## 🚀 Como Executar

### Pré-requisitos

1. **Java 17+** instalado na máquina
2. **Maven** instalado e configurado
3. **PostgreSQL** instalado e em execução

### Configuração do Banco de Dados

1. Crie um banco de dados PostgreSQL:
```sql
CREATE DATABASE banco_escola;
```

2. Configure as credenciais de conexão no arquivo `src/main/java/com/fuctura/database/Conexao.java`:
```java
private static final String URL = "jdbc:postgresql://localhost:5432/banco_escola";
private static final String USER = "seu_usuario";
private static final String PASSWORD = "sua_senha";
```

### Compilar e Executar

```bash
# Navigate to the project directory
cd demo/

# Compile the project
mvn clean compile

# Run the application
mvn exec:java -Dexec.mainClass="com.fuctura.App"

# Or execute directly after build
mvn clean package
java -jar target/demo-1.0-SNAPSHOT.jar
```

## 📂 Estrutura do Projeto

```
demo/
├── src/
│   ├── main/java/com/fuctura/
│   │   ├── App.java                      # Classe principal com menu CLI
│   │   ├── controller/
│   │   │   ├── AlunoController.java      # Lógica de gerenciamento de alunos
│   │   │   └── CursoController.java      # Lógica de gerenciamento de cursos
│   │   ├── dao/
│   │   │   ├── AlunoDAO.java            # Acesso a dados de alunos
│   │   │   └── CursoDAO.java            # Acesso a dados de cursos
│   │   ├── database/
│   │   │   ├── Conexao.java             # Gerenciamento de conexão com BD
│   │   │   └── DataBaseSeeder.java      # Inicialização e população do BD
│   │   └── models/
│   │       ├── Aluno.java               # Modelo de dados - Aluno
│   │       └── Curso.java               # Modelo de dados - Curso
│   └── test/java/com/fuctura/
│       └── AppTest.java                 # Testes unitários
├── pom.xml                              # Configuração Maven
└── target/                              # Artefatos compilados

```

## 📖 Uso

Ao executar a aplicação, você será apresentado com um menu principal:

```
========================================
           SISTEMA FUCTURA
========================================
1 - Gerenciar Alunos
2 - Gerenciar Cursos
0 - Sair
Escolha uma opção:
```

### Menu de Alunos

- **1** - Cadastrar novo aluno
- **2** - Listar todos os alunos
- **3** - Buscar aluno por ID
- **4** - Atualizar dados do aluno
- **5** - Deletar aluno
- **6** - Matricular aluno em curso
- **0** - Voltar ao menu principal

### Menu de Cursos

- **1** - Cadastrar novo curso
- **2** - Listar todos os cursos
- **3** - Buscar curso por ID
- **4** - Atualizar dados do curso
- **5** - Deletar curso
- **0** - Voltar ao menu principal

## 🗄️ Modelo de Dados

### Tabela: alunos
```sql
CREATE TABLE alunos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    idade INT NOT NULL,
    email VARCHAR(100) NOT NULL,
    matricula BOOLEAN DEFAULT false,
    curso_id INT REFERENCES cursos(id) ON DELETE SET NULL
);
```

### Tabela: cursos
```sql
CREATE TABLE cursos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    carga_horaria INT NOT NULL
);
```

## ✅ Testes

Execute os testes unitários com:

```bash
mvn test
```

## 🐛 Troubleshooting

### Erro: "FATAL: role "seu_usuario" does not exist"
Verifique se o usuário PostgreSQL existe ou crie um novo usuário:
```sql
CREATE USER seu_usuario WITH PASSWORD 'sua_senha';
ALTER USER seu_usuario SUPERUSER;
```

### Erro: "Connection refused"
Certifique-se de que o PostgreSQL está em execução:
```bash
# Linux/macOS
sudo service postgresql start

# macOS (Homebrew)
brew services start postgresql
```

## 📝 Notas de Desenvolvimento

- O projeto segue o padrão MVC (Model-View-Controller)
- As classes DAO implementam a lógica de acesso ao banco de dados
- O menu CLI na classe `App` gerencia a interação com o usuário
- A classe `DataBaseSeeder` garante que as tabelas existem e estão populadas na inicialização

## 📄 Licença

Este projeto é de código aberto. Sinta-se livre para usá-lo e modificá-lo conforme necessário.

## 👤 Autor

**Desenvolvido por**: TonhoDevi

## 🤝 Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.

