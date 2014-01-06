package com.smartapps.excel;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Workbook workbook = Workbook.getWorkbook(new File(
					"code_sign_map.xls"));
			Sheet sheet = workbook.getSheet(0);
			Cell a1 = sheet.getCell(0, 0);
			Cell a2 = sheet.getCell(1, 0);
			String heading1 = a1.getContents();
			String heading2 = a2.getContents();
			System.out.println(heading1 + " -- " + heading2);
			int count = sheet.getRows();
			String s3="";
			for (int i = 1; i < 10; i++) {
				Cell b1 = sheet.getCell(0, i);
				Cell b2 = sheet.getCell(1, i);
				String s1 = b1.getContents();
				
				if(s1.equals(""))
				{
					s1=s3;
				}
				else
				{
					s3=s1;
				}
				
				
				String s2 = b2.getContents();
				s2=s2.replace("  ", " ");
				System.out.println("insert into signmap (code, sign) values ('"
						+ s1 + "', '" + s2.trim() + "');");
			}

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// try {
		// Workbook workbook = Workbook.getWorkbook(new File("Rules-B.xls"));
		// Sheet sheet = workbook.getSheet(0);
		// Cell a1 = sheet.getCell(0, 0);
		// Cell a2 = sheet.getCell(1, 0);
		// Cell a3 = sheet.getCell(2, 0);
		// String heading1 = a1.getContents();
		// String heading2 = a2.getContents();
		// String heading3 = a3.getContents();
		// System.out
		// .println(heading1 + " -- " + heading2 + " -- " + heading3);
		// int count = sheet.getRows();
		// for (int i = 1; i < count; i++) {
		// Cell b1 = sheet.getCell(0, i);
		// Cell b2 = sheet.getCell(1, i);
		// Cell b3 = sheet.getCell(2, i);
		// String s1 = b1.getContents();
		// String s2 = b2.getContents();
		// String s3 = b3.getContents();
		// System.out.println("insert into rules (code, fine, violation) values ('"+s1+"', '"+s2+"', '"+s3+"');");
		// }
		//
		// } catch (BiffException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

	}

}
