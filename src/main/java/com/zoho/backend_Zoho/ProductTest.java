package com.zoho.backend_Zoho;


import java.util.*;
import java.io.*;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("store")
public class ProductTest{
	
	static ArrayList<Customer> customerInvoicePersist = new ArrayList<>();
	
	@POST
	@Path("/admin")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProd(@QueryParam("pid") int pID, @QueryParam("pname") String pName, @QueryParam("price") int price) throws IOException {
		ProductUpdate prod = new ProductUpdate();
		prod.setProductID(pID);
		prod.setProductName(pName);
		prod.setProductPrice(price);
		
		if(prod.isExist(pID)) {
			return Response.status(201).build();
		}else {
			prod.productupdate(prod.getProductID(), prod.getProductName(), prod.getProductPrice());
			return Response.status(200).build();
		}
	}
	
	
	@POST
	@Path("/customer")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response customerJson(@QueryParam("name") String Name, @QueryParam("gender") String gender, @QueryParam("phone") String phone, @QueryParam("state") String state, @QueryParam("city") String city, @QueryParam("products") String products, @QueryParam("ptype") String ptype, @QueryParam("pdetail") String pdetail,@QueryParam("ctype") String ctype) throws IOException {
		
		//thread for update invoice persist
		try {
			Thread persistInvoice = new Thread(new Runnable() {

				@Override
				public void run() {
					Customer customer = new Customer();
					customer.setName(Name);
					customer.setGender(gender);
					customer.setPhone(phone);
					customer.setState(state);
					customer.setCity(city);
					customer.setProducts(products);
					customer.setPaymentType(ptype);
					customer.setPayPin(pdetail);
					customer.setCustType(ctype);
					customerInvoicePersist.add(customer);
				}
				
			});
			
			//Thread for generate invoice
			Thread generateInvoice = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						CustomerInvoice cust = new CustomerInvoice();
						cust.createInvoice(Name, gender,phone,state,city,products,ptype,pdetail,ctype);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			});
			persistInvoice.setPriority(Thread.MIN_PRIORITY);
			generateInvoice.setPriority(Thread.MAX_PRIORITY);
			
	        persistInvoice.start();
	        generateInvoice.start();
	        
		}catch(Exception e) {
			e.getStackTrace();
			return Response.status(201).build();
			
		}
		

		return Response.status(200).build();
		
	}

	
}
