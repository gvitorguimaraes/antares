package br.com.atlantz.antares.model.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserRoleEnumConverter implements AttributeConverter<UserRoleEnum, String>
{
    @Override
    public String convertToDatabaseColumn(UserRoleEnum role)
    {
        return role != null ? role.getCode() : null;
    }

    @Override
    public UserRoleEnum convertToEntityAttribute(String dbData)
    {
        return dbData != null ? UserRoleEnum.fromCode(dbData) : null;
    }
}
