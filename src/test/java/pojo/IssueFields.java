package pojo;

public class IssueFields {
	private issuetype issuetype;
	private project project;
	private String summary;
	private String description;
	
	
	public project getProject() {
		return project;
	}
	public void setProject(project project) {
		this.project = project;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public issuetype getIssueType() {
		return issuetype;
	}
	public void setIssueType(issuetype issuetype) {
		this.issuetype = issuetype;
	}
	
	
}
