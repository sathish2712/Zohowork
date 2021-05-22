package com.zoho.backend_Zoho;


import java.util.*;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("store")
public class ProductTest{

	private Product product;
	private CustomerLogin login;
	private CustomerSignup signup;
	private Customer customer;

	public String encryptPasscode(String e) {
		StringBuffer result = new StringBuffer();
		for(int i = 0 ; i < e.length() ; i++){
			if(Character.isUpperCase(e.charAt(i))){
				char ch = (char) (((int) e.charAt(i) +  1 - 65) % 26 + 65);
				result.append(ch);
			}else{
				char ch = (char) (((int) e.charAt(i) + 1 - 97) % 26 + 97);
				result.append(ch);
			}

		}
		return result.toString();
	}

	public String decryptPasscode(String d){
		StringBuffer result = new StringBuffer();
		for(int i = 0 ; i < d.length() ; i++){
			char ch = d.charAt(i);
			if(ch >= 'a' && ch <= 'z'){
				ch = (char) (ch - 1);
				if(ch < 'a'){
					ch = (char) (ch + 'z' - 'a'  +1);
				}
				result.append(ch);
			}else if(ch >= 'A' && ch <= 'Z'){
				ch = (char)(ch - 1);
				if(ch < 'A'){
					ch = (char) (ch + 'Z' - 'A' + 1);
				}
				result.append(ch);
			}
		}
		return result.toString();
	}


	@POST
	@Path("/signup")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response signup(@QueryParam("name") String name, @QueryParam("password") String password){
		signup = new CustomerSignup();
		signup.setName(name);
		signup.setPassword(encryptPasscode(password));
		if(signup.signupCustomer()){
			return Response.status(201).build();
		}
		return Response.status(208).build();
	}


	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(@QueryParam("uname") String userName , @QueryParam("password") String password) {
		login = new CustomerLogin();
		login.setName(userName);
		login.setPassword(encryptPasscode(password));
		if(login.customerLogin()){
			return Response.status(201).build();
		}
		return Response.status(208).build();
	}


	@POST
	@Path("/admin")
	@Produces(MediaType.APPLICATION_JSON)
	public Response adminAccess(@QueryParam("pid") int id, @QueryParam("pname") String name,@QueryParam("pquant") String quantity, @QueryParam("price") int price, @QueryParam("type") String customerType) {
		try{
			product = new Product();
			product.setProductId(id);
			product.setProductName(name);
			product.setProductQuantity(quantity);
			product.setProductCost(price);
			product.setIsPremium(customerType);
			if(product.isIdExist()){
				return Response.status(208).build();
			}
			if(!product.updateProduct()){
				return Response.status(500).build();
			}
		} catch(Exception ex){
			System.out.println(ex);
			return Response.status(500).build();
		}
		return Response.status(201).build();
	}


	@POST
	@Path("/customer")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response customerAccess(@QueryParam("name") String Name, @QueryParam("gender") String gender, @QueryParam("phone") String phone, @QueryParam("state") String state, @QueryParam("city") String city, @QueryParam("products") String products, @QueryParam("ptype") String ptype, @QueryParam("pdetail") String pdetail,@QueryParam("ctype") String ctype){
		customer = new Customer();
		customer.setName(Name);
		customer.setGender(gender);
		customer.setPhone(phone);
		customer.setState(state);
		customer.setCity(city);
		customer.setProducts(products);
		customer.setPaymentType(ptype);
		customer.setPayPin(pdetail);
		customer.setCustType(ctype);
		if(customer.createInvoice()){
			Response.status(401).build();
		}
		return Response.status(201).build();
	}

	@GET
	@Path("/inventory")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Map<String,Object> getInventory(){
		Map<String,Object> resultMap = new HashMap<>();
		try{
			product = new Product();
			product.constructReport(resultMap);
		}catch (Exception e){
			System.out.println(e);
		}
		return resultMap;
	}

	@POST
	@Path("/report")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Map<String,Object> getReport(@QueryParam("sortBy") String sortby , @QueryParam("top") String topN){
		Map<String,Object> map = new HashMap<>();
		try{
			Report report = new Report();
			report.setSortBy(sortby);
			report.setTopN(topN);
			report.generateReport(map);
			Collections.sort(Report.customerReport);
		}catch(Exception e){
			System.out.println(e);
		}
		return map;
	}
}
