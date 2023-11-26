package Entities;

import java.time.LocalDateTime;

public class DataEntity<T> {

    public DataEntity(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public DataEntity(String key, T value, int expirationInterval) {
        this.key = key;
        this.value = value;
        LocalDateTime currentDate = LocalDateTime.now();
        expirationDateTime = currentDate.plusHours(expirationInterval);
    }

    private String key;
    private T value;
    private LocalDateTime expirationDateTime;

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
        return false;//LocalDateTime.now().isAfter(expirationDateTime);
    }
}
