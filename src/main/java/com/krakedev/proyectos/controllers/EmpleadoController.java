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

import com.krakedev.proyectos.entidades.Empleado;
import com.krakedev.proyectos.services.EmpleadoService;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService service;

    public EmpleadoController(EmpleadoService service) {
        super();
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity<?> guardar(@RequestBody Empleado empleado) {

        try {

            Empleado nuevo = service.guardar(empleado);

            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/")
    public ResponseEntity<?> listar() {

        try {

            List<Empleado> empleados = service.listar();

            return ResponseEntity.ok(empleados);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable int id) {

        try {

            Empleado empleado = service.buscar(id);

            return ResponseEntity.ok(empleado);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id,
                                        @RequestBody Empleado datos) {

        try {

            Empleado actualizado = service.actualizar(id, datos);

            return ResponseEntity.ok(actualizado);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {

        try {

            service.eliminar(id);

            return ResponseEntity.ok("Empleado eliminado");

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}