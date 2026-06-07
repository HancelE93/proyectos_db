# 🚀 Sistema de Gestión de Proyectos - API REST Segura

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge\&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4-green?style=for-the-badge\&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue?style=for-the-badge\&logo=postgresql)
![Hibernate](https://img.shields.io/badge/Hibernate-JPA-yellow?style=for-the-badge\&logo=hibernate)
![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-success?style=for-the-badge\&logo=springsecurity)

---

# 📌 Descripción

API REST desarrollada con **Java + Spring Boot** para gestionar proyectos, empleados y tareas utilizando arquitectura en capas, persistencia de datos con **JPA/Hibernate** y base de datos **PostgreSQL**.

Además, el proyecto implementa un sistema completo de autenticación y autorización utilizando:

* Spring Security
* JWT (JSON Web Token)
* BCrypt
* Roles (ADMIN y USER)
* Blacklist de Tokens
* Seguridad basada en anotaciones

---

# 🛠️ Tecnologías

* Java 21
* Spring Boot
* Spring Data JPA
* Hibernate
* PostgreSQL
* Spring Security
* JWT (java-jwt)
* BCrypt
* Maven
* Postman

---

# 🧱 Arquitectura

```text
Controller → Service → Repository → Database
```

---

# 🗂️ Entidades

## 📁 Proyecto

* id
* nombre
* descripcion
* fechaInicio

## 👨‍💻 Empleado

* id
* nombre
* cargo

## 📝 Tarea

* id
* descripcion
* fechaLimite
* costoEstimado

## 🔐 Usuario

* id
* username
* password
* rol

---

# 🔗 Relaciones JPA

## OneToMany

```java
@OneToMany(mappedBy = "proyecto")
@JsonIgnore
private List<Tarea> tareas;
```

## ManyToOne

```java
@ManyToOne
@JoinColumn(name = "proyecto_id")
private Proyecto proyecto;
```

## ManyToMany

```java
@ManyToMany
@JoinTable(
    name = "tarea_empleados",
    joinColumns = @JoinColumn(name = "tarea_id"),
    inverseJoinColumns = @JoinColumn(name = "empleado_id")
)
private List<Empleado> empleados;
```

---

# 🔐 Seguridad Implementada

## Registro de Usuarios

Los usuarios se registran mediante:

```http
POST /api/auth/registrar
```

Las contraseñas son almacenadas utilizando BCrypt:

```java
String passwordHash =
    BCrypt.hashpw(password, BCrypt.gensalt());
```

---

## Login con JWT

Los usuarios se autentican mediante:

```http
POST /api/auth/login
```

Si las credenciales son válidas, el sistema genera un JWT firmado digitalmente.

Ejemplo:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

## Logout con Blacklist

Para invalidar tokens antes de su expiración:

```http
POST /api/auth/logout
```

Los tokens se almacenan en una Blacklist en memoria.

---

## Spring Security

Se implementó:

* SecurityConfig
* JwtAuthenticationFilter
* SecurityContextHolder
* @EnableMethodSecurity

Todas las rutas están protegidas excepto:

```text
/api/auth/login
/api/auth/registrar
```

---

# 👤 Roles y Permisos

## ADMIN

Puede:

* Crear proyectos
* Eliminar proyectos
* Crear tareas
* Consultar proyectos
* Consultar empleados
* Consultar tareas

## USER

Puede:

* Consultar proyectos
* Consultar empleados
* Consultar tareas

No puede:

* Crear proyectos
* Eliminar proyectos
* Crear tareas

---

# 🛡️ Autorización por Roles

Ejemplos:

```java
@PreAuthorize("hasRole('ADMIN')")
```

```java
@PreAuthorize("hasAnyRole('ADMIN','USER')")
```

---

# 🌐 Endpoints

## 🔐 Autenticación

| Método | Endpoint            |
| ------ | ------------------- |
| POST   | /api/auth/registrar |
| POST   | /api/auth/login     |
| POST   | /api/auth/logout    |
| GET    | /api/auth/perfil    |

---

## 📁 Proyectos

| Método | Endpoint            |
| ------ | ------------------- |
| POST   | /api/proyectos      |
| GET    | /api/proyectos      |
| PUT    | /api/proyectos/{id} |
| DELETE | /api/proyectos/{id} |

---

## 👨‍💻 Empleados

| Método | Endpoint            |
| ------ | ------------------- |
| POST   | /api/empleados      |
| GET    | /api/empleados      |
| PUT    | /api/empleados/{id} |
| DELETE | /api/empleados/{id} |

---

## 📝 Tareas

| Método | Endpoint    |
| ------ | ----------- |
| POST   | /api/tareas |
| GET    | /api/tareas |

---

# 🔑 Ejemplo de Cabecera JWT

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

# ⚠️ Manejo de Excepciones

```java
try {

    return ResponseEntity.ok(servicio.listar());

} catch (Exception e) {

    return ResponseEntity.status(
            HttpStatus.INTERNAL_SERVER_ERROR)
            .body(e.getMessage());
}
```

---

# ▶️ Configuración

## Base de Datos

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/proyectos_db
spring.datasource.username=postgres
spring.datasource.password=tu_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

# ▶️ Ejecución

Compilar:

```bash
mvn clean install
```

Ejecutar:

```bash
mvn spring-boot:run
```

O:

```bash
java -jar target/proyectos.jar
```

---

# 📚 Aprendizajes

* Arquitectura REST
* Spring Boot
* Spring Data JPA
* Hibernate
* PostgreSQL
* Spring Security
* JWT
* BCrypt
* Roles y Permisos
* Filtros Personalizados
* Blacklist de Tokens
* APIs RESTful
* Persistencia de Datos
* Seguridad de Aplicaciones Backend

---

# 👨‍💻 Autor

## Hancel Espin

Proyecto desarrollado como práctica avanzada de Backend utilizando Java, Spring Boot, PostgreSQL, Spring Security y JWT.
