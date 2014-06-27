package com.isoftframework.common.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.LongType;



import java.awt.Font;
import java.beans.*;

 
abstract public class ExcelUtil {
	
	private   Log log = LogFactory.getLog(ExcelUtil.class);

	private   String shtName = "";
	//列属性名
	private   String[] cNames = null;
	//列显示名
	private   String[] cLabels = null;
	 
	//每页显示行数
	private   int rpp = 5000;

	private   HSSFCellStyle style = null;
	
	private HSSFWorkbook  wb;
	
	public ExcelUtil(){
		
	}
	
	
	public   void exportBySql(HttpServletResponse response,Session session, String querySql,String countSql,
			String fileName, String sheetName, String[] colTitle, int rowPerPage)  {

		if (rowPerPage <= 10000 && rowPerPage >= 1) {
			rpp = rowPerPage;
		}
		if (!"".equals(sheetName) && null != sheetName) {
			shtName = sheetName;
		} else {
			shtName = "sheet";
		}
		 
		this.cLabels=colTitle;
		 wb = new HSSFWorkbook();
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//--
		SQLQuery query=session.createSQLQuery(querySql);
		int curPage=1;
		int count =getCount(session,countSql);
		int totalPage = ((count + rpp) - 1) / rpp;
		for(int i=0;i<totalPage;i++){
			curPage=i+1;
			int firstResult=(curPage - 1) * rpp;
			query.setFirstResult(firstResult);
			query.setMaxResults(rpp);
			List list= query.list();
			HSSFSheet sheet = createSheet(wb, curPage,count);
			setSheetColumnTitle(sheet);
			for(int rowCnt=0;rowCnt<list.size();rowCnt++){
				Object entity=list.get(rowCnt);
				HSSFRow row = sheet.createRow(rowCnt + 1);
				setCelValue(rowCnt + 1,entity,row,  wb,   sheet); 
			}
		}
		
		 
		try {
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment; filename="
					+ getFileName(fileName));
			wb.write(os);
			 
		} catch (IOException ex) {
			log.info("Export Excel file error ! " + ex.getMessage());
		}
	}
	public   void exportBySql(HttpServletResponse response,Session session,Class cls, String querySql,String countSql,
			String fileName, String sheetName, String[] colTitle, int rowPerPage)  {

		if (rowPerPage <= 10000 && rowPerPage >= 1) {
			rpp = rowPerPage;
		}
		if (!"".equals(sheetName) && null != sheetName) {
			shtName = sheetName;
		} else {
			shtName = "sheet";
		}
		 
		this.cLabels=colTitle;
		 wb = new HSSFWorkbook();
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//--
		SQLQuery query=session.createSQLQuery(querySql).addEntity("order1",cls);
		int curPage=1;
		int count =getCount(session,countSql);
		int totalPage = ((count + rpp) - 1) / rpp;
		for(int i=0;i<totalPage;i++){
			curPage=i+1;
			int firstResult=(curPage - 1) * rpp;
			query.setFirstResult(firstResult);
			query.setMaxResults(rpp);
			List list= query.list();
			HSSFSheet sheet = createSheet(wb, curPage,count);
			setSheetColumnTitle(sheet);
			for(int rowCnt=0;rowCnt<list.size();rowCnt++){
				Object entity=list.get(rowCnt);
				HSSFRow row = sheet.createRow(rowCnt + 1);
				setCelValue(rowCnt + 1,entity,row,wb,sheet); 
			}
		}
		
		 
		try {
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment; filename="
					+ getFileName(fileName));
			wb.write(os);
			 
		} catch (IOException ex) {
			log.info("Export Excel file error ! " + ex.getMessage());
		}
	}
	
	public   List<HSSFRow> insertRow(HSSFWorkbook wb, HSSFSheet sheet,
			   int starRow, int rows) {
			List<HSSFRow> list=new ArrayList<HSSFRow>();
			  sheet.shiftRows(starRow + 1, sheet.getLastRowNum(), rows);

			  starRow = starRow - 1;
			  
			  for (int i = 0; i < rows; i++) {

			   HSSFRow sourceRow = null;
			   HSSFRow targetRow = null;
			   HSSFCell sourceCell = null;
			   HSSFCell targetCell = null;
			   int m;
			   
			   starRow = starRow + 1;
			   sourceRow = sheet.getRow(starRow);
			   targetRow = sheet.createRow(starRow + 1);
			   targetRow.setHeight(sourceRow.getHeight());

			   for (m = sourceRow.getFirstCellNum(); m < sourceRow.getPhysicalNumberOfCells(); m++) {
			    
			    sourceCell = sourceRow.getCell(m);
			    targetCell = targetRow.createCell(m);
			    
			    targetCell.setCellStyle(sourceCell.getCellStyle());
			    targetCell.setCellType(sourceCell.getCellType());

			   }
			   list.add(targetRow);
			 }
			return list;

	}
	public int getCount(Session session,String countSql){
		 return      ((Long)session.createSQLQuery( countSql )
			.addScalar( "count" , LongType.INSTANCE)
			.uniqueResult()).intValue(); 
	}
	public   void export(HttpServletResponse response,Session session, String hql,
			String fileName, String sheetName, String[] colTitle, int rowPerPage)  {

		if (rowPerPage <= 10000 && rowPerPage >= 1) {
			rpp = rowPerPage;
		}
		if (!"".equals(sheetName) && null != sheetName) {
			shtName = sheetName;
		} else {
			shtName = "sheet";
		}
		 
		this.cLabels=colTitle;
		 wb = new HSSFWorkbook();
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//--
		Query query = session.createQuery("select count(*) "
				+ hql);
		int curPage=1;
		int count =Integer.parseInt(String.valueOf(query.uniqueResult()));
		int totalPage = ((count + rpp) - 1) / rpp;
		for(int i=0;i<totalPage;i++){
			curPage=i+1;
			int firstResult=(curPage - 1) * rpp;
			query = session.createQuery(hql);
			query.setFirstResult(firstResult);
			query.setMaxResults(rpp);
			List list= query.list();
			HSSFSheet sheet = createSheet(wb, curPage,count);
			setSheetColumnTitle(sheet);
			for(int rowCnt=0;rowCnt<list.size();rowCnt++){
				Object entity=list.get(rowCnt);
				HSSFRow row = sheet.createRow(rowCnt + 1);
				setCelValue(rowCnt + 1,entity,row, wb,   sheet); 
			}
		}
		
		 
		try {
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment; filename="
					+ getFileName(fileName));
			wb.write(os);
			 
		} catch (IOException ex) {
			log.info("Export Excel file error ! " + ex.getMessage());
		}
	}
	
	abstract public void setCelValue(int rowIndex,Object entity,HSSFRow row,HSSFWorkbook wb, HSSFSheet sheet);
		
	 
	
	 

	/**
	 * 创建一个Sheet页并返回该对象
	 * 
	 * @param wb
	 *            HSSFWorkbook
	 * @param seq
	 *            int
	 * @return HSSFSheet
	 */
	private   HSSFSheet createSheet(HSSFWorkbook wb, int seq) {
		
		int sup = seq * rpp;
		int sub = (seq - 1) * rpp + 1;
		if (sub < 1) {
			sub = 1;
		}
		HSSFSheet sheet=wb.createSheet( );
		shtName=shtName + "(" + sub + "-" + sup + ")";
		 
		wb.setSheetName(seq-1,shtName);   
		;
		return sheet;
	}
	/**
	 * 设置Sheet页的列属性 123. *
	 * 
	 * @param sht
	 *            HSSFSheet 124.
	 */
	private   void setSheetColumnTitle(HSSFSheet sht) {
		
		HSSFRow row = sht.createRow(0);
		for (int i = 0; i < cLabels.length; i++) {
			HSSFCell cell = row.createCell((short) (i));
			cell.setCellValue(cLabels[i]);
			cell.setCellStyle(style);
			//sht.autoSizeColumn(i,true);
			
		}
	}
	private   HSSFSheet createSheet(HSSFWorkbook wb, int seq,int totalSize) {
		
		int sup = seq * rpp < totalSize ? seq * rpp : totalSize;
		int sub = (seq - 1) * rpp + 1;
		if (sub < 1) {
			sub = 1;
		}
		HSSFSheet sheet=wb.createSheet( );
		 
		wb.setSheetName(seq-1,shtName + "(" + sub + "-" + sup + ")");   
		 
		return sheet;
	}
	/**
	 * 获得导出的文件全名
	 * 
	 * @param tableName
	 *            String
	 * @return String
	 */
	private static String getFileName(String fileName) {
		 fileName+= new Date().getTime() + ".xls";
		try {
			return URLEncoder.encode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		 
	}
	
	
	public void setCellValue(HSSFCell cell, Object value) {
		setCellValue(cell, value, null, (short) -1);
	}
	public void setCellRichTextString(HSSFCell cell, Object valueObj,
			Object specialValueObj, short fontColor) {
		if (valueObj == null) {
			return;
		}
		String value = String.valueOf(valueObj);
		String specialValue = String.valueOf(specialValueObj);
		HSSFRichTextString richString = new HSSFRichTextString(String
				.valueOf(value));
		if (specialValue != null && value.contains(specialValue)) {
			HSSFCellStyle cellStyle = cell.getCellStyle();
			HSSFFont font = wb.createFont();
			font.setColor(fontColor);
			richString.applyFont(value.indexOf(specialValue), value
					.indexOf(specialValue)
					+ specialValue.length(), font);
		}

		cell.setCellValue(richString.getString());
		// System.out.println("我执行了---");
	}
	public void setCellRichTextString(HSSFCell cell, Object valueObj,short fontColor) {

		if (valueObj != null) {
			String value = String.valueOf(valueObj);
			HSSFRichTextString richString = new HSSFRichTextString(String
					.valueOf(value));
			HSSFFont font = wb.createFont();
			font.setColor(fontColor);
			richString.applyFont(font);
			cell.setCellValue(richString.getString());
		}

	}

	public void setCellValue(HSSFCell cell, Object value, Object specialValue,
			short fontColor) {
		// 设置页编码格式,可选.
		// targetCell.set(sourceCell.ENCODING_COMPRESSED_UNICODE);
		// System.out.println("cell===="+cell);
		int cType = cell.getCellType();

		switch (cType) {
		case HSSFCell.CELL_TYPE_BOOLEAN:
			cell.setCellValue(Boolean.valueOf(String.valueOf(value)));
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			cell.setCellErrorValue(Byte.valueOf(value.toString()));

			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			cell.setCellFormula(parseFormula(String.valueOf(value.toString())));

			// parseFormula这个函数的用途在后面说明
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			cell.setCellValue(Double.valueOf(value.toString()));

			break;
		case HSSFCell.CELL_TYPE_STRING:
			setCellRichTextString(cell, value, specialValue, fontColor);
			// cell.setCellValue(new HSSFRichTextString(String.valueOf(value)));
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			if (value != null)
				cell.setCellValue(value.toString());
			break;
		default:
			System.out.println("cType=" + cType + "  setCellValue 没有匹配项");
			break;
		}
	}

	public Object getCellValue(HSSFCell cell) {

		int cType = cell.getCellType();

		switch (cType) {
		case HSSFCell.CELL_TYPE_BOOLEAN:

			return cell.getBooleanCellValue();

		case HSSFCell.CELL_TYPE_ERROR:

			return cell.getErrorCellValue();

		case HSSFCell.CELL_TYPE_FORMULA:
			return parseFormula(cell.getCellFormula());
			// parseFormula这个函数的用途在后面说明

		case HSSFCell.CELL_TYPE_NUMERIC:

			return cell.getNumericCellValue();

		case HSSFCell.CELL_TYPE_STRING:

			//return cell.getRichStringCellValue();
			return cell.getStringCellValue();
		case HSSFCell.CELL_TYPE_BLANK:
			return cell.getStringCellValue();
		default:
			System.out.println("cType=" + cType + "  getCellValue 没有匹配项");
			return null;

		}

	}

	private String parseFormula(String pPOIFormula) {
		final String cstReplaceString = "ATTR(semiVolatile)";
		StringBuffer result = null;
		int index;
		result = new StringBuffer();
		index = pPOIFormula.indexOf(cstReplaceString);
		if (index >= 0) {
			result.append(pPOIFormula.substring(0, index));
			result.append(pPOIFormula.substring(index
					+ cstReplaceString.length()));
		} else {
			result.append(pPOIFormula);
		}
		return result.toString();
	}
}
