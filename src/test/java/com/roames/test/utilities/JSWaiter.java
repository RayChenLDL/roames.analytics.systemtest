package com.roames.test.utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;

public class JSWaiter {
	 
    private static ThreadLocal<WebDriver> jsWaitDriver = new ThreadLocal<WebDriver>();
    private static ThreadLocal<WebDriverWait> jsWait = new ThreadLocal<WebDriverWait>();
    private static ThreadLocal<JavascriptExecutor> jsExec = new ThreadLocal<JavascriptExecutor>();
 
    //Get the driver 
    public void setDriver (WebDriver driver) {
        //jsWaitDriver = driver;
        //jsWait = new WebDriverWait(jsWaitDriver, 10);
        //jsExec = (JavascriptExecutor) jsWaitDriver;
        
        setJsWaitDriver(driver);
        setJsWait(new WebDriverWait(getJsWaitDriver(), 10));
        setJsExec((JavascriptExecutor) getJsWaitDriver());
    }
 
    //Wait for JQuery Load
    public void waitForJQueryLoad() {
        //Wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((Long) ((JavascriptExecutor) getJsWaitDriver())
				        .executeScript("return jQuery.active") == 0);
			}
		};
 
        //Get JQuery is Ready
        boolean jqueryReady = (Boolean) getJsExec().executeScript("return jQuery.active==0");
 
        //Wait JQuery until it is Ready!
        if(!jqueryReady) {
            System.out.println("JQuery is NOT Ready!");
            //Wait for jQuery to load
            getJsWait().until(jQueryLoad);
        } else {
            System.out.println("JQuery is Ready!");
        }
    }
 
 
    //Wait for Angular Load
    public void waitForAngularLoad() {
        WebDriverWait wait = new WebDriverWait(getJsWaitDriver(),15);
        JavascriptExecutor jsExec = (JavascriptExecutor) getJsWaitDriver();
 
        final String angularReadyScript = "return angular.element(document).injector().get('$http').pendingRequests.length === 0";
 
        //Wait for ANGULAR to load
        ExpectedCondition<Boolean> angularLoad = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return Boolean.valueOf(((JavascriptExecutor) driver)
				        .executeScript(angularReadyScript).toString());
			}
		};
 
        //Get Angular is Ready
        boolean angularReady = Boolean.valueOf(jsExec.executeScript(angularReadyScript).toString());
 
        //Wait ANGULAR until it is Ready!
        if(!angularReady) {
            System.out.println("ANGULAR is NOT Ready!");
            //Wait for Angular to load
            wait.until(angularLoad);
        } else {
            System.out.println("ANGULAR is Ready!");
        }
    }
 
    //Wait Until JS Ready
    public void waitUntilJSReady() {
        WebDriverWait wait = new WebDriverWait(getJsWaitDriver(),15);
        JavascriptExecutor jsExec = (JavascriptExecutor) getJsWaitDriver();
 
        //Wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) getJsWaitDriver())
				        .executeScript("return document.readyState").toString().equals("complete");
			}
		};
 
        //Get JS is Ready
        boolean jsReady =  (Boolean) jsExec.executeScript("return document.readyState").toString().equals("complete");
 
        //Wait Javascript until it is Ready!
        if(!jsReady) {
            System.out.println("JS in NOT Ready!");
            //Wait for Javascript to load
            wait.until(jsLoad);
        } else {
            System.out.println("JS is Ready!");
        }
    }
 
    //Wait Until JQuery and JS Ready
    public void waitUntilJQueryReady() {
        JavascriptExecutor jsExec = (JavascriptExecutor) getJsWaitDriver();
 
        //First check that JQuery is defined on the page. If it is, then wait AJAX
        Boolean jQueryDefined = (Boolean) jsExec.executeScript("return typeof jQuery != 'undefined'");
        if (jQueryDefined == true) {
            //Pre Wait for stability (Optional)
            sleep(20);
 
            //Wait JQuery Load
            waitForJQueryLoad();
 
            //Wait JS Load
            waitUntilJSReady();
 
            //Post Wait for stability (Optional)
            sleep(20);
        }  else {
            System.out.println("jQuery is not defined on this site!");
        }
    }
 
    //Wait Until Angular and JS Ready
    public void waitUntilAngularReady() {
        JavascriptExecutor jsExec = (JavascriptExecutor) getJsWaitDriver();
 
        //First check that ANGULAR is defined on the page. If it is, then wait ANGULAR
        Boolean angularUnDefined = (Boolean) jsExec.executeScript("return window.angular === undefined");
        if (!angularUnDefined) {
            Boolean angularInjectorUnDefined = (Boolean) jsExec.executeScript("return angular.element(document).injector() === undefined");
            if(!angularInjectorUnDefined) {
                //Pre Wait for stability (Optional)
                sleep(20);
 
                //Wait Angular Load
                waitForAngularLoad();
 
                //Wait JS Load
                waitUntilJSReady();
 
                //Post Wait for stability (Optional)
                sleep(20);
            } else {
                //System.out.println("Angular injector is not defined on this site!");
            }
        }  else {
            //System.out.println("Angular is not defined on this site!");
        }
    }
 
    //Wait Until JQuery Angular and JS is ready
    public void waitJQueryAngular() {
        waitUntilJQueryReady();
        waitUntilAngularReady();
    }
 
    public void sleep (Integer seconds) {
        long secondsLong = (long) seconds;
        try {
            Thread.sleep(secondsLong);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
	// ============ ThreadLocal For jsWaitDriver ========
	public static void setJsWaitDriver(WebDriver dr){		
		jsWaitDriver.set(dr);
	}

	public static WebDriver getJsWaitDriver(){		
		return jsWaitDriver.get();
	}
	
	// ============ ThreadLocal For jsWait ========
	public static void setJsWait(WebDriverWait drWait){		
		jsWait.set(drWait);
	}

	public static WebDriverWait getJsWait(){		
		return jsWait.get();
	}
	
	// ============ ThreadLocal For jsExec ========
	public static void setJsExec(JavascriptExecutor js){		
		jsExec.set(js);
	}

	public static JavascriptExecutor getJsExec(){		
		return jsExec.get();
	}
}
