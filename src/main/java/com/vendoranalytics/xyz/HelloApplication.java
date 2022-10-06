package com.vendoranalytics.xyz;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.vendoranalytics.xyz.business.VendorAnalytics;

public class HelloApplication{

    public static void main(String[] args) {

    	 // creates an object of Scanner
        Scanner input = new Scanner(System.in);

        System.out.print("Enter Input directory: ");

        // takes input from the keyboard
        String inDirectoryPath = input.nextLine();
        
        System.out.print("Enter Output directory: ");
        
        // takes input from the keyboard
        String outDirectoryPath = input.nextLine();

       
        System.out.print("Enter Managers Id's (; seperated): ");
     // takes input from the keyboard
        String userId = input.nextLine();


        ArrayList<String> userIds = new ArrayList<>(List.of(userId.split(";")));
        userId.split(";");
        VendorAnalytics vendorAnalytics = new VendorAnalytics(inDirectoryPath,outDirectoryPath
                ,userIds);


        vendorAnalytics.startAnalysis();
        
        // closes the scanner
        input.close();
    }
}