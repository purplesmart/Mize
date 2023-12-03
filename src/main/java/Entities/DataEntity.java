package Entities;

import java.time.LocalDateTime;

public class DataEntity<T> {

    private final String key;
    private final T value;
    private final LocalDateTime expirationDateTime;

    public DataEntity(String key, T value, int expirationInterval) {
        this.key = key;
        this.value = value;
        LocalDateTime currentDate = LocalDateTime.now();
        expirationDateTime = currentDate.plusHours(expirationInterval);
    }

    public String getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }

    public LocalDateTime getExpirationDateTime() {
        return expirationDateTime;
    }

    public boolean IsExpired(){
        return LocalDateTime.now().isAfter(expirationDateTime);
    }
}
