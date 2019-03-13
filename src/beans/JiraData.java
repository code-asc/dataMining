package beans;

/**
 * @author sandeepchowdaryannabathuni
 * This is a simple POJO class which will store all the required
 * data of the JIRA XML file.
 *
 */
public class JiraData {
	
	private String title;
	private String link;
	private String projectID;
	private String ticketNumber;
	private String resolution;
	private String createdDate;
	private String resolvedDate;
	private String version;
	private String fixVersion;
	private String assignee;
	private String reporter;
	private String type;
	private String priority;
	


	public JiraData(String title, String link, String projectID, String ticketNumber, String resolution, String createdDate,
			String resolvedDate, String version, String fixVersion, String assignee, String reporter,String type, String priority) {
		super();
		this.title = title;
		this.link = link;
		this.projectID = projectID;
		this.ticketNumber = ticketNumber;
		this.resolution = resolution;
		this.createdDate = createdDate;
		this.resolvedDate = resolvedDate;
		this.version = version;
		this.fixVersion = fixVersion;
		this.assignee = assignee;
		this.reporter = reporter;
		this.type = type;
		this.priority = priority;
	}

	public JiraData() {
		this.title = null;
		this.link = null;
		this.projectID = null;
		this.ticketNumber = null;
		this.resolution = null;
		this.createdDate = null;
		this.resolvedDate = null;
		this.version = null;
		this.fixVersion = null;
		this.assignee = null;
		this.reporter = null;
		this.type = null;
		this.priority = null;
	}

	
	public String getPriority() {
		return priority;
	}

	
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	
	public String getType() {
		return type;
	}

	
	public void setType(String type) {
		this.type = type;
	}
	
	
	public String getTitle() {
		return title;
	}
	
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public String getLink() {
		return link;
	}
	
	
	public void setLink(String link) {
		this.link = link;
	}
	
	
	public String getProjectID() {
		return projectID;
	}
	
	
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	
	
	public String getTicketNumber() {
		return ticketNumber;
	}
	
	
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	
	
	public String getResolution() {
		return resolution;
	}
	
	
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	
	
	public String getCreatedDate() {
		return createdDate;
	}
	
	
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	
	public String getResolvedDate() {
		return resolvedDate;
	}
	
	
	public void setResolvedDate(String resolvedDate) {
		this.resolvedDate = resolvedDate;
	}
	
	
	public String getVersion() {
		return version;
	}
	
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	
	public String getFixVersion() {
		return fixVersion;
	}
	
	
	public void setFixVersion(String fixVersion) {
		this.fixVersion = fixVersion;
	}
	
	
	public String getAssignee() {
		return assignee;
	}
	
	
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	
	
	public String getReporter() {
		return reporter;
	}
	
	
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	
	
	
}
