package tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * This class is used to extract all the file name that
 * are available in the project that we use as a test case.
 * <b> The metrics path is mentioned in the config.properties file</b>
 * @author sandeepchowdaryannabathuni
 *
 */
public class ExtractMetrics {
	
	private static ExtractMetrics obj = null;
	
	public Map<String, Integer> fileNameWithRow = null;
	
	private ExtractMetrics() {
		fileNameWithRow = new HashMap<String, Integer>();
	}
	
	public static ExtractMetrics getInstance() {
		if(obj == null)
			obj = new ExtractMetrics();
		return obj;
	}
	
	
	
	
	/**
	 * @return List<String> The list of all the file names that are in the 
	 * experiment subject.
	 * @throws IOException
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 */
	public List<String> getAllFileNamesFromMetrix() throws IOException, EncryptedDocumentException, InvalidFormatException {
		
		List<String> files = new ArrayList<String>();
		
		// This is the metrics file from which we will
		// extract the required file names.
		String metricsFile = Config.getProperty("productmetrics");
		
		int col = Integer.parseInt(Config.getProperty("filenamecol"));
		int avoidRowHeading = Integer.parseInt(Config.getProperty("avoidrowheadings"));
		
		
		Workbook workbook = new XSSFWorkbook(new File(metricsFile));
		
		Sheet sheet = workbook.getSheetAt(0);
		
		Iterator<Row> rows = sheet.iterator();
		
		
		while(rows.hasNext()) {
			Row row = rows.next();
			//System.out.println(row.getRowNum());
			
			//System.out.println(row.getCell(1).getStringCellValue());
			if(row.getRowNum() != avoidRowHeading) {
				fileNameWithRow.put(row.getCell(col).getStringCellValue().strip(), row.getRowNum());
				files.add(row.getCell(col).getStringCellValue().strip());
			}
		}
		
		return files;
	}
	
	
	/**
	 * @return Map<String, Integer> This function is used to get all the fileNames from the 
	 * excel sheet along with their row number
	 * @throws IOException
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 */
	public Map<String, Integer> getFileNamesWithRowNumber() throws IOException, EncryptedDocumentException, InvalidFormatException {
		
		Map<String, Integer> filesAndRownum = new HashMap<String, Integer>();
		
		// This is the metrics file from which we will
		// extract the required file names.
		String metricsFile = Config.getProperty("productmetrics");
		
		int avoidRowHeading = Integer.parseInt(Config.getProperty("avoidrowheadings"));
		
		int col = Integer.parseInt(Config.getProperty("filenamecol"));
		
		
		Workbook workbook = new XSSFWorkbook(new File(metricsFile));
		
		Sheet sheet = workbook.getSheetAt(0);
		
		Iterator<Row> rows = sheet.iterator();
		
		
		while(rows.hasNext()) {
			Row row = rows.next();
			//System.out.println(row.getRowNum());
			
			//System.out.println(row.getCell(1).getStringCellValue());
			if(row.getRowNum() != avoidRowHeading)
				filesAndRownum.put(row.getCell(col).getStringCellValue().strip(), row.getRowNum());
		}
		
		return filesAndRownum;
	}
	
	
//	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException {
//		
//		ExtractMetrics files = ExtractMetrics.getInstance();
//		files.getAllFileNamesFromMetrix();
//		files.hasColumnName();
//	}
	
	
}
