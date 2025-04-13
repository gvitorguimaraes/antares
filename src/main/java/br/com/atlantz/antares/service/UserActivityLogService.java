package br.com.atlantz.antares.service;

import br.com.atlantz.antares.repo.UserActivityLogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserActivityLogService implements IUserActivityLogService
{
    @Autowired
    private UserActivityLogRepo repo;

}
