package com.roames.test.listeners;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.roames.test.constant.TestType;
import com.roames.test.utilities.PDFTestReport;
import com.roames.test.utilities.TestcaseUtilities;
import com.roames.test.utilities.testrail.TestRailUtility;

public class CustomListeners extends TestcaseBase implements ITestListener,ISuiteListener, IAlterSuiteListener {

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
		//try {
			TestcaseUtilities.captureScreenshot();
		//} catch (IOException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		
		log.info("Test: '" + getTestcaseName() + "' is failed.");
		
		getExtentTest().log(LogStatus.FAIL, getTestcaseName() +" Failed with exception : "+arg0.getThrowable());
		getExtentTest().log(LogStatus.INFO, getExtentTest().addScreenCapture(TestcaseUtilities.getScreenshotName()));		
		
		Reporter.log("Click to see Screenshot");
		Reporter.log("<a target=\"_blank\" href="+TestcaseUtilities.getScreenshotName()+">Screenshot</a>");
		Reporter.log("<br>");
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\" href="+TestcaseUtilities.getScreenshotName()+"><img src="+TestcaseUtilities.getScreenshotName()+" height=200 width=200></img></a>");
		exntentReport.endTest(getExtentTest());
		exntentReport.flush();

		if(getPDFTestReport() != null) {
			// MAR 6 2019, capture the error screenshot into PDF report when test fails
			getPDFTestReport().logTestResult(LogStatus.FAIL.toString(), getTestcaseName() + " Failed with exception : "+arg0.getThrowable());
			getPDFTestReport().insertImg(TestcaseUtilities.getScreenshotPath() + TestcaseUtilities.getScreenshotName());
			getPDFTestReport().pdfFlush();
		}
		
		if (TestcaseUtilities.config.getString("testrail_AutoResultUpdate").equals("true")){
			new TestRailUtility(TestType.GUI).updateTestResult();
		}
		
		System.out.println("Test: '" + getTestcaseName() + "' failed." + arg0.getThrowable());
		
		if(TestcaseBase.driver!=null){
			PageBase.logoutPortal();	
		}
	}

	public void onTestSkipped(ITestResult arg0) {

		log.info("Test: '" + testcaseName + "' is skipped." + arg0.getThrowable());
		//test.log(LogStatus.SKIP, arg0.getName().toUpperCase()+" Skipped the test as the Run mode is NO");
		getExtentTest().log(LogStatus.SKIP, getTestcaseName() + " / Row(" + getCurrentRow() + ") Skipped." + arg0.getThrowable());
		exntentReport.endTest(getExtentTest());
		exntentReport.flush();
		
		if(getPDFTestReport() != null) {
			// MAR 6 2019, capture the text into PDF report when test is skipped
			getPDFTestReport().logTestResult(LogStatus.SKIP.toString(), getTestcaseName() + " / Row(" + getCurrentRow() + ") Skipped." + arg0.getThrowable());
			getPDFTestReport().pdfFlush();
		}
		
		if (TestcaseUtilities.config.getString("testrail_AutoResultUpdate").equals("true")){
			new TestRailUtility(TestType.GUI).updateTestResult();
		}
		
		System.out.println("Test: '" + getTestcaseName() + "' is skipped." + arg0.getThrowable());
	}


	public void onTestStart(ITestResult arg0) {

		//test = rep.startTest(arg0.getName().toUpperCase());
		
		//extentTest = exntentReport.startTest(testcaseName);
		setExtentTest(exntentReport.startTest(getTestcaseName()));
		
		// Generate PDF report only if 'testrail_AutoResultUpdate' set to true
		if (TestcaseUtilities.config.getString("testrail_AutoResultUpdate").equals("true")){
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
		try {
			TestcaseBase.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date date1;

			date1 = format.parse(TestcaseBase.getStartTime());
			Date date2 = format.parse(TestcaseBase.getEndTime());
			long duration = (date2.getTime() - date1.getTime()) / 1000; 

			log.info("Test: '" + getTestcaseName() + "' is finished successfully.");
			//test.log(LogStatus.PASS, arg0.getName().toUpperCase()+" PASS");
			getExtentTest().log(LogStatus.PASS, getTestcaseName() +": " + getExtentTest().getRunStatus());
			
			(new PageBase()).logTestResult(LogStatus.INFO, "Test execution duration: " + duration + " second(s).");
			
			exntentReport.endTest(getExtentTest());
			exntentReport.flush();

			if(getPDFTestReport() != null) {
				getPDFTestReport().pdfFlush();
			}

			if (TestcaseUtilities.config.getString("testrail_AutoResultUpdate").equals("true")){
				new TestRailUtility(TestType.GUI).updateTestResult();
			}

			System.out.println("Test: '" + getTestcaseName() + "' " + getExtentTest().getRunStatus());

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
