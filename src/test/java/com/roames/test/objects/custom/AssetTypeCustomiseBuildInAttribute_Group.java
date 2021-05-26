package com.roames.test.objects.custom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.roames.test.base.PageBase;
import com.roames.test.utilities.TestcaseUtilities;

public class AssetTypeCustomiseBuildInAttribute_Group extends PageBase {
	
	public String groupName;
	
	public WebElement linkGroupName;
	private String linkGroupName_XPath;
	
	public WebElement txtGroup;
	private String txtGroup_XPath;
	
	public AssetTypeCustomiseBuildInAttribute_Group(String groupName) {
		this.groupName = groupName;
		linkGroupName_XPath = "//a[@class='accordion-toggle'][contains(text(),'" + groupName + "')]";
		txtGroup_XPath = "//a[@class='accordion-toggle'][contains(text(),'" + groupName + "')]//ancestor::div[@class='panel-heading']//input[@type='text']";
	}
	
	public WebElement getTxtGroup() {
		txtGroup = TestcaseUtilities.getDriver().findElement(By.xpath(txtGroup_XPath));
		return txtGroup;
	}
	
	public WebElement getLinkGroupName_XPath() {
		linkGroupName =  TestcaseUtilities.getDriver().findElement(By.xpath(linkGroupName_XPath));
		return linkGroupName;
	}
	
}
