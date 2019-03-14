package tools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import beans.GitData;
import beans.JiraData;
import exceptions.NoDataException;

/**
 * This class to used to get the stats of the prerelease bugs by using the
 * prerelease.log file.
 * @author sandeepchowdaryannabathuni
 *
 */
public class PreReleaseBugs {

	private static PreReleaseBugs obj = null;
	private static final String HADOOP_LINK = "https://issues.apache.org/jira/si/jira.issueviews:issue-xml/";
	
	private PreReleaseBugs() { }
	
	public static PreReleaseBugs getInstance() {
		if(obj == null)
			obj = new PreReleaseBugs();
		
		return obj;
	}
	
	
	public void copyPreReleaseBugsToExcel(String filePath) {
		final GitLog logData = GitLog.getInstance();
		final List<String> filesNotFound = new ArrayList<String>();
		
		try {
			
			List<GitData> gitDataObj = logData.extractLog("src/gitLogFile/prerelease.log");
		
			for(GitData data : gitDataObj) {
				
				System.out.print("***********************************************");
				System.out.println();
				String jiraTicketNumber = data.getTicketNumber();
				
				if(jiraTicketNumber.isBlank() || jiraTicketNumber.isEmpty()) 
					continue;
				
				String xmlLink = HADOOP_LINK + jiraTicketNumber +
								"/" + jiraTicketNumber + ".xml";
				
				
				JiraCall jira = JiraCall.getInstance();
				
				try {
					
					JiraData jiraData = jira.readWebXML(xmlLink);
					//System.out.println("Type :" + jiraData.getType());
					
					if(jiraData.getType().toUpperCase().contentEquals("BUG")) {
						
						System.out.println("Author: " + data.getAuthor());
						System.out.println("Commit ID: " + data.getCommitID());
						System.out.println("Deletions: " + data.getDeletions());
						System.out.println("Insertions: " + data.getInsertions());
						System.out.println("Total_churn: " + (data.getInsertions() +
								data.getDeletions()));
						
						
						//TODO implement the pre-release and post release bugs.
						
						//TODO add data to excel sheet.

					}
					
								
					
				} catch(FileNotFoundException e) {
					
					filesNotFound.add(jiraTicketNumber);
				}
				
				
				//System.out.println(jiraTicketNumber);
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
