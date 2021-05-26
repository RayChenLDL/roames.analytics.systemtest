package com.roames.test.objects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DataGridPopupMenu {
	@FindBy(xpath="//div[@class='ag-menu']//span[@class='ag-menu-option-text'][text()='Copy']")
	public WebElement menuCOPY;

	@FindBy(xpath="//div[@class='ag-menu']//span[@class='ag-menu-option-text'][text()='Copy with Headers']")
	public WebElement menuCOPYWithHeaders;
	
	@FindBy(xpath="//div[@class='ag-menu']//span[@class='ag-menu-option-text'][text()='Paste']")
	public WebElement menuPASTE;
	
	@FindBy(xpath="//div[@class='ag-menu']//span[@class='ag-menu-option-text'][text()='Tool Panel']")
	public WebElement menuToolPanel;
	
	@FindBy(xpath="//div[@class='ag-menu']//span[@class='ag-menu-option-text'][text()='Select highlighted rows']")
	public WebElement menuSelectHighLightedRows;
	
	@FindBy(xpath="//div[@class='ag-menu']//span[@class='ag-menu-option-text'][text()='Deselect All']")
	public WebElement menuDeselectAll;
	
	@FindBy(xpath="//div[@class='ag-menu']//span[@class='ag-menu-option-text'][text()='Export Selected Rows']")
	public WebElement menuExportSelectedRows;
	
	@FindBy(xpath="//div[@class='ag-menu']//span[@class='ag-menu-option-text'][text()='Export All Rows']")
	public WebElement menuExportAllRows;
	
	@FindBy(xpath="//div[@class='ag-menu']//span[@class='ag-menu-option-text'][text()='Navigate with filtered rows']")
	public WebElement menuNavigateWithFilteredRows;
	
	@FindBy(xpath="//div[@class='ag-menu']//span[@class='ag-menu-option-text'][text()='Span']")
	public WebElement submenuNavigateWithFilteredRows_Span;
	
	@FindBy(xpath="//div[@class='ag-menu']//span[@class='ag-menu-option-text'][text()='Span Defects']")
	public WebElement submenuNavigateWithFilteredRows_SpanDefects;
	
	@FindBy(xpath="//div[@class='ag-menu']//span[@class='ag-menu-option-text'][text()='Pole']")
	public WebElement submenuNavigateWithFilteredRows_Pole;
	
	@FindBy(xpath="//div[@class='ag-menu']//span[@class='ag-menu-option-text'][text()='Pole Defects']")
	public WebElement submenuNavigateWithFilteredRows_PoleDefects;
	
	@FindBy(xpath="//div[@class='ag-menu']//span[@class='ag-menu-option-text'][text()='CSV']")
	public WebElement submenuExportAsCSV;
	
	@FindBy(xpath="//div[@class='ag-menu']//span[@class='ag-menu-option-text'][text()='KML']")
	public WebElement submenuExportAsKML;
	
	@FindBy(xpath="//div[@class='ag-menu']//span[@class='ag-menu-option-text'][text()='Excel']")
	public WebElement submenuExportAsExcel;	
}
