package com.roames.test.wip;
/*
 *  Author: Ray Chen
 */
import java.util.ArrayList;
import java.util.List;

import com.roames.test.base.TestcaseBase;
import com.roames.test.utilities.ExcelReader;

public class loadData {

	public static void main(String[] args) {
		 ExcelReader excelMaster = new ExcelReader( "C:\\Courses\\Automation Architect_Selenium WebDriver\\PageObjectwithFactories\\PageObjectwithFactories\\src\\test\\resources\\testdata\\googleTest.xlsx");
		 ExcelReader excelExternal;
		 String excelFileFolder = "C:\\Courses\\Automation Architect_Selenium WebDriver\\PageObjectwithFactories\\PageObjectwithFactories\\src\\test\\resources\\testdata\\";
		 List<String[]> externalParameters = new ArrayList();
		 
		int rowsInMaster = excelMaster.getRowCount("ExternalData");
		int colsInMaster = excelMaster.getColumnCount("ExternalData");
		String masterDataSheetName = TestcaseBase.config.getString("masterSheetName");
		String externalDataSheetName = TestcaseBase.config.getString("externalSheetName");
		
		int currCol = 1;		
		boolean rowFinished = false;
		String[] parameter; 
		
		for (int rNum = 2; rNum <= rowsInMaster; rNum++) {
					
			currCol = 0;
			 rowFinished = false;
			 
			 // Collect all the parameters for one row
			 while (!rowFinished) {

					if (excelMaster.getCellData(externalDataSheetName, currCol, rNum).isEmpty()){
						rowFinished = true;						
					}else {
						parameter = new String[] {String.valueOf(rNum), excelMaster.getCellData(externalDataSheetName, currCol, rNum), excelMaster.getCellData(externalDataSheetName, currCol+1, rNum), excelMaster.getCellData(externalDataSheetName, currCol+2, rNum), excelMaster.getCellData(externalDataSheetName, currCol+3, rNum)};
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
			excelMaster.setCellData(masterDataSheetName, localExcelColName, rowNumberToUpdate, externalParmVal);
		}
	}

}
