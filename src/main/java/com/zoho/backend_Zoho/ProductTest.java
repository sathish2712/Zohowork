package com.zoho.backend_Zoho;


import java.util.Arrays;
import java.util.List;
import java.io.*;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("store")
public class ProductTest {

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
	public Response customerJson(@QueryParam("name") String name) {
		System.out.println(name);
		return Response.status(200).build();
		
	}
	
	
}
