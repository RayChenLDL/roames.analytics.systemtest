package com.roames.test.objects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

public class ReportsTabLocators {	

	@FindBy(xpath="//input[@ng-model='search'][@type='text']")
	public WebElement txtSearch;	

}
