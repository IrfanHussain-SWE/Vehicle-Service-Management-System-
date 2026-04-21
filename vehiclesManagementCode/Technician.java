package com.mycompany.vehicleservicecentermanagment;

import java.io.*;
import java.util.ArrayList;

public class Technician {

    private String techName;    
    
    private ArrayList<String> assignedServiceIDs;

    private static final String file = "technicians.txt";

    public Technician() {
        this.techName = null;   
        
        this.assignedServiceIDs = new ArrayList<>();
    }
    
    public Technician(String techName) {
        this();
        this.techName = techName;
    }
       
  

    public void assignService(String serviceID) {
        assignedServiceIDs.add(serviceID);
    }

    
    public static ArrayList<Technician> loadTechnicians() {
        ArrayList<Technician> techs = new ArrayList<>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String name = "";
               
                String assignedStr = "";

                int barCount = 0;
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    if (c == '|') {
                        barCount++;
                    } else {
                        if (barCount == 0) name += c;
                       
                        else if (barCount == 1) assignedStr += c;
                    }
                }

                Technician t = new Technician(name);

                if (!assignedStr.isEmpty()) {
                    String[] arr = assignedStr.split("\\|");
                    for (String sID : arr)
                        t.assignService(sID);
                }

                techs.add(t);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Technicians file not found, starting fresh.");
        } catch (IOException e) {
            System.out.println("Error loading technicians: " + e.getMessage());
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                System.out.println("Error closing technicians file.");
            }
        }

        return techs;
    }
    public static void saveTechnicians(ArrayList<Technician> techs) {
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(file));
            for (Technician t : techs) {
                String servicesStr = String.join(",", t.assignedServiceIDs);
                bw.write(t.techName  + "|" + servicesStr);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving technicians: " + e.getMessage());
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException e) {
                System.out.println("Error closing technicians file.");
            }
        }
    }
public void removeService(String serviceID) {
    assignedServiceIDs.remove(serviceID);
}

   

    public String getTechName() { 
        return techName; 
    }
    
    

    public ArrayList<String> getAssignedServiceIDs() { 
        return assignedServiceIDs; 
    }
}
