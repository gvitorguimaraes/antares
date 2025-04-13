package br.com.atlantz.antares.service;

import br.com.atlantz.antares.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniverseGalaxyService implements IUniverseGalaxyService
{
    @Autowired
    private IUniverseService universeService;

    @Autowired
    private IGalaxyService galaxyService;

    public void createDefaultEntitiesForUser(User user) throws Exception
    {
        //
        // create a Universe in the first login
        universeService.createNewUniverse(user);

        //
        // create a galaxy for universe (just in V1, because the galaxy module is inactive)
        galaxyService.createNewGalaxyAntaresV1(user);
    }
}
