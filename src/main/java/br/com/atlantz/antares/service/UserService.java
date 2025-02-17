package br.com.atlantz.antares.service;

import br.com.atlantz.antares.model.User;
import br.com.atlantz.antares.model.dto.LoginDTO;
import br.com.atlantz.antares.repo.UserRepo;
import br.com.atlantz.antares.security.AuthToken;
import br.com.atlantz.antares.security.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService
{
    @Autowired
    private UserRepo repo;

    @Override
    public User createNew(User user)
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }

    @Override
    public AuthToken login(LoginDTO login)
    {
        User user = repo.findByEmail(login.username());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(encoder.matches(login.password(), user.getPassword()))
        {
            return TokenUtil.encode(login);
        }
        return null;
    }
}
