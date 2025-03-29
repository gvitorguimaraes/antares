package br.com.atlantz.antares.service;

import br.com.atlantz.antares.model.Universe;
import br.com.atlantz.antares.model.User;

public interface IUniverseService
{
    public Universe createNewUniverse(User user) throws Exception;
    public Universe findUniverseByUser(User user) throws Exception;
}
