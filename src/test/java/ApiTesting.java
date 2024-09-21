import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import testdata.payloadData;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

public class ApiTesting {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//given - all input details - Headers, Body
		//when - submit the API - request, path parameter
		// then - validate the response
		
		//The base URI that's used by REST assured when making requests if a non-fully qualified URI is used in the request.
		RestAssured.baseURI="https://api-m.sandbox.paypal.com";
		String token = "A21AAIWfe4annWQXlH_0BrwK2wwq0m6GErPJ42_6HoG65Xkw1c1AON03G4X7HJmqK8Has_HMLdm5yzroGrZZr5kTxrT6LSbgA";
		
		  //To create an order 
		  Response responseForOrderCreation = given().log().all()
		  .header("Authorization", "Bearer " + token) .accept(ContentType.JSON)
		  .contentType(ContentType.JSON) .body(payloadData.payload())
		  .when().post("/v2/checkout/orders")
		  .then().log().all().assertThat().statusCode(201) .body("status",
		  equalTo("CREATED")) .extract().response();
		  System.out.println("Response for Order Creation: " + responseForOrderCreation.asString());
		  
		  //To store the order id
		 JsonPath js = new JsonPath(responseForOrderCreation.toString());
		   String order_id = js.getString("id");
		
		 //To get the order id  and validate the created order id is returned in response
		Response responseForGetOrders = given().log().all() .header("Authorization", "Bearer "
		  + token) .when().pathParam("order_id",order_id).get("/v2/checkout/orders/{order_id}")
		  .then().log().all().assertThat().statusCode(200).body("id", equalTo(order_id)).extract().response();
		 System.out.println("Response for Get Order Creation: " + responseForGetOrders.asString());
		  

		// To get the response for Get Method
		 JsonPath jsGet = new JsonPath(responseForGetOrders.asString());
		 
		//To validate the currency code and its value in amount
		String currency_code = jsGet.getString("purchase_units[0].amount.currency_code");
		String value = jsGet.getString("purchase_units[0].amount.value");
		 Assert.assertEquals( currency_code, "USD");
		 Assert.assertEquals(value, "100.00");
		 
		 //To validate the email address and merchant id
		 String emailAddress = jsGet.getString("purchase_units[0].payee.email_address");
		 String merchantId = jsGet.getString("purchase_units[0].payee.merchant_id");
		 Assert.assertEquals( emailAddress, "john_merchant@example.com");
		 Assert.assertEquals(merchantId, "C7CYMKZDG8D6E");
		 
		 //To validate the soft_descriptor
		 String soft_descriptor = jsGet.getString("purchase_units[0].soft_descriptor");
		 Assert.assertEquals(soft_descriptor, "JOHNMERCHAN");
		 
		 //To count the number of links
		 int countLinks = jsGet.getInt("links.size()");
		 System.out.println("The number of links are: " +countLinks);
		 
		 //To print the first link
		 String firstLink = jsGet.getString("links[0].href");
		 System.out.println("The first link is: " + firstLink);
		 
		 //To print all links
		 for(int i=0;i<countLinks;i++) {
			 String link = jsGet.getString("links["+ i +"].href");
			 System.out.println("Link "+ i + ": " + link);
		 }
		 
			 
}}

