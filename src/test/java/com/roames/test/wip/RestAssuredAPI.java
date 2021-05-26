package com.roames.test.wip;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

public class RestAssuredAPI {

	@Test
public void getPlaceAPI()
{
		// TODO Auto-generated method stub

		//BaseURL or Host
		RestAssured.baseURI="https://maps.googleapis.com";
		Response res=given().
		       param("location","-33.8670522,151.1957362").
		       param("radius","500").
		       param("key","AIzaSyCxT0Z6ZYYwh-9Wqp5L2rGLA0u9hOFr-gc").
		       when().
		       get("/maps/api/place/nearbysearch/json").
		       then().assertThat().statusCode(200).
		       and().contentType(ContentType.JSON).and().
		       body("results[0].name",equalTo("Sydney")).and().
		       extract().response();
		
		String responseString=res.asString();
		System.out.println(responseString);
		JsonPath js= new JsonPath(responseString);
		String placeid=js.get("place_id");
		System.out.println(placeid);
		       
	
}

}
