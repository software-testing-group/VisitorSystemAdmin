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
		HSSFSheet sheet = wb.createSheet("֤����");
        sheet.setColumnWidth(0,10*356); 
        sheet.setColumnWidth(1,25*356);
        sheet.setColumnWidth(2,10*356); 
        sheet.setColumnWidth(3,10*356);
        HSSFRow row = sheet.createRow((int) 0);    
		HSSFCellStyle style = wb.createCellStyle();
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellStyle(textStyle);
		cell.setCellValue("ȫ��");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("֤����");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("֤������");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("�Ƿ������");
		cell.setCellStyle(style);
		for (int i = 0; i < visitorIdentityList.size(); i++) {
			row = sheet.createRow((int) i + 1);
			VisitorIdentity visitorIdentity= (VisitorIdentity) visitorIdentityList.get(i); 
			row.createCell((short) 0).setCellValue(visitorIdentity.getFullName());    
            row.createCell((short) 1).setCellValue(visitorIdentity.getIdentityCard()); 
            if(visitorIdentity.getIdentityCardType()==0) {
            	row.createCell((short) 2).setCellValue("�й��������֤");   
            }
            else if(visitorIdentity.getIdentityCardType()==1){
            	row.createCell((short) 2).setCellValue("�й�����");   
            }
            else if(visitorIdentity.getIdentityCardType()==2){
            	row.createCell((short) 2).setCellValue("̨��֤");   
            }
            else {
            	row.createCell((short) 2).setCellValue("δ֪");   
            }
            if(visitorIdentity.getIsBanned()==0) {
            	row.createCell((short) 3).setCellValue("��");   
            }
            else if(visitorIdentity.getIsBanned()==1){
            	row.createCell((short) 3).setCellValue("��");   
            }
		}
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			String fileName = "֤����.xls";// �ļ���
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
		 // ��һ��������һ��webbook����Ӧһ��Excel�ļ�    
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFCellStyle textStyle = wb.createCellStyle();
        HSSFDataFormat format = wb.createDataFormat();
        textStyle.setDataFormat(format.getFormat("@"));
        // �ڶ�������webbook�����һ��sheet,��ӦExcel�ļ��е�sheet    
        HSSFSheet sheet = wb.createSheet("�û���");  
        sheet.setColumnWidth(0,20*356); 
        sheet.setColumnWidth(1,20*356);
        sheet.setColumnWidth(2,10*356); 
        sheet.setColumnWidth(3,25*356); 
        // ����������sheet����ӱ�ͷ��0��,ע���ϰ汾poi��Excel����������������short    
        HSSFRow row = sheet.createRow((int) 0);    
        HSSFCellStyle style = wb.createCellStyle();    
        HSSFCell cell = row.createCell((short) 0);   
        cell.setCellStyle(textStyle);
        cell.setCellValue("�û���");    
        cell.setCellStyle(style);    
        cell = row.createCell((short) 1);    
        cell.setCellValue("ע��ʱ��");    
        cell.setCellStyle(style);    
        cell = row.createCell((short) 2);    
        cell.setCellValue("�Ƿ������");    
        cell.setCellStyle(style);    
        cell = row.createCell((short) 3);    
        cell.setCellValue("�����֤");    
        cell.setCellStyle(style);    
        // ���岽��д��ʵ������ 
        for (int i = 0; i < visitorUserList.size(); i++)    
        {    
            row = sheet.createRow((int) i + 1);
            VisitorUser visitorUser= (VisitorUser) visitorUserList.get(i);    
            // ���Ĳ���������Ԫ�񣬲�����ֵ    
            row.createCell((short) 0).setCellValue(visitorUser.getOpenId());    
            row.createCell((short) 1).setCellValue(visitorUser.getCreateTime()); 
            if(visitorUser.getIsBanned()==0) {
            	row.createCell((short) 2).setCellValue("��");   
            }
            else if(visitorUser.getIsBanned()==1){
            	row.createCell((short) 2).setCellValue("��");   
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
            	row.createCell((short) 3).setCellValue("δ֪");
        } 
        OutputStream out = null;    
        try {        
            out = response.getOutputStream();    
            String fileName = "�û���.xls";// �ļ���    
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
