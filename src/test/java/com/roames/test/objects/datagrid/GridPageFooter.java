package com.roames.test.objects.datagrid;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GridPageFooter {
	
	@FindBy(xpath="//div[contains(@class,'button icon-chrome_reader_mode')]")
	public WebElement btnPagination;
	
	@FindBy(xpath="//div[contains(@class,'button toggle-button icon-roames_world')]")
	public WebElement btnRoamesWorld;
	
	//@FindBy(xpath="//div[@class='ag-paging-panel ag-unselectable']/span[@class='ag-paging-row-summary-panel']")
	@FindBy(xpath="//div[@class='toolbox__right']")
	public WebElement labelPageInfo;		
}
