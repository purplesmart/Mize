package Entities;

import java.util.List;

public class OpenExchangeRatesResponse {
    private String disclaimer;
    private String license;
    private Long timestamp;
    private String base;
    private List<Double> rates;

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public List<Double> getRates() {
        return rates;
    }

    public void setRates(List<Double> rates) {
        this.rates = rates;
    }
}
