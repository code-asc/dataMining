package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import beans.GitData;
import exceptions.NoDataException;
import tools.GitLog;
import tools.JiraCall;

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
	
	public static final String HADOOP_LINK = "https://issues.apache.org/jira/si/jira.issueviews:issue-xml/";
	
	public static void main(String[] args) {
		
		final GitLog logData = GitLog.getInstance();
		final List<String> filesNotFound = new ArrayList<String>();
		
		try {
			
			List<GitData> gitDataObj = logData.extractLog("src/gitLogFile/log.log");
		
			for(GitData data : gitDataObj) {
				
				String jiraTicketNumber = data.getTicketNumber();
				String xmlLink = Controller.HADOOP_LINK + jiraTicketNumber +
								"/" + jiraTicketNumber + ".xml";
				
				
				JiraCall jira = JiraCall.getInstance();
				
				try {
					
					jira.readWebXML(xmlLink);
					
				} catch(FileNotFoundException e) {
					
					filesNotFound.add(jiraTicketNumber);
				}
				
				
				System.out.println(jiraTicketNumber);
			}
				
			
		} catch (NoDataException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		} catch (ParserConfigurationException e) {
			
			e.printStackTrace();
			
		} catch (SAXException e) {
			
			e.printStackTrace();
		}
	}

}
