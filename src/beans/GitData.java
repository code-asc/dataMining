package beans;

import java.util.ArrayList;
import java.util.List;

public class GitData {
	
	
	private String commitID;
	private String author;
	private String date;
	private String ticketNumber;
	private String insertions;
	private String deletions;
	private String fileChanges;
	private List<String> changedFileNames;
	


	public GitData(String commitID, String author, String date, String ticketNumber, String insertions, String deletions,
			String fileChanges, ArrayList<String> changedFileNames) {
		super();
		this.commitID = commitID;
		this.author = author;
		this.date = date;
		this.ticketNumber = ticketNumber;
		this.insertions = insertions;
		this.deletions = deletions;
		this.fileChanges = fileChanges;
		this.changedFileNames = changedFileNames;
	}
	
	public GitData() {
		super();
		this.commitID = null;
		this.author = null;
		this.date = null;
		this.ticketNumber = null;
		this.insertions = null;
		this.deletions = null;
		this.fileChanges = null;
		this.changedFileNames = new ArrayList<String>();
	}
	
	public String getCommitID() {
		return commitID;
	}
	
	
	public void setCommitID(String commitID) {
		this.commitID = commitID;
	}
	
	
	public String getAuthor() {
		return author;
	}
	
	
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDate() {
		return date;
	}
	
	
	public void setDate(String date) {
		this.date = date;
	}
	
	
	public String getTicketNumber() {
		return ticketNumber;
	}
	
	
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	
	
	public String getInsertions() {
		return insertions;
	}
	
	
	public void setInsertions(String insertions) {
		this.insertions = insertions;
	}
	
	
	public String getDeletions() {
		return deletions;
	}
	
	
	public void setDeletions(String deletions) {
		this.deletions = deletions;
	}
	
	
	public String getFileChanges() {
		return fileChanges;
	}
	
	
	public void setFileChanges(String fileChanges) {
		this.fileChanges = fileChanges;
	}
	
	
	public List<String> getChangedFileNames() {
		return changedFileNames;
	}

	public void setChangedFileNames(List<String> changedFileNames) {
		this.changedFileNames = changedFileNames;
	}
	
	
}
