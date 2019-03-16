package controller;

import exceptions.NoStringDataException;
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
	
	
	public static void main(String[] args) throws NoStringDataException {
		
		PreReleaseBugs preReleaseBugs = PreReleaseBugs.getInstance();
		preReleaseBugs.copyPreReleaseBugsToExcel("");
	}

	
}
