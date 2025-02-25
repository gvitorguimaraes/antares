package br.com.atlantz.antares.model.enums;

public enum UserRoleEnum
{
    MASTER      ("M"),
    ADMIN       ("A"),
    USER        ("U");

    private final String code;

    UserRoleEnum(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

    public static UserRoleEnum fromCode(String code)
    {
        for (UserRoleEnum role : UserRoleEnum.values())
        {
            if (role.getCode().equals(code))
            {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role: "+code);
    }
}
