package com.roames.test.objects.datagrid;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DataGridPopupMenu {
	@FindBy(xpath="//div[@class='ag-menu-option']//span[@class='ag-menu-option-text ag-menu-option-part'][text()='Copy']")
	public WebElement menuCOPY;

	@FindBy(xpath="//div[@class='ag-menu-option']//span[@class='ag-menu-option-text ag-menu-option-part'][text()='Export Selected Rows']")
	public WebElement menuExportSelectedRows;	
	
	@FindBy(xpath="//div[@class='ag-menu-option']//span[@class='ag-menu-option-text ag-menu-option-part'][text()='Export All Rows']")
	public WebElement menuExportAllRows;
	
	@FindBy(xpath="//div[@class='ag-menu-option']//span[@class='ag-menu-option-text ag-menu-option-part'][text()='CSV']")
	public WebElement submenuExportAsCSV;	
	
	@FindBy(xpath="//div[@class='ag-menu-option']//span[@class='ag-menu-option-text ag-menu-option-part'][text()='KML']")
	public WebElement submenuExportAsKML;	
	
	@FindBy(xpath="//div[@class='ag-menu-option']//span[@class='ag-menu-option-text ag-menu-option-part'][text()='Excel']")
	public WebElement submenuExportAsExcel;	
	
	@FindBy(xpath="//div[@class='ag-menu-option']//span[@class='ag-menu-option-text ag-menu-option-part'][text()='Select Highlighted Rows']")
	public WebElement menuSelectHighlightedRows;
	
	@FindBy(xpath="//div[@class='ag-menu-option']//span[@class='ag-menu-option-text ag-menu-option-part'][text()='Deselect Highlighted Rows']")
	public WebElement menuDeselectHighlightedRows;
}
