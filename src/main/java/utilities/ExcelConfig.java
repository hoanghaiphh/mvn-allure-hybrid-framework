package utilities;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;

public class ExcelConfig {

    public static ExcelConfig getExcelData() {
        return new ExcelConfig();
    }

    public Map<String, String> getRowData(String fileName, String sheetName, int rowIndex) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("testData/" + fileName)) {
            if (inputStream == null) throw new RuntimeException("Không tìm thấy file: " + fileName);

            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheet(sheetName);
            Row row0 = sheet.getRow(0);
            if (row0 == null) throw new RuntimeException("Không tìm thấy hàng tiêu đề!");
            Row row = sheet.getRow(rowIndex);
            if (row == null) throw new RuntimeException("Không tìm thấy hàng: " + rowIndex);

            Map<String, String> rowData = new HashMap<>();
            for (int i = 0; i < row0.getLastCellNum(); i++) {
                Cell cellKey = row0.getCell(i);
                Cell cellValue = row.getCell(i);
                if (cellKey != null && cellValue != null)
                    rowData.put(cellKey.getStringCellValue(), getCellDataAsString(workbook, cellValue));
            }
            return rowData;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getCellDataAsString(Workbook workbook, Cell cell) {
        if (cell.getCellType() == CellType.FORMULA) {
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            CellValue cellValue = evaluator.evaluate(cell);

            return switch (cellValue.getCellType()) {
                case STRING -> cellValue.getStringValue();
                case NUMERIC -> String.valueOf(cellValue.getNumberValue());
                case BOOLEAN -> String.valueOf(cellValue.getBooleanValue());
                default -> "";
            };
        } else {
            DataFormatter dataFormatter = new DataFormatter();
            return dataFormatter.formatCellValue(cell);
        }
    }

}
