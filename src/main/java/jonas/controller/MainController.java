package jonas.controller;

import jonas.model.*;
import jonas.service.impl.LBCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private LBCurrencyService lBCurrencyService;

    List<CurrencyPeriod> list;

    @RequestMapping(value= "/")
    public ModelAndView mainPage(@RequestParam(name="download", required=false) String download, HttpServletResponse response) throws ParserConfigurationException, IOException, SAXException {
        ModelAndView mv = new ModelAndView();
        if(download!=null) {
            if (list.isEmpty()) {
                System.out.println("tuscias");
            } else {
                response.setContentType("application/octet-stream");
                DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                String currentDateTime = dateFormatter.format(new Date());

                String headerKey = "Content-Disposition";
                String headerValue = "attachment; filename=currency_" + currentDateTime + ".xlsx";
                response.setHeader(headerKey, headerValue);

                ExportToExcel exportToExcel = new ExportToExcel(list);
                exportToExcel.export(response);
            }
        }
        mv.addObject("currencyList", lBCurrencyService.getCurrencyList());
        if(list == null){
            mv.addObject("currencyRateList", new ArrayList<>());
        }else{
            mv.addObject("currencyRateList", list) ;
        }
        mv.addObject("currencyRateModel", new CurrAtr());
        mv.setViewName("main");
        return mv;
    }

    @RequestMapping(value = "/currency_list")
    public ModelAndView show(@ModelAttribute("getCurrRate")CurrAtr currAtr) throws ParserConfigurationException, IOException, SAXException {
        ModelAndView mv = new ModelAndView();

        if(currAtr!=null){
            list = lBCurrencyService.getCurrencyRateList(currAtr.getCurrencyCode(), currAtr.getDateFrom(), currAtr.getDateTo());
        }

        mv.setViewName("redirect:/");
        return mv;
    }

    @RequestMapping(value = "/holidays", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Holidays>> test() throws IOException {

        return ResponseEntity.ok(lBCurrencyService.getHolidays());
    }


}
