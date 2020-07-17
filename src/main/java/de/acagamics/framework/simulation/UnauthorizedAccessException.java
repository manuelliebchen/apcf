package de.acagamics.framework.simulation;

public class UnauthorizedAccessException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Tried to control locked player!";
    }
}
