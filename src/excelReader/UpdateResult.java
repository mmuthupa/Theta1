package excelReader;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import driverScript.ExecuteTest;
import objectRepository.LoadPropertySingleton;
import operations.UIOperations;

public class UpdateResult extends ExecuteTest
{
	public Sheet resultsheet ;
	public static Workbook workbook;
	public static Workbook resworkbook;
	public String fileName_R;
	Font font = null;
	CellStyle objCellStyle = null;
	Cell statuscell = null;
	Cell Errmsgcolumn = null;
	Cell Testcasestatus = null;
	Cell statusheader = null;
	Cell Errormsgheader = null;
	ArrayList<Integer> resultcol = null;
	ArrayList<Integer> rc = null;
	Row rwobj;
	int rwnum = 0;
	int val = 0;
	int srctestdatacol; 
	int counter = 0;
	int executetestdatacol = 1;
	int testdatarownum = 0 ;
	int totaltestdatacol;
	// Changed from XSSFRow to Row and XSSFCell to Cell
	public void writeOutputWorkBook(String reportfile , String sheetName,int rowNo, String status, String errorMsg) throws IOException, HeadlessException, AWTException, InterruptedException
	{				
		counter++;
		if(counter == 1)
		{		
			String taskCode = LoadPropertySingleton.configResourceBundle.getProperty("ListOfTaskCodes");
			ReadExcel objReadExcel = new ReadExcel();
			Sheet tcSheet = objReadExcel.readSheetContents(filePath_TC, fileName_TC , taskCode);
			Row rwcnt = tcSheet.getRow(0);
			int lastcolnum = rwcnt.getLastCellNum();
			srctestdatacol = lastcolnum - 7;
			val =srctestdatacol;	
			totaltestdatacol = lastcolnum - 7;
		}
		
		//executetestdatacol = testdatacol;		
		FileInputStream file = new FileInputStream(new File(reportfile));							
		workbook = new HSSFWorkbook(file);				
		resultsheet = workbook.getSheet(testcasesheetname);				
		//To print the result of the test step with irrespective of multiple test data
		Row objXSSFRow = (Row) resultsheet.getRow(rowNo);
		if(resultcolumn == 0)
		{
			resultcolumn = 9;
		}			
		if(status.equals("PASS") || status.equals("FAIL") || status.equals(""))
		{
			rc  = new ArrayList<Integer>();
			rc.add(rowNo);			
			
			if(totaltestdatacol == 1)
			{
				rwnum = rc.get(0);
			}
			if(executetestdatacol == 0 && rwnum!= 0)
			{	
				/*if(val == 0)
				{
					val = srctestdatacol;
					testdatarownum = rc.get(0);
					rwnum = testdatarownum;					
				}*/
				
				if(val >=1)
				{										
					val--;					
					rwnum = testdatarownum;
				}
				
				if(val == 0)
				{
					executetestdatacol = 1;
					val = srctestdatacol;
				}
			}
			else
			{
				val--;
				rwnum = rc.get(0);
				testdatarownum = rwnum;	
				executetestdatacol--;
			}
		}
		
		objCellStyle = workbook.createCellStyle();
		font = workbook.createFont();					
		//CellStyle newStyle = workbook.createCellStyle();
		//Font statusFont = workbook.createFont();
		
		if(status.equalsIgnoreCase("P"))
		{
			//font.setColor(IndexedColors.GREEN.getIndex());
			font.setFontHeightInPoints((short) 9);
			logger.log(Status.PASS, MarkupHelper.createLabel(Objectlablel+ "-" +" Test step PASSED", ExtentColor.GREEN));
			extent.flush();
		}
		else if(status.equalsIgnoreCase("PASS"))
		{
			objCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			objCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			font.setFontHeightInPoints((short) 18);
			logger.log(Status.PASS, MarkupHelper.createLabel(status +" Test Case PASSED", ExtentColor.GREEN));
		//	extent.flush();			
		}
		else if(status.equalsIgnoreCase("Error"))
		{
			UIOperations uioperations =  new UIOperations(driver);
			//Object screenshotPath = uioperations.getScreenShot(evidencePath, uioperations.fileName_E); 
					//getScreenShot(driver, Objectlablel);
			//String screenshotpath = getScreenShot(driver, Objectlablel);
			uioperations.getScreenShot(evidencePath, UIOperations.fileName_E);
			//System.out.println(fileName_Evidencefolder);	
			//font.setColor(IndexedColors.RED.getIndex());
			font.setFontHeightInPoints((short) 9);
			//Add by Sreenu for Error Message update
			//statusFont.setColor(IndexedColors.BLACK.getIndex());
			font.setColor(IndexedColors.BLACK.getIndex());
			//newStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
			//newStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			font.setFontHeightInPoints((short) 10);
			logger.log(Status.ERROR, MarkupHelper.createLabel(Objectlablel+ "-" + " - Test step Failed", ExtentColor.RED));
			logger.fail("Test Case Failed Snapshot is below " + logger.addScreenCaptureFromPath(UIOperations.screenshotfilepath));
			extent.flush();
		}
		else if(status.equalsIgnoreCase("STATUS"))
		{
			objCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
			objCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			font.setFontHeightInPoints((short) 18);
		}
		else
		{
			//String screenshotPath = getScreenShot(driver, Objectlablel);
			UIOperations uioperations =  new UIOperations(driver);
			uioperations.getScreenShot(evidencePath, UIOperations.fileName_E);
			objCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
			font.setFontHeightInPoints((short) 18);
			objCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			logger.log(Status.FAIL, MarkupHelper.createLabel("FAIL - Test Case Failed", ExtentColor.RED));
			logger.fail("Test Case Failed Snapshot is below " + logger.addScreenCaptureFromPath(UIOperations.screenshotfilepath));
			extent.flush();
		}
		objCellStyle.setFont(font);
		/*objXSSFCell.setCellStyle(objCellStyle);
		objXSSFCell.setCellValue(status);
		column.setCellStyle(newStyle);
		column.setCellValue(errorMsg);*/
			
		//To print the status of the test case
		if(status.toUpperCase().equals("P") || status.toUpperCase().equals("ERROR"))
		{
			objCellStyle.setFont(font);		
			statuscell = objXSSFRow.createCell(resultcolumn - 1);
			statuscell.setCellStyle(objCellStyle);
			statuscell.setCellValue(status);		
			//newStyle.setFont(statusFont);
			Errmsgcolumn = objXSSFRow.createCell(resultcolumn);
			//Errmsgcolumn.setCellStyle(newStyle);			
			Errmsgcolumn.setCellValue(errorMsg);
		}
		else if (status.equals("PASS") || status.equals("FAIL"))
		{
			rwobj = resultsheet.getRow(rwnum);						
			Testcasestatus = rwobj.createCell(resultcolumn - 1);
			objCellStyle.setFont(font);
			Testcasestatus.setCellStyle(objCellStyle);
			Testcasestatus.setCellValue(status);		
			//newStyle.setFont(statusFont);			
		}
		else if (status.equals("STATUS"))
		{
			
			objXSSFRow.createCell(resultcolumn -1);		
			statusheader = objXSSFRow.createCell(resultcolumn - 1);
			statusheader.setCellValue(status);
			//newStyle.setFont(statusFont);
			objXSSFRow.createCell(resultcolumn);
			Errormsgheader = objXSSFRow.createCell(resultcolumn);
			Errormsgheader.setCellValue(errorMsg);
			//newStyle.setFont(statusFont);
		}
		
		
		FileOutputStream fOut = new FileOutputStream(reportfile);
		workbook.write(fOut);
		fOut.close();	
		workbook.close();
	}
		
	public String Copyworkbook(String filePath,String fileName) throws IOException	//  To return FileInputStream
	{
		//Added by ganeshan to create a copy of the test case file with the time stamp		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
	    Date now = new Date();
	    String Time = sdf.format(now);
	    String fileName1 = LoadPropertySingleton.configResourceBundle.getProperty("ReportFileName");
		String filName_split1 = fileName1.substring(0, fileName1.indexOf("."));
		String filName_split2 = fileName1.substring(fileName1.indexOf("."));	
		fileName_R = filName_split1 + "_" + Time + filName_split2;		//		For Excel Report with Data and Time		
		File srcname = new File(filePath + fileName);
		File destname = new File(filePath_R + fileName_R);		
		FileUtils.copyFile(srcname, destname);		
		return filePath_R + fileName_R ;				
	}
		
	public void closeWorkBook(Workbook xssfWorkbook,String filePath,String fileName,FileInputStream fileInputStream) throws IOException
	{
		System.out.println("CloseWorkBook   xssfWorkbook: " + xssfWorkbook +"FilePath ==> "+filePath +"FileName ==> "+fileName);
		File targetFile = new File(filePath + fileName);
		FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
		xssfWorkbook.write(fileOutputStream);
		fileInputStream.close();
		fileOutputStream.close();
		xssfWorkbook.close();
	}


	/*public static String getScreenShot(WebDriver driver, String screenshotName) throws IOException 
	{
		String stringval = " ";
		 String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		 TakesScreenshot ts = (TakesScreenshot) driver;
		 File source = ts.getScreenshotAs(OutputType.FILE);
		 // after execution, you could see a folder "FailedTestsScreenshots" under src folder
		 if(screenshotName.contains(":"))
		 {
			 String str = screenshotName.substring(0, screenshotName.length());
			 stringval = str.replaceAll(":", " ");
		 }
		 String destination = System.getProperty("user.dir") + "/Screenshots/" + stringval + dateName + ".png";
		 File finalDestination = new File(destination);
		 FileUtils.copyFile(source, finalDestination);
		 return destination;
	}*/	
	public Sheet getSheetObject(String reportfile, String sheetName) throws IOException		//  To return Sheet
	{
		FileInputStream fso = new FileInputStream(reportfile);
		workbook = new HSSFWorkbook(fso);
		Sheet sheet = workbook.getSheet(sheetName);
		return sheet;
	}
	public void deletedata() throws IOException
	{				
		
		String taskCode = LoadPropertySingleton.configResourceBundle.getProperty("ListOfTaskCodes");
		ReadExcel objReadExcel = new ReadExcel();
		Sheet resultsheet = objReadExcel.readSheetContents(filePath_R, fileName_R , taskCode);		
		FileInputStream file = new FileInputStream(new File(filePath_R + fileName_R));							
		resworkbook = new HSSFWorkbook(file);				
		resultsheet = resworkbook.getSheet(testcasesheetname);
				
		int totRowCount = resultsheet.getLastRowNum();
		Row rwcnt = resultsheet.getRow(0);
		int lastcolnum = rwcnt.getLastCellNum();
		testdatacol = lastcolnum - 7;
		int totaltestdata = testdatacol;
		Row unusedrow;
		int deleterow = totRowCount + 100;
		//To delete the junk values of unused rows and columns		
		for(int rw = totRowCount + 1;rw<=deleterow;rw++)
		{
			unusedrow = resultsheet.getRow(rw);			
			for (int col = 1; col <255;col++)			
			{				
				if(unusedrow!= null)
				{
					Cell delemptycel = unusedrow.createCell(col);				
					delemptycel.setCellValue(" ");
				}								
				//System.setProperty("-XX:+HeapDumpOnOutOfMemoryError","D://heapdump.bin");
			}	
		}
		
		int columnloop = 0;
		int resultcol = 0;
		int restestdatacol = 7;
		String copyval = null;
		if(testdatacol > 1)
		{
			
			for (int k = 0;k<testdatacol;k++)
			{
				testdatacol--;
				columnloop++;
				restestdatacol++;
				for(int j = 0;j<totRowCount;j++)
				{
					resultcol = columnloop * 2;
					int col = resultcol + restestdatacol;
					Row rw = resultsheet.getRow(j);
					try
					{
						if(rw.getCell(restestdatacol)!= null)
						{
							copyval	 = rw.getCell(restestdatacol).toString();
						}
					}
					catch (Exception e) {
						System.out.println("Null values");
					}					
					try
					{
						Cell createcell = rw.createCell(col);
						createcell.setCellValue(copyval);
						Cell delcell = rw.createCell(restestdatacol);
						delcell.setCellValue(" ");
						copyval = " ";
					}
					catch (Exception e) {
						System.out.println("Null values in copy paste");
					}
					
				}
			}			
		}
			int statuserrmsgcol = 6;
			//To update the status and the Error Message					
			for (int updatecol = 1; updatecol<=totaltestdata;updatecol++)
			{						
				statuserrmsgcol += 3;
				Cell resultcell = rwcnt.createCell(statuserrmsgcol - 1);
				resultcell.setCellValue("STATUS");
				Cell statuscell = rwcnt.createCell(statuserrmsgcol);
				statuscell.setCellValue("Generated ID/Number/Error Message");
			}
						
		FileOutputStream fOut1 = new FileOutputStream(filePath_R + fileName_R);
		resworkbook.write(fOut1);
		fOut1.close();	
		resworkbook.close();
	}


}
