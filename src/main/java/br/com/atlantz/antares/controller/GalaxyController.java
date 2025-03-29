package br.com.atlantz.antares.controller;

import br.com.atlantz.antares.model.Galaxy;
import br.com.atlantz.antares.model.dto.GalaxyDTO;
import br.com.atlantz.antares.service.IGalaxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/galaxy")
public class GalaxyController
{
    @Autowired
    private IGalaxyService galaxyService;

    @PostMapping
    public ResponseEntity<GalaxyDTO> createGalaxy(@RequestBody GalaxyDTO galaxy)
    {
        try
        {
            Galaxy galaxyCreated = galaxyService.save(new Galaxy(galaxy));

            return ResponseEntity.ok(new GalaxyDTO(galaxyCreated.getId().toString(), galaxyCreated.getName(), galaxyCreated.getDescription()));
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<GalaxyDTO>> getAllGalaxies()
    {
        try
        {
            List<GalaxyDTO> dtos = new ArrayList<>();
            for (Galaxy galaxy : galaxyService.getActiveGalaxies())
            {
                dtos.add(new GalaxyDTO(galaxy.getId().toString(), galaxy.getName(), galaxy.getDescription()));
            }
            return ResponseEntity.ok(dtos);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<GalaxyDTO> getGalaxyById(@PathVariable UUID id)
    {
        Galaxy galaxy = galaxyService.findById(id);
        return galaxy != null ? ResponseEntity.ok(new GalaxyDTO(galaxy.getId().toString(), galaxy.getName(), galaxy.getDescription()))
                              : ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<GalaxyDTO> updateGalaxy(@RequestBody GalaxyDTO galaxyDTO)
    {
        try
        {
            if (galaxyDTO.id() == null) return ResponseEntity.notFound().build();

            Galaxy galaxy = galaxyService.updateGalaxy(galaxyDTO);
            if (galaxy != null)
            {
                return ResponseEntity.ok(new GalaxyDTO(galaxy.getId().toString(), galaxy.getName(), galaxy.getDescription()));
            }
            return ResponseEntity.notFound().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGalaxy(@PathVariable UUID id)
    {
        Galaxy galaxy = galaxyService.findById(id);

        if (galaxy == null) return ResponseEntity.notFound().build();

        galaxyService.softDelete(galaxy);
        return ResponseEntity.noContent().build();
    }
}
