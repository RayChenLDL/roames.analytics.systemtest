package com.roames.test.objects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

public class PopupWindowExportDataWarningLocators {
	
	@FindBy(xpath="//input[@id='name']")
	public WebElement txtFileName;
	
	
	@FindBy(xpath="//button//span[text()='Yes']")
	public WebElement btnYes;
	
	@FindBy(xpath="//button//span[text()='No']")
	public WebElement btnNo;
	
	@FindBy(xpath="//button//span[text()='Cancel']")
	public WebElement btnCANCEL;
	
	@FindBy(xpath="//button//span[text()='Submit']")
	public WebElement btnDownload;
	
	@FindBy(xpath="//button//span[text()='OK']")
	public WebElement btnOK;
	
}
