package beike.visitorsystem.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import beike.visitorsystem.visitor.model.*;

public class CreateSimpleExcelToDisk {
	@SuppressWarnings("deprecation")
	public void downloadVisitorIdentityExcelToDisk(List<VisitorIdentity> visitorIdentityList,HttpServletResponse response) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFCellStyle textStyle = wb.createCellStyle();
		HSSFDataFormat format = wb.createDataFormat();
		textStyle.setDataFormat(format.getFormat("@"));
		HSSFSheet sheet = wb.createSheet("证件表");
        sheet.setColumnWidth(0,10*356); 
        sheet.setColumnWidth(1,25*356);
        sheet.setColumnWidth(2,10*356); 
        sheet.setColumnWidth(3,10*356);
        HSSFRow row = sheet.createRow((int) 0);    
		HSSFCellStyle style = wb.createCellStyle();
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellStyle(textStyle);
		cell.setCellValue("全名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("证件号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("证件类型");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("是否黑名单");
		cell.setCellStyle(style);
		for (int i = 0; i < visitorIdentityList.size(); i++) {
			row = sheet.createRow((int) i + 1);
			VisitorIdentity visitorIdentity= (VisitorIdentity) visitorIdentityList.get(i); 
			row.createCell((short) 0).setCellValue(visitorIdentity.getFullName());    
            row.createCell((short) 1).setCellValue(visitorIdentity.getIdentityCard()); 
            if(visitorIdentity.getIdentityCardType()==0) {
            	row.createCell((short) 2).setCellValue("中国居民身份证");   
            }
            else if(visitorIdentity.getIdentityCardType()==1){
            	row.createCell((short) 2).setCellValue("中国护照");   
            }
            else if(visitorIdentity.getIdentityCardType()==2){
            	row.createCell((short) 2).setCellValue("台胞证");   
            }
            else {
            	row.createCell((short) 2).setCellValue("未知");   
            }
            if(visitorIdentity.getIsBanned()==0) {
            	row.createCell((short) 3).setCellValue("否");   
            }
            else if(visitorIdentity.getIsBanned()==1){
            	row.createCell((short) 3).setCellValue("是");   
            }
		}
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			String fileName = "证件表.xls";// 文件名
			System.out.println(fileName);
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
			wb.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}  
	}
	public void downloadVisitorExcelToDisk(List<VisitorUser> visitorUserList,HttpServletResponse response) {
		 // 第一步，创建一个webbook，对应一个Excel文件    
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFCellStyle textStyle = wb.createCellStyle();
        HSSFDataFormat format = wb.createDataFormat();
        textStyle.setDataFormat(format.getFormat("@"));
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet    
        HSSFSheet sheet = wb.createSheet("用户表");  
        sheet.setColumnWidth(0,20*356); 
        sheet.setColumnWidth(1,20*356);
        sheet.setColumnWidth(2,10*356); 
        sheet.setColumnWidth(3,25*356); 
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short    
        HSSFRow row = sheet.createRow((int) 0);    
        HSSFCellStyle style = wb.createCellStyle();    
        HSSFCell cell = row.createCell((short) 0);   
        cell.setCellStyle(textStyle);
        cell.setCellValue("用户名");    
        cell.setCellStyle(style);    
        cell = row.createCell((short) 1);    
        cell.setCellValue("注册时间");    
        cell.setCellStyle(style);    
        cell = row.createCell((short) 2);    
        cell.setCellValue("是否黑名单");    
        cell.setCellStyle(style);    
        cell = row.createCell((short) 3);    
        cell.setCellValue("主身份证");    
        cell.setCellStyle(style);    
        // 第五步，写入实体数据 
        for (int i = 0; i < visitorUserList.size(); i++)    
        {    
            row = sheet.createRow((int) i + 1);
            VisitorUser visitorUser= (VisitorUser) visitorUserList.get(i);    
            // 第四步，创建单元格，并设置值    
            row.createCell((short) 0).setCellValue(visitorUser.getOpenId());    
            row.createCell((short) 1).setCellValue(visitorUser.getCreateTime()); 
            if(visitorUser.getIsBanned()==0) {
            	row.createCell((short) 2).setCellValue("否");   
            }
            else if(visitorUser.getIsBanned()==1){
            	row.createCell((short) 2).setCellValue("是");   
            }
            cell = row.createCell((short) 3); 
            if(visitorUser.getVisitorBindingIdentityList()!=null) {
            	List<VisitorBindingIdentity> visitorBindingIdentityList=visitorUser.getVisitorBindingIdentityList();
            	for(int j=0;j<visitorBindingIdentityList.size();j++) {
            		VisitorBindingIdentity visitorBindingIdentity=(VisitorBindingIdentity)  visitorBindingIdentityList.get(j);
            		if(visitorBindingIdentity.getIsMain()==1) {
            			row.createCell((short) 3).setCellValue(visitorBindingIdentity.getVisitorIdentity().getIdentityCard());
            		}
            	}	
            } 
            else 
            	row.createCell((short) 3).setCellValue("未知");
        } 
        OutputStream out = null;    
        try {        
            out = response.getOutputStream();    
            String fileName = "用户表.xls";// 文件名    
            System.out.println(fileName);
            response.setContentType("application/x-msdownload");    
            response.setHeader("Content-Disposition", "attachment; filename="    
                                                    + URLEncoder.encode(fileName, "UTF-8"));    
            wb.write(out);    
        } catch (Exception e) {    
            e.printStackTrace();    
        } finally {      
            try {       
                out.close();      
            } catch (IOException e) {      
                e.printStackTrace();    
            }      
        }     
    }    

}
