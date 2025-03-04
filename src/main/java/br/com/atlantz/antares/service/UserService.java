package br.com.atlantz.antares.service;

import br.com.atlantz.antares.model.User;
import br.com.atlantz.antares.model.dto.LoginDTO;
import br.com.atlantz.antares.model.enums.UserRoleEnum;
import br.com.atlantz.antares.repo.UserRepo;
import br.com.atlantz.antares.security.AuthToken;
import br.com.atlantz.antares.security.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService implements IUserService
{
    @Autowired
    private UserRepo repo;

    @Autowired
    private IUniverseService universeService;

    @Override
    public User createNew(User user)
    {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRole(UserRoleEnum.USER);

        return repo.save(user);
    }

    @Override
    public AuthToken login(LoginDTO login)
    {
        User user = repo.findByEmail(login.username());

        if (user != null )
        {
            if(new BCryptPasswordEncoder().matches(login.password(), user.getPassword()))
            {
                actionsBeforeLogin(user);
                return TokenUtil.encode(new LoginDTO(login.username(), login.password(), user.getRole().getCode()));
            }
        }

        return null;
    }

    private void actionsBeforeLogin(User user)
    {
        //
        // create a Universe in the first login
        if (user.getLastLoginData() == null)
        {
            universeService.createNewUniverse(user);
        }


        user.setLastLoginData(LocalDateTime.now());
        repo.save(user);
    }
}
