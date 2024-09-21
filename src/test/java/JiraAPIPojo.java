import io.restassured.RestAssured;
import pojo.IssueFields;
import pojo.IssuePayload;
import pojo.issuetype;
import pojo.project;

import static io.restassured.RestAssured.*;

public class JiraAPIPojo {
	
	public static void main(String[] args) {
		String token = "Basic c2hpbHBpZWt0YTIwMjFAZ21haWwuY29tOkFUQVRUM3hGZkdGMHUyYmZmeTlKR0prRklyeGZuM1poMFhYb2taX3AyckR5RGs4aXJ3YzRJUFBEQ3gwbEl3cnZSS1hpNlpwaGVZMFhMVFBhT3B5NnFMZkVWOTYwMU1uXy1FUUdFTG8xcUR6SmllTWxTTGk4N29neEtGYUpYMGU3VjBtdktPdGQ5eHNtTlQ5QlQxRURFOHQtUUFrUDVwY0JzYkdOejFRVTZvdkxDTzBNSmpGbEx4cz0xMjdFMzI3Ng==\r\n"
				+ "";
	
		RestAssured.baseURI = "https://shilpiekta2021.atlassian.net";
		// Create an instance of Issue and set values
		IssuePayload ipayload = new IssuePayload();
		
		IssueFields ifields = new IssueFields();
		ifields.setDescription("Close button not working on the landing page");
		ifields.setSummary("Close button not working");
		
		issuetype itype = new issuetype();
		itype.setName("Bug");
		
		project p = new project();
		p.setKey("SCRUM");
		
		ifields.setIssueType(itype);
		ifields.setProject(p);
		
		ipayload.setFields(ifields);
		System.out.println("PAYLOAD " + ipayload);
		given().log().all().header("Authorization", token).contentType("application/json").body(ipayload)
		.when().post("/rest/api/2/issue")
		.then().log().all().assertThat().statusCode(201);
		
	}
}
