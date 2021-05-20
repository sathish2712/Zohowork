package com.zoho.backend_Zoho;

import static Utils.DataConstant.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

public class Product {
	private int productId;
	private String productName;
	private String productQuantity;
	private int productCost;
	private String isPremium;

	public String getIsPremium() {
		return isPremium;
	}

	public void setIsPremium(String isPremium) {
		this.isPremium = isPremium;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(String productQuantity) {
		this.productQuantity = productQuantity;
	}

	public int getProductCost() {
		return productCost;
	}

	public void setProductCost(int productCost) {
		this.productCost = productCost;
	}

	public boolean isIdExist(){
		try{
			String path = "D:\\data\\Inventory.text";
			File file = new File(path);
			if(!file.exists()){
				return false;
			}
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()){
				String[] arr = scan.nextLine().split(" ");
				if(arr[0].trim().equals(getProductId()) || arr[1].trim().equalsIgnoreCase(getProductName())){
					scan.close();
					return true;
				}
			}
		}catch (Exception e){
			System.out.println(e);
			return false;
		}
		return false;
	}
	public boolean updateProduct(){
		FileOutputStream fileOutputStream = null;
		try{
			String path = "D:\\data\\Inventory.text";
			String newLine = "\n";
			String data = String.valueOf(getProductId()) + " " + String.valueOf(getProductName()) + " " + String.valueOf(getProductQuantity()) + " " + String.valueOf(getProductCost()) + " " + String.valueOf(getIsPremium());
			System.out.println(data);
			fileOutputStream = new FileOutputStream(path,true);
			data += newLine;
			byte[] content = data.getBytes();
			fileOutputStream.write(content);
			fileOutputStream.close();
			System.out.println("Product added from Admin!");
		}catch (Exception e){
			System.out.println(e);
			return false;
		}finally {
			try {
				fileOutputStream.close();
			}catch (Exception ex){
				System.out.println(ex);
			}
		}
		return true;
	}
	public void constructReport(Map<String , Object> map){
		Map<String,Object> productMap = new HashMap<>();
		try{
			String path = "D:\\data\\Inventory.text";
			File file = new File(path);
			Scanner scan = new Scanner(file);
			List<Map<String,Object>> dataList = new ArrayList<>();
			while(scan.hasNextLine()){
				String[] arr = scan.nextLine().split(" ");
				Map<String,Object> dataMap = new HashMap<>();
				dataMap.put(PRODUCT_ID,arr[0]);
				dataMap.put(PRODUCT_NAME,arr[1]);
				dataMap.put(PRODUCT_QUANTITY,arr[2]);
				dataMap.put(PRODUCT_COST,arr[3]);
				dataMap.put(PRODUCT_TYPE,arr[4]);

				dataList.add(dataMap);
			}
			productMap.put(PRODUCT,dataList);
			map.put(JSON_DATA,productMap);
			map.put(JSON_STRING,JSON_SUCCESS);
		}catch(Exception e){
			map.put(JSON_STRING , JSON_ERROR);
			System.out.println(e);
		}
	}
}
