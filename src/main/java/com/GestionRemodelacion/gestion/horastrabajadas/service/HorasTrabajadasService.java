package com.GestionRemodelacion.gestion.horastrabajadas.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.GestionRemodelacion.gestion.dto.response.ApiResponse;
import com.GestionRemodelacion.gestion.horastrabajadas.dto.request.HorasTrabajadasRequest;
import com.GestionRemodelacion.gestion.horastrabajadas.dto.response.HorasTrabajadasResponse;
import com.GestionRemodelacion.gestion.horastrabajadas.model.HorasTrabajadas;
import com.GestionRemodelacion.gestion.horastrabajadas.repository.HorasTrabajadasRepository;
import com.GestionRemodelacion.gestion.mapper.HorasTrabajadasMapper;

@Service
public class HorasTrabajadasService {

    private final HorasTrabajadasRepository horasTrabajadasRepository;
    private final HorasTrabajadasMapper horasTrabajadasMapper;

    public HorasTrabajadasService(HorasTrabajadasRepository horasTrabajadasRepository,
                                  HorasTrabajadasMapper horasTrabajadasMapper) {
        this.horasTrabajadasRepository = horasTrabajadasRepository;
        this.horasTrabajadasMapper = horasTrabajadasMapper;
    }

    @Transactional(readOnly = true)
    public Page<HorasTrabajadasResponse> getAllHorasTrabajadas(Pageable pageable) {
        return horasTrabajadasRepository.findAll(pageable)
                .map(horasTrabajadasMapper::toHorasTrabajadasResponse);
    }

    @Transactional(readOnly = true)
    public HorasTrabajadasResponse getHorasTrabajadasById(Long id) {
        HorasTrabajadas horasTrabajadas = horasTrabajadasRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro de horas trabajadas no encontrado con ID: " + id));
        return horasTrabajadasMapper.toHorasTrabajadasResponse(horasTrabajadas);
    }

    @Transactional
    public HorasTrabajadasResponse createHorasTrabajadas(HorasTrabajadasRequest horasTrabajadasRequest) {
        HorasTrabajadas horasTrabajadas = horasTrabajadasMapper.toHorasTrabajadas(horasTrabajadasRequest); 
        HorasTrabajadas savedHorasTrabajadas = horasTrabajadasRepository.save(horasTrabajadas);
        return horasTrabajadasMapper.toHorasTrabajadasResponse(savedHorasTrabajadas);
    }

    @Transactional
    public HorasTrabajadasResponse updateHorasTrabajadas(Long id, HorasTrabajadasRequest horasTrabajadasRequest) {
        HorasTrabajadas horasTrabajadas = horasTrabajadasRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro de horas trabajadas no encontrado con ID: " + id));

        horasTrabajadasMapper.updateHorasTrabajadasFromRequest(horasTrabajadasRequest, horasTrabajadas); // <-- Usa el mapper
        HorasTrabajadas updatedHorasTrabajadas = horasTrabajadasRepository.save(horasTrabajadas);
        return horasTrabajadasMapper.toHorasTrabajadasResponse(updatedHorasTrabajadas);
    }

    @Transactional
    public ApiResponse<Void> deleteHorasTrabajadas(Long id) {
        if (!horasTrabajadasRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro de horas trabajadas no encontrado con ID: " + id);
        }
        horasTrabajadasRepository.deleteById(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Registro de horas trabajadas eliminado exitosamente.", null);
    }
}