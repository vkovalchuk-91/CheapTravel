package org.geekhub.kovalchuk.reports.report2;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class Report2Utils {
    private final XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private final List<Report2Data> dataList;
    int rowCount;

    public Report2Utils(List<Report2Data> dataList) {
        this.dataList = dataList;
        workbook = new XSSFWorkbook();
    }

    public void exportReport2(HttpServletResponse response) throws IOException {
        createHeaderRow();
        writeData();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void createHeaderRow(){
        sheet = workbook.createSheet("Статистика польотів з міста");
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
                "Статистика вартості польотів з обраного міста", style);
        createCell(row, 1, "", style);
        createCell(row, 2, "", style);
        createCell(row, 3, "", style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
        font.setFontHeightInPoints((short) 15);

        row = sheet.createRow(1);
        createCell(row, 0, "Маршрут", style);
        createCell(row, 1, "Вартість квитків, дол", style);
        createCell(row, 2, "", style);
        createCell(row, 3, "", style);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 3));

        row = sheet.createRow(2);
        createCell(row, 0, "", style);
        createCell(row, 1, "Мінімальна", style);
        createCell(row, 2, "Середня", style);
        createCell(row, 3, "Максимальна", style);
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

        CellStyle doubleCellStyle = workbook.createCellStyle();
        doubleCellStyle.cloneStyleFrom(style);
        DataFormat doubleDataFormat = workbook.createDataFormat();
        doubleCellStyle.setDataFormat(doubleDataFormat.getFormat("0.0"));

        for (Report2Data reportRouteData : dataList){
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, reportRouteData.getRouteName(), style);
            createCell(row, columnCount++, reportRouteData.getMinTicketPrice(), style);
            createCell(row, columnCount++, reportRouteData.getAverageTicketPrice(), doubleCellStyle);
            createCell(row, columnCount++, reportRouteData.getMaxTicketPrice(), style);
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
