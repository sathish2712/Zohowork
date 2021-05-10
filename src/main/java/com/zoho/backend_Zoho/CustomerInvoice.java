package com.zoho.backend_Zoho;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class CustomerInvoice extends Customer{
	static int invoiceNumber = 1;
	public void createInvoice(String name, String gender, String phone, String state, String city) throws IOException {

		File f = new File("D:/data/invoices/customer_invoice_" + invoiceNumber + ".text");
		BufferedWriter writer = new BufferedWriter(new FileWriter(f));
		writer.write(name);
		writer.write(gender);
		writer.write(phone);
		writer.write(state);
		writer.write(city);
		writer.close();
		invoiceNumber++;
		
	}
	
}
