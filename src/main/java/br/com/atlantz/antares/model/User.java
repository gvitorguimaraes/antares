package br.com.atlantz.antares.model;

import br.com.atlantz.antares.model.dto.RegisterDTO;
import br.com.atlantz.antares.model.enums.UserRoleEnum;
import br.com.atlantz.antares.model.enums.UserRoleEnumConverter;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "last_login_data")
    private LocalDateTime lastLoginData;

    @Convert(converter = UserRoleEnumConverter.class)
    @Column(nullable = false)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserActivityLog> activityLogs;

    public User()
    {
        super();
    }

    public User(RegisterDTO registerDTO)
    {
        super();

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

    public LocalDateTime getLastLoginData()
    {
        return lastLoginData;
    }

    public void setLastLoginData(LocalDateTime lastLoginData)
    {
        this.lastLoginData = lastLoginData;
    }

    public UserRoleEnum getRole()
    {
        return role;
    }

    public void setRole(UserRoleEnum role)
    {
        this.role = role;
    }

    public List<UserActivityLog> getActivityLogs()
    {
        if (activityLogs == null) activityLogs = new ArrayList<>();
        return activityLogs;
    }

    public void setActivityLogs(List<UserActivityLog> activityLogs)
    {
        this.activityLogs = activityLogs;
    }

}
