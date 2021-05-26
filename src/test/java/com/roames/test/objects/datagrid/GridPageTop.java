package com.roames.test.objects.datagrid;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GridPageTop {
	
	@FindBy(xpath="//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text navigation-button MuiButton-textPrimary']//span//div[text()='Bays']")
	public WebElement btn_Navigation_To_Bays;
	
	@FindBy(xpath="//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text navigation-button MuiButton-textPrimary']//span//div[text()='Intrusions - All']")
	public WebElement btn_Navigation_To_Intrusions;	
	
	@FindBy(xpath="//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text navigation-button MuiButton-textPrimary']//span//div[text()='Poles']")
	public WebElement btn_Navigation_To_Poles;
	
	@FindBy(xpath="//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text navigation-button MuiButton-textPrimary']//span//div[text()='Spans']")
	public WebElement btn_Navigation_To_Spans;
}
