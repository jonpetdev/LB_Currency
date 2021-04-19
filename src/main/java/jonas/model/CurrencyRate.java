package jonas.model;

public class CurrencyRate {
    private String currencyCode;
    private String date;
    private String amount;

    public CurrencyRate(){}

    public CurrencyRate(String currencyCode, String date, String amount) {
        this.currencyCode = currencyCode;
        this.date = date;
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
