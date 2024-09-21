import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import testdata.payloadData;

public class staticJson {
	
	String token = "A21AAIWfe4annWQXlH_0BrwK2wwq0m6GErPJ42_6HoG65Xkw1c1AON03G4X7HJmqK8Has_HMLdm5yzroGrZZr5kTxrT6LSbgA";
	String response = "";
	String order_id = "";
	@Test
	public void createOrderThroughStaticJson() throws IOException {
		RestAssured.baseURI="https://api-m.sandbox.paypal.com";
		 //To create an order 
		  Response responseForOrderCreation = given().log().all()
		  .header("Authorization", "Bearer " + token) .accept(ContentType.JSON)
		  .contentType(ContentType.JSON) .body(generateStringFromResource("C:\\Users\\User\\eclipse-workspace\\MapAPI\\src\\test\\java\\testdata\\AddOrderDetails.json"))
		  .when().post("/v2/checkout/orders")
		  .then().log().all().assertThat().statusCode(201) .body("status",
		  equalTo("CREATED")) .extract().response();
		  System.out.println("Response for Order Creation: " + responseForOrderCreation.asString());
	}
	
	
	public static String generateStringFromResource(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));
	}
}
