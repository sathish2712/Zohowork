package com.zoho.backend_Zoho;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Customer {

	private static int invoiceNumber = 1;

	private String name;
	private String gender;
	private String phone;
	private String state;
	private String city;
	private String products;
	private String paymentType;
	private String payPin;
	private String custType;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProducts() {
		return products;
	}
	public void setProducts(String products) {
		this.products = products;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPayPin() {
		return payPin;
	}
	public void setPayPin(String payPin) {
		this.payPin = payPin;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	
	public boolean createInvoice(){
		String inventoryFilePath = "D:\\data\\Inventory.text";
		String invoiceFilePath = "D:\\data\\CustomerInvoices\\customer_invoice_" + invoiceNumber;
		FileOutputStream writeToInvoice = null;
		FileOutputStream fileOutputStream = null;
		Scanner scan = null;
		Map<String,String> productsMap = new HashMap<>();
		try{
			boolean isProductNoStock = false;
			Integer totalCost = 0;
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			String[] dt = dtf.format(now).split(" ");
			String[] products = getProducts().split(",");
			StringBuilder fileWriteData = new StringBuilder();
			for(int i = 0 ; i < products.length ; ++i){
				scan = new Scanner(new File(inventoryFilePath));
				while(scan.hasNextLine()) {
					String[] arr = scan.nextLine().split(" ");
					if (products[i].trim().equalsIgnoreCase(arr[1]) && getCustType().trim().equalsIgnoreCase(arr[4])) {
						if (Integer.parseInt(arr[2].trim()) > 0) {
							//replace the quantity in file
							arr[2] = String.valueOf(Integer.parseInt(arr[2]) - 1);
							fileWriteData.append(arr[0] + " " + arr[1] + " " + arr[2] + " " + arr[3] + " " + arr[4]);
							productsMap.put(arr[1], arr[3]);
							totalCost += Integer.parseInt(arr[3]);
							fileWriteData.append("\n");
						} else {
							isProductNoStock = true;
						}
					} else {
						fileWriteData.append(arr[0] + " " + arr[1] + " " + arr[2] + " " + arr[3] + " " + arr[4]);
						fileWriteData.append("\n");
					}
					if(i != products.length-1){
						fileWriteData.delete(0,fileWriteData.length());
					}
				}
			}
			scan.close();
			fileOutputStream = new FileOutputStream(inventoryFilePath);
			byte[] byteArray = fileWriteData.toString().getBytes();
			fileOutputStream.write(byteArray);
			fileOutputStream.close();

			//File object writer for invoice generation
			writeToInvoice = new FileOutputStream(invoiceFilePath);
			StringBuffer invoiceData = new StringBuffer();
			invoiceData.append(dt[0]);
			invoiceData.append("\n");
			invoiceData.append(dt[1]);
			invoiceData.append("\n");
			invoiceData.append("----------------------");
			invoiceData.append("Name : " + getName());
			invoiceData.append("\n");
			invoiceData.append("Gender : " + getGender());
			invoiceData.append("\n");
			invoiceData.append("Contact No :" + getPhone());
			invoiceData.append("\n");
			invoiceData.append("State : " + getState());
			invoiceData.append("\n");
			invoiceData.append("City : " + getCity());
			invoiceData.append("\n");
			invoiceData.append("----------------------");
			invoiceData.append("Products purchased");
			invoiceData.append("\n");
			for(Map.Entry<String,String> map : productsMap.entrySet()){
				invoiceData.append(map.getKey() + "   " + map.getValue());
				invoiceData.append("\n");
			}
			invoiceData.append("----------------------");
			invoiceData.append("\n");
			invoiceData.append("Payment type : " + getPaymentType());
			invoiceData.append("\n");
			invoiceData.append("Total Net amount : " + totalCost);
			byte[] invoiveWrite = invoiceData.toString().getBytes();
			writeToInvoice.write(invoiveWrite);
			if(isProductNoStock){
				return false;
			}
			invoiceNumber++;
		}
		catch (Exception e){
			System.out.println(e);
		}
		finally {
			try{
				System.out.println("Invoice has been successfully created! :) ");
				writeToInvoice.close();
				fileOutputStream.close();
			}catch (Exception e){
				System.out.println(e);
			}
		}
		return true;
	}
}
