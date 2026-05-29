# 🚀 Sistema de Gestión de Proyectos - API REST

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge\&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4-green?style=for-the-badge\&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue?style=for-the-badge\&logo=postgresql)
![Hibernate](https://img.shields.io/badge/Hibernate-JPA-yellow?style=for-the-badge\&logo=hibernate)

---

# 📌 Descripción

API REST desarrollada con **Java + Spring Boot** para gestionar proyectos, empleados y tareas utilizando arquitectura en capas y persistencia de datos con **JPA/Hibernate** y **PostgreSQL**.

El sistema permite:

* 📁 Gestionar proyectos
* 👨‍💻 Administrar empleados
* 📝 Crear tareas
* 🔗 Relacionar proyectos y empleados con tareas

---

# 🛠️ Tecnologías

* Java 21
* Spring Boot 4
* Spring Data JPA
* Hibernate
* PostgreSQL
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

# 🌐 Endpoints

## 📁 Proyectos

| Método | Endpoint              |
| ------ | --------------------- |
| POST   | `/api/proyectos`      |
| GET    | `/api/proyectos`      |
| PUT    | `/api/proyectos/{id}` |
| DELETE | `/api/proyectos/{id}` |

## 👨‍💻 Empleados

| Método | Endpoint              |
| ------ | --------------------- |
| POST   | `/api/empleados`      |
| GET    | `/api/empleados`      |
| PUT    | `/api/empleados/{id}` |
| DELETE | `/api/empleados/{id}` |

## 📝 Tareas

| Método | Endpoint      |
| ------ | ------------- |
| POST   | `/api/tareas` |
| GET    | `/api/tareas` |

---

# ⚠️ Manejo de Excepciones

```java
try {

    return ResponseEntity.ok(servicio.listar());

} catch (Exception e) {

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(e.getMessage());
}
```

---

# ▶️ Ejecución

## Configuración

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/proyectos_db
spring.datasource.username=postgres
spring.datasource.password=tu_password

spring.jpa.hibernate.ddl-auto=update
```

## Ejecutar proyecto

```bash
mvn spring-boot:run
```

---

# 📚 Aprendizajes

* Arquitectura REST
* Spring Boot
* JPA/Hibernate
* Relaciones entre entidades
* PostgreSQL
* APIs RESTful
* Persistencia de datos

---

# 👨‍💻 Autor

### Hancel Espin

Proyecto desarrollado como práctica backend con Java + Spring Boot.
