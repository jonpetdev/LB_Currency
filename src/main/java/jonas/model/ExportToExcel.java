package jonas.model;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExportToExcel {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<CurrencyPeriod> currencyPeriodList;

    public ExportToExcel(List<CurrencyPeriod> currencyPeriodList) {
        this.currencyPeriodList = currencyPeriodList;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Valiuta", style);
        createCell(row, 1, "Data", style);
        createCell(row, 2, "Valiutos kursas", style);
        createCell(row, 3, "Pokytis", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (CurrencyPeriod cp : currencyPeriodList) {

            int columnCount = 0;

            for(CurrencyRate cr: cp.getCurrencyRateList()){
                Row row = sheet.createRow(rowCount++);
                createCell(row, columnCount++, cr.getCurrencyCode(), style);
                createCell(row, columnCount++, cr.getDate(), style);
                createCell(row, columnCount, cr.getAmount().toString(), style);
                columnCount = 0;
            }
            createCell(sheet.createRow(rowCount++),3,cp.getChange().toString(), style);

        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);

        outputStream.close();

    }
}
