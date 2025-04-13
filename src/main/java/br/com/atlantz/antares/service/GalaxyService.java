package br.com.atlantz.antares.service;

import br.com.atlantz.antares.model.Galaxy;
import br.com.atlantz.antares.model.Universe;
import br.com.atlantz.antares.model.User;
import br.com.atlantz.antares.model.dto.GalaxyDTO;
import br.com.atlantz.antares.repo.GalaxyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class GalaxyService implements IGalaxyService
{
    @Autowired
    private GalaxyRepo repo;

    @Autowired
    private IUniverseService universeService;

    @Autowired
    private IUserService userService;

    @Override
    public Galaxy save(Galaxy galaxy)
    {
        User user = userService.recoveryUserFromTokenAuth();
        if (user != null)
        {
            galaxy.setUniverse(user.getUniverse());
        }
        return repo.save(galaxy);
    }

    @Override
    public List<Galaxy> getActiveGalaxies()
    {
        try
        {
            User user =  userService.recoveryUserFromTokenAuth();
            if (user != null && user.getUniverse() != null) {
                return repo.findByUniverseAndExclusionIsNull(user.getUniverse());
            }
            return null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Galaxy findById(UUID id)
    {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void softDelete(Galaxy galaxy)
    {
        if (galaxy != null
                && galaxy.getId() != null
                && !galaxy.isDeleted())
        {
            galaxy.setExclusion(LocalDateTime.now());
            repo.save(galaxy);
        }
    }

    @Override
    public Galaxy updateGalaxy(GalaxyDTO galaxyDTO)
    {
        Galaxy galaxy = findById(UUID.fromString(galaxyDTO.id()));

        if (galaxy != null)
        {
            galaxy.setName(galaxyDTO.name());
            galaxy.setDescription(galaxyDTO.description());
            return repo.save(galaxy);
        }
        return null;
    }

    @Override
    public Galaxy createNewGalaxyAntaresV1(User user)
    {
        Galaxy galaxy = new Galaxy();
        galaxy.setName("My first galaxy");
        galaxy.setDescription("A galaxy auto generated for users registered in Antares version 1");
        galaxy.setUniverse(user.getUniverse());
        return repo.save(galaxy);
    }
}
