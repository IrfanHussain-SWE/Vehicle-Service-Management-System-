package com.mycompany.vehicleservicecentermanagment;

import java.io.*;
import java.util.ArrayList;

public class Booking {

    private String userName;
    private String vehicleName;
    private String serviceName;
    private String date;
    private String time;
    private String sparePart;
    private String number;

    private static final String file = "bookings.txt";

    public Booking(String userName, String vehicleName, String serviceName, String date,
                   String time, String sparePart, String numberField) {
        this.userName = userName;
        this.vehicleName = vehicleName;
        this.serviceName = serviceName;
        this.date = date;
        this.time = time;
        this.sparePart = sparePart;
        this.number = numberField;
    }

    public void saveBooking() {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file,true));
            bw.write(userName + "|" + vehicleName + "|" + serviceName + "|" +
                     date + "|" + time + "|" + sparePart + "|" + number);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving booking: " + e.getMessage());
        } finally {
            try {
                if (bw != null) bw.close();
            } catch (IOException e) {
                System.out.println("Error closing file: " + e.getMessage());
            }
        }
    }

    public static ArrayList<Booking> loadBookings() {
        ArrayList<Booking> bookingsList = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 7) {
                    bookingsList.add(new Booking(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Bookings file not found, starting fresh.");
        } catch (IOException e) {
            System.out.println("Error reading bookings: " + e.getMessage());
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                System.out.println("Error closing bookings file: " + e.getMessage());
            }
        }
        return bookingsList;
    }

    public String getUserName() { return userName; }
    public String getVehicleName() { return vehicleName; }
    public String getServiceName() { return serviceName; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getSparePart() { return sparePart; }
    public String getNumber() { return number; }
}
