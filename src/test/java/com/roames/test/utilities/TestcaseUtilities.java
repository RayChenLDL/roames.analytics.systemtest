package com.roames.test.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import java.nio.file.Files;
import java.sql.Timestamp;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;

import com.jayway.jsonpath.JsonPath;
import com.relevantcodes.extentreports.LogStatus;
import com.roames.test.base.TestcaseBase;

public class TestcaseUtilities  extends TestcaseBase {
	
	public static ThreadLocal<String> screenshotPath = new ThreadLocal<String>();
	public static ThreadLocal<String> screenshotName = new ThreadLocal<String>();

	
	/**
	 * Capture the screenshot along the way for End to End test. Accessible the page task
	 * @throws IOException
	 */
	public static void captureScreenshot() {
		
		try {

			File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);

			//Date d = new Date();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			//screenshotName = timestamp.toString().replace(":", "_").replace(" ", "_") + ".jpg";
			setScreenshotName(timestamp.toString().replace(":", "_").replace(" ", "_") + "_" + CommonUtilities.getRandomNumber(100000, 999999) + ".jpg");
			setScreenshotPath(System.getProperty("user.dir") + "//target//surefire-reports//html//");

			//FileUtils.copyFile(scrFile,
			//		new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\" + screenshotName));

			Files.copy(scrFile.toPath(), 
					(new File(System.getProperty("user.dir") + "//target//surefire-reports//html//" + getScreenshotName())).toPath());
		}
		catch(Exception e) {
			log.info(testcaseName + " failed with exception: " + e.toString());
			getExtentTest().log(LogStatus.FAIL, testcaseName + " failed with exception: " + e.toString());
		}
	}
	
	/**
	 * Test case level common data provider using spreadsheet
	 * @param m
	 * @return
	 */
	@DataProvider(name="dp")
	public Object[][] getData(Method m) {
		
		//testcaseName = m.getName();
		//testcaseExcel = new ExcelReader(System.getProperty("user.dir") + "\\src\\test\\resources\\testdata\\" +testcaseName + ".xlsx");
		int rows = getTestcaseExcel().getRowCount(sheetName);
		int cols = getTestcaseExcel().getColumnCount(sheetName);

		Object[][] data = new Object[rows - 1][1];
		
		Hashtable<String,String> table = null;

		for (int rowNum = 2; rowNum <= rows; rowNum++) { // 2

			table = new Hashtable<String,String>();
			
			for (int colNum = 0; colNum < cols; colNum++) {

				// data[0][0]
				table.put(getTestcaseExcel().getCellData(sheetName, colNum, 1), getTestcaseExcel().getCellData(sheetName, colNum, rowNum));
				data[rowNum - 2][0] = table;
			}
		}

		return data;

	}
	
/**
 * Function: Capture the dynamic data generated by the AUT and export to the testcase excel
 * @param parmName
 * @param parmVal
 * @return
 */
public static boolean outputParamter(String parmName, String parmVal){
		
	return getTestcaseExcel().setCellData(sheetName, parmName,getCurrentRow()+1, parmVal);
				
	}

/**
 * Function: To update property in Config.properties
 * @param parmName
 * @param parmVal
 */
public static void updateTestConfiguration(String parmName, String parmVal){

	try {
		TestcaseBase.config.setProperty(parmName, parmVal);
		TestcaseBase.config.save();
	} catch (ConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}				
	}

/**
 * Function: To determine if necessary to skip the current testcase
 * @param testName
 * @param excel
 * @return
 */
public static boolean isTestRunnable(String testName, ExcelReader excel){
	
	int rows = excel.getRowCount(testcaseConfigurationsheetName);
	
	
	for(int rNum=2; rNum<=rows; rNum++){
		
		String testCase = excel.getCellData(testcaseConfigurationsheetName, "TestcaseID", rNum);
		
		if(testCase.equalsIgnoreCase(testName)){
			
			String runmode = excel.getCellData(testcaseConfigurationsheetName, "RunFlag", rNum);
			
			if(runmode.equalsIgnoreCase("Y"))
				return true;
			else
				return false;
		}		
	}
	return false;
}

/*
 * Test case level common data provider using JSON file
 */
@DataProvider(name="dpJson")
public Object[][] getDataJSON(Method m) throws IOException {	
	
	//if (jsonDataFile == null) {
	//jsonDataFile = new File(System.getProperty("user.dir")+"//src//test//resources//testdata//json//" + testcaseName + ".json");
	setJsonDataFile(new File(System.getProperty("user.dir")+"//src//test//resources//testdata//json//" + getTestcaseName() + ".json"));
	//}
		
	//List<LinkedHashMap> jsonFileDataRows = JsonPath.read(jsonDataFile, "$");
	List<LinkedHashMap> jsonFileDataRows = JsonPath.read(getJsonDataFile(), "$");
	int rows = jsonFileDataRows.size();
	
	Object[][] data = new Object[rows][1];
	
	Hashtable<String,String> table = null;
	
	// Total number of rows for the current test scripts
	for (int rowNum=0;rowNum<jsonFileDataRows.size();rowNum++) {
		
		LinkedHashMap currRow = jsonFileDataRows.get(rowNum);
		Iterator it = currRow.keySet().iterator();
		table = new Hashtable<String,String>();
		int cols = currRow.keySet().size();		
		
		// For each row, retrieve all the key/value pair
		while(it.hasNext()) {
			Object key =it.next();			
			
			table.put(key.toString(), currRow.get(key).toString());
			data[rowNum][0] = table;
		}
	}
	
	return data;
}

	// ============ ThreadLocal For screenshotPath ========
	public static void setScreenshotPath(String path){		
		screenshotPath.set(path);
	}
	
	public static String getScreenshotPath(){		
		return screenshotPath.get();
	}
	
	// ============ ThreadLocal For screenshotName ========
	public static void setScreenshotName(String name){		
		screenshotName.set(name);
	}
	
	public static String getScreenshotName(){		
		return screenshotName.get();
	}
}
