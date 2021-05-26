package com.roames.test.objects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TopNavLocators {
	
	@FindBy(css=".home-tabs")
	public WebElement homeTab;

	@FindBy(css=".file-tabs")
	public WebElement fileTab;
	
	@FindBy(css=".file-tabs2")
	public WebElement driveTab;
	
	@FindBy(css=".report-tabs")
	public WebElement reportTab;
	
	@FindBy(css=".insight-tabs")
	public WebElement insightTab;

	@FindBy(css=".asset-tabs")
	public WebElement assetTab;
	
	@FindBy(css=".catalog-tabs")
	public WebElement dataTab;	
	
	@FindBy(xpath="//button[@class='btn-link dropdown-toggle profile-btn']")
	public WebElement profileBtn;	
	
	@FindBy(xpath="//span[contains(text(), 'Switch Role')]")
	public WebElement switchRoleSubMenu;
	
	@FindBy(xpath="//div[@class='toast-message'][contains(text(), 'You are currently authorised with different roles than your default. To exit,')]")
	public WebElement switchRoleSuccessMsg;	
	
	@FindBy(xpath="//span[contains(text(), 'Revert Role')]")
	public WebElement revertRoleSubMenu;
}
