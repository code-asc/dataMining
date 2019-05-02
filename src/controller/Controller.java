package controller;

import java.io.IOException;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import exceptions.ColumnAlreadyExistsException;
import exceptions.NoStringDataException;
import tools.Config;
import tools.ExcelAppender;
import tools.PostReleaseBugs;
import tools.PreReleaseBugs;
import tools.ReadAntiPattern;

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
		PostReleaseBugs postReleaseBugs = PostReleaseBugs.getInstance();
		ReadAntiPattern antiPattern = new ReadAntiPattern();
		
		Map<String, Integer> mapForPreRelease = preReleaseBugs.churnsForEachFile();
		Map<String, Integer> mapForPostRelease = postReleaseBugs.churnsForEachFile();
		Map<String, Map<String, Integer>> mapForAntipattern = antiPattern.generateData(Config.getProperty("antipattern"));
		
		System.exit(0);
		ExcelAppender ea = ExcelAppender.getInstance();
		
		String metricsFile = Config.getProperty("productmetrics");
		
		ea.addData(mapForPreRelease, preReleaseBugs.getMetricsData().fileNameWithRow, "preRelease", metricsFile);
		
		metricsFile = Config.getProperty("includeprereleaseproductmetrics");
		
		ea.addData(mapForPostRelease, postReleaseBugs.getMetricsData().fileNameWithRow, "postRelease", metricsFile);
		
		//System.out.println(map.size());
	}

	
}
