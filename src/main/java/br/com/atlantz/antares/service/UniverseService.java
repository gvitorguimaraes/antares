package br.com.atlantz.antares.service;

import br.com.atlantz.antares.model.Universe;
import br.com.atlantz.antares.model.User;
import br.com.atlantz.antares.repo.UniverseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniverseService implements IUniverseService
{
    @Autowired
    private UniverseRepo repo;

    @Autowired
    private IUserActivityLogService userActivityLog;

    @Override
    public Universe createNewUniverse(User user)
    {
        Universe universe = new Universe();
        universe.setUser(user);

        userActivityLog.createNewActivityLog(user, universe);

        return repo.save(universe);
    }
}