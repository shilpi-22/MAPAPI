import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import pojo.NewsResponse;

import static io.restassured.RestAssured.*;


public class NewsAPI {
	String response = "";
	@Test
	public void getNews() {
		RestAssured.baseURI = "https://chroniclingamerica.loc.gov";
		response = given().queryParam("terms", "oakland").queryParam("format", "json").queryParam("page", 5)
		.when().get("/search/titles/results")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
	}
	
	//print getTotalItems, endIndex, startIndex, itemsPerPage
	@Test 
	public void validateNewsResponse() {
		RestAssured.baseURI = "https://chroniclingamerica.loc.gov";
		NewsResponse rs = given().queryParam("terms", "oakland").queryParam("format", "json").queryParam("page", 5)
		.when().get("/search/titles/results")
		.then().log().all().assertThat().statusCode(200).extract().response().as(NewsResponse.class);
		int getTotalItems = rs.getTotalItems();
		int endIndex = rs.getEndIndex();
		int startIndex = rs.getStartIndex();
		int itemsPerPage = rs.getItemsPerPage();
		System.out.println(getTotalItems);
		System.out.println(endIndex);
		System.out.println(startIndex);
		System.out.println(itemsPerPage);
		
	}
	
	//print title of second array
		@Test 
		public void validateTitleOfSecondItems() {
			RestAssured.baseURI = "https://chroniclingamerica.loc.gov";
			NewsResponse rs = given().queryParam("terms", "oakland").queryParam("format", "json").queryParam("page", 5)
			.when().get("/search/titles/results")
			.then().log().all().assertThat().statusCode(200).extract().response().as(NewsResponse.class);
		
			int getItems = rs.getItems().size();
			for(int i=0;i<getItems;i++) {
				if(i==2) {
				System.out.println(rs.getItems().get(i).getTitle());
				break;
				}
			}
		}

		//print subject of  last array
				@Test 
				public void validateSubjectOfLastItems() {
					RestAssured.baseURI = "https://chroniclingamerica.loc.gov";
					NewsResponse rs = given().queryParam("terms", "oakland").queryParam("format", "json").queryParam("page", 5)
					.when().get("/search/titles/results")
					.then().log().all().assertThat().statusCode(200).extract().response().as(NewsResponse.class);
				
					int getItems = rs.getItems().size();
					for(int i=0;i<getItems;i++) {
						if(i==getItems-1) {
						int count = rs.getItems().get(i).getSubject().length;
						String[] k = rs.getItems().get(i).getSubject();
						for(int j=0;j<count;j++) {
							System.out.println(k[j]);
						}
						break;
						}
					}
				}


}
