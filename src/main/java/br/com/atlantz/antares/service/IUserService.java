package br.com.atlantz.antares.service;

import br.com.atlantz.antares.model.User;
import br.com.atlantz.antares.model.dto.LoginDTO;
import br.com.atlantz.antares.security.AuthToken;

public interface IUserService
{
    public User createNew(User user);
    public AuthToken login(LoginDTO login);
}
