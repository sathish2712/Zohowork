package com.zoho.backend_Zoho;

import static Utils.DataConstant.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Report implements Comparable<Report>{
    static ArrayList<Report> customerReport = new ArrayList<>();

    List<Map<String,Object>> dataList = new ArrayList<>();

    private String sortBy;
    private String topN;
    private String customerName;
    private String customerGender;
    private String customerPhone;
    private String customerCity;
    private String customerType;
    private Integer totalAmount;
    private List<String> customerProducts;

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTopN() {
        return topN;
    }

    public void setTopN(String topN) {
        this.topN = topN;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public List<String> getCustomerProducts() {
        return customerProducts;
    }

    public void setCustomerProducts(List<String> customerProducts) {
        this.customerProducts = customerProducts;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerGender() {
        return customerGender;
    }

    public void setCustomerGender(String customerGender) {
        this.customerGender = customerGender;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public void addDetails(){
        try{
            customerReport.add(this);
        }catch (Exception ex){
            System.out.println(ex);
        }
    }

    public void generateReport(Map<String,Object> map) {
        Map<String, Object> reportMap = new HashMap<>();
        try {
            for (int i = 0; i < Integer.parseInt(getTopN()); i++) {
                System.out.println("test");
                List<Object> productList = new ArrayList<>();
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put(CUSTOMER_NAME, customerReport.get(i).customerName);
                dataMap.put(CUSTOMER_GENDER, customerReport.get(i).customerGender);
                dataMap.put(CUSTOMER_PHONE, customerReport.get(i).customerPhone);
                dataMap.put(CUSTOMER_CITY, customerReport.get(i).customerCity);
                dataMap.put(CUSTOMER_TYPE, customerReport.get(i).customerType);
                dataMap.put(CUSTOMER_TOTAL_PURCHASE,customerReport.get(i).totalAmount);
                for (int j = 0; j < customerReport.get(i).customerProducts.size(); j++) {
                    productList.add(customerReport.get(i).customerProducts.get(j));
                }
                dataMap.put(CUSTOMER_PRODUCTS, productList);
                dataList.add(dataMap);
            }
            reportMap.put(CUSTOMER_REPORT, dataList);
            map.put(JSON_DATA, reportMap);
            map.put(JSON_STRING, JSON_SUCCESS);
        } catch (Exception e) {
            map.put(JSON_STRING, JSON_ERROR);
            System.out.println(e);
        }
    }

    @Override
    public int compareTo(Report report) {
        if(this.getTotalAmount() > report.getTotalAmount()){
            return 1;
        }else {
            return -1;
        }
    }
}
