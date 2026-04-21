package com.mycompany.vehicleservicecentermanagment;

import java.io.*;
import java.util.ArrayList;

public class Service {

    private String serviceID;
    private String name;
    private double price;
    private String vehicleID;

    private static final String file = "services.txt";

    public Service() {
        this.serviceID = null;
        this.name = null;
        this.price = 0.0;
        this.vehicleID = null;
    }

    public Service(String serviceID) {
        this();
        this.serviceID = serviceID;
    }

    public Service(String serviceID, String name) {
        this(serviceID);
        this.name = name;
    }

    public Service(String serviceID, String name, double price) {
        this(serviceID, name);
        this.price = price;
    }

    public Service(String serviceID, String name, double price, String vehicleID) {
        this(serviceID, name, price);
        this.vehicleID = vehicleID;
    }

    
    
    
    
    public static void saveServices(ArrayList<Service> services) {
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(file));
            for (Service s : services) {
                bw.write(s.getServiceID() + "|" +
                         s.getName() + "|" +
                         s.getPrice() + "|" +
                         s.getVehicleID());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving services: " + e.getMessage());
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException e) {
                System.out.println("Error closing services file.");
            }
        }
    }

    
    public static ArrayList<Service> loadServices() {
        ArrayList<Service> services = new ArrayList<>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String serviceID = "";
                String name = "";
                String priceStr = "";
                String vehicleID = "";

                int barCount = 0;
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);

                    if (c == '|') {
                        barCount++;
                    } else {
                        if (barCount == 0) serviceID += c;
                        else if (barCount == 1) name += c;
                        else if (barCount == 2) priceStr += c;
                        else if (barCount == 3) vehicleID += c;
                    }
                }

                double price = Double.parseDouble(priceStr);
                services.add(new Service(serviceID, name, price, vehicleID));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Services file not found, starting fresh.");
        } catch (IOException e) {
            System.out.println("Error loading services: " + e.getMessage());
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                System.out.println("Error closing services file.");
            }
        }

        return services;
    }
    

    public String getServiceID() { return serviceID; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getVehicleID() { return vehicleID; }
}
