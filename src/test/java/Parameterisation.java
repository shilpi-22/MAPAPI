import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import testdata.payloadData;

public class Parameterisation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
		  .contentType(ContentType.JSON) .body(payloadData.payload("EUR", "200.00"))
		  .when().post("/v2/checkout/orders")
		  .then().log().all().assertThat().statusCode(201) .body("status",
		  equalTo("CREATED")) .extract().response();
		  System.out.println("Response for Order Creation: " + responseForOrderCreation.asString());
		  
		  //To store the order id
		 JsonPath js = new JsonPath(responseForOrderCreation.asString());
		   String order_id = js.getString("id");
		
		 //To get the order id  and validate the created order id is returned in response
		Response responseForGetOrders = given().log().all() .header("Authorization", "Bearer "
		  + token) .when().pathParam("order_id",order_id).get("/v2/checkout/orders/{order_id}")
		  .then().log().all().assertThat().statusCode(200).body("id", equalTo(order_id)).extract().response();
		 System.out.println("Response for Get Order Creation: " + responseForGetOrders.asString());
		  

	}

}
