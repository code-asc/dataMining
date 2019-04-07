package controller;

import java.io.IOException;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import exceptions.ColumnAlreadyExistsException;
import exceptions.NoStringDataException;
import tools.ExcelAppender;
import tools.PreReleaseBugs;

/**
 * This is controller class. This class is responsible for link both beans 
 * and the tools. Here we extract all the necessary data from the log file in 
 * the gitLogFile folder. Later we pass the data to the JiraCall class to
 * extract the required information through web services.
 * 
 * @author sandeepchowdaryannabathuni
 *
 */
public class Controller {
	
	
	public static void main(String[] args) throws NoStringDataException, IOException, InvalidFormatException, ColumnAlreadyExistsException {
		
		PreReleaseBugs preReleaseBugs = PreReleaseBugs.getInstance();
		Map<String, Integer> map = preReleaseBugs.churnsForEachFile();
		ExcelAppender ea = ExcelAppender.getInstance();
		ea.addData(map, preReleaseBugs.getMetricsData().fileNameWithRow, "preRelease");
		
		System.out.println(map.size());
	}

	
}
