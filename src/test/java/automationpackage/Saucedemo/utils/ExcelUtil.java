package automationpackage.Saucedemo.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

	public static Object[][] getTestData(String resourcePath, String sheetName) {

	    List<Object[]> data = new ArrayList<>();

	    try (InputStream is = ExcelUtil.class
	            .getClassLoader()
	            .getResourceAsStream(resourcePath)) {

	        if (is == null) {
	            throw new RuntimeException("❌ Excel NOT found: " + resourcePath);
	        }

	        Workbook workbook = new XSSFWorkbook(is);
	        Sheet sheet = workbook.getSheet(sheetName.trim());

	        if (sheet == null) {
	            throw new RuntimeException(
	                "❌ Sheet not found: '" + sheetName + "'"
	            );
	        }

	        int rows = sheet.getPhysicalNumberOfRows();
	        int cols = sheet.getRow(0).getPhysicalNumberOfCells();

	        for (int i = 1; i < rows; i++) {
	            Row row = sheet.getRow(i);
	            Object[] rowData = new Object[cols];

	            for (int j = 0; j < cols; j++) {
	                Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

	                switch (cell.getCellType()) {
	                    case STRING:
	                        rowData[j] = cell.getStringCellValue().trim();
	                        break;
	                    case NUMERIC:
	                        rowData[j] = String.valueOf((long) cell.getNumericCellValue());
	                        break;
	                    case BOOLEAN:
	                        rowData[j] = String.valueOf(cell.getBooleanCellValue());
	                        break;
	                    default:
	                        rowData[j] = "";
	                }
	            }
	            data.add(rowData);
	        }

	        workbook.close();

	    } catch (Exception e) {
	        throw new RuntimeException("Failed to read Excel data", e);
	    }

	    return data.toArray(new Object[0][]);
	}

}
