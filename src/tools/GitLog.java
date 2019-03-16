package tools;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import beans.GitData;
import exceptions.NoDataException;
import exceptions.NoStringDataException;

/**
 * @author sandeepchowdaryannabathuni
 * This class is used to extract the required process metrices 
 * from the GIT log file.
 */
public class GitLog {
	
	private static GitLog obj = null;
	
	// Regular expression to find the individual logs.
	private final String REGEX = "(\\s|\\n|\\r)*(commit)\\s+\\w*(\\r|\\n)+.*(\\r|\\n).*(\\r|\\n)+.*([\\na-zA-Z\\s0-9()\\r*.:/]*)?(\\s*\\W*[a-zA-Z0-9/.|\\s+-_{}]*).*(\\r|\\n)*";
	
	
	/**
	 * This is a private constructor.
	 */
	private GitLog() {
		
	}
	
	/**
	 * It is the factory pattern to create the GitLog object
	 * @return GitLog 
	 * for the GitLog class.
	 */
	public static GitLog getInstance() {
		
		if(obj == null) {
			obj = new GitLog();
		}
		
		return obj;
	}
	
	/**
	 * Before calling this method, we need to create a GIT log
	 * file by using certain parameters.
	 * Example:
	 * <code>
	 * git log --stat fileName.log
	 * </code>
	 * output should look like: 
	 * 
	 * 
	 * commit xxxxxxxxxxxxxxxx
	 * Author: Mr XYZ <XYZ@test.org>
	 * Date:   Thu Jul 19 12:03:24 2018 -0700

   	 * MAPREDUCE-7118. Distributed cache conflicts breaks backwards compatability. (Jason Lowe via wangda)
   
   	 * Change-Id: xxxxxxxxxxxxxxxx

	 * .../path/fileNme.java      |  8 +++-----
	 * .../path/fileName.java  | 20 ++++++++++++++++++--
	 * x files changed, y insertions(+), z deletions(-)
	 * 
	 * 
	 * @param path The path for the GIT log file.
	 * @return List<String>
	 * @throws NoDataException
	 * @throws IOException
	 * @throws NoStringDataException 
	 */
	@SuppressWarnings("unused")
	public List<GitData> extractLog(String path) throws NoDataException, IOException, NoStringDataException{
		
		final List<String> data = new ArrayList<String>();
		final List<GitData> gitData = new ArrayList<GitData>();
		
		final FileReader fr = new FileReader(path);
		Scanner sc = new Scanner(fr);
		
		String storeData = "";
		
		
		while(sc.hasNext()) {
			storeData = storeData + sc.nextLine() + "\n";
			
		}
		
		Pattern pattern = Pattern.compile(REGEX);
		Matcher matcher = pattern.matcher(storeData);
		
		
		while(matcher.find()) {
			
			data.add(matcher.group());
		}
		
		sc.close();
		fr.close();
		
		if(data.size() < 1)
			throw new NoDataException("No data found "
					+ "while extracting from GIT log file in GitLog.java : extractLog");
		
		
		for(String dataStr : data) {
			GitData obj = new GitData();
			
			obj.setCommitID(getCommitID(dataStr));
			obj.setAuthor(getAuthor(dataStr));
			obj.setDate(getDate(dataStr));
			obj.setTicketNumber(getTicketNumber(dataStr));
			obj.setInsertions(getInsertions(dataStr));
			obj.setDeletions(getDeletions(dataStr));
			obj.setFileChanges(getNumberOfFilesChanged(dataStr));
			obj.setChangedFileNames(getChangedFileNamesAndThresold(dataStr));
			
			gitData.add(obj);
		}
		
		return gitData;
	}
	
	
	/**
	 * @param data This is the GIT log.
	 * @return String The commit id.
	 */
	private String getCommitID(String data) {
		final String regex = "(commit)\\s*\\w*";
		String id = "";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(data);
		
		while(matcher.find())
			id = matcher.group();
		return id.replace("commit", "").strip();
	}
	
	
	/**
	 * @param data This is the GIT log.
	 * @return String The author of the commit
	 */
	private String getAuthor(String data) {
		final String regex = "(Author:).*";
		String author = "";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(data);
		
		while(matcher.find())
			author = matcher.group();
		return author.replace("Author", "").strip();
	}
	
	
	/**
	 * @param data This is the GIT log.
	 * @return String The commit date.
	 */
	private String getDate(String data) {
		final String regex = "(Date:).*";
		String date = "";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(data);
		
		while(matcher.find())
			date = matcher.group();
		return date.replace("Date", "").strip();
	}
	
	
	/**
	 * @param data This is the GIT log.
	 * @return The Ticket id of the commit.
	 */
	private String getTicketNumber(String data) {
		final String regex = "\\b[a-zA-Z]+-[0-9]+\\b";
		String ticketNumber = "";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(data);
		
		while(matcher.find())
			ticketNumber = matcher.group();
		
		return ticketNumber.strip();
	}
	
	
	/**
	 * @param data This is the GIT log.
	 * @return String The total number of insertions for the commit.
	 */
	private String getInsertions(String data) {
		final String regex = "[0-9]*\\s+insertion(s)?\\(\\+\\)";
		String insertions = "";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(data);
		
		while(matcher.find())
			insertions = matcher.group();
		
		insertions = insertions.replace("insertions(+)", "");
		insertions = insertions.replace("insertion(+)", "");
		return insertions.strip();
		
	}
	
	
	/**
	 * @param data This is the GIT log.
	 * @return String The total number of deletions for a commit.
	 */
	private String getDeletions(String data) {
		final String regex = "[0-9]*\\s+deletions(s)?\\(\\-\\)";
		String deletions = "";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(data);
		
		while(matcher.find())
			deletions = matcher.group();
		
		deletions = deletions.replace("deletions(-)", "");
		deletions = deletions.replace("deletion(-)", "");
		return deletions.strip();
		
	}
	
	
	/**
	 * @param data This is the GIT log.
	 * @return String Number of changes in files for a particular commit.
	 */
	private String getNumberOfFilesChanged(String data) {
		final String regex = "(\\s)+[0-9]*(\\s)file(s)?(\\s)changed";
		String changedCount = "";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(data);
		
		while(matcher.find())
			changedCount = matcher.group();
		
		changedCount = changedCount.replace("files changed", "");
		changedCount = changedCount.replace("file changed", "");
		
		return changedCount.strip();
	}
	
	
	/**
	 * @param data This is the GIT log.
	 * @return List<String> The file name that is changed followed by number of lines.
	 * @throws NoStringDataException
	 */
	private List<String> getChangedFileNamesAndThresold(String data) throws NoStringDataException {
		final String regex = "(\\s)\\.*/[a-zA-Z0-9/.]*(\\s)*|[0-9]*";
		List<String> fileData = new ArrayList<String>();
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(data);
		
		while(matcher.find()) {
			String temp = matcher.group();
			String[] fileContent = temp.split("|");
			String fileName = fileContent[0].strip();
			String changes = fileContent[1].strip();
			
			if(fileName.isBlank() || fileName.isEmpty() || 
					changes.isBlank() || changes.isEmpty())
				throw new NoStringDataException("Exception found in GitLog.java : getChangedFileNamesAndThresold");
				
			fileData.add(fileName.concat(" " + changes));
			
		}
			
		
		return fileData;
		
	}
	
	
//	public static void main(String[] args) throws NoDataException, IOException {
//		GitLog logs = GitLog.getInstance();
//		logs.extractLog("src/gitlog.log");
//		
//	}
}
