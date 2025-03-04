package br.com.atlantz.antares.controller;

import br.com.atlantz.antares.model.User;
import br.com.atlantz.antares.model.dto.LoginDTO;
import br.com.atlantz.antares.model.dto.RegisterDTO;
import br.com.atlantz.antares.security.AuthToken;
import br.com.atlantz.antares.security.TokenUtil;
import br.com.atlantz.antares.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController
{
    @Autowired
    private IUserService service;

    @PostMapping("/auth")
    public ResponseEntity<AuthToken> realizarLogin(@RequestBody LoginDTO login)
    {
        AuthToken token = service.login(login);
        if (token != null)
        {
            return ResponseEntity.ok(token);
        }

        //
        // TODO - tratar erro caso não encontre o usuario

        return ResponseEntity.status(403).build();
    }

    @PostMapping("/register")
    public ResponseEntity createNewUser(@RequestBody RegisterDTO registerDTO)
    {
        try
        {
            service.createNew(new User(registerDTO));
            return ResponseEntity.status(201).build();
        }
        catch (Exception e)
        {
            //
            // TODO - tratar erro caso já exista usuário com email informado, ou caso a senha esteja errada

            return ResponseEntity.status(404).build();
        }

    }

    //
    // test
    @GetMapping("/master")
    public String sayHelloAdmin()
    {
        return "Hello MASTER";
    }

}
