package jonas.model;

public class CurrAtr {
    private String currencyCode;
    private String dateFrom;
    private String dateTo;

    public CurrAtr(){}

    public CurrAtr(String currencyCode, String dateFrom, String dateTo) {
        this.currencyCode = currencyCode;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public CurrAtr(String dateFrom, String dateTo){
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public CurrAtr(String currencyCode){
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
}
