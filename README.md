# 🐾 Animal Information Assistant (Spring AI + RAG)

This project is a **Spring Boot 3 + Spring AI** application that provides an **AI-powered assistant for animal information**.  
It integrates with **PostgreSQL (with pgvector)** to store animal data and embeddings, enabling Retrieval-Augmented Generation (RAG).  

The AI runs **locally** using [Ollama](https://ollama.ai/) with:  
- **Chat model:** `llama3.2:1b`  
- **Embedding model:** `mxbai-embed-large`  

---

## ⚡ Features

- REST API for querying animal information via AI.  
- Uses **Spring AI** with Ollama (local inference).  
- Vector database (Postgres + `pgvector`) for semantic search.  
- Ready-to-use **Docker Compose** setup.  

---

## 🛠️ Requirements

- **Java 21+**  
- **Maven 3.9+**  
- **Docker & Docker Compose**  
- **Ollama installed locally** (required to pull models)  

---

## 🐳 Running Services with Docker Compose

Start **Postgres with pgvector** and **Ollama** containers:  

```yaml
version: "3.9"
services:
  db:
    image: ankane/pgvector:latest
    container_name: animals_db
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: animals_db
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data

  ollama:
    image: ollama/ollama:latest
    container_name: ollama
    ports:
      - "11434:11434"
    volumes:
      - ~/.ollama:/root/.ollama

volumes:
  pgdata:
```
---

## Start the service

``` docker compose up -d ```

----

## Pull required models

On your host machine (inside the container):

```
docker ps
docker exec -it container_name bash
ollama pull llama3.2:1b
ollama pull mxbai-embed-large
```
---

🐘 Database Setup

After containers are running, connect to Postgres:

```
docker exec -it animals_db psql -U postgres -d animals_db
```
Create the database
```
CREATE EXTENSION IF NOT EXISTS vector;

CREATE TABLE animals (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    species VARCHAR(100) NOT NULL,
    weight FLOAT NOT NULL,
    habitat VARCHAR(255),
    diet VARCHAR(255),
    lifespan INT,
    description TEXT
);

```
Insert the sample data
```
INSERT INTO animals (name, species, weight, habitat, diet, lifespan, description)
VALUES
('Leo', 'Lion', 190.5, 'Savannah', 'Carnivore', 15, 'A strong and social big cat.'),
('Dumbo', 'Elephant', 4500, 'Forest/Savannah', 'Herbivore', 70, 'A giant herbivore with a trunk.'),
('Marty', 'Zebra', 350, 'Grasslands', 'Herbivore', 25, 'Striped horse-like mammal.'),
('Kiki', 'Parrot', 1.2, 'Tropical forests', 'Omnivore', 50, 'Colorful bird with mimicry abilities.');
```
---
⚙️ Spring Configuration

```application.yml```
```
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/animals_db
    username: postgres
    password: postgres
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

spring:
  ai:
    ollama:
      chat:
        model: llama3.2:1b
      embedding:
        model: mxbai-embed-large

```
---

▶️ Running the Project

Start services with Docker Compose

```docker compose up -d```


Pull Ollama models (llama3.2:1b and mxbai-embed-large).

Run Spring Boot app:

```mvn spring-boot:run```


Access API at:

``` http://localhost:8080/{user}/assitant  ```

---
GET
```
http://localhost:8080/{user}/assitant?question=Your_query
```

🖥️ Visual Studio Code Setup

You can run and debug this project directly in VS Code using the built-in Java & Spring Boot extensions.

1. Install Required Extensions

Extension Pack for Java

Spring Boot Extension Pack

2. Configure VS Code launch.json

Create or edit .vscode/launch.json in your project root:

```
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "Spring Boot - Run App",
      "request": "launch",
      "mainClass": "net.developer.space.chat_assistance_ai_rag.ChatAssistanceAiRagApplication",
      "projectName": "chat-assistance-ai-rag",
      "cwd": "${workspaceFolder}",
      "env": {
        "SPRING_DATASOURCE_URL": "jdbc:postgresql://localhost:5432/animals_db",
        "SPRING_DATASOURCE_USERNAME": "postgres",
        "SPRING_DATASOURCE_PASSWORD": "postgres",
        "SPRING_AI_OLLAMA_CHAT_MODEL": "llama3.2:1b",
        "SPRING_AI_OLLAMA_EMBEDDING_MODEL": "mxbai-embed-large"
      },
      "args": ""
    }
  ]
}
```
