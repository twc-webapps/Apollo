/*'###############################################################################
'-------------------------------------------------------------------------------
' Case Study: Case Study 2
' Script Name: TS_BookFlight
' Brief Functional Description: Book a return Journey
' Created On: 09-06-2016
' Created By: k.y.sadashivappa
' Comments/Remmark: 
'-------------------------------------------------------------------------------
'###############################################################################*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import jxl.*;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import jxl.write.Label;

public class FlighReservation2 {
	
   public static void main(String[] args) throws BiffException, IOException, InterruptedException, RowsExceededException, WriteException
	 {
	    String currentdir = System.getProperty("user.dir");
	    System.out.println(currentdir);	  
	    WebDriver driver;
	    
	    File data = new File("C:\\Users\\k.y.sadashivappa\\workspace\\CaseStudy2\\Input.xls");
		WorkbookSettings ws = new WorkbookSettings();
		ws.setLocale(new Locale("er", "ER"));
		Workbook wb = Workbook.getWorkbook(data, ws);	    
	    Sheet sh = wb.getSheet(0);
	    
	    File file = new File("out.txt");
	 	FileOutputStream fos = new FileOutputStream(file);
	 	PrintStream ps = new PrintStream(fos);
	 	PrintStream console = System.out;
	 	System.setOut(ps);
	 	
	 	WritableWorkbook workbook = Workbook.createWorkbook(new File("C:\\Users\\k.y.sadashivappa\\workspace\\CaseStudy2\\Output.xls"));
	 	WritableSheet wSheet = workbook.createSheet("First Sheet", 0);	
	    
	    String url = sh.getCell(2,1).getContents();
	    //String br=sh.getCell(2,2).getContents();
	    String Uname =sh.getCell(4,1).getContents();
	    String Pwrd = sh.getCell(6,1).getContents();
	    
	    Label label= new Label(1, 0, "Account Number");
	 	wSheet.addCell(label); 
	 	Label label2= new Label(2, 0, "Marketing");
	 	wSheet.addCell(label2);
	 	Label label3= new Label(3, 0, "Division");
	 	wSheet.addCell(label3);
	 	Label label4= new Label(4, 0, "Comments");
	 	wSheet.addCell(label4);
	    
	    System.out.println(url);
	    System.out.println(Uname);
	    System.out.println(Pwrd);
	    
	    //if(br.equals("FF"))
	    driver = new FirefoxDriver();
	    //else
	    //	driver = new ChromeDriver();
		
	    driver.manage().window().maximize();
	    
	    driver.get(url);
	    Thread.sleep(1000);
	    
	    String previousURL = driver.getCurrentUrl();
	    	    
	    driver.findElement(By.id("csrLoginControl_UserName")).sendKeys(Uname);
	    driver.findElement(By.id("csrLoginControl_Password")).sendKeys(Pwrd);	    
		driver.findElement(By.id("csrLoginControl_btnSubmit")).click();
		int i=1,j=1;
		
	    do{		    
	    	String UMdiv = null,ErrMsg = null;
	    	String AccNum =sh.getCell(8,i).getContents();
	    	String Div = sh.getCell(10,i).getContents();	
	    	Thread.sleep(3000);
	    	driver.findElement(By.id("ctl00_cphContent_txtAccountNum")).clear();
	    	driver.findElement(By.id("ctl00_cphContent_txtAccountNum")).sendKeys(AccNum);
		
	    	new Select(driver.findElement(By.id("ctl00_cphContent_ddlDivisions"))).selectByVisibleText(Div);
		
	    	driver.findElement(By.id("ctl00_cphContent_btnSubmit")).click();
		
	    	if(driver.findElements(By.xpath(".//*[@id='ctl00_cphContent_lblErrorMessage']")).size()>0)
	    		ErrMsg=driver.findElement(By.xpath(".//*[@id='ctl00_cphContent_lblErrorMessage']")).getText();
	    	else if(driver.findElements(By.xpath(".//*[@id='ctl00_cphContent_recordLocatorResults']/table/tbody/tr")).size()>1)
	    	{			
	    		driver.findElement(By.id("ctl00_cphContent_repUserDetails_ctl00_btnDetails")).click();
	    		Thread.sleep(3000);
	    		
	    		//if(driver.findElements(By.xpath(".//*[@id='ctl00_cphContent_lblErrorMessage']")).size()>0)
		    		//System.out.println("Error message is"+driver.findElement(By.xpath(".//*[@id='ctl00_cphContent_lblErrorMessage']")).getText());
	    		UMdiv =driver.findElement(By.xpath(".//*[@id='ctl00_cphContent_ucCustomerRecord_lblCustomerDivision']")).getText();
	    	
	    		driver.findElement(By.id("ctl00_cphContent_lnkBack")).click();
	    	}
	    	
	    	label= new Label(1, j, AccNum);
		 	wSheet.addCell(label); 
		 	label2= new Label(2, j, Div);
		 	wSheet.addCell(label2);
		 	label3= new Label(3, j, UMdiv);
		 	wSheet.addCell(label3);
		 	label4= new Label(4, j, ErrMsg);
		 	wSheet.addCell(label4);
		 	System.out.println("J"+j);
		 	i++;
		 	j++;
				
	    }while(i<=100);
 	  //Thread.sleep(2000);
 	   System.setOut(console);
 	  workbook.write(); 
	  workbook.close(); 
 	  driver.close();
	}
	
	System.out.println("");
   
}
