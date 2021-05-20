package com.zoho.backend_Zoho;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;

public class CustomerSignup {
    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean signupCustomer(){
        try{
            String path = "D:\\data\\CustomerData\\cred.text";
            if(!isCustomerExist(getName(),path)){
                FileOutputStream file = new FileOutputStream(path,true);
                String str = getName() + " " + getPassword();
                str = str + "\n";
                byte[] arr = str.getBytes();
                file.write(arr);
                file.close();
                System.out.println("New customer added !");
            }else{
                return false;
            }
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }
    public boolean isCustomerExist(String name, String path){
        boolean isPresent = false;
        Scanner scan = null;
        try{
            File file = new File(path);
            if(!file.exists()){
                return false;
            }
            scan = new Scanner(file);
            while (scan.hasNextLine()){
                String[] arr = scan.nextLine().split(" ");
                if(arr[0].trim().equals(getName())){
                    isPresent = true;
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }finally {
            try{
                scan.close();
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return isPresent;
    }
}
