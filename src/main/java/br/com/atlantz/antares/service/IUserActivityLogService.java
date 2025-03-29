package br.com.atlantz.antares.service;

import br.com.atlantz.antares.model.BaseEntity;
import br.com.atlantz.antares.model.User;

public interface IUserActivityLogService
{
    public void createNewActivityLog(User user, BaseEntity entity) throws Exception;
    public void createNewActivityLog(User user, BaseEntity entity, Double time) throws Exception;
}
