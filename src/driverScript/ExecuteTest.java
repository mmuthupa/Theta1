package driverScript;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.StringTokenizer;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import excelReader.ReadExcel;
import excelReader.UpdateResult;
import objectRepository.LoadPropertySingleton;
import operations.UIOperations;

public class ExecuteTest 
{	
	 public ExtentHtmlReporter htmlReporter;
	 public static ExtentReports extent;
	 public static ExtentTest logger;
	static final Logger loggerET = LogManager.getLogger(ExecuteTest.class.getName());
	static LoadPropertySingleton objectLoad = LoadPropertySingleton.getInstance();
	public static WebDriver driver = null;
	public boolean executionCheckFlag = false;
	public boolean prevTC = false;
	public static boolean driverFlag = true;
	public static boolean loginCheckFlag = true;
	public int tcNameRow;
	public String passCase = LoadPropertySingleton.configResourceBundle.getProperty("TESTCASE_PASS");
	public String failCase = LoadPropertySingleton.configResourceBundle.getProperty("TESTCASE_FAIL");
	public ArrayList<String> t_Status = new ArrayList<String>();
	public String testdata_status = "";
	public String filePath_TC = LoadPropertySingleton.configResourceBundle.getProperty("TestCaseFilePath");
	public String fileName_TC = LoadPropertySingleton.configResourceBundle.getProperty("TestCaseFileName");
	public String filePath_R = LoadPropertySingleton.configResourceBundle.getProperty("ReportFilePath");
	public String evidencePath = LoadPropertySingleton.configResourceBundle.getProperty("EvidenceFilePath");
//	public static String webDriverLocation = LoadPropertySingleton.configResourceBundle.getProperty("chromeDriver");
	public static String webDriverLocation = LoadPropertySingleton.configResourceBundle.getProperty("IEDriver");
	public String browserName = LoadPropertySingleton.configResourceBundle.getProperty("Browser");
	public String geckoDriverPath = LoadPropertySingleton.configResourceBundle.getProperty("chromeDriver");
	public String chromeDriverPath = LoadPropertySingleton.configResourceBundle.getProperty("chromeDriver");
	public String testcasesheetname = LoadPropertySingleton.configResourceBundle.getProperty("ListOfTaskCodes");
	/**********************************/
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
    Date now = new Date();
    String Time = sdf.format(now);
    public String evidenceFolderName;
    public String fileName = LoadPropertySingleton.configResourceBundle.getProperty("ReportFileName");
	public String filName_split1 = fileName.substring(0, fileName.indexOf("."));
	public String filName_split2 = fileName.substring(fileName.indexOf("."));	
	public String fileName_R = filName_split1 + "_" + Time + filName_split2;		//		For Excel Report with Data and Time	
	public int testdatacolumnno = 6;
	public static int testdatacol;
	public int testdatanos = 0; //to decide the test result sheet column no
	public int columnmatrixnos;//multiplied by 2 with the testdatanos
	public int k = 1;
	public static String Reportpath;
	public Sheet reportSheet; 
	public static int resultcolumn; //To know the column number for status and Error message in the result sheet
	public int i;	
	public ArrayList<Integer> al = null;	
	public static String Objectlablel ;
	public static UIOperations objUIOperations = null;
	public static ReadExcel objReadExcel=null;	
    /**********************************/
//	@Test
	@SuppressWarnings("static-access")
	public void execute(String taskCode) throws Exception 
	{
		loggerET.info("ExecuteTest.java : execute()"+taskCode);		
		initializeRunTimeTask(browserName,driverFlag,webDriverLocation,chromeDriverPath);
		startReport();
		objReadExcel = new ReadExcel();
       //ReadObject objReadObject = new ReadObject();
		//Properties propertiesObj = objReadObject.getObjectRepository();
		objUIOperations = new UIOperations(driver);
		UpdateResult objUpdateResult = new UpdateResult();				
//		Sheet sheet = objReadExcel.readSheetContents(filePath_TC, taskCode+"_"+fileName_TC , taskCode);
		Sheet tcSheet = objReadExcel.readSheetContents(filePath_TC, fileName_TC , taskCode);
		int rowCount = tcSheet.getLastRowNum()- tcSheet.getFirstRowNum();
		int totRowCount = tcSheet.getLastRowNum();
		System.out.println("Test Case File Name ==>: " + fileName_TC + "      Test Case Sheet Name ==>: " + taskCode);
		System.out.println("Last Row Number: " + totRowCount + "   Row Count: " + rowCount);
		tcNameRow = 1;		
		// Make a copy of Test case workbook
		Reportpath = objUpdateResult.Copyworkbook(filePath_TC, fileName_TC );
		objUpdateResult.deletedata();
		reportSheet =objUpdateResult.getSheetObject(Reportpath,taskCode);
		//  Status Column Heading & Generated ID in Report Sheet
		//objUpdateResult.writeOutputWorkBook(Reportpath,taskCode, 0, "STATUS","Generated ID/Number/Error Message");									
		Row rwcnt = tcSheet.getRow(0);
		int lastcolnum = rwcnt.getLastCellNum();
		//Total count of test data's
		testdatacol = lastcolnum - 7;		
		int testdatacolumn = testdatacol;
		int previouscolumnval = 0;
		String setvalue = "yettoupdate";
		//Create a loop over all the rows of excel file to read it
		for (i = 1; i <= totRowCount; i++) 
		{
			loggerET.info("execute() Test case Processing Starts with ==> " + i);
			loggerET.info("I = " + i);
			objUIOperations.alertMessage = "";
			Row row = tcSheet.getRow(i);				
			/*************************************** To Update the Test Case Level Status PASS or FAIL - Starts************************************************************************************/
			try
			{								
				if(prevTC=true && row.getCell(1).toString().length()!=0 || testdatacol == 0)
				{
					loggerET.info("TC Name: " + row.getCell(1).toString() + "---> " + "Previous TestCase Flag: " + prevTC);					
					if(t_Status.contains("False") && (executionCheckFlag))
					{
						objUpdateResult.writeOutputWorkBook(Reportpath,taskCode, tcNameRow, failCase,"");
						loggerET.info("1_Excel Updated in row no: " + tcNameRow + " FAIL");
						loggerET.info("Updated Test Case-Result in the Report as FAIL when atleast one step in the test case fails ");
						t_Status = new ArrayList<String>();												
					}
					else if((t_Status.isEmpty()))
					{
						loggerET.info("Test data value is null");
						//t_Status = new ArrayList<String>();
					}
					else if(t_Status.contains("Null"))
					{
						objUpdateResult.writeOutputWorkBook(Reportpath,taskCode, tcNameRow, "","");
						loggerET.info("Test data value is null");
						t_Status = new ArrayList<String>();
					}
					else 
						//if(!t_Status.contains("False") && (executionCheckFlag))
					{												
							objUpdateResult.writeOutputWorkBook(Reportpath,taskCode, tcNameRow, passCase,"");
							loggerET.info("2_Excel Updated in row no: " + tcNameRow + " PASS");
							loggerET.info("Updated Test Case-Result in the Report as PASS when all the test steps are Passed ");
							t_Status = new ArrayList<String>();												
					}
					prevTC = false;
				}				
			}
			catch(Exception ex)
			{
				loggerET.error("Exception Caught :ex : "+ex);
				ex.printStackTrace();
			}
			/***************************************To Update the Test Case Level Status PASS or FAIL - Ends************************************************************************************/
			try
			{
				if(row.getCell(1).toString().length()!=0)
				{
					if(loginCheckFlag == false)		//	LoginCheckFlag is to skip the successive test steps when the login itself failed and update the test case level status as FAIL.
					{
						objUpdateResult.writeOutputWorkBook(Reportpath,taskCode, tcNameRow, failCase,"");
						loggerET.info("Test Case Result updated as FAIL - LoginCheckFlag - False");
						loginCheckFlag = true;	//	LoginCheckFlag changed to TRUE so that the next test case can be executed.
					}
					
					
					if(row.getCell(2).toString().equalsIgnoreCase(LoadPropertySingleton.configResourceBundle.getProperty("YES")))
	                {
						logger = extent.createTest(row.getCell(1).toString());						
						//Print the new test case name when it gets started
						loggerET.info("New Testcase-> "+row.getCell(1).toString() + " Run_Flag: " + row.getCell(2).toString() + " Started");
						if(UIOperations.Pass_SnapShot.equalsIgnoreCase("TRUE") || UIOperations.Fail_SnapShot.equalsIgnoreCase("TRUE"))
						{
//							objUIOperations.tcNameFolder(row.getCell(1).toString());	 		//	To create File name or Folder Name for screen shot placement
							evidenceFolderName = row.getCell(1).toString() + "_" + Time;							
							objUIOperations.tcNameFolder(evidenceFolderName);			//	To create File name or Folder Name for screen shot placement with Data and Time
						}
	                	
	 	               	executionCheckFlag = true;		//	To make the test steps to be executed for the Execution Flag - "Yes" test cases, keep executionCheckFlag as True
	 	               	prevTC = true;
	                	tcNameRow = i;//	To update the status of the Test Case Level, keep previousTestCase as True
	                	
	                	al = new ArrayList<Integer>();
	                	al.add(i);	             
	                	k = al.get(0);
	                	if(testdatacolumn == 0)
	    				{	                		
	                		if(setvalue.equals("final"))
	                		{
	                			//testdatanos = 0;
	                			testdatacolumnno ++;
	                			testdatanos++;
	                			columnmatrixnos = testdatanos * 2;
	    		        		resultcolumn = testdatacolumnno + columnmatrixnos;	
	    		        		setvalue= "yettoupdate";
	                		}
	                		else
	                		{
	                			testdatacolumn = testdatacol;
	    						testdatanos = 0;	    							    						
	    						testdatacolumnno = 6;
	    						//testdatanos++;
	    						columnmatrixnos = testdatanos * 2;
	    		        		resultcolumn = testdatacolumnno + columnmatrixnos;
	    						previouscolumnval = k;	    		        		
	    		        		//i = k;
	    		           		//setvalue = "";
	                		}
	                			
	    				}	    								    					    				

	    				if(testdatacolumn >= 1)
	    				{	    			    						    					
	    					testdatacolumn--;
	    					testdatacolumnno ++;	                			
	    					testdatanos++;	    
	    					columnmatrixnos = testdatanos * 2;
	    					resultcolumn = testdatacolumnno + columnmatrixnos;	
	    					i = k;
	    					
	    					if(previouscolumnval != 0)
	    					{
	    						if(k > previouscolumnval)
	    						{
	    							i = previouscolumnval;
	    							testdata_status = "False"; // To validate the single or double test data per test case
	    						}
	    					}
	    					else
	    					{
	    						previouscolumnval = al.get(0);
	    					}	    					
	    				}
	    				
	                }
	                else
	                {
	                	executionCheckFlag = false;
	                	loggerET.info("New Testcase-> "+row.getCell(1).toString() + " Run_Flag: " + row.getCell(2).toString());
	                }										
				}													          	
				else
				{					
					loggerET.info("Login Check Flag: " + loginCheckFlag);
	/********************************************** Test Steps Execution Starts ******************************************************************************************/				
					if(executionCheckFlag && loginCheckFlag)		//	If only the ExecutionCheckFlag and LoginCheckFlag is true, the test steps will be executed
					{						
						loggerET.info("Sheet Name: " + taskCode);	
						try
						{
							if(row.getCell(testdatacolumnno).toString().length() >=1)
							{
								if(row.getCell(7).getCellType()==row.getCell(7).CELL_TYPE_NUMERIC)
		        				{
		        					//Call perform function to perform objUIOperations on UI when cell type(Test Data) is Numeric
		        					loggerET.info("Calling objUIOperations when cell type is NUMERIC ");		        					
		        					System.out.println("Value: " + row.getCell(7).toString());		        				
		        					objUIOperations.perform(row.getCell(1).toString(), row.getCell(3).toString(), row.getCell(4).toString(),  
		        					row.getCell(5).toString(), row.getCell(6).toString(), NumberToTextConverter.toText(row.getCell(7).getNumericCellValue()), i, tcNameRow, t_Status, webDriverLocation, reportSheet, taskCode, browserName);
		        				}
		        				else 
		        					//if(row.getCell(testdatacolumnno).toString().length() > 1)
		        				{
		        					//Call perform function to perform operation on UI when cell type(Test Data) is Non-Numeric
			        				loggerET.info("Calling Operation when cell type is .NON-NUMERIC ");		        				
			        				objUIOperations.perform(row.getCell(1).toString(), row.getCell(3).toString(), row.getCell(4).toString(), 
			        				row.getCell(5).toString(), row.getCell(6).toString(), row.getCell(testdatacolumnno).toString(), i, tcNameRow, t_Status, webDriverLocation, reportSheet, taskCode, browserName);
			        				testdata_status = "True";     					
		        				}
							}
							else if (testdata_status == "True")
							{								
								if(row.getCell(7).getCellType()==row.getCell(7).CELL_TYPE_NUMERIC)
			        			{
			        				//Call perform function to perform objUIOperations on UI when cell type(Test Data) is Numeric
			        				loggerET.info("Calling objUIOperations when cell type is NUMERIC ");		        					
			        				System.out.println("Value: " + row.getCell(7).toString());		        				
			        				objUIOperations.perform(row.getCell(1).toString(), row.getCell(3).toString(), row.getCell(4).toString(),  
			        				row.getCell(5).toString(), row.getCell(6).toString(), NumberToTextConverter.toText(row.getCell(7).getNumericCellValue()), i, tcNameRow, t_Status, webDriverLocation, reportSheet, taskCode, browserName);
			        			}
			        			else if(row.getCell(testdatacolumnno).toString().length() == 0)
			        			{
			        				 //Call perform function to perform operation on UI when cell type(Test Data) is Non-Numeric
				        			 loggerET.info("Calling Operation when cell type is .NON-NUMERIC ");		        				
				        			 objUIOperations.perform(row.getCell(1).toString(), row.getCell(3).toString(), row.getCell(4).toString(), 
				        			 row.getCell(5).toString(), row.getCell(6).toString(), row.getCell(testdatacolumnno).toString(), i, tcNameRow, t_Status, webDriverLocation, reportSheet, taskCode, browserName);
				        			 testdata_status = "True";			        						        					
			        			}
							}														
	        				else
        					{
        						t_Status.add("Null");	        						
        					}
						}
						catch (Exception e) 
						{
							String errmsg = e.getLocalizedMessage();
							if(errmsg == null)
							{								
								System.out.println("No Test data available");
							}
							else
							{
								e.printStackTrace();
							}
						}											
			       }
	/********************************************** Test Steps Execution Ends ******************************************************************************************/				
			   }																												            
			}
				catch (Exception exp)
				{
					loggerET.info("Catch block  exp: " + exp);
					loggerET.error("Exception  Caught exp ==>"+exp);
//					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, tcNameRow, LoadPropertySingleton.configResourceBundle.getProperty("TESTCASE_FAIL"),"");
					exp.printStackTrace();
				}
				loggerET.info("Array List: " + t_Status);	
				
				if(i == totRowCount && testdatacolumn >0)
				{			
					//testdatacolumnno ++;
					//testdatacolumn--;					
					if(k > previouscolumnval)
					{
						i = previouscolumnval;						
					}
					else
					{
						previouscolumnval = al.get(0);
						testdata_status = "False";
					}					
					
					i = previouscolumnval - 1; 
					setvalue = "final";
				}				
		   }
	}
   	public static void killTask() throws Exception 
	{
   		if(LoadPropertySingleton.configResourceBundle.getProperty("Browser").equalsIgnoreCase("IE")) 
   		{
   			Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
   			Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
   			//driver.manage().deleteAllCookies();
   			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");	
   			Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");	
   			Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe");
   		}
   		else 
   		{
   			driver.quit();  
   		}

	}
	@SuppressWarnings("deprecation")
	public void initializeRunTimeTask(String browserName,Boolean driverFlag,String webDriverLocation,String chromeDriverPath) 
    		throws Exception
    {
    	DOMConfigurator.configure("Logs.xml");		
		
/******************************* Launching IE Browser ****************************************************************/			
    	if(browserName.equalsIgnoreCase("IE"))
		{
    		
    		if(webDriverLocation.endsWith("MicrosoftWebDriver.exe"))
    		{
    			System.setProperty("webdriver.edge.driver", webDriverLocation);
    			driver = new EdgeDriver();
    			loggerET.info("IE Driver Launched Successfully");
    		}
    		else
    		{
    			System.setProperty("webdriver.ie.driver", webDriverLocation);     			
    			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
    			ieCapabilities.setCapability("nativeEvents", false);
    			ieCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
    			ieCapabilities.setCapability("ignoreProtectedModeSettings", true);
    			ieCapabilities.setCapability("disable-popup-blocking", true);
    			ieCapabilities.setCapability("enablePersistentHover", true);
    			ieCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);    			    			
    			driver = new InternetExplorerDriver();
    			loggerET.info("IE Driver Launched Successfully");
    		}
    		
		}
		else if (browserName.equalsIgnoreCase("FireFox"))
		{
/******************************* Launching Firefox Browser ****************************************************************/	
		System.setProperty("webdriver.gecko.driver", chromeDriverPath);
		DesiredCapabilities dc = DesiredCapabilities.firefox();
		dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		dc.setCapability("acceptInsecureCerts", true);
		dc.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
		dc.setCapability("marionette", false);
		driver = new FirefoxDriver(dc);
		System.out.println("Firefox Driver Launched Successfully");
		driver.manage().deleteAllCookies();
		}
		else if(browserName.equalsIgnoreCase("Chrome"))
		{
/******************************* Launching Chrome Browser ****************************************************************/			
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			DesiredCapabilities chromecapabilities = DesiredCapabilities.chrome();
			chromecapabilities.setCapability("nativeEvents", false);
			chromecapabilities.setCapability("unexpectedAlertBehaviour", "accept");
			chromecapabilities.setCapability("ignoreProtectedModeSettings", true);
			chromecapabilities.setCapability("disable-popup-blocking", true);
			chromecapabilities.setCapability("enablePersistentHover", true);						 
			driver = new ChromeDriver();
			loggerET.info("Chrome Driver Launched Successfully");
			System.out.println("Chrome Driver Launched Successfully");
			driver.manage().deleteAllCookies();
		}
    	
    }
/************************************* Key Word Driven Ends *********************************************************/
	public static void main(String args[]) throws Exception 
	{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date now = new Date();
	    String startTime=sdf.format(now);
	    System.out.println("Start time ==> "+startTime);
		ExecuteTest objExecuteTest = new ExecuteTest();
		String taskCode = LoadPropertySingleton.configResourceBundle.getProperty("ListOfTaskCodes");
		StringTokenizer token = new StringTokenizer(taskCode, ",");
		while(token.hasMoreTokens())
		{
			String taskCodeName = token.nextToken();
			System.out.println("Task Code: " + taskCodeName);
			objExecuteTest.execute(taskCodeName);
			driverFlag = false;
			System.out.println("Driver Flag 2: " + driverFlag);
		}
		killTask();
		Date end = new Date();
		String endTime=sdf.format(end);
		System.out.println("startTime ==> "+ startTime + "   End time ==> "+endTime);
	}

	/****************************************Starting of the Extent Report *******************************************/
	
	 public void startReport() 
	 {
	 htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/Report.html");
	         // Create an object of Extent Reports
	 extent = new ExtentReports();  
	 extent.attachReporter(htmlReporter);
	 extent.setSystemInfo("Host Name", "DF");
	         extent.setSystemInfo("Environment", "SIT");
	 extent.setSystemInfo("User Name", "DF");
	 htmlReporter.config().setDocumentTitle("Test Result"); 
	                // Name of the report
	 htmlReporter.config().setReportName("Automation Report"); 
	                // Dark Theme
	 htmlReporter.config().setTheme(Theme.STANDARD);		 
	 }
	
}