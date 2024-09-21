import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;

public class JiraAPI {
	
	String token = "c2hpbHBpZWt0YTIwMjFAZ21haWwuY29tOkFUQVRUM3hGZkdGMHUyYmZmeTlKR0prRklyeGZuM1poMFhYb2taX3AyckR5RGs4aXJ3YzRJUFBEQ3gwbEl3cnZSS1hpNlpwaGVZMFhMVFBhT3B5NnFMZkVWOTYwMU1uXy1FUUdFTG8xcUR6SmllTWxTTGk4N29neEtGYUpYMGU3VjBtdktPdGQ5eHNtTlQ5QlQxRURFOHQtUUFrUDVwY0JzYkdOejFRVTZvdkxDTzBNSmpGbEx4cz0xMjdFMzI3Ng==";
	String issue_id = "";
	
	//To create an issue in Jira
	@Test
	public void createIssue() {
		RestAssured.baseURI = "https://shilpiekta2021.atlassian.net";
        String response = given().log().all().header("Authorization", "Basic " + token).accept("application/json").contentType("application/json")
        .body("{\r\n"
        		+ "    \"fields\": {\r\n"
        		+ "       \"project\":\r\n"
        		+ "       {\r\n"
        		+ "          \"key\": \"SCRUM\"\r\n"
        		+ "       },\r\n"
        		+ "       \"summary\": \"Verify Button is not working\",\r\n"
        		+ "       \"description\": \"Creating of an issue using project keys and issue type names using the REST API\",\r\n"
        		+ "       \"issuetype\": {\r\n"
        		+ "          \"name\": \"Bug\"\r\n"
        		+ "       }\r\n"
        		+ "   }\r\n"
        		+ "}\r\n"
        		+ "")
        .when().post("/rest/api/2/issue")
        .then().log().all().assertThat().statusCode(201).extract().response().asString();
		
        JsonPath js = new JsonPath(response);
        issue_id = js.getString("id");
        
	}
	
	//To get the details of issue in Jira
	@Test
	public void getIssueDetails() {
		RestAssured.baseURI = "https://shilpiekta2021.atlassian.net";
		given().header("Authorization", "Basic " +token).accept("application/json").contentType("application/json")
		.when().pathParam("issue_id", issue_id).get("/rest/api/2/issue/{issue_id}")
		.then().log().all().assertThat().statusCode(200).body("id", equalTo(issue_id));
	}

	//To add an attachment in Jira using multiPart function to pass file
	@Test
	public void addAttachment() {
		RestAssured.baseURI = "https://shilpiekta2021.atlassian.net";
		given().header("Authorization", "Basic " +token).header("X-Atlassian-Token", "no-check").contentType("multipart/form-data")
		.accept("*/*").pathParam("issue_id", issue_id)
		.multiPart("file", new File("C:\\Users\\User\\Postman\\files\\abc.txt")).log().all()
		.when().post("/rest/api/2/issue/{issue_id}/attachments")
		.then().log().all().assertThat().statusCode(200).body("id", equalTo(issue_id)).body("filename", equalTo("abc.txt"));
	}
	
	
	
}
