# 🎌 Japanese SRS - Aplicación Fullstack de Repaso Espaciado

![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Angular](https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)

Una aplicación web Fullstack diseñada para el aprendizaje de vocabulario mediante un sistema de repetición espaciada (SRS). Este proyecto separa completamente la interfaz de usuario de la lógica de negocio, asegurando las comunicaciones mediante tokens JWT y orquestando toda la infraestructura con Docker.

La idea de la aplicación surge de mi necesidad, como estudiante de japonés, de tener alguna herramienta cómoda y fácil de usar para ir registrando los nuevos terminos que me voy encontrando. 

Aunque ya existen aplicaciones similares, ninguna tiene la agilidad que buscaba. Además, consideré que era un proyecto sencillo pero interesante como introducción a Angular.

## Características Principales

* **Autenticación Segura:** Sistema de login y registro basado en Spring Security y JWT. Arquitectura *Stateless*.
* **Algoritmo de Repaso:** Simulador de sesión de estudio que filtra las tarjetas diarias según la calificación del usuario (Fácil, Bien, Difícil).
* **Gestión de Diccionario:** CRUD completo (Crear, Leer, Actualizar, Borrar) de tarjetas de vocabulario.
* **Diseño Responsivo:** Interfaz moderna con animaciones 3D (giro de tarjetas) y navegación optimizada para dispositivos móviles (Bottom Navigation).
* **Despliegue con un Clic:** Entorno completamente dockerizado utilizando *Multi-stage builds* para optimizar el tamaño de las imágenes.

---

## Arquitectura del Sistema

El proyecto sigue una arquitectura de microservicios básica dividida en tres contenedores principales:

1. **Frontend (Angular):** 
   - Arquitectura basada en *Signals* para la reactividad.
   - Componentes presentacionales aislados (`<ng-content>`).
   - Interceptores HTTP para inyección automática de JWT y manejo global de errores (401/403).
   - Servido en producción a través de **Nginx**.

2. **Backend (Spring Boot):**
   - API protegida con Spring Security y JWT.
   - Manejo de contraseñas con encriptación **BCrypt**.
   - Filtros personalizados (`OncePerRequestFilter`) para la validación de tokens.
   - Conexión a base de datos mediante Spring Data JPA.

3. **Base de Datos (PostgreSQL):**
   - Persistencia de datos mediante volúmenes de Docker.

---

## Instalación y Despliegue (Docker)

El proyecto está dividido en dos repositorios independientes (Frontend y Backend).

**Requisitos previos:** Tener instalado [Docker](https://www.docker.com/) y Docker Compose.

1. Crea un directorio principal para el proyecto y entra en él:
```bash
mkdir anki-workspace
cd anki-workspace
```
2. Clona ambos repositorios:
```bash
git clone https://github.com/joaquinrda/japanese-srs.git
git clone https://github.com/joaquinrda/japanese-srs-ui.git
```
3. Crea el fichero docker-compose.yml:
```yaml
services:
  db:
    image: postgres:18.3-alpine
    container_name: japanese-srs-postgres
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: japanese_srs
    volumes:
      - postgres_data:/var/lib/postgresql/data

  backend:
    build:
      context: ./japanese-srs
    container_name: japanese-srs-backend
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/japanese_srs
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: postgres
      SPRING_JPA_HIBERNATE_DDL_AUTO: update
    depends_on:
      - db
    restart: on-failure

  frontend:
    build:
      context: ./japanese-srs-ui
    container_name: japanese-srs-frontend
    ports:
      - "4200:80"
    depends_on:
      - backend


volumes:
  postgres_data:
```
4. La estructura debería verse así:
```text
   /anki-workspace
     ├── /japanese-srs
     ├── /japanese-srs-ui
     └── docker-compose.yml
```
5. Despliega con Docker Compose:
```bash
docker-compose up --build
```
6. Accede a la aplicación en:
   * **Frontend:** `http://localhost:4200`
   * **Backend:** `http://localhost:8080/api` (Necesario autenticarse)
> **Nota:** La primera vez que se despliegue, Docker descargará las imágenes base de Java y Node, y compilará ambos proyectos desde cero, además de crear la BBDD. Esto puede tardar unos minutos.
