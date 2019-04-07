package tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import exceptions.ColumnAlreadyExistsException;
import exceptions.NoStringDataException;

/**
 * This class is used to write the data to the excel sheet.
 * 
 * @author sandeepchowdaryannabathuni
 *
 */
public class ExcelAppender {
	
	private static ExcelAppender ea = null;
	
	
	private Map<String, Integer> trackRowByFileName = null;
	
	private ExcelAppender() {
		
	}
	
	public static ExcelAppender getInstance() {
		if(ea == null)
			ea = new ExcelAppender();
		
		return ea;
	}
	
	
	
	/**
	 * @param colName The column name we want to find in the excel
	 * @return true if the column name is found in the excel, otherwise false
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	private boolean hasColumnName(String colName) throws InvalidFormatException, IOException {
		
		List<String> colNames = new ArrayList<String>();
		
		// This is the metrics file from which we will
		// extract the required column names.
		String metricsFile = Config.getProperty("productmetrics");
		
		Workbook workbook = new XSSFWorkbook(new File(metricsFile));
		
		Sheet sheet = workbook.getSheetAt(0);
		
		Row row = sheet.getRow(1);
		
		int totalCol = row.getLastCellNum();
		
		for(int i = 0; i < totalCol; i++) {
			colNames.add(row.getCell(i).getStringCellValue().toLowerCase());
		}
		
		return colNames.contains(colName.toLowerCase());
	}
	
	
//	private void fillEmptyColWithZero(String fileName, int maxRow, int col) throws IOException, EncryptedDocumentException, InvalidFormatException {
//		
//		String metricsFile = Config.getProperty("productmetrics");
//		String finalMetricsFiles = Config.getProperty("includeprereleaseproductmetrics");
//		int avoidRowHeading = Integer.parseInt(Config.getProperty("avoidrowheadings"));
//		InputStream inp = new FileInputStream(metricsFile); 
//		Workbook wb = new XSSFWorkbook(inp); //Access the workbook
//        
//        Sheet worksheet = wb.getSheetAt(0);
//        Cell cell = null; // declare a Cell object
//        
////        for(int i = 2; i < maxRow; i++) {
////        	cell = worksheet.getRow(i).getCell(col);   // Access the second cell in second row to update the value
////            
////            cell.setCellValue("OverRide Last Name");
////        }
//        
//        Iterator<Row> rows = worksheet.iterator();
//    	while(rows.hasNext()) {
//			Row row = rows.next();
//			System.out.println(row.getRowNum());
//			
//			
//			//System.out.println(row.getCell(1).getStringCellValue());
//			if(row.getRowNum() != avoidRowHeading) {
//				cell = row.getCell(col);
//				cell.setCellValue("OverRide Last Name");
//				
//			}
//			
//		}
//          
//        inp.close(); //Close the InputStream
//         
//        FileOutputStream output_file =new FileOutputStream(new File(metricsFile));  //Open FileOutputStream to write updates
//          
//        wb.write(output_file); //write changes
//          
//        output_file.close();
//	}
	
	private void addDataHelper(Map<String, Integer> data, 
							   Map<String, Integer> fileRowNum,
							   String newColName) throws IOException, InvalidFormatException {
		
		
		String metricsFile = Config.getProperty("productmetrics");
		String finalMetricsFiles = Config.getProperty("includeprereleaseproductmetrics");
		
		InputStream inp = new FileInputStream(metricsFile); 
		Workbook workbook = WorkbookFactory.create(inp);
		
		Sheet sheet = workbook.getSheetAt(0);
		
		int maxRows = sheet.getLastRowNum();
		
		int lastColumnBefore = sheet.getRow(1).getLastCellNum();
		
		Cell addColumnName = sheet.getRow(1).createCell(lastColumnBefore);
		addColumnName.setCellValue(newColName);
		
		 
		
		
		for(String file : data.keySet()) {
			//System.out.println(file); XceiverClient.java
			
			
			int rowNum = fileRowNum.getOrDefault(file, -1);
			
			if(rowNum >= 1)
				sheet.getRow(rowNum).createCell(lastColumnBefore).setCellValue(data.get(file));
		}
		
		FileOutputStream fileOut = new FileOutputStream(finalMetricsFiles); 
	    workbook.write(fileOut); 
	    fileOut.close();
	    
	   // fillEmptyColWithZero(newColName, maxRows, lastColumnBefore);
	}
	
	
	/**
	 * This method is used to create a new file which contains the prerelease bug data.
	 * @param fileChurn
	 * @param fileRowNum
	 * @param newColName
	 * @throws ColumnAlreadyExistsException
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public void addData(Map<String, Integer> fileChurn, 
						Map<String, Integer> fileRowNum, 
						String newColName) throws ColumnAlreadyExistsException, InvalidFormatException, IOException {
		
		System.out.println("Appending data to the excel.");
		if(hasColumnName(newColName)) 
			throw new ColumnAlreadyExistsException("Error in adding a data to"
					+ "the excel addData():ExeclAppender");
		
		
		addDataHelper(fileChurn, fileRowNum, newColName);
		
		System.out.println("Data appended");
		// TODO write data to the sheet
	}
	
	
	
	
//	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException, ColumnAlreadyExistsException, NoStringDataException {
//		
//		PreReleaseBugs obj = PreReleaseBugs.getInstance();
//		Map<String, Integer> map = obj.churnsForEachFile();
//		
//		ExcelAppender ea = ExcelAppender.getInstance();
//		
//		//ea.activateFileNameAndRow();
//		ea.addData(map, obj.getMetricsData().fileNameWithRow, "preRelease");
//	}
}
