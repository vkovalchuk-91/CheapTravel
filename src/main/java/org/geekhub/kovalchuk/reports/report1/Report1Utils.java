package org.geekhub.kovalchuk.reports.report1;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class Report1Utils {
    private final XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private final List<Report1Data> dataList;
    int totalCities;
    int rowCount;

    public Report1Utils(List<Report1Data> dataList) {
        this.dataList = dataList;
        workbook = new XSSFWorkbook();
        this.totalCities = dataList.size();
    }

    public void exportReport1(HttpServletResponse response) throws IOException {
        createHeaderRow();
        writeData();
        writeTotal();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void createHeaderRow(){
        sheet = workbook.createSheet("Статистика міст");
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
                "Статистика кількості та вартості рейсів з/до міст, які доступні в програмі", style);
        createCell(row, 1, "", style);
        createCell(row, 2, "", style);
        createCell(row, 3, "", style);
        createCell(row, 4, "", style);
        createCell(row, 5, "", style);
        createCell(row, 6, "", style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
        font.setFontHeightInPoints((short) 15);

        row = sheet.createRow(1);
        createCell(row, 0, "Місто", style);
        createCell(row, 1, "Кількість міст зі сполученням", style);
        createCell(row, 2, "", style);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 2));
        createCell(row, 3, "% міст з якими є сполучення", style);
        createCell(row, 4, "", style);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 4));
        createCell(row, 5, "Середня вартість квитка, дол", style);
        createCell(row, 6, "", style);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, 6));

        row = sheet.createRow(2);
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "", style);
        createCell(row, 1, "Рейси до міста", style);
        createCell(row, 2, "Рейси з міста", style);
        createCell(row, 3, "Рейси до міста", style);
        createCell(row, 4, "Рейси з міста", style);
        createCell(row, 5, "Рейси до міста", style);
        createCell(row, 6, "Рейси з міста", style);
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
    }

    private void writeData() {
        rowCount = 3;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);

        CellStyle percentCellStyle = workbook.createCellStyle();
        percentCellStyle.cloneStyleFrom(style);
        DataFormat percentDataFormat = workbook.createDataFormat();
        percentCellStyle.setDataFormat(percentDataFormat.getFormat("0.0 %"));

        CellStyle doubleCellStyle = workbook.createCellStyle();
        doubleCellStyle.cloneStyleFrom(style);
        DataFormat doubleDataFormat = workbook.createDataFormat();
        doubleCellStyle.setDataFormat(doubleDataFormat.getFormat("0.0"));

        for (Report1Data reportCityData : dataList){
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, reportCityData.getCityName(), style);
            createCell(row, columnCount++, reportCityData.getAvailableRoutesToCity(), style);
            createCell(row, columnCount++, reportCityData.getAvailableRoutesFromCity(), style);

            createCell(row, columnCount++, reportCityData.getPercentageCitiesWithRoutesToCity(), percentCellStyle);
            createCell(row, columnCount++, reportCityData.getPercentageCitiesWithRoutesFromCity(), percentCellStyle);
            createCell(row, columnCount++, reportCityData.getAverageTicketPriceToCity(), doubleCellStyle);
            createCell(row, columnCount++, reportCityData.getAverageTicketPriceFromCity(), doubleCellStyle);
        }

    }

    private void writeTotal() {
        Row row = sheet.createRow(++rowCount);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);
        createCell(row, 0, "Загальна кількість доступних міст = " + totalCities, style);
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
