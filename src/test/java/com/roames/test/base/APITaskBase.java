package com.roames.test.base;

import java.util.Arrays;

import com.relevantcodes.extentreports.LogStatus;
import com.roames.test.utilities.TestcaseUtilitiesAPI;

import io.restassured.response.Response;

public class APITaskBase {
	
	/**
	 * 25 June 2019: To check if API failed with known errors or not
	 * @param endpoint
	 * @param requestString
	 * @param res
	 * @return
	 */
	public boolean isKnownError(String endpoint, String requestString, Response res) {
		boolean knownError = false;
		int actualStatusCode = 0;
		String knownErrors = TestcaseBaseAPI.config.getString("API_KNOWN_ERROR_" + TestcaseBaseAPI.testingEnv);		
		String[] knownErrorCodes = knownErrors.split(";");
		
		for(int i=0;i<knownErrorCodes.length;i++) {
			int code = Integer.valueOf(knownErrorCodes[i]);
			actualStatusCode = res.then().log().ifStatusCodeIsEqualTo(code).extract().statusCode();
		}

		if(actualStatusCode !=0) {
			knownError = true;
			logTestResult(LogStatus.WARNING, "****** API TEST AUTOMATION WARNING ****** Failed calling " + endpoint + "/" + requestString + " with known error code: " + actualStatusCode);
		}
		return knownError;
	}
	
	public void logTestResult(LogStatus testStatus, String testDesc)
	{
		TestcaseUtilitiesAPI.getExtentTest().log(testStatus, testDesc);
		TestcaseUtilitiesAPI.log.info(testDesc);
		System.out.println(testStatus + ": " + testDesc);

		if(TestcaseUtilitiesAPI.getPDFTestReport() != null) {
			TestcaseUtilitiesAPI.getPDFTestReport().logTestResult(testStatus.toString(), testDesc);
		}
	}
	
	/**
	 * 25 June 2019: To check if API failed with known errors or not
	 * @param e
	 * @return
	 */
	public boolean isKnownError(String endpoint, String requestString, AssertionError e) {
		boolean knownError = false;
		
		// All known error codes from config
		String knownErrors = TestcaseBaseAPI.config.getString("API_KNOWN_ERROR_" + TestcaseBaseAPI.testingEnv);		
		String[] knownErrorCodes = knownErrors.split(";");
		
		// Actual error code
		String eString = e.toString();
		String actualStatusCode = eString.substring(eString.indexOf("but was <") + 9, eString.lastIndexOf(">"));		
		
		if(Arrays.asList(knownErrorCodes).contains(actualStatusCode)) {
			knownError = true;
			logTestResult(LogStatus.WARNING, "****** API TEST AUTOMATION WARNING ****** Failed calling " + endpoint + "/" + requestString + " with known error code: " + actualStatusCode);
		}
		return knownError;
	}

}
