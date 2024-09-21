import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import testdata.payloadData;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;
public class Orders {

	String token = "A21AAIWfe4annWQXlH_0BrwK2wwq0m6GErPJ42_6HoG65Xkw1c1AON03G4X7HJmqK8Has_HMLdm5yzroGrZZr5kTxrT6LSbgA";
	String response = "";
	String order_id = "";
	List<String> list = new ArrayList<>();
	
	@Test(priority = 1, dataProvider = "multipleData")
	public void createOrder(String currency, String value) {
		RestAssured.baseURI = "https://api-m.sandbox.paypal.com";
		response = given().log().all().header("Authorization", "Bearer " + token).contentType("application/json")
		.body(payloadData.payload(currency, value))
		.when().post("/v2/checkout/orders")
		.then().assertThat().statusCode(201)
		.body("status", equalTo("CREATED")).extract().response().asString();
		System.out.println("RESPONSE IS: " + response);
		
		//Add order in list
		JsonPath js = new JsonPath(response);
		order_id = js.getString("id");
		list.add(order_id);
	}

	@Test(priority =2)
	public void getOrder() {
		for(String x: list) {
			order_id = x;
			 //To get the order id  and validate the created order id is returned in response
			Response responseForGetOrders = given().log().all() .header("Authorization", "Bearer "
					  + token) .when().pathParam("order_id",order_id).get("/v2/checkout/orders/{order_id}")
					  .then().log().all().assertThat().statusCode(200).body("id", equalTo(order_id)).extract().response();
					 System.out.println("Response for Get Order Creation: " + responseForGetOrders.asString());
					  
		}
		
	}
	
	@DataProvider(name="multipleData")
	public Object[][] multipleData(){
		 Object [][] dataSet = {{"BRL", "900.00"},{"EUR", "1000.00"}, {"MXN", "1100.00"}};
		 return dataSet;
	}
	
}
