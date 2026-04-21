package com.mycompany.vehicleservicecentermanagment;

import java.io.*;
import java.util.ArrayList;

public class Invoice {
    private String invoiceID;
    private String ownerName;
    private String vehicleID;
    private String serviceID;
    private double amount;
private static final String file = "invoices.txt";
    public Invoice() {
        this.invoiceID = null;
        this.ownerName = null;
        this.vehicleID = null;
        this.serviceID = null;
        this.amount = 0.0;
    }
    
    public Invoice(String invoiceID) {
        this();
        this.invoiceID = invoiceID;
    }
    
    public Invoice(String invoiceID, String ownerName) {
        this(invoiceID);
        this.ownerName = ownerName;
    }
    
    public Invoice(String invoiceID, String ownerName, String vehicleID) {
        this(invoiceID, ownerName);
        this.vehicleID = vehicleID;
    }
    
    public Invoice(String invoiceID, String ownerName, String vehicleID, String serviceID) {
        this(invoiceID, ownerName, vehicleID);
        this.serviceID = serviceID;
    }

    public Invoice(String invoiceID, String ownerName, String vehicleID,String serviceID, double amount) {
        this(invoiceID, ownerName, vehicleID, serviceID);
        this.amount = amount;
    }

    
    public static ArrayList<Invoice> loadInvoices() {
        ArrayList<Invoice> list = new ArrayList<>();
        BufferedReader br = null;

        try {
    br = new BufferedReader(new FileReader(file));
    String line;

    while ((line = br.readLine()) != null) {
        String invoiceID = "";
        String ownerName = "";
        String vehicleID = "";
        String serviceID = "";
        String amountStr = "";

        int barCount = 0; 
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '|') {
                barCount++;
            } else {
                if (barCount == 0) invoiceID += c;
                else if (barCount == 1) ownerName += c;
                else if (barCount == 2) vehicleID += c;
                else if (barCount == 3) serviceID += c;
                else if (barCount == 4) amountStr += c;
            }
        }

        double amount = Double.parseDouble(amountStr);
        list.add(new Invoice(invoiceID, ownerName, vehicleID, serviceID, amount));
    }
     } catch (Exception e) {
    System.out.println("No invoices found, starting fresh.");
    } finally {
    try {
        if (br != null)
            br.close();
    } catch (IOException e) {
        System.out.println("Error closing invoices file.");
    }
}

        return list;
    }

    
    public static void saveInvoices(ArrayList<Invoice> list) {
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(file));
            for (Invoice inv : list) {
                bw.write(
                    inv.getInvoiceID() + "|" +
                    inv.getOwnerName() + "|" +
                    inv.getVehicleID() + "|" +
                    inv.getServiceID() + "|" +
                    inv.getAmount()
                );
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error saving invoices.");
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException e) {
                System.out.println("Error closing invoices file.");
            }
        }
    }

    public String getInvoiceID() { return invoiceID; }
    public String getOwnerName() { return ownerName; }
    public String getVehicleID() { return vehicleID; }
    public String getServiceID() { return serviceID; }
    public double getAmount() { return amount; }
}
