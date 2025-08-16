package com.GestionRemodelacion.gestion.proyecto.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.GestionRemodelacion.gestion.dto.response.ApiResponse;
import com.GestionRemodelacion.gestion.mapper.ProyectoMapper;
import com.GestionRemodelacion.gestion.proyecto.dto.request.ProyectoRequest;
import com.GestionRemodelacion.gestion.proyecto.dto.response.ProyectoResponse;
import com.GestionRemodelacion.gestion.proyecto.model.Proyecto;
import com.GestionRemodelacion.gestion.proyecto.repository.ProyectoRepository;

@Service
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final ProyectoMapper proyectoMapper;

    public ProyectoService(ProyectoRepository proyectoRepository,ProyectoMapper proyectoMapper) {
        this.proyectoRepository = proyectoRepository;
        this.proyectoMapper = proyectoMapper;
    }

    @Transactional(readOnly = true)
    public Page<ProyectoResponse> getAllProyectos(Pageable pageable) {
        return proyectoRepository.findAllWithClienteAndEmpleado(pageable) 
                .map(proyectoMapper::toProyectoResponse);
    }

    @Transactional(readOnly = true)
    public ProyectoResponse getProyectoById(Long id) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado con ID: " + id));
        return proyectoMapper.toProyectoResponse(proyecto);
    }

    @Transactional
    public ProyectoResponse createProyecto(ProyectoRequest proyectoRequest) {
        Proyecto proyecto = proyectoMapper.toProyecto(proyectoRequest);
        Proyecto savedProyecto = proyectoRepository.save(proyecto);
        return proyectoMapper.toProyectoResponse(savedProyecto);
    }

    @Transactional
    public ProyectoResponse updateProyecto(Long id, ProyectoRequest proyectoRequest) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado con ID: " + id));

        proyectoMapper.updateProyectoFromRequest(proyectoRequest, proyecto);
        Proyecto updatedProyecto = proyectoRepository.save(proyecto);
        return proyectoMapper.toProyectoResponse(updatedProyecto);
    }

    @Transactional
    public ApiResponse<Void> deleteProyecto(Long id) {
        if (!proyectoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado con ID: " + id);
        }
        proyectoRepository.deleteById(id);
        return ApiResponse.success(null);
    }

}