-- Plataforma de Cursos — Script de criação do banco
-- Execute: psql -U postgres -d plataforma_cursos -f schema.sql

CREATE TABLE IF NOT EXISTS usuario (
    id    SERIAL PRIMARY KEY,
    nome  VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS curso (
    id            SERIAL PRIMARY KEY,
    titulo        VARCHAR(150) NOT NULL,
    descricao     TEXT,
    carga_horaria INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS matricula (
    id             SERIAL PRIMARY KEY,
    usuario_id     INTEGER REFERENCES usuario(id),
    curso_id       INTEGER REFERENCES curso(id),
    data_inicio    DATE NOT NULL DEFAULT CURRENT_DATE,
    data_conclusao DATE,
    concluido      BOOLEAN DEFAULT FALSE
);

-- Dados iniciais de cursos para teste
INSERT INTO curso (titulo, descricao, carga_horaria) VALUES
('Java para Iniciantes',       'Fundamentos da linguagem Java, POO e coleções.',        40),
('JavaFX — Interfaces Desktop','Criação de interfaces gráficas com JavaFX e FXML.',     30),
('Banco de Dados com PostgreSQL','SQL, modelagem relacional e boas práticas.',           35),
('Git e GitHub na Prática',    'Versionamento de código e colaboração com GitHub.',      20),
('Lógica de Programação',      'Algoritmos, estruturas de dados e resolução de problemas.', 25);
