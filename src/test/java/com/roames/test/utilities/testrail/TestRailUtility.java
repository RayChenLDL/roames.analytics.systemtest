package com.roames.test.utilities.testrail;

import java.util.List;

import org.json.simple.JSONObject;

import com.rmn.testrail.entity.Project;
import com.rmn.testrail.entity.TestInstance;
import com.rmn.testrail.entity.TestPlan;
import com.rmn.testrail.entity.TestResult;
import com.rmn.testrail.entity.TestRun;
import com.rmn.testrail.service.TestRailService;
import com.roames.test.base.TestcaseBase;
import com.roames.test.base.TestcaseBaseAPI;
import com.roames.test.constant.TestType;
import com.roames.test.utilities.AWSUtilities;

public class TestRailUtility{

	public static String testrail_AutoResultUpdate;
	public static String testrail_URL;
	public static String testrail_ID;
	public static String testrail_User;	
	public static String testrail_Password;
	public static String testrail_Artifact_Location;
	public static String testrail_Project;
	public static String testrail_TestPlan;
	public static String testrail_TestRun;
	public static TestRailService testRailService;
	public static Project project;
	public static TestPlan plan;
	public static TestRun targetTestRun = null;
	public TestType testtype;
	public String testcaseName;
	public String actualTestResult;
	
	public TestRailUtility(TestType testtype) {
		try {
			this.testtype = testtype;
			switch (testtype) {
			case GUI:
				testrail_AutoResultUpdate = TestcaseBase.config.getString("testrail_AutoResultUpdate");
				testrail_URL = TestcaseBase.config.getString("testrail_URL");
				testrail_ID = TestcaseBase.config.getString("testrail_ID");
				testrail_User = TestcaseBase.config.getString("testrail_User");	
				testrail_Password = TestcaseBase.encrypter.decrypt(TestcaseBase.config.getString("testrail_Password"));
				testrail_Artifact_Location = TestcaseBase.config.getString("testrail_Artifact_Location");
				testrail_Project = TestcaseBase.config.getString("testrail_Project_" + TestcaseBase.testingEnv);
				testrail_TestPlan = TestcaseBase.config.getString("testrail_TestPlan_" + TestcaseBase.testingEnv);
				testrail_TestRun = TestcaseBase.config.getString("testrail_TestRun_" + TestcaseBase.testingEnv);
				testcaseName = TestcaseBase.getTestcaseName();
				actualTestResult = TestcaseBase.getExtentTest().getRunStatus().toString();
				break;
			case API:
				testrail_AutoResultUpdate = TestcaseBaseAPI.config.getString("testrail_AutoResultUpdate");
				testrail_URL = TestcaseBaseAPI.config.getString("testrail_URL");
				testrail_ID = TestcaseBaseAPI.config.getString("testrail_ID");
				testrail_User = TestcaseBaseAPI.config.getString("testrail_User");	
				testrail_Password = TestcaseBaseAPI.encrypter.decrypt(TestcaseBaseAPI.config.getString("testrail_Password"));
				testrail_Artifact_Location = TestcaseBaseAPI.config.getString("testrail_Artifact_Location");
				testrail_Project = TestcaseBaseAPI.config.getString("testrail_Project_" + TestcaseBaseAPI.testingEnv);
				testrail_TestPlan = TestcaseBaseAPI.config.getString("testrail_TestPlan_" + TestcaseBaseAPI.testingEnv);
				testrail_TestRun = TestcaseBaseAPI.config.getString("testrail_TestRun_" + TestcaseBaseAPI.testingEnv);
				testcaseName = TestcaseBaseAPI.getTestcaseName();
				actualTestResult = TestcaseBaseAPI.getExtentTest().getRunStatus().toString();
				break;	
			}
			
			testRailService = new TestRailService(testrail_ID, testrail_User, testrail_Password);
			project = testRailService.getProjectByName(testrail_Project);
			plan = project.getTestPlanByName(testrail_TestPlan);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateTestResult() {

		try {
			if (targetTestRun == null) {

				List<TestRun> testRuns = plan.getTestRuns();

				for ( TestRun testRun: testRuns ) {

					if(testrail_TestRun.equalsIgnoreCase(testRun.getName())) {
						targetTestRun = testRun;
						break;
					}            
				}
			}		

			// Update TestRail result only it is told to
			if (testrail_AutoResultUpdate.equalsIgnoreCase("true")){

				List<TestInstance> tests = targetTestRun.getTests();  
				int testID = 0;

				for (TestInstance test: tests) {

					// TestRail test case name needs to match with automation script
					if (test.getTitle().equalsIgnoreCase(testcaseName)) {
						testID = test.getId();						
						
						TestResult result = new TestResult();	
						result.setVerdict(actualTestResult);
						
						if ("AWS_S3".equalsIgnoreCase(testrail_Artifact_Location)) {
							String URLToDisplay = new AWSUtilities(testtype).uploadFileToS3(System.getProperty("user.dir") + "\\target\\pdf-reports\\", testcaseName);
							result.setComment("Test result updated by automation: " + actualTestResult + ".\r\n\r\nPlease refer to the URL of the test artifact: \r\n" + URLToDisplay);
							testRailService.addTestResult(testID, result);
						}else if("TestRail".equalsIgnoreCase(testrail_Artifact_Location)){
							result.setComment("Test result updated by automation: " + actualTestResult + ".\r\n\r\nPlease find test artifact attached below: \r\n");
							
							// Get the TestResult from API call, so that the pdf attachment could be uploaded via API call
							TestResult testResultReturnedFromAPI = testRailService.addTestResult(testID, result);						

							// Upload PDF report as attachment for the test result (new features of TestRail v5.7 published on APR 2019
							APIClient testRailAPI = new APIClient(testrail_URL);
							testRailAPI.setUser(testrail_User);
							testRailAPI.setPassword(testrail_Password);
							JSONObject response = (JSONObject) testRailAPI.sendPost("add_attachment_to_result/" + testResultReturnedFromAPI.getId(), System.getProperty("user.dir") + "\\target\\pdf-reports\\" + testcaseName + ".pdf");
						}else {
							result.setComment("No test artifact is saved. Please check configuration of 'testrail_Artifact_Location' from Test Automation Framework." );
							testRailService.addTestResult(testID, result);
						}										
						
						String printMessage = "TestRail Updated: " + actualTestResult + " (" + testcaseName + ")";
						if ("Pass".equalsIgnoreCase(actualTestResult)) {
							printMessage = "TestRail Updated: V " + actualTestResult + " (" + testcaseName + ")";
						}else if("Fail".equalsIgnoreCase(actualTestResult)){
							printMessage = "TestRail Updated: X " + actualTestResult + " (" + testcaseName + ")";
						}
						System.out.println(printMessage); 

						break;
					}        	
				}

				// Fail to find test under Project / Test Plan / Test Run given
				if(testID == 0) {
					System.out.println("TestRail Updated: cannot find test '" + testcaseName + "' under Project:" + testrail_Project + " / Test Plan:" + testrail_TestPlan + " / Test Run:" + testrail_TestRun); 
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
