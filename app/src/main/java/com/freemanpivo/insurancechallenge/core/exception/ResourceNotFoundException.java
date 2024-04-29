package com.freemanpivo.insurancechallenge.core.exception;

public class ResourceNotFoundException extends RuntimeException {
    private final ExceptionFlow flow;
    private final String message;
    public ResourceNotFoundException(ExceptionFlow flow, String id) {
        this.flow = flow;
        switch (flow) {
            case SEARCH -> this.message = String.format("The resource with id %s does not exists.", id);
            case UPDATE -> this.message = String.format("Can't update product with id=%s. Resource does not exists", id);
            default -> this.message = "cant find resource";
        }
    }

    public ExceptionFlow flow() { return flow; }
    public String message() { return message; }
}
