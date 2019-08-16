package objectRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ReadRepository 
{

	public  WebDriver driver ;
	public 	ChromeDriver driverchrome;
	public 	static String repositoryfilepath = "";
	public Cell objectlabelcell = null;
	public Cell objectnamecell = null;
	public Cell locatortypecell = null;
	public Cell valuecell = null;		
	public int columnno = 0;
	public String ObjectName;
	public String ObjectLabel;
	public String objectLabeloldval;
	public String objectLabelnewval;
	public String Locatortype;
	public String value;
	public String rbtype;
	public String Objectlabelval = "Not found";
	public Element lblsibling;
	public int txtboxcounterval;
	public String dupelval;
	
     public static void main( String[] args ) throws Exception
     { 
    	 ReadRepository ra = new ReadRepository();	    	 
    	 ra.getrepository();
    	 
     }    	 	    	
		@SuppressWarnings("unused")
		public void getrepository() throws IOException
    	{    
    		 	String filename = "";
    		 	Document doc = Jsoup.parse(new File(filename), "utf-8");
	    	 	System.setProperty("java.net.useSystemProxies", "true");	    	    	    	    
	            Elements links = doc.select("*");        		            
	            for (Element link : links) 
	            {  
	            	String id = link.attr("id");
	            	String type = link.attr("type");
	            	String name = link.attr("name");
	            	String placeholdervalue = link.attr("placeholder");
	            	String text = link.ownText();
	            	String tagname = link.tagName();		
	            	//System.out.println(tagname);
	            	String classname = link.className();
	            	String result = null;
	            	String strvalue = "pass";
	            	lblsibling = null;	            	
	            	Objectlabelval = "Not found";
	            	
	            	//Verify the input tag
	            	if(tagname.equals("input"))
	            	{	            		
	            		//verify the id present
	            		if(id!= "")
		            	{	
	            			//System.out.println(id);	            			
							Element lblsibling= link.parent().previousElementSibling();	    
							//Storing the object label values and identifying the counter values if the same label has more than one textbox
            				if(lblsibling != null)
            				{
            					if(lblsibling.text().length() > 1)
            					{            							            					
            						Objectlabelval = "found";  
            						objectLabelnewval = lblsibling.text();
            						if(objectLabelnewval.equals(objectLabeloldval))
            						{       
            							try
            							{
            								if(dupelval.equals(lblsibling.text()))
                							{
                								txtboxcounterval++;
                							}
            							}
            							catch(Exception e)
            							{
            								txtboxcounterval = 0;
            							}            											
            							ObjectLabel = lblsibling.text() + txtboxcounterval;
            							dupelval= objectLabeloldval;
            						}
            						else
            						{
            							ObjectLabel = lblsibling.text();
            						}
            					}
            					objectLabeloldval = lblsibling.text();
            				}               				            				
	            			Attributes attr = link.attributes();
	            			for(Attribute att : attr)
	            			{	            				
	            				if(att.getKey().contains("name"))
	            				{
	            					//String attrval = att.getValue();	
	            					if(Objectlabelval == "Not found")
	            					{
	            						ObjectLabel = capitalizeWord(att.getValue());
	            					}	            					
	            					ObjectName = "txt" + att.getValue();
	            					Locatortype = "id";
				            		value =	id;
	            					result = "Yes";
	            					break;
	            				}			            						            			
	            			}
	            				
	            			//To identify the radio button type
	            			int counter = 0;
	            			for(Attribute at : attr)
	            			{
	            				counter ++;		            				
	            				if(at.getKey().equals("type") && at.getValue().equals("radio")) 
	            				{
	            					ObjectName = "rb" + id;		            					
	            					strvalue = "found";	
	            					rbtype = "radio";
	            					break;
	            				}
	            						            						            				
	            				if(link.attributes().size() == counter && strvalue != "pass")
		            			{
		            				ObjectName = "txt" + id;		            				
		            			}
	            			 }	
	            			
	            			if(rbtype == "radio")
	            			{
	            				for(Attribute attrbs : attr)
		            			{			            				
		            				if(attrbs.getKey().equals("id"))
		            				{
		            					ObjectLabel = capitalizeWord(attrbs.getValue());
		            					break;
		            				}
		            			}
	            			}
	            			try
	            			{
	            				if(ObjectLabel.length()>1 && ObjectName.length()>1 && Locatortype.length()>1 && value.length() >1)
		            			{
		            				System.out.println(ObjectLabel + " " +ObjectName+ " " + Locatortype+ " " + value);			            					            		
				            		Verifyvaluesinxl();
		            			}
	            			}
	            			catch (Exception e) 
	            			{		   
	            				if(e.getMessage() == null)
	            				{	            					
	            				}
	            				else
	            				{
	            					e.printStackTrace();
	            				}			            						            			
							}		            					            						            					          			            		
		            		//id present and no name attribute is present
		            		if(result!="Yes")
		            		{		            					            						            		
		            			if(lblsibling.tagName().equals("label")) 
				            	{	
		            				if(ObjectLabel == null)
		            				{
		            					break;
		            				}		            									            			
				            	}
		            			else
	            				{
	            					ObjectLabel = capitalizeWord(lblsibling.id());
				            		ObjectName = "txt" + id;
					            	Locatortype = "id";
					            	value =	id;
					            	System.out.println(ObjectLabel + " " +ObjectName+ " " + Locatortype+ " " + value);			            					            		
					            	Verifyvaluesinxl();
	            				}		            					            			
		            		}
		            	 }
	            	else if (id == "" && name!="")
	            	{
	            		Element elements = link.parent().parent();
	            		Elements ele = elements.getAllElements();
	            		for(Element el : ele)
	            		{
	            			if(el.tagName().equals("label")) 
			           		{				            		
			           			ObjectLabel = el.text();				            			
			           			break;
			           		}
	            		}
	            		Attributes att = link.attributes();
	            		for(Attribute at : att)
	            		{
	            			if(at.getKey().equals("type") && at.getValue().equals("radio")) 
	            			{
	            				ObjectName = "rb" + name;
	            				strvalue = "found";
	            				break;
	            				}
	            		}
	            		
	            			if(strvalue != "found")
	            			{
	            				if(name.length()>0)
	            				{
	            					ObjectLabel = capitalizeWord(name);
		            				ObjectName = "txt" + name;
	            				}
	            						            				
	            			}
	            		
	            			Locatortype = "name";
		            		value =	name;
		            		System.out.println(ObjectLabel + " " +ObjectName+ " " + Locatortype+ " " + value);			            					            		
		            		Verifyvaluesinxl();		            						            					            					            				            
	            	}
	            }		         		            	
	            //Verify with the button tag
	            else if(tagname.equals("button"))
	            {
	            	if(id!="")
	            	{
	            		ObjectLabel = capitalizeWord(link.ownText());
	            		ObjectName = "btn" + id;
		           		Locatortype = "id";
		           		value =	id;		            			
	            		System.out.println(ObjectLabel + ObjectName + Locatortype + value);		            			
	            		Verifyvaluesinxl();		            					            					            			
	            	}
	            	else if(name.length() > 0 && text.length()> 0)
	            	{
	            		ObjectLabel = capitalizeWord(link.ownText());
	            		ObjectName = "btn" + text.trim().toLowerCase();
		           		Locatortype = "xpath";
		           		value =	"//"+link.tagName()+"[(normalize-space(text())='"+text+"')]";		            			
	            		System.out.println(ObjectLabel + ObjectName + Locatortype + value);
	            		Verifyvaluesinxl();		            					            			
	            	}
	            	else if(name == "" && text.length()> 0)
	            	{
	            		ObjectLabel = capitalizeWord(link.ownText());
	            		ObjectName = "btn" + text.trim().toLowerCase();
		           		Locatortype = "xpath";
		           		value =	"//"+link.tagName()+"[(normalize-space(text())='"+text+"')]";		            			
	            		System.out.println(ObjectLabel + ObjectName + Locatortype + value);
	            		Verifyvaluesinxl();		            					            			
	            	}
	            }
	            // To verify with the select tag for dropdown list
	            else if(tagname.contains("select"))
	            {
	            	if(id!="")
	            	{
	            		ObjectLabel = capitalizeWord(link.ownText());
	            		ObjectName = "btn" + id;
		           		Locatortype = "id";
		           		value =	id;		            			
	            		System.out.println(ObjectLabel + ObjectName + Locatortype + value);		            			
	            		Verifyvaluesinxl();		            					            					            			
	            	}
	            	else if(name.length() > 0)
	            	{		            				            		
		           		Locatortype = "xpath";
		           		Attributes attr = link.attributes();
		           		for(Attribute atr:attr)
		           		{
		           			if(atr.getKey().equals("name"))
		           			{
		           				ObjectLabel = capitalizeWord(atr.getValue());
		           				ObjectName = "ddl" + atr.getValue().toLowerCase();
		           				value =	"//"+link.tagName()+"[(@name='"+atr.getValue()+"')]";		            			
			            		System.out.println(ObjectLabel + ObjectName + Locatortype + value);
			            		Verifyvaluesinxl();		   
		           			}
		           		}			           		         					            		
	            	}
	            	else if(name.length() > 0 && text.length()<=0)
	            	{
	            		ObjectLabel = capitalizeWord(link.attr("name"));
	            		ObjectName = "ddl" + link.attr("name").trim().toLowerCase();
		           		Locatortype = "xpath";
		           		value =	"//"+link.tagName()+"[(normalize-space(text())='"+link.attr("name")+"')]";		            			
	            		System.out.println(ObjectLabel + ObjectName + Locatortype + value);
	            		Verifyvaluesinxl();		            					            			
	            	}
	            	
	            }
	            
	            else if(type.equals("text") && id== "" && name == "" && placeholdervalue!="")
	            {		  
	            	ObjectLabel = capitalizeWord(link.attr("placeholder"));
	            	ObjectName = "txt" + placeholdervalue;
	            	Locatortype = "xpath";
	            	value =	"//"+link.tagName()+"[(@placeholder='"+placeholdervalue+"')]";		            		
	            	System.out.println(ObjectName + Locatortype + value);
	            	try
	            	{
	            		Element labelname = link.previousElementSibling();
	            		ObjectLabel = labelname.text();
	            	}
	            	catch (Exception e) 
	            	{
	            		continue;
					}
	            	Verifyvaluesinxl();
	            }		      
	            else if(link.tagName().equals("textarea"))
	            {
	            	Element lblsibling= link.parent().previousElementSibling();	    
    				if(lblsibling != null)
    				{
    					if(lblsibling.text().length() > 1)
    					{            							            					    						
    						ObjectLabel = lblsibling.text();
    					}               				            				        			
    				}
    				Attributes attr = link.attributes();
        			for(Attribute att : attr)
        			{	            				
        				if(att.getKey().contains("name"))
        				{        						            				
        					ObjectName = "txt" + att.getValue();
        					Locatortype = "id";
		            		value =	id;
        					result = "Yes";
        					break;
        				}			            						            			
        			}
	            }	
	            else if(text.length() >1)
	            {
	            	ObjectLabel = capitalizeWord(link.ownText().trim() + "label");
	            	if(ObjectLabel.length() == 0)
	            	{
	            		ObjectLabel = text;
	            	}
	            	ObjectName = "lbl" + text.toLowerCase();
	            	Locatortype = "xpath";
	            	value =	"//"+link.tagName()+"[contains(text(),'"+text.trim()+"')]";			            		
            		System.out.println(ObjectLabel + ObjectName + Locatortype + value);
            		try
            		{	            			
            			Verifyvaluesinxl();
            		}
            		catch (Exception e) {
						e.printStackTrace();
					}
	            }
	            /*else if(id == "" && type == "" && name == "" && text.length()>1 && classname!="")
	            {
	            	ObjectLabel = capitalizeWord(link.ownText().trim());
	            	ObjectName = "lnk" + text.toLowerCase();
	            	Locatortype = "xpath";
	            	value =	"//"+link.tagName()+"[(normalize-space(text())='"+text+"')]";			            		
            		System.out.println(ObjectLabel + ObjectName + Locatortype + value);
            		try
            		{	            			
            			Verifyvaluesinxl();
            		}
            		catch (Exception e) {
						e.printStackTrace();
					}
            			
	            }*/	           
	        }  
    	 }  
    	 
    	public static void killchromedriver() throws Exception 
    	{
    		Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
    		Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
    		Thread.sleep(1000);
    		Thread.sleep(2000);
    	}
    		
    		//Writing the Object Name, Object Values in excel
    	public void writexl() throws IOException
    	{
    		FileInputStream file = new FileInputStream(new File(repositoryfilepath));	    			
    		@SuppressWarnings("resource")
			HSSFWorkbook workbook = new HSSFWorkbook(file);
    		HSSFSheet resultsheet = workbook.getSheet("NAB");	    			
    		int lastrowval = resultsheet.getLastRowNum();
    		HSSFRow lastrow = resultsheet.createRow(lastrowval + 1);	    				    	
    		//Writing the object name
    		objectlabelcell = lastrow.createCell(columnno);
    		objectlabelcell.setCellValue(ObjectLabel);
    		objectnamecell = lastrow.createCell(columnno + 1);
    		objectnamecell.setCellValue(ObjectName);
    		//Writing the Locator type
    		locatortypecell = lastrow.createCell(columnno + 2);
    		locatortypecell.setCellValue(Locatortype);
    		//Writing the value
    		valuecell = lastrow.createCell(columnno + 3);
    		valuecell.setCellValue(value);	    			
    		FileOutputStream fOut = new FileOutputStream(repositoryfilepath);
    		workbook.write(fOut);
    		fOut.close();	
    	}	    				    		
    	
    	public void Verifyvaluesinxl() throws IOException
    	{
    		String dupvalstatus = "";
    		FileInputStream file = new FileInputStream(repositoryfilepath); 
    		@SuppressWarnings("resource")
			HSSFWorkbook workbook = new HSSFWorkbook(file);
    		HSSFSheet resultsheet = workbook.getSheet("NAB");	    			
    		int lastrowval = resultsheet.getLastRowNum();
    		for(int i = 0;i<=lastrowval;i++)
    		{
    			String dupObjectLabel = resultsheet.getRow(i).getCell(0).getStringCellValue();
    			String dupObjectName = resultsheet.getRow(i).getCell(1).getStringCellValue();
    			String dupLocator = resultsheet.getRow(i).getCell(2).getStringCellValue();
    			String dupvalue = resultsheet.getRow(i).getCell(3).getStringCellValue();
    			if(dupObjectLabel.equals(ObjectLabel) && dupObjectName.equals(ObjectName) 
    			   && dupLocator.equals(Locatortype) && dupvalue.equals(value))
    			{
    				System.out.println("Duplicate values are found" + " " + dupObjectLabel + dupObjectName + dupLocator + dupvalue);
    				dupvalstatus = "Yes";
    				break;
    			}	    			
    		}	    			    		
    		
    		if(dupvalstatus.length() > 1)
    		{	    			
    			dupvalstatus = "";
    		}
    		else 
    		{
    			writexl();
    		}
    	}
      
    	public static String capitalizeWord(String str)
    	{  
    		String words[]=str.split("\\s");  
    		String capitalizeWord="";  
    		for(String w:words)
    		{  
    			if(w.length()>1)
    			{
    				String first=w.substring(0,1);  
	    			String afterfirst=w.substring(1);  
	    			capitalizeWord+=first.toUpperCase()+afterfirst+" ";	    				
    			}
    			else
    			{    				
    			}	    			  
    		}  
    		return capitalizeWord.trim();  
    	}  

}
