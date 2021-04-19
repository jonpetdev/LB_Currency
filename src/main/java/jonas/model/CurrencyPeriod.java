package jonas.model;

import java.util.List;

public class CurrencyPeriod {
    private Currency currency;
    private List<CurrencyRate> currencyRateList;
    private Float change;

    public CurrencyPeriod(Currency currency, List<CurrencyRate> currencyRateList, Float change) {
        this.currency = currency;
        this.currencyRateList = currencyRateList;
        this.change = change;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public List<CurrencyRate> getCurrencyRateList() {
        return currencyRateList;
    }

    public void setCurrencyRateList(List<CurrencyRate> currencyRateList) {
        this.currencyRateList = currencyRateList;
    }

    public Float getChange() {
        return change;
    }

    public void setChange(Float change) {
        this.change = change;
    }
}
