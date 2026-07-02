package com.krakedev.proyectos.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import com.krakedev.proyectos.entidades.Proyecto;
import com.krakedev.proyectos.services.ProyectoService;

/*
@CrossOrigin(
        origins = "http://localhost:5173",
        allowedHeaders = {
                "Authorization",
                "Content-Type"
        },
        methods = {
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE
        }
)*/
@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

	private final ProyectoService service;

	public ProyectoController(ProyectoService service) {
		super();
		this.service = service;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/")
	public ResponseEntity<?> guardar(@RequestBody Proyecto proyecto) {

		try {

			Proyecto nuevo = service.guardar(proyecto);

			return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/")
    public ResponseEntity<?> listar() {

        try {

            List<Proyecto> proyectos = service.listar();

            return ResponseEntity.ok(proyectos);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
	
	
	@GetMapping("/publico/resumen")
	public ResponseEntity<?> resumen() {

	    try {

	        Long total = service.contarProyectos();

	        return ResponseEntity.ok(total);

	    } catch (Exception e) {

	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(e.getMessage());
	    }
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable int id) {

		try {

			Proyecto proyecto = service.buscar(id);

			return ResponseEntity.ok(proyecto);

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Proyecto datos) {

		try {

			Proyecto actualizado = service.actualizar(id, datos);

			return ResponseEntity.ok(actualizado);

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable int id) {

		try {

			service.eliminar(id);

			return ResponseEntity.ok("Proyecto eliminado");

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}