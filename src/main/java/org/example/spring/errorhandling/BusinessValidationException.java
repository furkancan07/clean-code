package org.example.spring.errorhandling;

import org.springframework.http.HttpStatus;

// enum bazlı ne hata gelirse oradan yöentebiliriz
public class BusinessValidationException extends RuntimeException{
    private final BusinessValidationRule rule;

    public BusinessValidationException(BusinessValidationRule rule) {
        super(rule.getMessage());
        this.rule = rule;
    }
    public HttpStatus getStatus() {
        return rule.getStatus();
    }
}
