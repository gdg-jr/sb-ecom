package com.ecommerce.Exception;

public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String field;
    String fieldName;
    Long fieldId;

    public ResourceNotFoundException(String resourceName, String field, String fieldname) {
        super(String.format("%s not found with %s: %s",resourceName,field,fieldname));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldName = fieldname;
    }

    public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
        super(String.format("%s not found with %s: %d",resourceName,field,fieldId));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;
    }

    public ResourceNotFoundException() {
    }
}
