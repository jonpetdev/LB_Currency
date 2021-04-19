package jonas.service.impl;


import jonas.model.Currency;
import jonas.model.CurrencyPeriod;
import jonas.model.CurrencyRate;
import jonas.model.Holidays;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class LBCurrencyServiceImpl implements LBCurrencyService{


     List<Currency> currencyListService;

    @Override
    public List<Holidays> getHolidays() throws IOException {
        List<Holidays> holidays = new ArrayList<>();
        Integer year = LocalDate.now().getYear();
        for(int a= year; a>(year-4); a-- ) {
            JSONArray json = new JSONArray(IOUtils.toString(new URL("https://date.nager.at/Api/v2/PublicHolidays/" + a + "/LT"), Charset.forName("UTF-8")));

            for (int i = 0; i < json.length(); i++) {
                LocalDate localDate = LocalDate.parse(json.getJSONObject(i).get("date").toString());
                holidays.add(new Holidays(localDate.getYear(),localDate.getMonthValue(), localDate.getDayOfMonth(), json.getJSONObject(i).get("name").toString()));
            }
        }

        return holidays;
    }


    @Override
    public List<Currency> getCurrencyList() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse("http://www.lb.lt/webservices/fxrates/FxRates.asmx/getCurrencyList");
        document.getDocumentElement().normalize();

        NodeList nList = document.getElementsByTagName("CcyNtry");

        List<Currency> currencyList = new ArrayList<>();

        for (int i = 0; i < nList.getLength(); i++)
        {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element eElement = (Element) node;
                currencyList.add(new Currency(eElement.getElementsByTagName("Ccy").item(0).getTextContent(),
                        eElement.getElementsByTagName("CcyNm").item(0).getTextContent(),
                        eElement.getElementsByTagName("CcyNm").item(1).getTextContent(),
                        eElement.getElementsByTagName("CcyNbr").item(0).getTextContent()));
            }
        }
        currencyListService=currencyList;

        return currencyList;
    }

    @Override
    public List<CurrencyPeriod> getCurrencyRateList(String currencyCode, String dateFrom, String dateTo) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();


        Document document = builder.parse("http://www.lb.lt/webservices/fxrates/FxRates.asmx/getFxRatesForCurrency?tp=EU&ccy="+currencyCode+"&dtFrom="+dateFrom+"&dtTo="+dateTo);
        document.getDocumentElement().normalize();
        Element root = document.getDocumentElement();


        NodeList nList = document.getElementsByTagName("FxRate");
        List<CurrencyPeriod> currencyPeriodList = new ArrayList<>();

        for(int i=0; i<nList.getLength(); i++){
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element eElement = (Element) node;
                NodeList n2List = eElement.getElementsByTagName("CcyAmt");
                Element e2Element = (Element) n2List.item(1);

                Currency currency = getCurrency(e2Element.getElementsByTagName("Ccy").item(0).getTextContent());
                if(ifHasValue(currencyPeriodList,currency)){
                    CurrencyPeriod cPeriod = getPeriod(currencyPeriodList, currency);
                    cPeriod.getCurrencyRateList().add(new CurrencyRate(e2Element.getElementsByTagName("Ccy").item(0).getTextContent(),
                            eElement.getElementsByTagName("Dt").item(0).getTextContent(),
                            e2Element.getElementsByTagName("Amt").item(0).getTextContent()));
                    currencyPeriodList = updateList(currencyPeriodList, cPeriod);
                }else{
                    List<CurrencyRate> currRateList = new ArrayList<>();
                    currRateList.add(new CurrencyRate(e2Element.getElementsByTagName("Ccy").item(0).getTextContent(),
                            eElement.getElementsByTagName("Dt").item(0).getTextContent(),
                            e2Element.getElementsByTagName("Amt").item(0).getTextContent()));
                    order(currRateList);
                    currencyPeriodList.add(new CurrencyPeriod(currency, currRateList, 0.0f));
                }

            }
        }

        return currencyPeriodList;
    }

    public Currency getCurrency(String currency) throws ParserConfigurationException, IOException, SAXException {
        return currencyListService.stream().filter(cur -> currency.equals(cur.getCurrencyCode())).findFirst().orElse(null);
    }

    public boolean ifHasValue(List<CurrencyPeriod> currencyPeriodList, Currency currency){
        return currencyPeriodList.stream().anyMatch(cur -> cur.getCurrency().equals(currency));
    }

    public CurrencyPeriod getPeriod(List<CurrencyPeriod> currencyPeriodList, Currency currency){
        return currencyPeriodList.stream().filter(cp -> cp.getCurrency().equals(currency)).findFirst().orElse(null);
    }

    public List<CurrencyPeriod> updateList(List<CurrencyPeriod> currencyPeriodList, CurrencyPeriod currencyPeriod){
        int ind = currencyPeriodList.indexOf(currencyPeriod);
        Float change = currencyPeriod.getChange();

        Float amountFirst = Float.parseFloat(currencyPeriod.getCurrencyRateList().get(0).getAmount());
        Float amountLast = Float.parseFloat(currencyPeriod.getCurrencyRateList().get(currencyPeriod.getCurrencyRateList().size()-1).getAmount());
        change = amountLast-amountFirst;
        currencyPeriod.setChange(change);
        currencyPeriodList.set(ind, currencyPeriod);
        return currencyPeriodList;
    }

    private static void order(List<CurrencyRate> currency) {

        currency.sort(new Comparator() {

            public int compare(Object o1, Object o2) {

                String x1 = ((CurrencyRate) o1).getCurrencyCode();
                String x2 = ((CurrencyRate) o2).getCurrencyCode();
                int sComp = x1.compareTo(x2);

                if (sComp != 0) {
                    return sComp;
                }

                x1 = ((CurrencyRate) o1).getDate();
                x2 = ((CurrencyRate) o2).getDate();
                return x1.compareTo(x2);
            }
        });
    }

    @Override
    public void downloadCurrencyPeriod(List<CurrencyPeriod> currencyPeriodList){
        if(currencyPeriodList.isEmpty()){
            System.out.println("tuscias listas");
        }else{

        }

    }

}
