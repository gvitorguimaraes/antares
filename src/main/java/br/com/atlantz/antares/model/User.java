package br.com.atlantz.antares.model;

import br.com.atlantz.antares.model.dto.RegisterDTO;
import br.com.atlantz.antares.model.enums.UserRoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "app_user")
public class User extends BaseEntity
{
    @Column(length = 200, nullable = false)
    private String name;

    @Column(length = 200, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column
    private Boolean active;

    @Column(name = "last_edit_data")
    private LocalDateTime lastEditData;

    @Column(nullable = false)
    private UserRoleEnum role;

    public User()
    {
        super();
    }

    public User(RegisterDTO registerDTO)
    {
        this.setName(registerDTO.name());
        this.setEmail(registerDTO.email());
        this.setPassword(registerDTO.password());
        this.setActive(true);
        this.setInclusion(LocalDateTime.now());
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public LocalDateTime getLastEditData()
    {
        return lastEditData;
    }

    public void setLastEditData(LocalDateTime lastEditData)
    {
        this.lastEditData = lastEditData;
    }

    public UserRoleEnum getRole()
    {
        return role;
    }

    public void setRole(UserRoleEnum role)
    {
        this.role = role;
    }
}
