package com.roames.test.utilities.testrail;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.JSONObject;

public class TestRailReportAPI {

	public static void main(String[] args) throws MalformedURLException, IOException, APIException {
		// TODO Auto-generated method stub
		APIClient tesrailAPI = new APIClient("https://fugro.testrail.io/");
		tesrailAPI.setUser("automation.portaltest@roames.com.au");
		tesrailAPI.setPassword("P@ssword1");
		JSONObject response = (JSONObject) tesrailAPI.sendGet("run_report/2");
		System.out.print(response);
	}

}
