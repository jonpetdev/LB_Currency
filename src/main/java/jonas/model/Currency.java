package jonas.model;

public class Currency {

    private String currencyCode;
    private String ltName;
    private String enName;
    private String number;

    public Currency(){}

    public Currency(String currencyCode, String ltName, String enName, String number) {
        this.currencyCode = currencyCode;
        this.ltName = ltName;
        this.enName = enName;
        this.number = number;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getLtName() {
        return ltName;
    }

    public void setLtName(String ltName) {
        this.ltName = ltName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
