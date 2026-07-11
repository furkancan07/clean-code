package org.example.cleancode.errorhandling;
// burada kurduğumuz yapı ile tüm hataları global exception handler de yönetebiliriz
// şu an spring paketleri yüklü olmadığı için orası klasın orada best practise problemdetails sınıfını kullanmaktır
public enum BusinessValidationRule {
    INVALID_EMAIL("Invalid email format"),
    CONFLICT_EMAIL("Conflict email", 409),
    RATE_LIMIT_EXCEEDED("Rate limit exceeded", 429);

    private final String message;
    private final Integer status; // springde bunu httpstatus kullan

    BusinessValidationRule(String message, Integer status) {
        this.message = message;
        this.status = status;
    }

    BusinessValidationRule(String message) {
        this.message = message;
        this.status=400;
    }

    public String getMessage() {
        return message;
    }

    public Integer getStatus() {
        return status;
    }
}
