package br.com.atlantz.antares.service;

import br.com.atlantz.antares.model.BaseEntity;
import br.com.atlantz.antares.model.User;
import br.com.atlantz.antares.model.UserActivityLog;
import br.com.atlantz.antares.repo.UserActivityLogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserActivityLogService implements IUserActivityLogService
{
    @Autowired
    private UserActivityLogRepo repo;

    @Override
    public void createNewActivityLog(User user, BaseEntity entity)
    {
        repo.save(new UserActivityLog(user, entity).setShowInTimeline(false));
    }

    @Override
    public void createNewActivityLog(User user, BaseEntity entity, Double time)
    {
        repo.save(new UserActivityLog(user, entity).setShowInTimeline(false));
    }
}
