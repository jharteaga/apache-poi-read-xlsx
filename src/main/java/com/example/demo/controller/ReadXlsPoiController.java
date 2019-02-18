package com.example.demo.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Employee;
import com.example.demo.service.ReadXlsPoiService;

@Controller
public class ReadXlsPoiController {
	
	@Autowired
	private ReadXlsPoiService readXlsPoiService; 
	
	@GetMapping("")
	public @ResponseBody List<Employee> defaultPage() throws EncryptedDocumentException, IOException, ParseException {
		String fileName = "C:\\Users\\jharteaga\\Desktop\\EmployeeWorkbook.xlsx";
		List<Employee> empList = new ArrayList<Employee>();
		empList = readXlsPoiService.readXlsFile(fileName);
		return empList;
	}
	
}
