package com.mycompany.vehicleservicecentermanagment;

import javafx.application.Application;
import javafx.stage.Stage;
import java.util.Scanner;

public class App extends Application {
AdminDashborad admin;
    @Override  
    public void start(Stage stage) {
        admin = new AdminDashborad();
        admin.showLogin(stage);
    }

    public static void main(String[] args) {
        launch();

        Scanner input = new Scanner(System.in);

        VehicleParts c1 = new Car();
        Car c2 = (Car) c1;

        VehicleParts b1 = new Bike();
        Bike b2 = (Bike) b1;

        while (true) {
            System.out.println("Enter vehicle type (car/bike) or 'no' to exit:");
            String vehicleType = input.next().toLowerCase();
            if (vehicleType.equals("no")) break;

            VehicleParts vehicle;
            if (vehicleType.equals("car")) vehicle = c2;
            else if (vehicleType.equals("bike")) vehicle = b2;
            else {
                System.out.println("Invalid vehicle type!");
                continue;
            }

            while (true) {
                System.out.println("Which part do you want to add? (body/engine/electrical) or 'done' to finish this vehicle:");
                String partType = input.next().toLowerCase();
                if (partType.equals("done")) break;

                System.out.println("Enter part name:");
                String name = input.next();
                System.out.println("Enter price:");
                double price = input.nextDouble();

                if (vehicleType.equals("car")) {
                    if (partType.equals("body")) c2.addBodyParts(new Body(name, price));
                    else if (partType.equals("engine")) c2.addEngineParts(new Engine(name, price));
                    else if (partType.equals("electrical")) c2.addElectricalParts(new Electrical(name, price));
                    else System.out.println("Invalid part type!");
                } else if (vehicleType.equals("bike")) {
                    if (partType.equals("body")) b2.addBodyParts(new Body(name, price));
                    else if (partType.equals("engine")) b2.addEngineParts(new Engine(name, price));
                    else if (partType.equals("electrical")) b2.addElectricalParts(new Electrical(name, price));
                    else System.out.println("Invalid part type!");
                }
            }

            System.out.println("Do you want to add another vehicle? (yes/no)");
            String more = input.next().toLowerCase();
            if (more.equals("no")) break;
        }
    }
}
