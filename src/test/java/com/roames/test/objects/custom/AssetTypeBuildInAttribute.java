package com.roames.test.objects.custom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.roames.test.base.PageBase;
import com.roames.test.utilities.TestcaseUtilities;

public class AssetTypeBuildInAttribute extends PageBase {
	public String attributeName;
	public WebElement attributeCell;
	private String attributeCell_XPath;
	public WebElement btnCustomise;
	private String btnCustomise_XPath;
	public WebElement btnPermissions;
	private String btnPermissions_XPath;
	
	public AssetTypeBuildInAttribute(String attributeName) {
		this.attributeName = attributeName;
		attributeCell_XPath = "//span[@class='attr-name-subtitle ng-hide'][contains(text(), '" + attributeName + "')]/ancestor::TD";	
		btnCustomise_XPath = "//span[@class='attr-name-subtitle ng-hide'][contains(text(), '" + attributeName + "')]/ancestor::tbody//span[contains(text(),'Customise')]";
		btnPermissions_XPath = "//span[@class='attr-name-subtitle ng-hide'][contains(text(), '" + attributeName + "')]/ancestor::tbody//span[contains(text(),'Permissions')]";
	}
	
	public WebElement getAttributeCell() {
		attributeCell = TestcaseUtilities.getDriver().findElement(By.xpath(attributeCell_XPath));
		return attributeCell;
	}
	
	public WebElement getBtnCustomise() {
		btnCustomise =  TestcaseUtilities.getDriver().findElement(By.xpath(btnCustomise_XPath));
		return btnCustomise;
	}
	
	public WebElement getBtnPermissions() {
		btnPermissions =  TestcaseUtilities.getDriver().findElement(By.xpath(btnPermissions_XPath));
		return btnPermissions;
	}
}
