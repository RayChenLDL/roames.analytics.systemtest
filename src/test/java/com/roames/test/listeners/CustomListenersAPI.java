package com.roames.test.listeners;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.IAlterSuiteListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.xml.XmlSuite;

import com.relevantcodes.extentreports.LogStatus;
import com.roames.test.base.PageBase;
import com.roames.test.base.TestcaseBase;
import com.roames.test.base.TestcaseBaseAPI;
import com.roames.test.constant.TestType;
import com.roames.test.utilities.MonitoringMail;
import com.roames.test.utilities.PDFTestReport;
import com.roames.test.utilities.TestConfig;
import com.roames.test.utilities.TestcaseUtilities;
import com.roames.test.utilities.TestcaseUtilitiesAPI;
import com.roames.test.utilities.testrail.TestRailUtility;

public class CustomListenersAPI extends TestcaseBaseAPI implements ITestListener,ISuiteListener, IAlterSuiteListener {

	public 	String messageBody;
	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailure(ITestResult arg0) {

		System.setProperty("org.uncommons.reportng.escape-output","false");
		
		log.info("Test: '" + getTestcaseName() + "' is failed.");
		
		getExtentTest().log(LogStatus.FAIL, getTestcaseName() +" Failed with exception : "+arg0.getThrowable());	
		
		exntentReport.endTest(getExtentTest());
		exntentReport.flush();

		if(getPDFTestReport() != null) {
			getPDFTestReport().pdfFlush();
		}		

		// Update TestRail if required
		if (TestcaseUtilitiesAPI.config.getString("testrail_AutoResultUpdate").equals("true")){
			new TestRailUtility(TestType.API).updateTestResult();
		}

		System.out.println("Test: '" + getTestcaseName() + "' failed." + arg0.getThrowable());
		
	}

	public void onTestSkipped(ITestResult arg0) {

		log.info("Test: '" + testcaseName + "' is skipped." + arg0.getThrowable());
		//test.log(LogStatus.SKIP, arg0.getName().toUpperCase()+" Skipped the test as the Run mode is NO");
		getExtentTest().log(LogStatus.SKIP, getTestcaseName() + " / Row(" + getCurrentRow() + ") Skipped." + arg0.getThrowable());
		exntentReport.endTest(getExtentTest());
		exntentReport.flush();

		if(getPDFTestReport() != null) {
			getPDFTestReport().pdfFlush();
		}

		// Update TestRail if required
		if (TestcaseUtilitiesAPI.config.getString("testrail_AutoResultUpdate").equals("true")){
			new TestRailUtility(TestType.API).updateTestResult();
		}
				
		System.out.println("Test: '" + getTestcaseName() + "' is skipped." + arg0.getThrowable());
	}


	public void onTestStart(ITestResult arg0) {

		//test = rep.startTest(arg0.getName().toUpperCase());
		
		//extentTest = exntentReport.startTest(testcaseName);
		setExtentTest(exntentReport.startTest(getTestcaseName()));
		
		// Generate PDF report only if 'testrail_AutoResultUpdate' set to true
		if (TestcaseUtilitiesAPI.config.getString("testrail_AutoResultUpdate").equals("true")){
			setPDFTestReport(new PDFTestReport(System.getProperty("user.dir") + "\\target\\pdf-reports\\", getTestcaseName(), testingEnv));
		}
		
		//if (!TestcaseUtilities.isTestRunnable(testcaseName, excel)){
		//	log.info("Test: '" + testcaseName + "' is skipped.");
		//	throw new SkipException("Skipped the testcase: " + testcaseName +" as the Run Flag is NO.");			
		//}	

		log.info("Test: '" + getTestcaseName() + "' is started.");
		System.out.println("============================== TEST START ===================================");
		System.out.println("Test: '" + getTestcaseName() + "' is started.");
	}

	public void onTestSuccess(ITestResult arg0) {

		log.info("Test: '" + getTestcaseName() + "' is finished successfully.");
		//test.log(LogStatus.PASS, arg0.getName().toUpperCase()+" PASS");
		getExtentTest().log(LogStatus.PASS, getTestcaseName() +": " + getExtentTest().getRunStatus());
		exntentReport.endTest(getExtentTest());
		exntentReport.flush();

		if(getPDFTestReport() != null) {
			getPDFTestReport().pdfFlush();
		}
		
		// Update TestRail if required
		if (TestcaseUtilitiesAPI.config.getString("testrail_AutoResultUpdate").equals("true")){
			new TestRailUtility(TestType.API).updateTestResult();
		}

		System.out.println("Test: '" + getTestcaseName() + "' " + getExtentTest().getRunStatus());		
	}

	public void onFinish(ISuite arg0) {
		/*
		MonitoringMail mail = new MonitoringMail();
		 
		try {
			messageBody = "http://" + InetAddress.getLocalHost().getHostAddress()
					+ ":8080/job/RoamesTestAutomation/Extent_Report/";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		try {
			mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
		
	}

	public void onStart(ISuite arg0) {
		// TODO Auto-generated method stub
		
	}
	
    @Override
    public void alter(List suites) {
        //XmlSuite suite = (XmlSuite) suites.get(0);
        //suite.setThreadCount(1);
        //System.out.println("The current Thread Count is: " + suite.getThreadCount() + ".");
          
    	if (System.getProperty("ParallelThreads") != null) {
    		XmlSuite suite = (XmlSuite) suites.get(0);
            suite.setThreadCount(Integer.valueOf(System.getProperty("ParallelThreads")));
    	}        
    }
}
