package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.ReadXlsPoiRepository;

@Service
public class ReadXlsPoiService {
	
	@Autowired
	private ReadXlsPoiRepository readXlsPoiRepository;
	
	public List<Employee> readXlsFile(String fileName) throws EncryptedDocumentException, IOException, ParseException {
		
		// Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = WorkbookFactory.create(new File(fileName));
       
        // Getting the Sheet at index zero
        Sheet sheet = workbook.getSheetAt(0);

        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();
        List<Employee> empList = new ArrayList<Employee>();
        System.out.println("\n\nIterating over Rows and Columns using for-each loop\n");
        boolean headerFlag = false;
        int columnsCount;
        Employee emp;
        for (Row row: sheet) {
        	if (!headerFlag) {
        		headerFlag = true;
        	} else {
        		columnsCount = 0;
        		emp = new Employee();
        		for(Cell cell: row) {
                    String cellValue = dataFormatter.formatCellValue(cell);
                    System.out.print(cellValue + "\t");
                    switch(columnsCount) {
                    	case 0: emp.setName(cellValue);
                    			break;
                    	case 1: emp.setEmail(cellValue);
                    			break;
                    	case 2: Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(cellValue);
                    			emp.setBirthday(date1);
                    			break;
                    	case 3: emp.setSalary(Float.valueOf(cellValue));
                    			break;
                    	case 4: emp.setDepartment(cellValue);
                    			break;
                    }
                    columnsCount += 1;
                }
        		empList.add(emp);
        	}
        }
        
        empList = readXlsPoiRepository.saveAll(empList);
        
		return empList;
	}
	
}
