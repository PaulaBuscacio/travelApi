package net.buscacio.travelApi.exception;

public class ResourceException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ResourceException(String message) {
        super(message);
    }
}
