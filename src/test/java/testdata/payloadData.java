package testdata;

import org.testng.annotations.DataProvider;

public class payloadData {
	
	String RequestBody;
	
	public static String payload(){
		
		return "{\r\n"
				+ "    \"intent\": \"CAPTURE\",\r\n"
				+ "    \"purchase_units\": [\r\n"
				+ "        {\r\n"
				+ "            \"amount\": {\r\n"
				+ "                \"currency_code\": \"USD\",\r\n"
				+ "                \"value\": \"100.00\"\r\n"
				+ "            }\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		
	}
	
	public static String payload(String currencyCode, String value) {
		return "{\r\n"
				+ "    \"intent\": \"CAPTURE\",\r\n"
				+ "    \"purchase_units\": [\r\n"
				+ "        {\r\n"
				+ "            \"amount\": {\r\n"
				+ "                \"currency_code\": \"" + currencyCode + "\",\r\n"
				+ "                \"value\": \"" + value + "\"\r\n"
				+ "            }\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		
	}
	
	

}
