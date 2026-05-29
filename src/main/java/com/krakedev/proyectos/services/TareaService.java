package com.krakedev.proyectos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.krakedev.proyectos.entidades.Tarea;
import com.krakedev.proyectos.repositories.TareaRepository;

@Service
public class TareaService {

    private final TareaRepository repository;

    public TareaService(TareaRepository repository) {
        super();
        this.repository = repository;
    }

    public Tarea guardar(Tarea tarea) {
        return repository.save(tarea);
    }

    public List<Tarea> listar() {
        return repository.findAll();
    }

    public Tarea buscar(int id) {

        Optional<Tarea> resultado = repository.findById(id);

        return resultado.orElseThrow(
                () -> new RuntimeException("Tarea no encontrada"));
    }

    public Tarea actualizar(int id, Tarea datos) {

        Tarea tarea = buscar(id);

        tarea.setDescripcion(datos.getDescripcion());
        tarea.setFechaLimite(datos.getFechaLimite());
        tarea.setCostoEstimado(datos.getCostoEstimado());
        tarea.setProyecto(datos.getProyecto());
        tarea.setEmpleados(datos.getEmpleados());

        return repository.save(tarea);
    }

    public boolean eliminar(int id) {

        buscar(id);

        repository.deleteById(id);

        return true;
    }
}