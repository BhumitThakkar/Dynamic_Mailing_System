package service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelService {
    public static List<List<String>> ExcelReader(String excelFilePath,List<List<String>> listRow) throws IOException, InvalidFormatException {
        final String XLSX_FILE_PATH = excelFilePath;

            // Creating a Workbook from an Excel file (.xls or .xlsx)
            Workbook workbook = WorkbookFactory.create(new File(XLSX_FILE_PATH));

            // Retrieving the number of sheets in the Workbook
            System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

            /*
               =============================================================
               Iterating over all the sheets in the workbook
               =============================================================
            */

            // 1.Or you can use a for-each loop
            System.out.println("Retrieving Sheets using for-each loop");
            for(Sheet sheet: workbook) {
            	System.out.println("=> " + sheet.getSheetName());
            }
            
            /*
	            ==================================================================
	            Iterating over all the rows and columns in a Sheet (Multiple ways)
	            ==================================================================
	        */
	
	        // Getting the Sheet at index zero
	        Sheet sheet = workbook.getSheetAt(0);
	        
	        // Create a DataFormatter to format and get each cell's value as String
	        DataFormatter dataFormatter = new DataFormatter();
	        
	        // 2. Or you can use a for-each loop to iterate over the rows and columns
	        System.out.println("\n\nIterating over Rows and Columns using for-each loop\n");
	        for (Row row: sheet) {
	        	List<String> listCell = new ArrayList<String>();
	            for(Cell cell: row) {
	            	String cellValue = dataFormatter.formatCellValue(cell);
	                listCell.add(cellValue);
	                System.out.print(cellValue + "\t");
	            }
	            listRow.add(listCell);
	            System.out.println();
	        }
	        
	        // Closing the workbook
	        workbook.close();
	        return listRow;

        }
}