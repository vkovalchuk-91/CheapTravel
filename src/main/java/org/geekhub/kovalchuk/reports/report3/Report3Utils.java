package org.geekhub.kovalchuk.reports.report3;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class Report3Utils {
    private final XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private final List<Report3Data> dataList;
    int rowCount;

    public Report3Utils(List<Report3Data> dataList) {
        this.dataList = dataList;
        workbook = new XSSFWorkbook();
    }

    public void exportReport3(HttpServletResponse response) throws IOException {
        createHeaderRow();
        writeData();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void createHeaderRow(){
        sheet = workbook.createSheet("Топ-100 дешевих маршрутів");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(15);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        createCell(row, 0,
                "Топ-100 маршрутів з найнижчими цінами", style);
        createCell(row, 1, "", style);
        createCell(row, 2, "", style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
        font.setFontHeightInPoints((short) 15);

        row = sheet.createRow(1);
        createCell(row, 0, "№", style);
        createCell(row, 1, "Маршрут", style);
        createCell(row, 2, "Середня ціна, дол.", style);
    }

    private void writeData() {
        rowCount = 2;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);

        CellStyle doubleCellStyle = workbook.createCellStyle();
        doubleCellStyle.cloneStyleFrom(style);
        DataFormat doubleDataFormat = workbook.createDataFormat();
        doubleCellStyle.setDataFormat(doubleDataFormat.getFormat("0.0"));

        int dataRowCounter = 1;
        for (Report3Data reportRouteData : dataList){
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, dataRowCounter++, style);
            createCell(row, columnCount++, reportRouteData.getRouteName(), style);
            createCell(row, columnCount++, reportRouteData.getAverageTicketPrice(), doubleCellStyle);
        }

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer){
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double){
            cell.setCellValue((Double) value);
        } else if (value instanceof Boolean){
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long){
            cell.setCellValue((Long) value);
        } else {
            cell.setCellValue(value.toString());
        }
        cell.setCellStyle(style);
    }

}
