package com.ideas2it.flipzon.exception;

public class ResourceNotFoundException extends RuntimeException {

    String resourceName;
    String field;
    String fieldName;
    Long fieldId;

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String resourceName, String field) {
        super(String.format("%s not found with %s", resourceName, field));
        this.resourceName = resourceName;
        this.field = field;
    }

    public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
        super(String.format("%s not found with %s: %d", resourceName, field, fieldId));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;
    }

    public ResourceNotFoundException(String resourceName, Long fieldId) {
        super(String.format("%s not found with: %d", resourceName, fieldId));
        this.resourceName = resourceName;
        this.fieldId = fieldId;
    }
}
