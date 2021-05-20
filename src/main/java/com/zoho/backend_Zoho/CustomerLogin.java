package com.zoho.backend_Zoho;

import java.io.File;
import java.util.Scanner;

public class CustomerLogin {
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

    public boolean customerLogin(){
        Boolean isPresent = false;
        try{
            String path = "D:\\data\\CustomerData\\cred.text";
            String userName = getName();
            String password = getPassword();
            File file = new File(path);
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine()){
                String[] arr = scan.nextLine().split(" ");
                if(arr[0].trim().equals(userName) && arr[1].trim().equals(password)){
                    isPresent = true;
                    break;
                }
            }
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
        return isPresent;
    }
}
