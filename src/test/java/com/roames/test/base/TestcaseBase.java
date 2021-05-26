package com.roames.test.base;
/*
 * Author: Ray.Chen@roames.com.au
 * 
 * Function: FUGRO Roames Portal Base Testcase Class
 * 				Every testcase class should inherit from this base class.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.paulhammant.ngwebdriver.NgWebDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.roames.test.utilities.AESEnryption;
import com.roames.test.utilities.ExcelReader;
import com.roames.test.utilities.ExtentManager;
import com.roames.test.utilities.PDFTestReport;
import com.roames.test.utilities.TestcaseUtilities;

public class TestcaseBase {
	
	// Global variables definition
	public static String sheetName;
	public static String testcaseConfigurationsheetName;
	public static PropertiesConfiguration config;
	public static String testingEnv;
	public static ExtentReports exntentReport = ExtentManager.getInstance();
	public static Logger log = Logger.getLogger("roamesPortalLogger");
	public static AESEnryption encrypter;
	
	// ThreadLocal variables definition
	public static ThreadLocal<ExcelReader> testcaseExcel = new ThreadLocal<ExcelReader>();
	public static ThreadLocal<File> jsonDataFile = new ThreadLocal<File>();
	public static ThreadLocal<String> testcaseName = new ThreadLocal<String>();
	public static ThreadLocal<Integer> currentRow = new ThreadLocal<Integer>();
	public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();	
	public static ThreadLocal<WebDriverWait> wait = new ThreadLocal<WebDriverWait>();
	public static ThreadLocal<PDFTestReport> pdfRpt = new ThreadLocal<PDFTestReport>();
	public static ThreadLocal<String> browser = new ThreadLocal<String>();
	public static ThreadLocal<String > startTime = new ThreadLocal<String>();
	public static ThreadLocal<String > endTime = new ThreadLocal<String>();
	
	//public static NgWebDriver ngWebDriver;		
	//public static ExcelReader excel = new ExcelReader(
	//		System.getProperty("user.dir") + "//src//test//resources//properties//Testcases Configuration.xlsx");	
	//public static FileInputStream fis;
	//public static FileOutputStream fos;
	
	/**
	 * Base class constructor, to load test configuration.
	 */
	public TestcaseBase() {
		try {			 
			
			if (config == null) {
			 config = new PropertiesConfiguration(System.getProperty("user.dir") + "//src//test//resources//properties//Config.properties");
			
			sheetName = config.getString("masterSheetName");
			testcaseConfigurationsheetName =config.getString("testcaseConfigurationSheetName");			
						
			// Testing env input parameters
			 if (System.getProperty("Environment") != null && !System.getProperty("Environment").isEmpty()) {
				 testingEnv = System.getProperty("Environment");
				//TestcaseUtilities.updateTestConfiguration("testingEnv", testingEnv);
			 }else {
				 testingEnv = config.getString("testingEnv");
			 }
			 
			 System.out.println("Running test in " + testingEnv);
			
			// fis = new FileInputStream(	System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			 
			 //config.load(fis);
			 log.debug("Test configuration file 'Config.properties' is loaded!");
			// fis.close();
			 

				//fos = new FileOutputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			//config.setProperty("sampleAttibute", "999");
			//config.store(fos, "");
			 
			 if (encrypter == null) {
				 encrypter = new AESEnryption();
			 }
			}
			 
			setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setupTestcasename() {
		 //testcaseName = getClass().getSimpleName();
		setTestcaseName(getClass().getSimpleName());
	}

	/**
	 * For each test case, pre processing / loading the test data in the spreadsheet before running it. 
	 */
	public void preProcessingTestData() {		
		
		//testcaseName = getClass().getSimpleName();
		log.debug("Pre processing test data sheet for '" + testcaseName + "' started." );
		//testcaseExcel = new ExcelReader(System.getProperty("user.dir") + "//src//test//resources//testdata//excel//" +testcaseName + ".xlsx");
		setTestcaseExcel(new ExcelReader(System.getProperty("user.dir") + "//src//test//resources//testdata//excel//" +testcaseName + ".xlsx"));
		
		ExcelReader excelExternal;
		 String excelFileFolder = System.getProperty("user.dir") + "//src//test//resources//testdata//excel//";
		 List<String[]> externalParameters = new ArrayList();
		 
		int rowsInMaster = getTestcaseExcel().getRowCount("ExternalData");	
		int colsInMaster = getTestcaseExcel().getColumnCount("ExternalData");
		String masterDataSheetName = TestcaseBase.config.getString("masterSheetName");
		String externalDataSheetName = TestcaseBase.config.getString("externalSheetName");
		
		int currCol = 0;		
		boolean rowFinished = false;
		String[] parameter; 
		
		for (int rNum = 2; rNum <= rowsInMaster; rNum++) {
					
			currCol = 0;
			 rowFinished = false;
			 
			 // Collect all the parameters for one row
			 while (!rowFinished) {
					if (getTestcaseExcel().getCellData(externalDataSheetName, currCol, rNum).isEmpty()){
						rowFinished = true;						
					}else {
						parameter = new String[] {String.valueOf(rNum), getTestcaseExcel().getCellData(externalDataSheetName, currCol, rNum), getTestcaseExcel().getCellData(externalDataSheetName, currCol+1, rNum), getTestcaseExcel().getCellData(externalDataSheetName, currCol+2, rNum), getTestcaseExcel().getCellData(externalDataSheetName, currCol+3, rNum)};
						externalParameters.add(parameter);
						currCol = currCol + 4;
					}				 
			 }
		}

		// Update test data from external excels
		for(int i=0;i<externalParameters.size();i++){
			
			parameter = externalParameters.get(i);
			int rowNumberToUpdate = Integer.valueOf(parameter[0]);
			String externalExcelFileName = parameter[1];
			String externalColName = parameter[2];
			int externalRowlNumber = (int)Double.parseDouble(parameter[3]);
			String localExcelColName = parameter[4];

			excelExternal = new  ExcelReader(excelFileFolder +externalExcelFileName );
			String externalParmVal = excelExternal.getCellData(masterDataSheetName, externalColName, externalRowlNumber);
			getTestcaseExcel().setCellData(masterDataSheetName, localExcelColName, rowNumberToUpdate, externalParmVal);
		}	
		
		log.debug("Pre processing test data sheet for '" + testcaseName + "' finished." );
	}
	
	
	
	// ============ ThreadLocal For currentRow ==========
	public static void setCurrentRow(Integer rowNumber){		
		currentRow.set(rowNumber);
	}
	
	public static Integer getCurrentRow(){		
		return currentRow.get();
	}	
	
	// ============ ThreadLocal For testcaseExcel =======
	public static void setTestcaseExcel(ExcelReader reader){		
		testcaseExcel.set(reader);
	}
	
	public static ExcelReader getTestcaseExcel(){		
		return testcaseExcel.get();
	}
	
	// ============ ThreadLocal For jsonDataFile ========
	public static void setJsonDataFile(File file){		
		jsonDataFile.set(file);
	}

	public static File getJsonDataFile(){		
		return jsonDataFile.get();
	}
	
	// ============ ThreadLocal For testcaseName ========
	public static void setTestcaseName(String tcName){		
		testcaseName.set(tcName);
	}

	public static String getTestcaseName(){		
		return testcaseName.get();
	}
	
	// ============ ThreadLocal For wait ========
	public static void setWait(WebDriverWait wdw){		
		wait.set(wdw);
	}

	public static WebDriverWait getWait(){		
		return wait.get();
	}
	
	// ============ ThreadLocal For extentTest ========
	public static void setExtentTest(ExtentTest exTest){		
		extentTest.set(exTest);
	}

	public static ExtentTest getExtentTest(){		
		return extentTest.get();
	}
	
	// ============ ThreadLocal For driver ========
	public static void setDriver(WebDriver dr){		
		driver.set(dr);
	}

	public static WebDriver getDriver(){		
		return driver.get();
	}
	
	// ============ ThreadLocal For PDFTestReport ========
	public static void setPDFTestReport(PDFTestReport pdfReport){		
		pdfRpt.set(pdfReport);
	}

	public static PDFTestReport getPDFTestReport(){		
		return pdfRpt.get();
	}	
	
	// ============ ThreadLocal For browser ========
	public static void setBrowser(String b){		
		browser.set(b);
	}

	public static String getBrowser(){		
		return browser.get();
	}	
	
	// ============ ThreadLocal For start time ========
	public static void setStartTime(String time){		
		startTime.set(time);
	}

	public static String getStartTime(){		
		return startTime.get();
	}

	// ============ ThreadLocal For end time ========
	public static void setEndTime(String time){		
		endTime.set(time);
	}

	public static String getEndTime(){		
		return endTime.get();
	}
}
