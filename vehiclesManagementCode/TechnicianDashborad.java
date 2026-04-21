
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

public class TechnicianDashborad {
     
  ArrayList<Service> service; 
   ArrayList<Technician> techs;
  
    public void showTech(Stage stage,String currentTechName) 
    {
           
        
        Label techLbl1=new Label("Welcome to Technician DashBoard!");
        techLbl1.setStyle("-fx-font-weight: bold; -fx-text-fill: blue;");
        Label techLbl2=new Label("MENU FOR TECHNICIAN");
        techLbl2.setStyle("-fx-font-weight: bold; -fx-text-fill: green;");
        Label techLbl3=new Label("SELECT ANY OPTION!");
        Button techBtn1=new Button(" > View Tasks");
         techBtn1.setStyle( "-fx-background-color: lightYellow");
        techBtn1.setOnAction(e -> showAssignedTasks(stage, currentTechName));
        Button techBtn2=new Button(" > Update Progress");
        techBtn2.setStyle( "-fx-background-color: slateGray");
        techBtn2.setOnAction(e -> showUpdateTask(stage, currentTechName));        
        Button techBtn3=new Button(" > Notes/Comments");
        techBtn3.setStyle( "-fx-background-color: oliveDrab");
        techBtn3.setOnAction(e -> showAddComment(stage, currentTechName));
        Button logoutBtn4=new Button(" Logout");
        logoutBtn4.setStyle( "-fx-background-color: tomato");
        AdminDashborad admin = new AdminDashborad();
         logoutBtn4.setOnAction(e -> admin.showLogin(stage));
        VBox techVb1=new VBox(30);
        techVb1.setPadding(new Insets(100));
        techVb1.getChildren().addAll(techLbl1,techLbl2,techLbl3,techBtn1,techBtn2,techBtn3,logoutBtn4);
        ScrollPane sp = new ScrollPane(techVb1);
        Scene techScn=new Scene(sp,500,500);
        stage.setScene(techScn);
        stage.show();
    }
    
private void showAssignedTasks(Stage stage, String techName) {
    
    techs = Technician.loadTechnicians();
   service= Service.loadServices();

    
    Technician current = null;
    for (Technician t : techs) {
        if (t.getTechName().equals(techName)) {
            current = t;
            break;
        }
    }

    if (current == null) {
        alert("Technician record not found!");
        return;
    }

    
    VBox box = new VBox(10);
    box.setPadding(new Insets(20));
    Label header = new Label("Assigned Tasks for: " + current.getTechName());
    header.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
    box.getChildren().add(header);

    if (current.getAssignedServiceIDs().isEmpty()) {
        box.getChildren().add(new Label("No tasks assigned yet."));
    } else {
        for (String serviceID : current.getAssignedServiceIDs()) {
            Service task = null;
            for (Service s : service) {
                if (s.getServiceID().equals(serviceID)) {
                    task = s;
                    break;
                }
            }
            if (task != null) {
                Label lbl = new Label(
                    "Service ID: " + task.getServiceID() +
                    " | Name: " + task.getName() +
                    " | Price: " + task.getPrice() +
                    " | Vehicle ID: " + task.getVehicleID()
                );
                box.getChildren().add(lbl);
            } else {
                box.getChildren().add(new Label("Service ID: " + serviceID + " (Details not found)"));
            }
        }
    }

    Button back = new Button("Back");
     back.setStyle( "-fx-background-color: darkGray");
    back.setOnAction(e -> showTech(stage, techName)); 
    box.getChildren().add(back);
    Scene scene = new Scene(box, 500, 400);
    stage.setScene(scene);
    stage.show();
}


private void showUpdateTask(Stage stage, String techName) {
     techs = Technician.loadTechnicians();
    

    Technician current = null;
    for (Technician t : techs) {
        if (t.getTechName().equals(techName)) {
            current = t;
            break;
        }
    }

    if (current == null) {
        alert("Technician not found!");
        return;
    }

    VBox box = new VBox(15);
    box.setPadding(new Insets(20));
    Label header = new Label("Update Task Progress for: " + techName);
    header.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
    box.getChildren().add(header);

    if (current.getAssignedServiceIDs().isEmpty()) {
        box.getChildren().add(new Label("No tasks assigned."));
    } else {
        ComboBox<String> serviceBox = new ComboBox<>();
        for (String sID : current.getAssignedServiceIDs()){
            serviceBox.getItems().add(sID);
        }
        serviceBox.setPromptText("Select Service");
        ComboBox<String> statusBox = new ComboBox<>();
        statusBox.getItems().addAll("PENDING", "IN PROGRESS", "COMPLETED");
        statusBox.setPromptText("Select Status");
        Button saveBtn = new Button("Update Status");
        saveBtn.setOnAction(e -> {
            String selectedService = serviceBox.getValue();
            String selectedStatus = statusBox.getValue();

            if (selectedService == null || selectedStatus == null) {
                alert("Select service and status!");
                return;
            }

            saveStatusToFile(selectedService, techName, selectedStatus);
            alert("Status updated!");
            showTech(stage, techName); 
        });

        box.getChildren().addAll(serviceBox, statusBox, saveBtn);
    }

    Button back = new Button("Back");
     back.setStyle( "-fx-background-color: darkGray");
    back.setOnAction(e -> showTech(stage, techName));
    box.getChildren().add(back);
   ScrollPane sp = new ScrollPane(box);
    stage.setScene(new Scene(sp, 450, 350));
    stage.show();
}


private void saveStatusToFile(String serviceID, String techName, String status) {
    BufferedWriter bw = null;
    try {
        bw = new BufferedWriter(new FileWriter("service_status.txt",true));
        bw.write(serviceID + "|" + techName + "|" + status);
        bw.newLine();
    } catch (IOException e) {
        System.out.println("Error saving status: " + e.getMessage());
    } finally {
        try {
            if (bw != null) bw.close(); 
        } catch (IOException e) {
            System.out.println("Error closing status file: " + e.getMessage());
        }
    }
    }

private void showAddComment(Stage stage, String techName) {
   techs = Technician.loadTechnicians();

    Technician current = null;
    for (Technician t : techs) {
        if (t.getTechName().equals(techName)) {
            current = t;
            break;
        }
    }

    if (current == null) {
        alert("Technician not found!");
        return;
    }

    VBox box = new VBox(15);
    box.setPadding(new Insets(20));
    Label header = new Label("Add Comment / Note for Tasks: " + techName);
    header.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
    box.getChildren().add(header);

    if (current.getAssignedServiceIDs().isEmpty()) {
        box.getChildren().add(new Label("No tasks assigned."));
    } else {
        ComboBox<String> serviceBox = new ComboBox<>();
        for (String sID : current.getAssignedServiceIDs()) serviceBox.getItems().add(sID);
        serviceBox.setPromptText("Select Service");

        TextArea commentArea = new TextArea();
        commentArea.setPromptText("Write comment…");

        Button saveBtn = new Button("Save Comment");
       saveBtn.setStyle( "-fx-background-color: paleGreen");
        saveBtn.setOnAction(e -> {
            String selectedService = serviceBox.getValue();
            String comment = commentArea.getText();

            if (selectedService == null || comment.isEmpty()) {
                alert("Select service and write comment!");
                return;
            }

            saveCommentToFile(selectedService, techName, comment);
            alert("Comment saved!");
            showTech(stage, techName);
        });

        box.getChildren().addAll(serviceBox, commentArea, saveBtn);
    }

    Button back = new Button("Back");
     back.setStyle( "-fx-background-color: darkGray");
    back.setOnAction(e -> showTech(stage, techName));
    box.getChildren().add(back);
    ScrollPane sp = new ScrollPane(box);
    stage.setScene(new Scene(sp, 500, 400));
    stage.show();
}


private void saveCommentToFile(String serviceID, String techName, String comment) {
    BufferedWriter bw = null;
    try {
        bw = new BufferedWriter(new FileWriter("service_comments.txt",true));
        bw.write(serviceID + "|" + techName + "|" + comment);
        bw.newLine();
    } catch (IOException e) {
        System.out.println("Error saving comment: " + e.getMessage());
    } finally {
        try {
            if (bw != null) bw.close(); 
        } catch (IOException e) {
            System.out.println("Error closing comment file: " + e.getMessage());
        }
    }
}


      private void alert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    } 

}
