package com.roames.test.objects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

public class FilesTabLocators {	

	@FindBy(css="input[ng-model='search.text']")
	public WebElement txtSearch;
}
