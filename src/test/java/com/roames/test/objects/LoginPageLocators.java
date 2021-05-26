package com.roames.test.objects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPageLocators {
	
	@FindBy(name="email")
	public WebElement userEmail;
	
	@FindBy(name="password")
	public WebElement password;
		
	@FindBy(css=".auth0-lock-submit")
	public WebElement btnLogin;
	
	@FindBy(css=".toast-message")
	public WebElement msgPleaseClickToLogin;
	
	@FindBy(css="#identifierId")
	//@FindBy(name="identifier")
	//@FindBy(xpath="//*[@id='identifierId']")
	public WebElement txtEmailPrefix;
	
	@FindBy(css="#identifierNext")
	public WebElement btnEmailNext;
	
	@FindBy(css="#passwordNext")
	public WebElement btnPasswordNext;
	
	}
