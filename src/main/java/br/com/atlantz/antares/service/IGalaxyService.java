package br.com.atlantz.antares.service;

import br.com.atlantz.antares.model.Galaxy;
import br.com.atlantz.antares.model.dto.GalaxyDTO;

import java.util.List;
import java.util.UUID;

public interface IGalaxyService
{
    Galaxy save(Galaxy galaxy);
    List<Galaxy> getActiveGalaxies();
    Galaxy findById(UUID id);
    void softDelete(Galaxy galaxy);
    Galaxy updateGalaxy(GalaxyDTO galaxyDTO);
}
