package br.com.atlantz.antares.model.enums;

public enum TaskStatusEnum
{
    NEW             ("N"),
    IN_PROGRESS     ("IP"),
    FINISHED        ("F");

    private final String code;

    TaskStatusEnum(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

    public static TaskStatusEnum fromCode(String code)
    {
        for (TaskStatusEnum role : TaskStatusEnum.values())
        {
            if (role.getCode().equals(code))
            {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown status: "+code);
    }
}
