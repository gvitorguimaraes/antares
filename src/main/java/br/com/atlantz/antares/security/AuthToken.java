package br.com.atlantz.antares.security;

public class AuthToken
{
    private String token;

    public AuthToken(String token)
    {
        super();
        this.token = token;
    }

    public AuthToken()
    {
        super();
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }
}
