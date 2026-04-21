package com.mycompany.vehicleservicecentermanagment;

import java.io.*;
import java.util.ArrayList;

public class Vehicle {

    private String vehicleID;
    private String ownerName;
    private String model;
    private String type;

    private static final String file = "vehicles.txt";

    public Vehicle() {
        this.vehicleID = null;
        this.ownerName = null;
        this.model = null;
        this.type = null;
    }

    public Vehicle(String vehicleID) {
        this();
        this.vehicleID = vehicleID;
    }

    public Vehicle(String vehicleID, String ownerName) {
        this(vehicleID);
        this.ownerName = ownerName;
    }

    public Vehicle(String vehicleID, String ownerName, String model) {
        this(vehicleID, ownerName);
        this.model = model;
    }

    public Vehicle(String vehicleID, String ownerName, String model, String type) {
        this(vehicleID, ownerName, model);
        this.type = type;
    }

  

    
    public static void saveVehicles(ArrayList<Vehicle> vehicles) {
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(file));
            for (Vehicle v : vehicles) {
                bw.write(v.getVehicleID() + "|" +
                         v.getOwnerName() + "|" +
                         v.getModel() + "|" +
                         v.getType());
                        bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving vehicles: " + e.getMessage());
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException e) {
                System.out.println("Error closing vehicles file.");
            }
        }
    }

  
   public static ArrayList<Vehicle> loadVehicles() {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String vehicleID = "";
                String ownerUsername = "";
                String model = "";
                String type = "";

                int barCount = 0;
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);

                    if (c == '|') {
                        barCount++;
                    } else {
                        if (barCount == 0) vehicleID += c;
                        else if (barCount == 1) ownerUsername += c;
                        else if (barCount == 2) model += c;
                        else if (barCount == 3) type += c;
                    }
                }

                vehicles.add(new Vehicle(vehicleID, ownerUsername, model, type));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Vehicles file not found, starting fresh.");
        } catch (IOException e) {
            System.out.println("Error loading vehicles: " + e.getMessage());
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                System.out.println("Error closing vehicles file.");
            }
        }

        return vehicles;
    }
   
   
     public String getVehicleID() { return vehicleID; }
    public String getOwnerName() { return ownerName; }
    public String getModel() { return model; }
    public String getType() { return type; }
}
