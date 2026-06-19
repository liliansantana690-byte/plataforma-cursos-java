# Plataforma de Cursos Online

Sistema em Java (console) com cadastro/login de usuarios, listagem de cursos,
matricula e geracao automatica de comprovante em PDF — **sem bibliotecas
externas** (PDF gerado manualmente em Java puro).

## Estrutura

```
src/main/java/com/plataforma/
├── App.java                  -> Menu principal (ponto de entrada)
├── model/
│   ├── Usuario.java          -> Dados do usuario + cursos matriculados
│   └── Curso.java            -> Dados do curso
├── dao/
│   ├── UsuarioDAO.java        -> Persistencia de usuarios (arquivo usuarios.dat)
│   └── CursoDAO.java          -> Catalogo de cursos disponiveis (em memoria)
├── service/
│   ├── AutenticacaoService.java -> Login e cadastro
│   └── MatriculaService.java    -> Matricula + geracao de PDF
└── util/
    └── PdfGenerator.java      -> Gerador de PDF (estrutura nativa, sem libs)
```

## Como executar

### Linha de comando

```bash
# Compilar
find src -name "*.java" > sources.txt
javac -d out @sources.txt

# Executar
java -cp out com.plataforma.App
```

### VS Code / Eclipse / IntelliJ

1. Abra a pasta do projeto na IDE.
2. Marque `src/main/java` como **source root** (se necessario).
3. Execute a classe `App.java`.

## Fluxo de uso

1. **Cadastrar novo usuario** (opcao 2 no menu inicial).
2. **Login** com email e senha cadastrados.
3. **Listar cursos disponiveis**.
4. **Matricular-se em um curso** -> gera PDF automaticamente em `certificados/`.
5. **Meus cursos** -> lista cursos matriculados.
6. **Gerar PDF de um curso ja matriculado** -> re-gera o comprovante quando quiser.

## Persistencia

- Usuarios: salvos em `usuarios.dat` (serializacao binaria Java).
- PDFs: salvos na pasta `certificados/`, nome `Nome_Aluno_Nome_Curso.pdf`.

## Pontos de evolucao (para portfolio)

- Trocar persistencia em arquivo por banco relacional (PostgreSQL/MySQL + JDBC).
- Adicionar hash de senha (ex: BCrypt) em vez de texto puro.
- Migrar o gerador de PDF para PDFBox/iText se precisar de layouts mais
  elaborados (logos, tabelas, fontes com acentuacao nativa).
- Adicionar interface grafica (JavaFX/Swing) ou expor como API REST (Spring Boot).

## Limitacoes conhecidas

- O gerador de PDF usa a fonte Helvetica padrao sem mapa de encoding
  customizado, por isso acentos sao removidos automaticamente no texto do PDF
  (ex: "matricula" em vez de "matrícula"). Para manter acentos, seria
  necessario incorporar uma fonte com encoding UTF-8/CID, o que aumenta
  bastante a complexidade do gerador manual.
- Senhas sao armazenadas em texto puro — adequado apenas para fins
  academicos/demonstracao, nunca para producao.
