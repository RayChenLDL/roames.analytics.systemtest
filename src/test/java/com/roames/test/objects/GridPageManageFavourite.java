package com.roames.test.objects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GridPageManageFavourite {
	
	@FindBy(xpath="//button/span[text()='Cancel']")
	public WebElement btnCancel;

	@FindBy(xpath="//button/span[text()='Delete']")
	public WebElement btnDelete;
	
	@FindBy(xpath="//button/span[text()='Submit']")
	public WebElement btnSubmit;

	@FindBy(xpath="//input[@id='name']")
	public WebElement txtFavouriteName;
}
