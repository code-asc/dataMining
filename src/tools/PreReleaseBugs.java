package tools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.xml.sax.SAXException;

import beans.GitData;
import beans.JiraData;
import exceptions.NoDataException;
import exceptions.NoStringDataException;

/**
 * This class to used to get the stats of the prerelease bugs by using the
 * prerelease.log file.
 * @author sandeepchowdaryannabathuni
 *
 */
public class PreReleaseBugs {

	private static PreReleaseBugs obj = null;
	
	private static String HADOOP_LINK;
	private static String PRE_RELEASE_LOG;
	private ExtractMetrics projectFiles;
	
	


	static {

		try {
			HADOOP_LINK = Config.getProperty("hadooprestbase");
			PRE_RELEASE_LOG =  Config.getProperty("prerelease");
		} catch (IOException e) {
			
			System.out.println("Error which assiging static values in " +
			"PreReleaseBugs.java : static block");
		}
		
	}
	
	private PreReleaseBugs() {
		projectFiles = ExtractMetrics.getInstance();
	}
	
	
	public static PreReleaseBugs getInstance() throws IOException {
		if(obj == null)
			obj = new PreReleaseBugs();
		
		return obj;
	}
	
	public ExtractMetrics getMetricsData() {
		return projectFiles;
	}
	
	
	/**
	 * This method is used to gather all the required pre relsease churns.
	 * @throws NoStringDataException
	 */
	public Map<String, Integer> churnsForEachFile() throws NoStringDataException {
		
		
		final GitLog logData = GitLog.getInstance();
		final List<String> filesNotFound = new ArrayList<String>();
		final Map<String, Integer> churnData = new HashMap<String, Integer>();
		
		
		try {
			
			
			List<String> allFilesNamesInMetrix = projectFiles.getAllFileNamesFromMetrix();
			Set<String> map = new HashSet<String>();
			map.addAll(allFilesNamesInMetrix);
			//System.out.println("Total files: " + allFilesNamesInMetrix.size());
			
			for(String fileName : allFilesNamesInMetrix) {
				churnData.put(fileName, 0);
			}
			//System.out.println(churnData.size());
			//System.exit(0);
			
			System.out.println("Total : " + allFilesNamesInMetrix.size());
			System.out.println("Set : " + map.size());
			System.out.println("Before churn data : " + churnData.size());
			
			List<GitData> gitDataObj = logData.extractLog(PRE_RELEASE_LOG);
		
			for(GitData data : gitDataObj) {
				
				//System.out.print("***********************************************");
				//System.out.println();
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
						
						//System.out.println("Author: " + data.getAuthor());
						//System.out.println("Commit ID: " + data.getCommitID());
						//System.out.println("Deletions: " + data.getDeletions());
						//System.out.println("Insertions: " + data.getInsertions());
						//System.out.println("Total_churn: " + (data.getInsertions() +
								//data.getDeletions()));
						
						for(String str : data.getChangedFileNames()) {
							//System.out.println("File : " + str);
							String[] arrayData = str.split(" ");
							String file = arrayData[0].strip();
							int changes = Integer.parseInt(arrayData[1].strip());
							//System.out.println(file + " --> " + changes);
							
							
							
							if(file!= null && changes > 0) {
								
								churnData.put(file, churnData.getOrDefault(file, 0) + changes);
							}
					
						}
							
						
//						for(String str : churnData.keySet()) {
//							if(churnData.get(str) > 0)
//								System.out.println(str + " : " + churnData.get(str));
//						}
//						
						//TODO implement the pre-release and post release bugs.
						
						//TODO add data to excel sheet.

					}
					
								
					
				} catch(FileNotFoundException e) {
					
					filesNotFound.add(jiraTicketNumber);
				}
				
				
				//System.out.println(jiraTicketNumber);
			}
				
			
		} catch (NoDataException|IOException|ParserConfigurationException|EncryptedDocumentException|InvalidFormatException|SAXException e) {
			
			System.out.println("Error " + e.getCause() +
					" PreReleaseBugs.java : churnsForEachFile");
			
		}
		System.out.println("Churn: " + churnData.size());
		return churnData;
	}
}
