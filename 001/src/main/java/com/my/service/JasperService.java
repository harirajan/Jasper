package com.my.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;

public class JasperService {

	public static Connection establishConnection()
	{
		
	String jdbcUrl = "jdbc:postgresql://localhost:5432/hari";
    String username = "hari";
    String password = "Amba81ttha";

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      // Step 1 - Load driver
      Class.forName("org.postgresql.Driver"); // Class.forName() is not needed since JDBC 4.0

      // Step 2 - Open connection
      conn = DriverManager.getConnection(jdbcUrl, username, password);
      } catch (Exception e) {
    	  e.printStackTrace();
    	  
      }
    
    return conn;
	}
	
	public static void main(String[] args) throws JRException {
	/* JasperReport is the object
	that holds our compiled jrxml file */
	JasperReport jasperReport;
	/* JasperPrint is the object contains
	report after result filling process */
	JasperPrint jasperPrint;
	// connection is the data source we used to fetch the data from
	Connection connection = establishConnection(); 
	// jasperParameter is a Hashmap contains the parameters
	// passed from application to the jrxml layout
	Map<String, Object> jasperParameter = new HashMap<String,Object>();

	// jrxml compiling process
	jasperReport = JasperCompileManager.compileReport("src/main/resources/sample_report.jrxml");

	// filling report with data from data source

	jasperPrint = JasperFillManager.fillReport(jasperReport,jasperParameter, connection);
	// exporting process
	// 1- export to PDF
	JasperExportManager.exportReportToPdfFile(jasperPrint, "src/main/resources/sample_report.pdf");

	// 2- export to HTML
	JasperExportManager.exportReportToHtmlFile(jasperPrint, "src/main/resources/sample_report.html" );

	// 3- export to Excel sheet
	JRXlsExporter exporter = new JRXlsExporter();
	exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "src/main/resources/simple_report.xls" );

	exporter.exportReport();
	}
}