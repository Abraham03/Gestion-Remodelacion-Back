package com.GestionRemodelacion.gestion.dto.response;

import java.time.LocalDate;

public interface ProyectoDashboardProjection {
 Long getId();
    String getNombreProyecto();
    String getDescripcion();
    LocalDate getFechaInicio();
    LocalDate getFechaFinEstimada();
    String getEstado(); // This will automatically get p.estado.toString()
    Integer getProgresoPorcentaje(); // Or getProgresoPorcentajeAsInteger() if needed for clarity
}
