package org.example.spring.errorhandling;

import org.springframework.http.HttpStatus;

// burada kurduğumuz yapı ile tüm hataları global exception handler de yönetebiliriz
// şu an spring paketleri yüklü olmadığı için orası klasın orada best practise problemdetails sınıfını kullanmaktır
public enum BusinessValidationRule {
    INVALID_EMAIL("Invalid email format"),
    CONFLICT_EMAIL("Conflict email", HttpStatus.CONFLICT),
    RATE_LIMIT_EXCEEDED("Rate limit exceeded", HttpStatus.TOO_MANY_REQUESTS),
    NOT_FOUND("Not found",HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status; // springde bunu httpstatus kullan

    BusinessValidationRule(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    BusinessValidationRule(String message) {
        this.message = message;
        this.status=HttpStatus.BAD_REQUEST;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }


}
