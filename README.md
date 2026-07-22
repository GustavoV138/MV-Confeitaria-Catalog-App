# Catálogo de Confeitaria (MVConfeitariaCatalogApp)

Um sistema completo (Fullstack) para gerenciamento de um catálogo de produtos de confeitaria. O projeto conta com uma API REST robusta no backend e um aplicativo mobile nativo Android moderno para o consumo dos dados, focando em usabilidade e design elegante.

## 📱 Visão Geral e Funcionalidades

O projeto foi dividido em duas frentes: uma aplicação Mobile Android e uma API REST Backend.

### Funcionalidades do Aplicativo Mobile
- **Listagem do Catálogo:** Visualização agradável de todos os bolos cadastrados em formato de cards.
- **Busca Eficiente:** Barra de pesquisa em tempo real para filtrar bolos pelo nome.
- **Gerenciamento (CRUD):** Possibilidade de criar novos bolos, editar informações existentes e excluir (com confirmação segura).
- **Compartilhamento:** Botão rápido para compartilhar os detalhes do bolo (nome, descrição, preço e validade) em outros aplicativos (WhatsApp, Telegram, etc).
- **Design Nativo:** Desenvolvido com uma paleta de cores agradável, utilizando temas, formas arredondadas e ícones vetoriais em Jetpack Compose.

### Funcionalidades da API Backend
- **Endpoints RESTful:** Rotas claras para `GET`, `POST`, `PUT` e `DELETE`.
- **Persistência de Dados:** Integração com banco de dados relacional para guardar as informações do catálogo de forma segura.
- **Arquitetura em Camadas:** Código bem estruturado separando *Controllers*, *Services* e *Repositories*.

## 🛠️ Tecnologias Utilizadas

### Frontend (Mobile / Android)
- **Linguagem:** Kotlin
- **Interface:** Jetpack Compose (UI Declarativa)
- **Arquitetura:** MVVM (Model-View-ViewModel)
- **Rede & Comunicação:** Retrofit, OkHttp e Gson (conversão JSON)
- **Navegação:** Navigation Compose

### Backend (API REST)
- **Linguagem/Framework:** Java 17+ com Spring Boot
- **Banco de Dados:** PostgreSQL
- **Integração de Dados:** Spring Data JPA / Hibernate
- **Build Tool:** Maven ou Gradle

## 📁 Estrutura do Repositório

```
MVConfeitariaCatalogApp/
├── Back/        # Código-fonte da API REST (Spring Boot)
├── Front/       # Código-fonte do aplicativo Android (Jetpack Compose)
└── README.md    # Documentação geral do projeto
```

## 🚀 Como Executar o Projeto

### Pré-requisitos
- JDK 17 ou superior
- Android Studio (versão mais recente recomendada)
- PostgreSQL (rodando localmente ou via Docker)
- Dispositivo Android físico ou Emulador (API 24+)

### 1. Rodando o Backend
1. Navegue até a pasta `Back/`.
2. Configure as credenciais do banco de dados no arquivo `application.properties` (ou `application.yml`):
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/mv_confeitaria
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   ```
3. Execute o projeto na sua IDE (IntelliJ, Eclipse, VS Code) ou via linha de comando (ex: `./mvnw spring-boot:run`).
4. O servidor iniciará na porta **8080** por padrão.

### 2. Rodando o Frontend (Aplicativo Android)
1. Abra a pasta `Front/` utilizando o **Android Studio**.
2. Aguarde a sincronização do Gradle (download das dependências do Retrofit, Compose, etc).
3. **Importante:** Como o aplicativo precisa se conectar com a API local rodando na sua máquina, certifique-se de que a `BASE_URL` no arquivo `RetrofitClient.kt` esteja apontando para o IP correto:
   - Se estiver rodando no **Emulador Android Studio**, use: `http://10.0.2.2:8080/`
   - Se estiver usando um **Dispositivo Físico** na mesma rede Wi-Fi, use o IP da sua máquina, ex: `http://192.168.1.XX:8080/`
4. Clique em **Run (▶)** para instalar e abrir o app no emulador/dispositivo.

## 📡 Referência Rápida da API (Endpoints)

Base URL (Local): `http://localhost:8080/api/v1/catalog`

| Método | Rota             | Descrição                                      |
|--------|------------------|------------------------------------------------|
| `GET`  | `/cakes`         | Lista todos os bolos do catálogo.              |
| `POST` | `/cake`          | Cadastra um novo bolo.                         |
| `PUT`  | `/cake/{id}`     | Atualiza as informações de um bolo existente.  |
| `DELETE`| `/cake/{id}`    | Exclui um bolo específico.                     |

---
*Projeto desenvolvido como demonstração de habilidades Fullstack focadas em Java/Spring e Kotlin/Android.*
