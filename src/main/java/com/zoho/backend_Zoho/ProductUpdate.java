package com.zoho.backend_Zoho;
import java.io.*;
import java.util.*;

public class ProductUpdate extends Product {
	//checks if prod is already present
	public boolean isExist(int id) throws FileNotFoundException {
		File file = new File("D:/data/inventory.text");
		Scanner scan = new Scanner(file);
		while(scan.hasNextLine()) {
			String[] arr = scan.nextLine().split(" ");
			for(int i = 0 ; i < arr.length ; i++) {
				if(arr[i].trim().equals(String.valueOf(id))) {
					return true;
				}
			}
		}
		return false;
		
	}
	
	public void productupdate(int id, String name, int cost) throws IOException{
		try {
			String data = String.valueOf(id) + "       " + name + "       " + String.valueOf(cost);
			File f = new File("D:/data/inventory.text");
			BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));
			writer.write(data);
			writer.newLine();
			writer.close();
			System.out.println("Success, product added!");	
		}catch(Exception e) {
			e.getStackTrace();
		}
	}
}
