# 📚 Literalura – Challenge #3 de Alura

**Literalura** es una aplicación en Java que consume datos de una API de libros, los guarda en una base de datos y permite realizar consultas y filtros.  
Está desarrollada usando **Spring Boot**, **Spring Data JPA**, y conexión a base de datos relacional (PostgreSQL).

---

## 🚀 Características principales

- **Carga de libros desde API externa** y almacenamiento en base de datos.
- **Consultas personalizadas** usando *Derived Queries* y *JPQL*:
  - Listar libros por idioma.
  - Contar libros por idioma.
  - Buscar libros por nombre de autor.
  - Mostrar los **10 libros más descargados**.
  - Listar autores filtrando por año de nacimiento o fallecimiento.
- **Generación de estadísticas** usando `DoubleSummaryStatistics` para métricas de descargas.
- **Top 10 libros más descargados** directo desde base de datos.
- Código modular y mantenible con arquitectura en capas:
  - `entity` → Entidades JPA.
  - `repository` → Interfaces de acceso a datos.
  - `service` → Lógica de negocio.
  - `Vista` → Implementada mediante la interfaz de consola.

---

## 🛠️ Tecnologías utilizadas

- **Java 17**
- **Spring Boot** 3.5.4
- **Spring Data JPA**
- **Hibernate**
- **PostgreSQL**
- **Maven** como gestor de dependencias

---

## 📂 Estructura del proyecto

```plaintext
src/
 ├─ main/
 │   ├─ java/
 │   │   └─ com/alura/literalura/
 │   │       ├─ entity/        # Clases de modelo (Book, Author)
 │   │       ├─ repository/    # Interfaces JpaRepository
 │   │       ├─ service/       # Lógica de negocio
 │   │       └─ util/          # "Vista" del sistema (ConsoleUtil para interacción por consola)  
 │   └─ resources/
 │       └─ application.properties  # Configuración de base de datos
