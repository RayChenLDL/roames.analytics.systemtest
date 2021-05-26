package com.roames.test.utilities;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Highly customized PDF utility to generate PDF detail test report by automation script which is water marked  with Fugro logo.
 * @author Ray Chen 12 Feb 2019
 *
 */
public class PDFTestReport extends PdfPageEventHelper {	
	
	private Document document = null;
	private HashMap<Integer, Throwable> throwableMap = null;
	private int nbExceptions = 0;
	private String testRptPath = "";
	private String testcaseName = "";
	private String testingEnv = "";
	
	private Font regular = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new Color(0, 0, 0));
	private Font regular_red = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new Color(255, 0, 0));
	private Font regular_green = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new Color(0, 150, 0));
	private Font bold = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new Color(0, 0, 0));
	
	
	public void onStartPage(PdfWriter writer,Document document) {
        ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Top Left"), 100, 100, 0);
    }
	
	public void onEndPage(PdfWriter writer,Document document) {
        ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Top Left"), 100, 100, 0);
    }

	/**
	 * Constructor
	 * @param testcaseName - Test case name from the TestNG XML
	 */
	public PDFTestReport(String testRptPath, String testcaseName, String testingEnv) {	
		this.testRptPath = testRptPath;
		this.testcaseName = testcaseName;
		this.testingEnv = testingEnv;
		this.document = new Document();
		this.throwableMap = new HashMap<Integer, Throwable>();
		
		// Create PDF reports DIR if not exists
		File directory = new File(String.valueOf(testRptPath));
		if(!directory.exists()){
            directory.mkdir();
		}
		
		try {
			PdfWriter.getInstance(this.document, new FileOutputStream(testRptPath + testcaseName +".pdf"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.document.open();
		
        // Only setup for header
		Phrase phase = new Phrase("Test Name: ", bold);
		phase.add(new Chunk(testcaseName, regular));
        HeaderFooter header = new HeaderFooter(phase, false);
        //HeaderFooter footer = new HeaderFooter(new Phrase("Footer"), false); 
        this.document.setHeader(header);
        //this.document.setFooter(footer);
		
        // Face Page: Title
        Paragraph p = new Paragraph("Test Automation Detail Report",
				FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD, new Color(0, 0, 255)));
		
		try {
			
			this.document.add(new Paragraph(" "));
			this.document.add(new Paragraph(" "));
			this.document.add(new Paragraph(" "));
			this.document.add(p);
			this.document.add(new Paragraph(" "));
			this.document.add(new Paragraph(" "));
			this.document.add(new Paragraph(" "));
			this.document.add(new Paragraph(" "));

			// Face Page: Test Name
			p = new Paragraph("Test Artifact: ", bold);
			p.add(new Chunk(testcaseName, regular));
			this.document.add(p);

			// Face Page: Test testingEnv
			p = new Paragraph("Test Environment: ", bold);
			p.add(new Chunk(testingEnv, regular));
			this.document.add(p);
			
			// Face Page: Test Date and Time
			p = new Paragraph("Test Date: ", bold);
			p.add(new Chunk(new Date().toString(), regular));
			this.document.add(p);
			
			// Face Page: Tester
			p = new Paragraph("Tester: ",bold);
			p.add(new Chunk("Roames Test Automation", regular));			
			this.document.add(p);
			this.document.newPage();
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}		
	}		
	
	public void logTestResult(String testStatus, String testDesc) {		
		try {
			// Blank line
			this.document.add(new Paragraph(" "));
			
			// Test Step Description
			Paragraph p = new Paragraph("Step Description: ",bold);
			p.add(new Chunk(testDesc, regular));
			this.document.add(p);
			
			// Test Time
			p = new Paragraph("Date Time: ",bold);
			p.add(new Chunk(new Date().toString(), regular));
			this.document.add(p);
			
			// Test Step Result
			p = new Paragraph("Step Result: ",bold);
			
			// High light in red if test step fails
			if (testStatus.equalsIgnoreCase("FAIL")) {
				p.add(new Chunk(testStatus, regular_red));
			}else if(testStatus.equalsIgnoreCase("PASS")) {
				p.add(new Chunk(testStatus, regular_green));
			}else {
				p.add(new Chunk(testStatus, regular));
			}
			this.document.add(p);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	/**
	 * 
	 * @param context
	 */
	public void pdfFlush() {
		
		Set<Integer> keys = this.throwableMap.keySet();
		
		assert keys.size() == this.nbExceptions;
		
		for(Integer key : keys) {
			Throwable throwable = this.throwableMap.get(key);
			
			Chunk chunk = new Chunk(throwable.toString(), regular_red);
					//FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new Color(255, 0, 0)));
			chunk.setLocalDestination("" + key);
			Paragraph throwTitlePara = new Paragraph(chunk);
			try {
				this.document.add(throwTitlePara);
			} catch (DocumentException e3) {
				e3.printStackTrace();
			}
			
			StackTraceElement[] elems = throwable.getStackTrace();
			for(StackTraceElement ste : elems) {
				Paragraph throwParagraph = new Paragraph(ste.toString());
				try {
					this.document.add(throwParagraph);
				} catch (DocumentException e2) {
					e2.printStackTrace();
				}
			}
		}
		
		this.document.close();
		addWatermark();
	}
	
	public void insertImg(String imgFile) {
		try {
			this.document.add(new Paragraph(" "));
			Image img = Image.getInstance(imgFile);
			img.scaleToFit(PageSize.A5.width(), PageSize.A5.height());

			this.document.add(img);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addWatermark() {
		PdfReader pdfReader;
		try {
			
			String oldFileName = testRptPath + testcaseName +".pdf";
			String newFileNameWithWatermark = testRptPath + testcaseName +"_watermark.pdf";
			
			pdfReader = new PdfReader(oldFileName);
			int n = pdfReader.getNumberOfPages();
			
			// Get the PdfStamper object
	        PdfStamper stamp = new PdfStamper(pdfReader, new FileOutputStream(newFileNameWithWatermark));
	        // Get the PdfContentByte type by pdfStamper.
	        
	        int i = 0;
	           PdfContentByte under;
	           PdfContentByte over;
	           Image img = Image.getInstance(System.getProperty("user.dir")  + "/src/test/resources/FUGRO_Logo.jpg");
	           img.scaleToFit(PageSize.A5.width(), PageSize.A5.height());
	          
	           BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
	           img.setAbsolutePosition(20, 20);
	           
	           while (i<n) {
	        	 //img.setAlignment(Element.ALIGN_CENTER);
		         img.setAbsolutePosition(90, 300);

	           	i++;
	           	// watermark under the existing page
	           	under = stamp.getUnderContent(i);
	           	under.addImage(img);
	           	// text over the existing page
	           	over = stamp.getOverContent(i);
	           	over.beginText();
	           	over.setFontAndSize(bf, 12);
	           	over.setTextMatrix(30, 30);
	           	//over.showText("Page " + i + " of " + n);
	           	over.showTextAligned(Element.ALIGN_CENTER, "Page " + i + " of " + n, 300,10 , 0);
	           	over.setColorFill(Color.GRAY);
	           	over.setFontAndSize(bf, 58);
	           	over.showTextAligned(Element.ALIGN_JUSTIFIED, "Roames Test Automation", 100, 200, 45);
	           	over.endText();
	           }
	           	           
	            stamp.close();
	            
	            // Delete intermediate file
	            Files.deleteIfExists(Paths.get(oldFileName));
	            Path sourcePath = Paths.get(newFileNameWithWatermark);
	            Path destinationPath = Paths.get(oldFileName);
	            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);	            
	            
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
