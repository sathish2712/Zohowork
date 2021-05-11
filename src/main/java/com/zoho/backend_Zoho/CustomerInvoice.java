package com.zoho.backend_Zoho;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class CustomerInvoice extends Thread{
	
	static int invoiceNumber = 1;

	public void createInvoice(String name, String gender, String phone, String state, String city, String products,
			String ptype, String pdetail, String ctype) throws IOException {
		//tokenizing products string
		String[] prods = products.split(",");
		
		//Date and Time Display
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now(); 
		String[] dt = dtf.format(now).split(" ");
		
		
		//product name and price as Key value pair
		HashMap<String,Integer> productMap = new HashMap<>();
		Integer totalCost = 0;
		String blankSpace = "-------------------------------------";
		
		//Invoice write to the document
		File f = new File("D:/data/invoices/customer_invoice_" + invoiceNumber + ".text");
		BufferedWriter writer = new BufferedWriter(new FileWriter(f));
		writer.write("Date  " + dt[0] + "        Time "+ dt[1]);
		
		
		for(int i = 0 ; i < prods.length ; i++) {
			String product = prods[i];
			File file = new File("D:/data/inventory.text");
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()) {
				String[] arr = scan.nextLine().split(" ");
				if(arr[1].trim().equals(product)) {
					totalCost += Integer.parseInt(arr[2].trim());
					productMap.put(arr[1].trim(), Integer.parseInt(arr[2].trim()));
				}
			}
		}
		
		writer.write("Customer Name : " + name);
		writer.write("Customer Gender : " + gender);
		writer.write("Phone Number : " + phone);
		writer.write("State " + state);
		writer.write("City " + city);
		writer.write(blankSpace);
		
		writer.write("-------Products purchased-------");
		for(Map.Entry<String, Integer> entry : productMap.entrySet()) {
			writer.write(entry.getKey() + "      " + entry.getValue());
		}
		writer.write(blankSpace);
		writer.write("Payment Method : " + ptype);
		writer.write("Your total purchased amount : " + totalCost);
		writer.write("You are "+ ctype);
		
		writer.close();
		System.out.println(productMap);
		
	}
	
}
