package jonas.service.impl;

import jonas.model.Currency;
import jonas.model.CurrencyPeriod;
import jonas.model.Holidays;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public interface LBCurrencyService {

    void downloadCurrencyPeriod(List<CurrencyPeriod> currencyPeriodList);

    List<Holidays> getHolidays() throws IOException;

    List<Currency> getCurrencyList() throws ParserConfigurationException, IOException, SAXException;

    List<CurrencyPeriod> getCurrencyRateList(String currencyCode, String dateFrom, String dateTo) throws ParserConfigurationException, IOException, SAXException;
}
