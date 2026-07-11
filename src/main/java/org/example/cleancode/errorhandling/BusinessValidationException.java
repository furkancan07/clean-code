package org.example.cleancode.errorhandling;
// enum bazlı ne hata gelirse oradan yöentebiliriz
public class BusinessValidationException extends RuntimeException{
    private final BusinessValidationRule rule;

    public BusinessValidationException(BusinessValidationRule rule) {
        super(rule.getMessage());
        this.rule = rule;
    }
    public Integer getStatus() {
        return rule.getStatus();
    }
}
