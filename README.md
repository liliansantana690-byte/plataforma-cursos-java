# 📚 Plataforma de Cursos — Java Desktop

> Sistema desktop de gestão de cursos com autenticação, matrícula e geração de certificado em PDF.

![Version](https://img.shields.io/badge/versão-1.0.0-blue)
![Java](https://img.shields.io/badge/Java-17+-orange)
![JavaFX](https://img.shields.io/badge/JavaFX-21-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![Status](https://img.shields.io/badge/status-concluído-brightgreen)

---

## 📌 Sobre o projeto

A **Plataforma de Cursos** é uma aplicação desktop desenvolvida em Java com JavaFX, voltada para o gerenciamento de cursos e certificações. O sistema permite que usuários se cadastrem, façam login, se matriculem em cursos e gerem um certificado em PDF ao concluir.

Projeto desenvolvido como portfólio acadêmico do curso de **Engenharia de Software — UNIJORGE**.

> ⚠️ Esta é a **versão 1.0** do projeto. A v2 inclui galeria de concluintes com foto via webcam. Veja o [roadmap](#-roadmap).

---

## ✨ Funcionalidades (v1)

- [x] Cadastro de usuários
- [x] Login com validação de credenciais
- [x] Listagem de cursos disponíveis
- [x] Matrícula em cursos
- [x] Marcar curso como concluído
- [x] Geração de certificado em PDF com nome, curso, carga horária e data
- [x] Painel "Meu Progresso" — quantos cursos o usuário concluiu
- [x] Ranking geral de usuários por total de conclusões

---

## 🛠️ Tecnologias utilizadas

| Tecnologia | Versão | Finalidade |
|---|---|---|
| Java | 17+ | Linguagem principal |
| JavaFX | 21 | Interface gráfica desktop |
| PostgreSQL | 16 | Banco de dados relacional |
| iText 7 | 8.0.2 | Geração de certificados em PDF |
| Maven | 3.9+ | Gerenciamento de dependências |

---

## 📁 Estrutura do projeto

```
plataforma-cursos/
├── src/
│   └── main/
│       ├── java/
│       │   ├── model/          # Usuario, Curso, Matricula, UsuarioRanking
│       │   ├── dao/            # UsuarioDAO, CursoDAO, MatriculaDAO
│       │   ├── controller/     # Controllers das telas JavaFX
│       │   ├── view/           # Arquivos .fxml
│       │   └── util/           # ConexaoBD, GeradorCertificadoPDF
│       └── resources/
│           └── schema.sql      # Script de criação do banco
├── certificados/
│   └── pdf/                    # Certificados gerados
├── .gitignore
├── pom.xml
└── README.md
```

---

## 🗄️ Modelo de dados

```sql
CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE curso (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    descricao TEXT,
    carga_horaria INTEGER NOT NULL
);

CREATE TABLE matricula (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER REFERENCES usuario(id),
    curso_id  INTEGER REFERENCES curso(id),
    data_inicio DATE NOT NULL,
    data_conclusao DATE,
    concluido BOOLEAN DEFAULT FALSE
);
```

---

## ▶️ Como executar

### Pré-requisitos

- Java 17 ou superior
- Maven 3.9 ou superior
- PostgreSQL instalado e rodando

### Passo a passo

**1. Clone o repositório**
```bash
git clone https://github.com/seu-usuario/plataforma-cursos-java.git
cd plataforma-cursos-java
```

**2. Configure o banco de dados**
```sql
CREATE DATABASE plataforma_cursos;
```
Execute o script:
```bash
psql -U postgres -d plataforma_cursos -f src/main/resources/schema.sql
```

**3. Configure as variáveis de ambiente**

Crie um arquivo `.env` na raiz do projeto (já está no `.gitignore`):
```
DB_URL=jdbc:postgresql://localhost:5432/plataforma_cursos
DB_USER=seu_usuario
DB_PASSWORD=sua_senha
```

**4. Execute**
```bash
mvn clean javafx:run
```

---

## 🔐 Variáveis de ambiente

O projeto usa variáveis de ambiente para proteger credenciais do banco.
**Nunca compartilhe o arquivo `.env`.**

| Variável | Descrição |
|---|---|
| `DB_URL` | URL de conexão com o banco PostgreSQL |
| `DB_USER` | Usuário do banco |
| `DB_PASSWORD` | Senha do banco |

---

## 📸 Telas do sistema

> *(Adicione prints das telas após rodar o projeto)*

| Login | Cadastro |
|---|---|
| `[print aqui]` | `[print aqui]` |

| Lista de Cursos | Meu Progresso |
|---|---|
| `[print aqui]` | `[print aqui]` |

| Ranking de Usuários | Certificado PDF |
|---|---|
| `[print aqui]` | `[print aqui]` |

---

## 🔍 Decisões técnicas

**Senha com hash (BCrypt)**
Senhas nunca são salvas em texto puro. O BCrypt adiciona salt automático e é resistente a ataques de força bruta — padrão de mercado para autenticação.

**Credenciais via variável de ambiente**
Nenhuma senha ou configuração sensível está no código. A conexão com o banco é lida de variáveis de ambiente, seguindo a prática recomendada de separação entre código e configuração.

**Geração de PDF com iText 7**
O certificado é gerado dinamicamente com nome do usuário, título do curso, carga horária e data de conclusão — sem templates estáticos.

**Arquitetura em camadas (MVC)**
Separação entre Model (dados), DAO (acesso ao banco), Controller (lógica de tela) e View (FXML) — facilita manutenção e evolução do projeto.

---

## 🗺️ Roadmap

### ✅ v1.0 — Concluída (esta versão)
- Autenticação (cadastro e login)
- Matrícula e conclusão de cursos
- Geração de certificado em PDF
- Painel de progresso e ranking

### 🔜 v2.0 — Planejada
- [ ] Captura de foto com webcam ao concluir o curso
- [ ] Galeria pública de concluintes por curso (com foto)
- [ ] Consentimento explícito antes de exibir foto na galeria
- [ ] Opção de revogar consentimento e remover foto
- [ ] Atualização automática da galeria via polling (5 segundos)
- [ ] Exportar relatório geral de usuários em PDF

### 💡 v3.0 — Visão futura
- [ ] Migração para aplicação web (Spring Boot + React)
- [ ] Deploy em nuvem com URL pública
- [ ] Tela de administrador para cadastrar e editar cursos
- [ ] Filtro de cursos por categoria

---

## 👩‍💻 Autora

**Lilian**
Estudante de Engenharia de Software — UNIJORGE | Salvador, BA

[![LinkedIn](https://img.shields.io/badge/LinkedIn-blue?style=flat&logo=linkedin)](https://linkedin.com/in/seu-perfil)
[![GitHub](https://img.shields.io/badge/GitHub-black?style=flat&logo=github)](https://github.com/seu-usuario)

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

