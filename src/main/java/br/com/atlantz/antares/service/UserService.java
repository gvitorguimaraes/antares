package br.com.atlantz.antares.service;

import br.com.atlantz.antares.model.User;
import br.com.atlantz.antares.model.dto.LoginDTO;
import br.com.atlantz.antares.model.enums.UserRoleEnum;
import br.com.atlantz.antares.repo.UserRepo;
import br.com.atlantz.antares.security.AuthToken;
import br.com.atlantz.antares.security.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepo repo;

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
        try
        {
            User user = repo.findByEmail(login.username());

            if (user != null )
            {
                if(new BCryptPasswordEncoder().matches(login.password(), user.getPassword()))
                {
                    actionsBeforeLogin(user);
                    return TokenUtil.encode(user.getId(), user.getRole().getCode());
                }
            }
            return null;
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
            return null;
        }
    }

    private void actionsBeforeLogin(User user) throws Exception
    {
        user.setLastLoginData(LocalDateTime.now());
        repo.save(user);
    }

    @Override
    public User recoveryUserFromTokenAuth()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() != null)
        {
            return repo.findById(UUID.fromString(authentication.getPrincipal().toString())).orElse(null);
        }
        return null;
    }
}
