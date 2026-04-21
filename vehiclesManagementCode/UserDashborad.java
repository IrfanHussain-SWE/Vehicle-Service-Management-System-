package com.mycompany.vehicleservicecentermanagment;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.io.*;

public class UserDashborad {
   
    Booking booking;
     VehicleParts vParts ;
     ArrayList<Service> services;
       Car C1;
        Bike B1;
     public void showUser(Stage stage) 
    {
        Label userLbl1=new Label("Welcome to User DashBoard!");       
        userLbl1.setStyle("-fx-font-weight: bold; -fx-text-fill: blue;");
        Label userLbl2=new Label("        MENU FOR USER");
        userLbl2.setStyle("-fx-font-weight: bold; -fx-text-fill: green;");
        Label userLbl3=new Label("SELECT ANY OPTION  !");
        Button userBtn1=new Button(" > Book Services");
        userBtn1.setStyle( "-fx-background-color: darkSeaGreen");
        userBtn1.setOnAction(e -> showBookService(stage));
        Button userBtn2=new Button(" > Available Services");
        userBtn2.setStyle( "-fx-background-color: teal");
        userBtn2.setOnAction(e -> showAvailableServices(stage));
        Button userBtn3=new Button(" > Spare Parts");
        userBtn3.setStyle( "-fx-background-color: lightSeaGreen");
        userBtn3.setOnAction(e -> showSparePartsMenu(stage));
          Button logoutBtn3=new Button(" Logout");
           logoutBtn3.setStyle( "-fx-background-color: tomato");
           AdminDashborad admin = new AdminDashborad();
       logoutBtn3.setOnAction(e -> admin.showLogin(stage));
         Label infolb2 = new Label(" We will inform you on your entered phone number\n"+" when Service will be completed\n"+"For Details 03209925798");
     infolb2.setStyle("-fx-text-fill: red;");  
        VBox userVb1=new VBox(30);
        userVb1.setPadding(new Insets(100));
        userVb1.getChildren().addAll(userLbl1,userLbl2,userLbl3,userBtn1,userBtn2,userBtn3,logoutBtn3,infolb2);
         ScrollPane sp = new ScrollPane(userVb1);
        Scene userScn=new Scene(sp,500,500);
        stage.setScene(userScn);
        stage.show();
    }
    
    private void showBookService(Stage stage) {
         services = Service.loadServices();
        
        
    Label lbl = new Label("BOOK  A  SERVICES"); 
    TextField userField = new TextField();
    userField.setPromptText("Enter Your Name");  
    TextField vehicleField = new TextField();
    vehicleField.setPromptText("Enter Vehicle Name");  
    ComboBox<String> serviceBox = new ComboBox<>();
    for (Service s : services){
        serviceBox.getItems().add(s.getServiceID() + " - " + s.getName());
    }
    serviceBox.setPromptText("Select Service");
    DatePicker datePicker = new DatePicker();
    datePicker.setPromptText("Select Date");  
    TextField timeField = new TextField();
    timeField.setPromptText("Enter Time (HH:MM)");
    TextField spareField = new TextField();
    spareField.setPromptText("Enter Spare Part (if any)");
    TextField numberField = new TextField();
    numberField.setPromptText("Enter  phone number");  
    Button bookBtn = new Button("Book Now");
     bookBtn.setStyle( "-fx-background-color: paleGreen");
     Label lb2 = new Label(" We will inform your on your entered phone number\n"+" when Service will be completed\n"+"For Details 03209925798");
     lb2.setStyle("-fx-text-fill: red;");
    bookBtn.setOnAction(e -> {
        String userName = userField.getText();
        String vehicleName = vehicleField.getText();
        String selectedService = serviceBox.getValue();
        String date = datePicker.getValue() != null ? datePicker.getValue().toString() : "";
        String time = timeField.getText();
        String sparePart = spareField.getText().isEmpty() ? "None" : spareField.getText();
         String nuberField = numberField.getText();
        if (userName.isEmpty() || vehicleName.isEmpty() || selectedService == null || date.isEmpty() || time.isEmpty()|| numberField.getText().isEmpty()) {
            alert("Please fill all required fields!");
            return;
        }

        
        String serviceName = selectedService.split(" - ")[1];

        booking = new Booking(userName, vehicleName, serviceName, date, time, sparePart,nuberField);
        booking.saveBooking();

        alert("Service booked successfully!");
       
        userField.clear();
        vehicleField.clear();
        serviceBox.setValue(null);
        datePicker.setValue(null);
        timeField.clear();
        spareField.clear();
        numberField.clear();
    });

    
    Button backBtn = new Button("Back");
     backBtn.setStyle( "-fx-background-color: darkGray");
    backBtn.setOnAction(e -> showUser(stage));

    VBox vbox = new VBox(15, lbl, userField, vehicleField, serviceBox, datePicker, timeField, spareField, numberField,bookBtn, backBtn,lb2);
    vbox.setPadding(new Insets(30));
 ScrollPane sp = new ScrollPane(vbox);
    stage.setScene(new Scene(sp, 400, 450));
    stage.show();
}


    private void showAvailableServices(Stage stage) {
    services = Service.loadServices();

    VBox box = new VBox(10);
    box.setPadding(new Insets(20));
    box.getChildren().add(new Label("AVAILABLE   SERVICES"));

    for (Service s : services) {
        Label lbl = new Label(s.getServiceID() + " - " + s.getName() + " - Price: " + s.getPrice());
        box.getChildren().add(lbl);
    }

    Button backBtn = new Button("Back");
     backBtn.setStyle( "-fx-background-color: darkGray");
    backBtn.setOnAction(e -> showUser(stage));
    box.getChildren().add(backBtn);
ScrollPane sp = new ScrollPane(box);
    stage.setScene(new Scene(sp, 400, 300));
    stage.show();
}



    private void showSparePartsMenu(Stage stage) {
        vParts = new VehicleParts();
        
    Label infolbp = new Label(vParts.priceInfo());
    infolbp.setStyle("-fx-text-fill: red;");
    Label infolb = new Label(vParts.sparePaartsInfo());
    infolb.setStyle("-fx-text-fill: red;");
    Label lbl = new Label("SELECT  VEHICLE TYPE TO VIEW SPARE PARTS");  
    Button carBtn = new Button("CAR");
     carBtn.setStyle( "-fx-background-color: lavender");
    carBtn.setOnAction(e -> displayCarSpareParts(stage));   
    Button bikeBtn = new Button("BIKE");
     bikeBtn.setStyle( "-fx-background-color: plum");
    bikeBtn.setOnAction(e -> displayBikeSpareParts(stage));  
    Button backBtn = new Button("Back");
     backBtn.setStyle( "-fx-background-color: darkGray");
    backBtn.setOnAction(e -> showUser(stage)); 
    VBox box = new VBox(20);
    box.setPadding(new Insets(50));
    box.getChildren().addAll(lbl, carBtn, bikeBtn, backBtn,infolbp ,infolb);    
    Scene scene = new Scene(box, 400, 300);
    stage.setScene(scene);
    stage.show();
}

    private void displayCarSpareParts(Stage stage) {
      C1 = new Car();
   
    C1.displayCarBody();
    C1.displayCarEngine();
    C1.displayCarElectrical();

    VBox box = new VBox(10);
    box.setPadding(new Insets(20));
    box.getChildren().add(new Label("CAR  SPARE PARTS "));

    
    box.getChildren().add(new Label(" BODY "));
    for (Body b : C1.bd) box.getChildren().add(new Label(b.showDetails()));

    
    box.getChildren().add(new Label(" ENGINE "));
    for (Engine e : C1.eng) box.getChildren().add(new Label(e.showDetails()));

    
    box.getChildren().add(new Label(" ELECTRICAL   "));
    for (Electrical elec : C1.elec) box.getChildren().add(new Label(elec.showDetails()));

    Button backBtn = new Button("Back");
     backBtn.setStyle( "-fx-background-color: darkGray");
    backBtn.setOnAction(e -> showSparePartsMenu(stage));
    box.getChildren().add(backBtn);
    ScrollPane spp = new ScrollPane(box);
    stage.setScene(new Scene(spp, 400, 600));
    stage.show();
}

    private void displayBikeSpareParts(Stage stage) {
     B1 = new Bike();
    B1.displayBikeBody();
    B1.displayBikeEngine();
    B1.displayBikeElectrical();

    VBox box = new VBox(10);
    box.setPadding(new Insets(20)); 
    box.getChildren().add(new Label(" BIKE  SPARE PARTS  "));   
    box.getChildren().add(new Label("BODY"));  // fixed typo
    for (Body b : B1.bd) box.getChildren().add(new Label(b.showDetails()));

   box.getChildren().add(new Label("ENGINE"));  // already correct
   for (Engine e : B1.eng) box.getChildren().add(new Label(e.showDetails()));

box.getChildren().add(new Label("ELECTRICAL"));  // fixed typo
for (Electrical elec : B1.elec) box.getChildren().add(new Label(elec.showDetails()));


    Button backBtn = new Button("Back");
     backBtn.setStyle( "-fx-background-color: darkGray");
    backBtn.setOnAction(e -> showSparePartsMenu(stage));
    box.getChildren().add(backBtn);
    ScrollPane sp = new ScrollPane(box);
    stage.setScene(new Scene(sp, 400, 600));
    stage.show();
}

      private void alert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    } 

}