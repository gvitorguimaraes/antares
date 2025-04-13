package br.com.atlantz.antares.util.error;

public class ServiceInternException extends RuntimeException
{
    public <T> ServiceInternException(Class<T> serviceClass, String message) {
        super(serviceClass.getName()+": " + message);
    }

    public <T> ServiceInternException(Class<T> serviceClass, String message, Throwable cause) {
        super(serviceClass.getName()+": " + message, cause);
    }
}
