package com.roames.test.tasks.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.roames.test.base.APITaskBase;
import com.roames.test.base.TestcaseBase;
import com.roames.test.base.TestcaseBaseAPI;
import com.roames.test.utilities.AESEnryption;
import com.roames.test.utilities.TestcaseUtilitiesAPI;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;

public class AccessAPITask extends APITaskBase{

	public static String getJWTToken() throws InvalidKeyException, SecurityException
	{
		try {

			//BaseURL or Host
			RestAssured.baseURI = TestcaseBase.config.getString("EndPoint_AccessAPI_" + TestcaseBase.testingEnv);
			String api_key_id = TestcaseBase.encrypter.decrypt(TestcaseBase.config.getString("API_KEY_ID_" + TestcaseBase.testingEnv));
			String api_secret = TestcaseBase.encrypter.decrypt(TestcaseBase.config.getString("API_SECRET_" + TestcaseBase.testingEnv));

			int ts = Math.round(System.currentTimeMillis()/1000);

			String  string_to_sign = api_key_id + '-' + api_secret + '-' + ts;

			String signature = getSigNature(string_to_sign,api_secret);	

			Response res=given().
					queryParam("id",api_key_id).
					queryParam("tokenType","BEARER").
					queryParam("timestamp",ts).
					queryParam("signature",signature).
					when().
					get("/tokens").
					then().assertThat().statusCode(200).
					and().contentType(ContentType.JSON).and().
					extract().response();

			String responseString=res.asString();
			JsonPath js= new JsonPath(responseString);
			String JWTToken =js.get("token");
			JWTToken = "Bearer " + JWTToken;
			return JWTToken;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ERROR";
		}
	}


	public static String swtichToAdmin(String currentJWT) throws InvalidKeyException, SecurityException
	{			
		//BaseURL or Host
		RestAssured.baseURI = TestcaseBase.config.getString("EndPoint_AccessAPI_" + TestcaseBase.testingEnv);

		Response res=given().
				queryParam("Authorization",currentJWT).
				when().
				get("/openIdUsers/actAs").
				then().assertThat().statusCode(200).
				and().contentType(ContentType.JSON).and().
				extract().response();

		String responseString=res.asString();
		JsonPath js= new JsonPath(responseString);
		String adminJWTToken =js.get("token");
		adminJWTToken = "Bearer " + adminJWTToken;
		return adminJWTToken;
	}

	public static String getJWTTokenAPI() throws InvalidKeyException, SecurityException
	{
		try {

			//BaseURL or Host
			RestAssured.baseURI = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);
			String api_key_id = TestcaseBaseAPI.encrypter.decrypt(TestcaseBaseAPI.config.getString("API_KEY_ID_" + TestcaseBaseAPI.testingEnv));
			String api_secret = TestcaseBaseAPI.encrypter.decrypt(TestcaseBaseAPI.config.getString("API_SECRET_" + TestcaseBaseAPI.testingEnv));

			int ts = Math.round(System.currentTimeMillis()/1000);

			String  string_to_sign = api_key_id + '-' + api_secret + '-' + ts;

			String signature = getSigNature(string_to_sign,api_secret);	

			Response res=given().
					queryParam("id",api_key_id).
					queryParam("tokenType","BEARER").
					queryParam("timestamp",ts).
					queryParam("signature",signature).
					when().
					get("/tokens").
					then().assertThat().statusCode(200).
					and().contentType(ContentType.JSON).and().
					extract().response();

			String responseString=res.asString();
			JsonPath js= new JsonPath(responseString);
			String JWTToken =js.get("token");
			JWTToken = "Bearer " + JWTToken;

			//System.out.println(JWTToken);
			return JWTToken;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ERROR";
		}
	}

	public static String getAdminJWTToken() throws InvalidKeyException, SecurityException
	{			
		//BaseURL or Host
		RestAssured.baseURI = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);

		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject();
		requestParams.put("tenant", "Roames");
		requestParams.put("roles", "Roames/Roames Administrator");		 
		request.header("Content-Type", "application/json");
		request.header("Accept", "application/json");
		request.header("Authorization", getJWTTokenAPI());

		request.body(requestParams.toJSONString());
		Response response = request.post("/openIdUsers/actAs");

		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		String JWTToken = response.jsonPath().get("access_token");		
		JWTToken = "Bearer " + JWTToken;
//System.out.print(JWTToken);
		return JWTToken;
	}

	private static String getSigNature(String message, String secret) {
		String hash = "";
		try {

			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
			sha256_HMAC.init(secret_key);

			hash = Base64.encodeBase64URLSafeString(sha256_HMAC.doFinal(message.getBytes()));

		}
		catch (Exception e){
			System.out.println("Error");
		}
		hash = make_url_safe(hash);

		return hash;
	}

	private static String make_url_safe(String url) {
		url = url.replace("+", "-");
		url = url.replace("/", "-");
		url = url.replace("=", "");
		return url;
	}

	/**
	 * identityRoamesAccessKey
	 * @throws InterruptedException
	 */
	public String[] identityRoamesAccessKey() throws InterruptedException{
		
		RestAssured.baseURI = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);	
		
		String[] AccessKeyIDs = new String[2];
		List<List> AccessKeys;

		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					when().
					get("identity/RoamesAccessKey").
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
			
			AccessKeys =  res.jsonPath().getList("");

			AccessKeyIDs = new String[AccessKeys.size()];

			for (int i=0;i<AccessKeys.size();i++) {			
				HashMap dataSource = (HashMap) AccessKeys.get(i);
				AccessKeyIDs[i] = dataSource.get("id").toString();
			}	
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/identity/RoamesAccessKey");
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(RestAssured.baseURI, "/identity/RoamesAccessKey", e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/identity/RoamesAccessKey");
				throw new InterruptedException(e.getMessage());
			}else {
				return null;
			}
		}

		logTestResult(LogStatus.PASS, "Successful calling " + RestAssured.baseURI + "/identity/RoamesAccessKey");
		
		return AccessKeyIDs;
	}
	
	/**
	 * 
	 * @throws InterruptedException
	 */
	public void identityRoamesAccessKeyCount() throws InterruptedException{
		
		RestAssured.baseURI = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);	

		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					urlEncodingEnabled(false).
					when().
					get("identity/RoamesAccessKey/$count").
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/identity/RoamesAccessKey/$count");
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(RestAssured.baseURI, "/identity/RoamesAccessKey/$count", e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/identity/RoamesAccessKey/$count");
				throw new InterruptedException(e.getMessage());
			}
		}
		
		logTestResult(LogStatus.PASS, "Successful calling " + RestAssured.baseURI + "/identity/RoamesAccessKey/$count");
	}	
	
	public void identityRoamesAccessKeyPK(String accessKeyPK) throws InterruptedException{
		
		RestAssured.baseURI = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);	
		
		String requestString = "/identity/RoamesAccessKey/" + accessKeyPK;

		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					when().
					get(requestString).
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + requestString);
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(RestAssured.baseURI, requestString, e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + requestString);
				throw new InterruptedException(e.getMessage());
			}
		}

		logTestResult(LogStatus.PASS, "Successful calling " + RestAssured.baseURI + requestString);
	}
	
	public void identityRoamesRole() throws InterruptedException{
		
		RestAssured.baseURI = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);	

		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					when().
					get("identity/RoamesRole").
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/identity/RoamesRole");
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(RestAssured.baseURI, "/identity/RoamesRole", e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/identity/RoamesRole");
				throw new InterruptedException(e.getMessage());
			}
		}

		logTestResult(LogStatus.PASS, "Successful calling " + RestAssured.baseURI + "/identity/RoamesRole");
	}
	
	public void identityRoamesRoleCount() throws InterruptedException{
		
		RestAssured.baseURI = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);	

		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					urlEncodingEnabled(false).
					when().
					get("identity/RoamesRole/$count").
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/identity/RoamesRole/$count");
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(RestAssured.baseURI, "/identity/RoamesRole/$count", e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/identity/RoamesRole/$count");
				throw new InterruptedException(e.getMessage());
			}
		}

		logTestResult(LogStatus.PASS, "Successful calling " + RestAssured.baseURI + "/identity/RoamesRole/$count");
	}
	
	public String[] identityRoamesTenant() throws InterruptedException{
		
		RestAssured.baseURI = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);	

		String[] tenantsIDs = new String[2];
		List<List> tenants;
		
		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					when().
					get("identity/RoamesTenant").
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
						
			tenants =  res.jsonPath().getList("");

			tenantsIDs = new String[tenants.size()];

			for (int i=0;i<tenants.size();i++) {			
				HashMap dataSource = (HashMap) tenants.get(i);
				tenantsIDs[i] = dataSource.get("id").toString();
			}	
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/identity/RoamesTenant");
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(RestAssured.baseURI, "/identity/RoamesTenant", e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/identity/RoamesTenant");
				throw new InterruptedException(e.getMessage());
			}else {
				return null;
			}
		}

		logTestResult(LogStatus.PASS, "Successful calling " + RestAssured.baseURI + "/identity/RoamesTenant");
		
		return tenantsIDs;
	}
	
	
	public void identityRoamesTenantCount() throws InterruptedException{
		
		RestAssured.baseURI = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);	

		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					urlEncodingEnabled(false).
					when().
					get("identity/RoamesTenant/$count").
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/identity/RoamesTenant/$count");
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(RestAssured.baseURI, "/identity/RoamesTenant/$count", e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/identity/RoamesTenant/$count");
				throw new InterruptedException(e.getMessage());
			}
		}

		logTestResult(LogStatus.PASS, "Successful calling " + RestAssured.baseURI + "/identity/RoamesTenant/$count");
	}
	
	public void identityRoamesTenantPK(String tenantPK) throws InterruptedException{
		
		RestAssured.baseURI = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);	
		
		String requestString = "/identity/RoamesTenant/" + tenantPK;

		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					when().
					get(requestString).
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + requestString);
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(RestAssured.baseURI, requestString, e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + requestString);
				throw new InterruptedException(e.getMessage());
			}
		}

		logTestResult(LogStatus.PASS, "Successful calling " + RestAssured.baseURI + requestString);
	}
	
	
	public String[] identityRoamesUser() throws InterruptedException{
		
		RestAssured.baseURI = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);	
		
		String[] usersIDs = new String[2];
		List<List> users;

		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					when().
					get("identity/RoamesUser").
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
			
			users =  res.jsonPath().getList("");

			usersIDs = new String[users.size()];

			for (int i=0;i<users.size();i++) {			
				HashMap dataSource = (HashMap) users.get(i);
				usersIDs[i] = dataSource.get("id").toString();
			}	
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/identity/RoamesUser");
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(RestAssured.baseURI, "/identity/RoamesUser", e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/identity/RoamesUser");
				throw new InterruptedException(e.getMessage());
			}else {
				return null;
			}
		}

		logTestResult(LogStatus.PASS, "Successful calling " + RestAssured.baseURI + "/identity/RoamesUser");
		
		return usersIDs;
	}
	
	
	public void identityRoamesUserPK(String userPK) throws InterruptedException{
		
		RestAssured.baseURI = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);	
		
		String requestString = "/identity/RoamesUser/" + userPK;

		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					when().
					get(requestString).
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + requestString);
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(RestAssured.baseURI, requestString, e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + requestString);
				throw new InterruptedException(e.getMessage());
			}
		}

		logTestResult(LogStatus.PASS, "Successful calling " + RestAssured.baseURI + requestString);
	}
	
	
	public void identityRoamesUserCount() throws InterruptedException{
		
		RestAssured.baseURI = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);	

		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					urlEncodingEnabled(false).
					when().
					get("identity/RoamesUser/$count").
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/identity/RoamesUser/$count");
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(RestAssured.baseURI, "/identity/RoamesUser/$count", e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/identity/RoamesUser/$count");
				throw new InterruptedException(e.getMessage());
			}
		}

		logTestResult(LogStatus.PASS, "Successful calling " + RestAssured.baseURI + "/identity/RoamesUser/$count");
	}
	
	public void identityRoamesUserPassword() throws InterruptedException{
		
		RestAssured.baseURI = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);	

		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					when().
					get("identity/RoamesUserPassword").
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/identity/RoamesUserPassword");
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(RestAssured.baseURI, "/identity/RoamesUserPassword", e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/identity/RoamesUserPassword");
				throw new InterruptedException(e.getMessage());
			}
		}

		logTestResult(LogStatus.PASS, "Successful calling " + RestAssured.baseURI + "/identity/RoamesUserPassword");
	}
	
	
	public void identityRoamesUserPasswordCount() throws InterruptedException{
		
		RestAssured.baseURI = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);	

		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					urlEncodingEnabled(false).
					when().
					get("identity/RoamesUserPassword/$count").
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/identity/RoamesUserPassword/$count");
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(RestAssured.baseURI, "/identity/RoamesUserPassword/$count", e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/identity/RoamesUserPassword/$count");
				throw new InterruptedException(e.getMessage());
			}
		}

		logTestResult(LogStatus.PASS, "Successful calling " + RestAssured.baseURI + "/identity/RoamesUserPassword/$count");
	}
	 
	public String[] permissionsAuthorisation() throws InterruptedException{
		
		RestAssured.baseURI = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);	
		
		String[] authorisationsIDs = new String[2];
		List<List> authorisations;

		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					when().
					get("permissions/Authorisation").
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
			
			authorisations =  res.jsonPath().getList("");

			authorisationsIDs = new String[authorisations.size()];

			for (int i=0;i<authorisations.size();i++) {			
				HashMap dataSource = (HashMap) authorisations.get(i);
				authorisationsIDs[i] = dataSource.get("id").toString();
			}
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/permissions/Authorisation");
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(RestAssured.baseURI, "/permissions/Authorisation", e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/permissions/Authorisation");
				throw new InterruptedException(e.getMessage());
			}else {
				return null;
			}
		}

		logTestResult(LogStatus.PASS, "Successful calling " + RestAssured.baseURI + "/permissions/Authorisation");
		
		return authorisationsIDs;
	}
	
	
	public void permissionsAuthorisationCount() throws InterruptedException{
		
		RestAssured.baseURI = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);	

		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					urlEncodingEnabled(false).
					when().
					get("permissions/Authorisation/$count").
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/permissions/Authorisation/$count");
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(RestAssured.baseURI, "/permissions/Authorisation/$count", e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/permissions/Authorisation/$count");
				throw new InterruptedException(e.getMessage());
			}
		}

		logTestResult(LogStatus.PASS, "Successful calling " + RestAssured.baseURI + "/permissions/Authorisation/$count");
	}	
	
	public void permissionsAuthorisationPK(String authorisationPK) throws InterruptedException{
		
		RestAssured.baseURI = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);	
		
		String requestString = "/permissions/Authorisation/" + authorisationPK;

		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					when().
					get(requestString).
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + requestString);
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(RestAssured.baseURI, requestString, e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + requestString);
				throw new InterruptedException(e.getMessage());
			}
		}

		logTestResult(LogStatus.PASS, "Successful calling " + RestAssured.baseURI + requestString);
	}
	
	public String[] permissionsAuthorisationScope() throws InterruptedException{
		
		RestAssured.baseURI = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);	
		
		String[] authorisationScopesIDs = new String[2];
		List<List> authorisationScopes;

		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					when().
					get("permissions/AuthorisationScope").
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
			
			authorisationScopes =  res.jsonPath().getList("");

			authorisationScopesIDs = new String[authorisationScopes.size()];

			for (int i=0;i<authorisationScopes.size();i++) {			
				HashMap dataSource = (HashMap) authorisationScopes.get(i);
				authorisationScopesIDs[i] = dataSource.get("id").toString();
			}	
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/permissions/AuthorisationScope");
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(RestAssured.baseURI, "/permissions/AuthorisationScope", e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/permissions/AuthorisationScope");
				throw new InterruptedException(e.getMessage());
			}else {
				return null;
			}
		}

		logTestResult(LogStatus.PASS, "Successful calling " + RestAssured.baseURI + "/permissions/AuthorisationScope");
		
		return authorisationScopesIDs;
	}
	
	
	public void permissionsAuthorisationScopeCount() throws InterruptedException{
		
		RestAssured.baseURI = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);	

		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					urlEncodingEnabled(false).
					when().
					get("permissions/AuthorisationScope/$count").
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/permissions/AuthorisationScope/$count");
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(RestAssured.baseURI, "/permissions/AuthorisationScope/$count", e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/permissions/AuthorisationScope/$count");
				throw new InterruptedException(e.getMessage());
			}
		}

		logTestResult(LogStatus.PASS, "Successful calling " + RestAssured.baseURI + "/permissions/AuthorisationScope/$count");
	}
	
	public void permissionsAuthorisationScopePK(String authorisationScopePK) throws InterruptedException{
		
		RestAssured.baseURI = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);	
		
		String requestString = "/permissions/AuthorisationScope/" + authorisationScopePK;

		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					when().
					get(requestString).
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + requestString);
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(RestAssured.baseURI, requestString, e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + requestString);
				throw new InterruptedException(e.getMessage());
			}
		}

		logTestResult(LogStatus.PASS, "Successful calling " + RestAssured.baseURI + requestString);
	}

	public void permissionsAuthorisationSubScriber() throws InterruptedException{
		
		RestAssured.baseURI = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);	

		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					when().
					get("permissions/AuthorisationSubscriber").
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/permissions/AuthorisationSubscriber");
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(RestAssured.baseURI, "/permissions/AuthorisationSubscriber", e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/permissions/AuthorisationSubscriber");
				throw new InterruptedException(e.getMessage());
			}
		}

		logTestResult(LogStatus.PASS, "Successful calling " + RestAssured.baseURI + "/permissions/AuthorisationSubscriber");
	}
	
	
	public void permissionsAuthorisationSubScriberCount() throws InterruptedException{
		
		RestAssured.baseURI = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);	

		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					urlEncodingEnabled(false).
					when().
					get("permissions/AuthorisationSubscriber/$count").
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/permissions/AuthorisationSubscriber/$count");
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(RestAssured.baseURI, "/permissions/AuthorisationSubscriber/$count", e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/permissions/AuthorisationSubscriber/$count");
				throw new InterruptedException(e.getMessage());
			}
		}

		logTestResult(LogStatus.PASS, "Successful calling " + RestAssured.baseURI + "/permissions/AuthorisationSubscriber/$count");
	}	
	
	public void processRequest(String requestString) throws InterruptedException{
		
		RestAssured.baseURI = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);	
		
		try {
			Response res=given().
					header("Authorization",AccessAPITask.getAdminJWTToken()).
					urlEncodingEnabled(true).
					when().
					get(requestString).
					then().
					assertThat().statusCode(200).
					and().
					extract().response();
		}
		catch(Exception e) {
			logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/" + requestString);
			throw new InterruptedException(e.getMessage());
		}
		catch(AssertionError e) {
			if(!isKnownError(RestAssured.baseURI, requestString, e)) {
				logTestResult(LogStatus.FAIL, "Failed calling " + RestAssured.baseURI + "/" + requestString);
				throw new InterruptedException(e.getMessage());
			}
		}

		logTestResult(LogStatus.PASS, "Successful calling " + RestAssured.baseURI + "/" + requestString);
	}
	
	public void processRequestWithInvalidToken(String requestString) throws InterruptedException{
		
		String endpoint = TestcaseBaseAPI.config.getString("EndPoint_AccessAPI_" + TestcaseBaseAPI.testingEnv);
		RestAssured.baseURI = endpoint;
		
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
			if(!isKnownError(RestAssured.baseURI, requestString, e)) {
				logTestResult(LogStatus.FAIL, "401 is NOT returned as expected by calling " + endpoint + "/" + requestString + " with an invalid JWT token.");
				throw new InterruptedException(e.getMessage());
			}
		}
		
		logTestResult(LogStatus.PASS, "401 is returned as expected by calling " + endpoint + "/" + requestString + " with an invalid JWT token.");
	}
}
