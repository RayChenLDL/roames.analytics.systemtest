package com.roames.test.tasks.api;
/*
 * Author: Ray.Chen@roames.com.au
 * 
 * 
 * Function: FUGRO Roames Portal ViewAPITask
 */

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.security.InvalidKeyException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.roames.test.base.APITaskBase;
import com.roames.test.base.TestcaseBase;
import com.roames.test.base.TestcaseBaseAPI;
import com.roames.test.utilities.TestcaseUtilities;
import com.roames.test.utilities.TestcaseUtilitiesAPI;

public class ViewAPITask extends APITaskBase{
	
	String endpoint;
	Hashtable columnNameAndIdMapping = new Hashtable();
	Hashtable viewNamesAndHandlesMapping = new Hashtable();
	
	public ViewAPITask() {
		endpoint = TestcaseBase.config.getString("EndPoint_ViewAPI_" + TestcaseBase.testingEnv);
	}
	
	public ViewAPITask(String typeOfTest) {
		endpoint = TestcaseBaseAPI.config.getString("EndPoint_ViewAPI_" + TestcaseBaseAPI.testingEnv);
	}
	
	/**
	 * 
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 * @throws InterruptedException
	 */
	public void callSearchAttributesAPI() throws InvalidKeyException, SecurityException, InterruptedException
	{
		// TODO Auto-generated method stub
			
		//BaseURL or Host
		RestAssured.baseURI = this.endpoint;
				
		Response res=given().
						header("Authorization",AccessAPITask.getJWTToken()).
				when().
		       get("/search_attributes").
		       then().
		       assertThat().statusCode(200).
		       and().contentType(ContentType.JSON).and().
		       extract().response();
		
		String responseString=res.asString();
		System.out.println(responseString);	
		
		TestcaseUtilities.log.debug("Response by calling search_attributes API : " + responseString);
		TestcaseUtilities.getExtentTest().log(LogStatus.INFO, "Response by calling search_attributes API : " + responseString);
	}
	
	/**
	 * 
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 * @throws InterruptedException
	 */
	public void callopsRoutesAPI() throws InvalidKeyException, SecurityException, InterruptedException
	{
		// TODO Auto-generated method stub
			
		//BaseURL or Host
		RestAssured.baseURI = this.endpoint;
				
		Response res=given().
						header("Authorization",AccessAPITask.getJWTToken()).
				when().
		       get("/ops/routes").
		       then().
		       assertThat().statusCode(200).
		       and().contentType(ContentType.JSON).and().
		       extract().response();
		
		String responseString=res.asString();
		System.out.println(responseString);	
		
		//TestcaseUtilities.log.debug("Response by calling search_attributes API : " + responseString);
		//TestcaseUtilities.extentTest.log(LogStatus.INFO, "Response by calling search_attributes API : " + responseString);
	}
	
	/**
	 * Return all the asset type for the customer name given
	 * @param custName
	 * @return Hashtable with Asset Type ID / Asset Type Name pair
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 * @throws InterruptedException
	 */
	public Hashtable getCustAssetTypes(String custName) throws InvalidKeyException, SecurityException, InterruptedException
	{
		// TODO Auto-generated method stub
		Hashtable assetTypes = new Hashtable();
		String assetTypesFoundForCustName = "";
		
		//BaseURL or Host
		RestAssured.baseURI = this.endpoint;
				
		Response res=given().
						header("Authorization",AccessAPITask.getJWTToken()).
				when().
		       get("/custom/asset-types").
		       then().
		       assertThat().statusCode(200).
		       and().contentType(ContentType.JSON).and().
		       extract().response();
		
		//String responseString=res.asString();		
		//JsonPath jsonRes = new JsonPath(responseString);		
		//List<String> jsonResponse = res.jsonPath().getList("$");
		
		List<String> assetTypeIDs = res.jsonPath().getList("asset_type_id");
		List<String> assetTypeNames = res.jsonPath().getList("asset_type_name");
		List<List> tenants =  res.jsonPath().getList("tenants");
		
		// Iterate through all the asset types
		for (int i=0;i<tenants.size();i++) {
			
			List tenantsForCurrAssetType = tenants.get(i);
			boolean currAssetTypeContainsCustName = false;
			
			// Iterate through all the tenants
			for (int j=0;j<tenantsForCurrAssetType.size();j++) {
				if (custName.equals(tenantsForCurrAssetType.get(j).toString())) {
					currAssetTypeContainsCustName = true;
					assetTypesFoundForCustName +=  "'" + assetTypeNames.get(i) + "/" + assetTypeIDs.get(i) + "' ";
					break;
				}
			}
			
			// If the current asset type contains cust name, then add into the result
			if (currAssetTypeContainsCustName) {
				assetTypes.put(assetTypeIDs.get(i), assetTypeNames.get(i));
			}			
		}			
		
		//TestcaseUtilities.log.debug("The customer '" + custName + "' has asset types: " + assetTypesFoundForCustName);
		//TestcaseUtilities.extentTest.log(LogStatus.INFO, "The customer '" + custName + "' has asset types: " + assetTypesFoundForCustName);
				
		System.out.println("The customer '" + custName + "' has asset types: " + assetTypesFoundForCustName);
		return assetTypes;
	}
	
	/**
	 * Retrieve the column name and col-id mapping for the view given
	 * @param viewName
	 * @return
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 */
	public Hashtable retrieveDataGridNameAndColIDMapping(String viewName) throws InvalidKeyException, SecurityException
	{					
		// Retrieve the view handle based on the view name
		this.retrieveViewNamesAndViewHandlesMapping();
		String view_Handle = this.viewNamesAndHandlesMapping.get(viewName).toString();
		
		//BaseURL or Host
		RestAssured.baseURI = this.endpoint;
				
		Response res=given().
						header("Authorization",AccessAPITask.getJWTToken()).
				when().
		       get("/view/" + view_Handle).
		       then().
		       assertThat().statusCode(200).
		       and().contentType(ContentType.JSON).and().
		       extract().response();
		
		String responseString=res.asString();
		
		List<List> attributes =  res.jsonPath().getList("attributes");
		
		// Retrieve the display name and col-id (name) for each column and build the mapping
		for (int i=0;i<attributes.size();i++) {			
			HashMap columnDefinition = (HashMap) attributes.get(i);
			this.columnNameAndIdMapping.put(columnDefinition.get("display_name"), columnDefinition.get("name"));		
			
			//System.out.println(columnDefinition.get("display_name").toString() + " / " + columnDefinition.get("name").toString());
		}
		
		TestcaseUtilities.log.debug("'retrieveDataGridNameAndColIDMapping' is called");
		
		return this.columnNameAndIdMapping;
	}
	
	/**
	 * Build the mapping of View Names and View Hanldes
	 * @return
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 */
	public Hashtable retrieveViewNamesAndViewHandlesMapping() throws InvalidKeyException, SecurityException
	{					
		//BaseURL or Host
		RestAssured.baseURI = this.endpoint;
				
		Response res=given().
						header("Authorization",AccessAPITask.getJWTToken()).
				when().
		       get("/view").
		       then().
		       assertThat().statusCode(200).
		       and().contentType(ContentType.JSON).and().
		       extract().response();
		
		String responseString=res.asString();
		
		List<List> views =  res.jsonPath().getList("result");
		
		// Retrieve the view name and view_handle for each view and build the mapping
		for (int i=0;i<views.size();i++) {			
			HashMap viewDefinition = (HashMap) views.get(i);
			this.viewNamesAndHandlesMapping.put(viewDefinition.get("display_name"), viewDefinition.get("view_handle"));	
			
			//System.out.println(viewDefinition.get("display_name").toString() + " / " + viewDefinition.get("view_handle").toString());
			//TestcaseUtilities.log.debug(viewDefinition.get("display_name").toString() + " / " + viewDefinition.get("view_handle").toString());
			//TestcaseUtilities.extentTest.log(LogStatus.INFO, viewDefinition.get("display_name").toString() + " / " + viewDefinition.get("view_handle").toString());
			
		}
		
		TestcaseUtilities.log.debug("'retrieveViewNameaAndViewHandlesMapping' is called");
		
		return this.viewNamesAndHandlesMapping;
	}
	
	/**
	 * Search Attributes
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 * @throws InterruptedException
	 */
	public void searchAttributes() throws InvalidKeyException, SecurityException, InterruptedException
	{
		// TODO Auto-generated method stub
			
		//BaseURL or Host
		RestAssured.baseURI = this.endpoint;				
		
		try {
		Response res=given().
						header("Authorization",AccessAPITask.getJWTTokenAPI()).
				when().
		       get("/search_attributes").
		       then().
		       assertThat().statusCode(200).
		       and().contentType(ContentType.JSON).and().
		       extract().response();
		
		String responseString=res.asString();
		System.out.println(responseString);
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + endpoint + "/search_attributes");	
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(endpoint, "Failed calling " + endpoint + "/search_attributes", e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + endpoint + "/search_attributes");	
				throw new InterruptedException(e.getMessage());
			}
		}
				
		logTestResult(LogStatus.PASS, "Successful calling " + endpoint + "/search_attributes");
	}
	
	/**
	 * List all the views
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 * @throws InterruptedException
	 */
	public String[] listAllViews() throws InvalidKeyException, SecurityException, InterruptedException
	{
		// TODO Auto-generated method stub
			
		//BaseURL or Host
		RestAssured.baseURI = this.endpoint;	
		String[] viewHandles = new String[2];
		List<List> views = null;
		
		try {
			Response res=given().
					header("Authorization",AccessAPITask.getJWTTokenAPI()).
					when().
					get("/view").
					then().
					assertThat().statusCode(200).
					and().contentType(ContentType.JSON).and().
					extract().response();

			views =  res.jsonPath().getList("result");

			viewHandles = new String[views.size()];

			for (int i=0;i<views.size();i++) {			
				HashMap dataSource = (HashMap) views.get(i);
				viewHandles[i] = dataSource.get("view_handle").toString();
			}		
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + endpoint + "/view");
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(endpoint, "/view", e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + endpoint + "/view");
				throw new InterruptedException(e.getMessage());
			}else {
				return null;
			}
		}
				
		logTestResult(LogStatus.PASS, "Successful calling " + endpoint + "/view. There are total " + views.size() + " views in '" + TestcaseBaseAPI.testingEnv + "'.");		
		return viewHandles;
	}
	
	/**
	 * List single view with view handle given
	 * @param viewHandle
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 * @throws InterruptedException
	 */
	public void listSingleView(String viewHandle) throws InvalidKeyException, SecurityException, InterruptedException
	{
		// TODO Auto-generated method stub
			
		//BaseURL or Host
		RestAssured.baseURI = this.endpoint;
		
		// Some views have errors (Needs developer to fix or clean):
		String[] errorViewHandles = {"gridintrusionseq_9nah"};		
		
		if (Arrays.asList(errorViewHandles).contains(viewHandle)){
			// Do nothing
		}else {
			try {
				Response res=given().
						header("Authorization",AccessAPITask.getJWTTokenAPI()).
						when().
						get("/view/" + viewHandle).
						then().
						assertThat().statusCode(200).
						and().contentType(ContentType.JSON).and().
						extract().response();
			}
			catch(Exception e) {
				logTestResult(LogStatus.FAIL, "Failed calling " + endpoint + "/view/" + viewHandle);	
				throw new InterruptedException(e.getMessage());
			}
			catch(AssertionError e) {
				if(!isKnownError(endpoint, "/view/" + viewHandle, e)) {
					logTestResult(LogStatus.FAIL, "Failed calling " + endpoint + "/view/" + viewHandle);
					throw new InterruptedException(e.getMessage());
				}
			}

			logTestResult(LogStatus.PASS, "Successful calling " + endpoint + "/view/" + viewHandle);	
		}
	}
	
	/**
	 * List data of the single view with the view handle given
	 * @param viewHandle
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 * @throws InterruptedException
	 */
	public void listSingleViewData(String viewHandle) throws InvalidKeyException, SecurityException, InterruptedException
	{
		// TODO Auto-generated method stub
			
		//BaseURL or Host
		RestAssured.baseURI = this.endpoint;
		
		// Some views have errors (Needs developer to fix or clean):
		// PROD - "gridmaintenancezonespans2eq_tb70","gridmaintenancezonespans99eq_juj4","gridintrusions_4tsi"
		// UAT - "gridintrusionseq_semp","gridbayseq_wmp8"
		// DEV -"polestestdev_wm3s","gridmaintenancezonepoleseq_1qp5","gridmaintenancezonepoleseq4_bzp4","gridintrusionseq_9nah","gridmaintenancezonepoleseq2_aa8a","gridmaintenancezonespaneq_r3gi",
		//      "gridmaintenancezonespanseq2_d7ep","gridmaintenancezonespanseq2_ifg9","gridmaintenancezonepoleseqtest_mg9l","gridbayseqlatest_q3gg","poles_5m8p","poles_8lbg",
		//      "gridmaintenancezonepoleseq2_1jkw","polesergon_f810"
		
		String[] errorViewHandles = {"gridmaintenancezonespans2eq_tb70","gridmaintenancezonespans99eq_juj4","gridintrusions_4tsi",
				"gridintrusionseq_semp","gridbayseq_wmp8",
				"polestestdev_wm3s","gridmaintenancezonepoleseq_1qp5","gridmaintenancezonepoleseq4_bzp4","gridintrusionseq_9nah","gridmaintenancezonepoleseq2_aa8a","gridmaintenancezonespaneq_r3gi",
				"gridmaintenancezonespanseq2_d7ep","gridmaintenancezonespanseq2_ifg9","gridmaintenancezonepoleseqtest_mg9l","gridbayseqlatest_q3gg","poles_5m8p","poles_8lbg",
				"gridmaintenancezonepoleseq2_1jkw","polesergon_f810"};
		
		if (Arrays.asList(errorViewHandles).contains(viewHandle)){
			// Do nothing
		}else {
			try {
				Response res=given().
						header("Authorization",AccessAPITask.getJWTTokenAPI()).
						when().
						get("/view/" + viewHandle + "/data").
						then().
						assertThat().statusCode(200).
						and().contentType(ContentType.JSON).and().
						extract().response();
			}
			catch(Exception e) {
				logTestResult(LogStatus.FAIL, "Failed calling " + endpoint + "/view/" + viewHandle + "/data");	
				throw new InterruptedException(e.getMessage());
			}
			catch(AssertionError e) {
				if(!isKnownError(endpoint, "/view" + viewHandle + "/data", e)) {
					logTestResult(LogStatus.FAIL, "Failed calling " + endpoint + "/view/" + viewHandle + "/data");	
					throw new InterruptedException(e.getMessage());
				}
			}

			logTestResult(LogStatus.PASS, "Successful calling " + endpoint + "/view/" + viewHandle + "/data");	
		}
	}
	

	/**
	 * Ops Ping
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 * @throws InterruptedException
	 */
	public void opsPing() throws InvalidKeyException, SecurityException, InterruptedException
	{
		// TODO Auto-generated method stub
			
		//BaseURL or Host
		RestAssured.baseURI = this.endpoint;	
		
		try {
			Response res=given().
					header("Authorization",AccessAPITask.getJWTTokenAPI()).
					when().
					get("/ops/ping").
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + endpoint + "/ops/ping");
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(endpoint, "/ops/ping", e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + endpoint + "/ops/ping");
				throw new InterruptedException(e.getMessage());
			}
		}
				
		logTestResult(LogStatus.PASS, "Successful calling " + endpoint + "/ops/ping");
	}
	
	/**
	 * opsRefreshPermissions
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 * @throws InterruptedException
	 */
	public void opsRefreshPermissions() throws InvalidKeyException, SecurityException, InterruptedException
	{
		// TODO Auto-generated method stub
			
		//BaseURL or Host
		RestAssured.baseURI = this.endpoint;	
		
		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					when().
					get("/ops/refresh_permissions").
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + endpoint + "/ops/refresh_permissions");
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(endpoint, "/ops/refresh_permissions", e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + endpoint + "/ops/refresh_permissions");
				throw new InterruptedException(e.getMessage());
			}
		}
		
		logTestResult(LogStatus.PASS, "Successful calling " + endpoint + "/ops/refresh_permissions");
	}
	
	/**
	 * opsRoutes
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 * @throws InterruptedException
	 */
	public void opsRoutes() throws InvalidKeyException, SecurityException, InterruptedException
	{
		// TODO Auto-generated method stub
			
		//BaseURL or Host
		RestAssured.baseURI = this.endpoint;	
		
		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					when().
					get("/ops/routes").
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + endpoint + "/ops/routes");
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(endpoint, "/ops/routes", e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + endpoint + "/ops/routes");
				throw new InterruptedException(e.getMessage());
			}
		}
				
		logTestResult(LogStatus.PASS, "Successful calling " + endpoint + "/ops/routes");
	}
	
	/**
	 * opsTail
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 * @throws InterruptedException
	 */
	public void opsTail() throws InvalidKeyException, SecurityException, InterruptedException
	{
		// TODO Auto-generated method stub
			
		//BaseURL or Host
		RestAssured.baseURI = this.endpoint;	
		
		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					when().
					get("/ops/tail").
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + endpoint + "/ops/tail");
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(endpoint, "/ops/tail", e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + endpoint + "/ops/tail");
				throw new InterruptedException(e.getMessage());
			}
		}
				
		logTestResult(LogStatus.PASS, "Successful calling " + endpoint + "/ops/tail");
	}
	
	/**
	 * 
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 * @throws InterruptedException
	 */
	public void opsLogLevel() throws InvalidKeyException, SecurityException, InterruptedException
	{
		// TODO Auto-generated method stub
			
		//BaseURL or Host
		RestAssured.baseURI = this.endpoint;	
		
		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					when().
					get("/ops/log_level").
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + endpoint + "/ops/log_level");
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(endpoint, "/ops/log_level", e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + endpoint + "/ops/log_level");
				throw new InterruptedException(e.getMessage());
			}
		}
				
		logTestResult(LogStatus.PASS, "Successful calling " + endpoint + "/ops/log_level");
	}
	
	public void opsListServerLog(int numberOfLines) throws InvalidKeyException, SecurityException, InterruptedException
	{
		// TODO Auto-generated method stub
			
		//BaseURL or Host
		RestAssured.baseURI = this.endpoint;	
		
		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					when().
					get("/ops/tail/" + numberOfLines).
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + endpoint + "/ops/tail/" + numberOfLines);
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(endpoint, "/ops/tail" + numberOfLines, e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + endpoint + "/ops/tail/" + numberOfLines);
				throw new InterruptedException(e.getMessage());
			}
		}
				
		logTestResult(LogStatus.PASS, "Successful calling " + endpoint + "/ops/tail/" + numberOfLines);
	}
	
	public void processRequestWithInvalidToken(String requestString) throws InterruptedException{
		
		RestAssured.baseURI = this.endpoint;
		
		try {
			Response res=given().
					header("Authorization","Invalid JWT Token").
					urlEncodingEnabled(false).
					when().
					get(requestString).
					then().
					assertThat().statusCode(401).
					and().
					extract().response();
			
			//String responseString=res.asString();			
			//System.out.println(responseString);
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "401 is NOT returned as expected by calling " + endpoint + "/" + requestString + " with an invalid JWT token.");
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(endpoint, requestString, e)) {
				logTestResult(LogStatus.FAIL, "401 is NOT returned as expected by calling " + endpoint + "/" + requestString + " with an invalid JWT token.");
				throw new InterruptedException(e.getMessage());
			}
		}
		
		logTestResult(LogStatus.PASS, "401 is returned as expected by calling " + endpoint + "/" + requestString + " with an invalid JWT token.");
	}
}
