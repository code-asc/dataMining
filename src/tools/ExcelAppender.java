package tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import exceptions.ColumnAlreadyExistsException;

/**
 * This class is used to write the data to the excel sheet.
 * 
 * To use this class, after creating the instance, one should use the
 * method <code>activateFileNameAndRow()<code> so that the object get
 * all the sufficient info it need for appending.
 * 
 * @author sandeepchowdaryannabathuni
 *
 */
public class ExcelAppender {
	
	private static ExcelAppender ea = null;
	private ExtractMetrics em = null;
	
	private Map<String, Integer> trackRowByFileName = null;
	
	private ExcelAppender() {
		em = ExtractMetrics.getInstance();
	}
	
	public static ExcelAppender getInstance() {
		if(ea == null)
			ea = new ExcelAppender();
		
		return ea;
	}
	
	
	public void activateFileNameAndRow() throws EncryptedDocumentException, InvalidFormatException, IOException {
		trackRowByFileName = em.getFileNamesWithRowNumber();
		
		for(String str : trackRowByFileName.keySet())
			if(trackRowByFileName.get(str) == 2)
				System.out.println(str);
			//System.out.println(str + " " + trackRowByFileName.get(str));
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
	
	
	public void addData(Map<String, Integer> data, String newColName) throws ColumnAlreadyExistsException, InvalidFormatException, IOException {
		if(hasColumnName(newColName)) 
			throw new ColumnAlreadyExistsException("Error in adding a data to"
					+ "the excel addData():ExeclAppender");
		
		
		// TODO write data to the sheet
	}
	
	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException {
		ExcelAppender ea = ExcelAppender.getInstance();
		ea.activateFileNameAndRow();
	}
}
