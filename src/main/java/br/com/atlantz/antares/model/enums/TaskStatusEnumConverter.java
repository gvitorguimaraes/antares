package br.com.atlantz.antares.model.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TaskStatusEnumConverter implements AttributeConverter<TaskStatusEnum, String>
{
    @Override
    public String convertToDatabaseColumn(TaskStatusEnum status)
    {
        return status != null ? status.getCode() : null;
    }

    @Override
    public TaskStatusEnum convertToEntityAttribute(String dbData)
    {
        return dbData != null ? TaskStatusEnum.fromCode(dbData) : null;
    }
}
