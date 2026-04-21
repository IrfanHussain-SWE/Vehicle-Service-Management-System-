
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
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminDashborad  {
    
    ArrayList<Technician> techs;
    ArrayList<Vehicle> vehicles;
     ArrayList<Service> services;     
     ArrayList<Invoice> invoiceList;
      ArrayList<User> userList;
    private HashMap<String, User> users ;
    TechnicianDashborad technician ;
    UserDashborad user ;
    
   
       private final String file = "users.txt";
       public AdminDashborad(){
            users = new HashMap<>();
           technician = new TechnicianDashborad();
           user = new UserDashborad();
       }
   public void loadUsers() {
    BufferedReader br = null;
    try {
        br = new BufferedReader(new FileReader(file));

        String line;
        while ((line = br.readLine()) != null) {

            String username = "";
            String password = "";
            String role = "";

            int barCount = 0;  

            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);

                if (c == '|') {
                    barCount++;
                } else {
                    if (barCount == 0) {
                        username = username + c;
                    } else if (barCount == 1) {
                        password = password + c;
                    } else if (barCount == 2) {
                        role = role + c;
                    }
                }
            }
            users.put(username, new User(username, password, role));
        }

    } catch (Exception e) {
        System.out.println("File not found, starting fresh.");
    } finally {
        try {
            if (br != null) br.close(); 
        } catch (IOException e) {
            System.out.println("Error closing file.");
        }
    }
    
}

 
    public void saveUsers() {
    BufferedWriter bw = null;
    try {
        bw = new BufferedWriter(new FileWriter(file));
        for (User u : users.values()) {
            bw.write(u.getUsername() + "|" + u.getPassword() + "|" + u.getRole());
            bw.newLine();
        }
    } catch (IOException e) {
        System.out.println("Error saving users.");
    } finally {
        try {
            if (bw != null) bw.close(); 
        } catch (IOException e) {
            System.out.println("Error closing file.");
        }
    }
}

    
    public  void showLogin(Stage stage) {
      
             loadUsers();
        TextField name = new TextField();
        name.setPromptText("Username");
        PasswordField pass = new PasswordField();
        pass.setPromptText("Password");
        ToggleGroup tg = new ToggleGroup();
        RadioButton r1 = new RadioButton("ADMIN");
        RadioButton r2 = new RadioButton("TECHNICIAN");
        RadioButton r3 = new RadioButton("USER");
        r1.setToggleGroup(tg);
        r2.setToggleGroup(tg);
        r3.setToggleGroup(tg);
        HBox rbBox = new HBox(15, r1, r2, r3);
        rbBox.setAlignment(Pos.CENTER);
        Button login = new Button("Login");
         login.setStyle( "-fx-background-color: forestGreen");
         login.setOnAction(e -> {

            String n = name.getText();
            String p = pass.getText();
            RadioButton rb = (RadioButton) tg.getSelectedToggle();

            if (n.isEmpty() || p.isEmpty() || rb == null) {
                alert("Please fill all fields");
                return;
            }

            String role = rb.getText();

            if (users.containsKey(n)) {

                User u = users.get(n);

                if (u.getPassword().equals(p) && u.getRole().equals(role)) {

                    if (role.equals("ADMIN")){
                        showAdmin(stage);
                    }
                    else if (role.equals("TECHNICIAN")){ 
                                              String currentName=u.getUsername();
                                                technician.showTech(stage, currentName);
                                      }
                    else {
                        user.showUser(stage);
                    }

                } else {
                    alert("Wrong password or role!");
                }

            } else {
                alert("User does not exist!");
            }
        });

        Button reg = new Button("Register");
         reg.setStyle( "-fx-background-color: cadetBlue");
        reg.setOnAction(e -> showRegister(stage));

        VBox root = new VBox(15, name, pass, rbBox, login, reg);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

       
        stage.setScene(new Scene(root, 400, 350));
        stage.show();
    }
    
    
    

    private void showRegister(Stage stage) {
 
        TextField name = new TextField();
        name.setPromptText("New Username");
        PasswordField pass = new PasswordField();
        pass.setPromptText("New Password");
        ToggleGroup tg = new ToggleGroup();
        RadioButton r1 = new RadioButton("ADMIN");
        RadioButton r2 = new RadioButton("TECHNICIAN");
        RadioButton r3 = new RadioButton("USER");
        r1.setToggleGroup(tg);
        r2.setToggleGroup(tg);
        r3.setToggleGroup(tg);

        HBox rbBox = new HBox(15, r1, r2, r3);
        rbBox.setAlignment(Pos.CENTER);

        Button reg = new Button("Register");
         reg.setStyle( "-fx-background-color: cadetBlue");
        reg.setOnAction(e -> {

            String n = name.getText();
            String p = pass.getText();
            RadioButton rb = (RadioButton) tg.getSelectedToggle();
            String role = rb.getText();
            if (n.isEmpty() || p.isEmpty() || rb == null) {
                alert("All fields required!");
                return;
            }

            if (users.containsKey(n)) {
                System.out.println("Username already exists!");
                return;
            }

          
            
            users.put(n, new User(n, p, role));
            saveUsers();
             if (role.equals("TECHNICIAN")) {
            ArrayList<Technician> techs = Technician.loadTechnicians();

            boolean exists = false;
           for (Technician t : techs) {
            if (t.getTechName().equals(n)) {
            exists = true;
            break;
           }
        }

         if (!exists) {
        techs.add(new Technician(n));
        Technician.saveTechnicians(techs);
         }
          }


       alert("Registered Successfully!");
     showLogin(stage);
 
        });

        Button back = new Button("Back");
         back.setStyle( "-fx-background-color: darkGray");
        back.setOnAction(e -> showLogin(stage));

        VBox root = new VBox(15, name, pass, rbBox, reg, back);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        stage.setScene(new Scene(root, 400, 350));
        stage.show();
    }
    
    
    
     public void showAdmin(Stage stage)  
    {
        Label adminLbl1=new Label("Welcome to Admion DashBoard!");
        adminLbl1.setStyle("-fx-font-weight: bold; -fx-text-fill: blue;");
        Label adminLbl2=new Label("MENU FOR ADMIN");
        adminLbl2.setStyle("-fx-font-weight: bold; -fx-text-fill: green;");
        Label adminLbl3=new Label("SELECT ANY OPTION!");
        Button adminBtn1=new Button(" > Customers ");
        adminBtn1.setStyle( "-fx-background-color: lightYellow");
        adminBtn1.setOnAction(e -> showCustomers(stage));    
        Button adminBtn2=new Button(" > Vehicles  ");
         adminBtn2.setStyle( "-fx-background-color: greenYellow");
        adminBtn2.setOnAction(e -> showVehicles(stage));
        Button adminBtn3=new Button(" > Services  ");
         adminBtn3.setStyle( "-fx-background-color: aqua");
        adminBtn3.setOnAction(e -> showServices(stage));
        Button adminBtn4=new Button(" > Technicians");
         adminBtn4.setStyle( "-fx-background-color: steelBlue");
        adminBtn4.setOnAction(e -> showTechnicians(stage));
        Button adminBtn5=new Button(" >  Settings ");
         adminBtn5.setStyle( "-fx-background-color: wheat");
        adminBtn5.setOnAction(e -> showUsers(stage));
        Button adminBtn6=new Button(" > Invoices ");
         adminBtn6.setStyle( "-fx-background-color: coral");
        adminBtn6.setOnAction(e -> showInvoices(stage));
       Button logoutBtn7=new Button(" Logout");
        logoutBtn7.setStyle( "-fx-background-color: tomato");
       logoutBtn7.setOnAction(e -> showLogin(stage));       
        VBox adminVb1=new VBox(30);
        adminVb1.setPadding(new Insets(100));
        adminVb1.getChildren().addAll(adminLbl1,adminLbl2,adminLbl3,adminBtn1,adminBtn2,adminBtn3,adminBtn4,adminBtn5,adminBtn6, logoutBtn7);
         ScrollPane sp = new ScrollPane(adminVb1);
        Scene adminScn=new Scene(sp,500,500);
        stage.setScene(adminScn);
        stage.show();
    }
          
    
      private void showCustomers(Stage stage) {

    TableView<Booking> table = new TableView<>(); 
    TableColumn<Booking, String> userCol = new TableColumn<>("User");
    userCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
    TableColumn<Booking, String> vehicleCol = new TableColumn<>("Vehicle");
    vehicleCol.setCellValueFactory(new PropertyValueFactory<>("vehicleName"));
    TableColumn<Booking, String> serviceCol = new TableColumn<>("Service");
    serviceCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
    TableColumn<Booking, String> dateCol = new TableColumn<>("Date");
    dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    TableColumn<Booking, String> timeCol = new TableColumn<>("Time");
    timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
    TableColumn<Booking, String> spareCol = new TableColumn<>("Spare Part");
    spareCol.setCellValueFactory(new PropertyValueFactory<>("sparePart"));
    TableColumn<Booking, String> numberCol = new TableColumn<>("Number");
    numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
    table.getColumns().addAll(userCol, vehicleCol, serviceCol, dateCol, timeCol, spareCol, numberCol);
    ObservableList<Booking> bookingsList = FXCollections.observableArrayList();
    bookingsList.addAll(Booking.loadBookings());  
    table.setItems(bookingsList);
    Button backBtn = new Button("Back");
    backBtn.setStyle("-fx-background-color: darkGray");
    backBtn.setOnAction(e -> showAdmin(stage));
    VBox root = new VBox(10, table, backBtn);
    root.setPadding(new Insets(20));
    stage.setScene(new Scene(new ScrollPane(root), 800, 450));
    stage.show();
}



    private void showVehicles(Stage stage) {
    
   vehicles = Vehicle.loadVehicles();//this is   


    VBox vehicleBox = new VBox(10);
    vehicleBox.setPadding(new Insets(20));
    Label existVeh = new Label("EXISTING VEHICLES");
    vehicleBox.getChildren().add(existVeh);
    
    for (Vehicle v : vehicles) {
        Label lbl = new Label(v.getVehicleID() + " - " + v.getModel() + " - Type: " + v.getType()
                              + " - Owner: " + v.getOwnerName());
        vehicleBox.getChildren().add(lbl);
    }

    
    TextField vehicleIDField = new TextField();
    vehicleIDField.setPromptText("Vehicle ID");
    TextField modelField = new TextField();
    modelField.setPromptText("Model");
    TextField typeField = new TextField();
    typeField.setPromptText("Type");
    TextField ownerField = new TextField();
    ownerField.setPromptText("Owner");
    Button addBtn = new Button("Add Vehicle");
     addBtn.setStyle( "-fx-background-color: paleGreen ");
    addBtn.setOnAction(ev -> {
        String id = vehicleIDField.getText();
        String model = modelField.getText();
        String type = typeField.getText();
        String owner = ownerField.getText();

        if (id.isEmpty() || model.isEmpty() || type.isEmpty() || owner == null) {
            alert("All fields are required!");
            return;
        }

        Vehicle newVehicle = new Vehicle(id, owner, model, type);
        vehicles.add(newVehicle);
        Vehicle.saveVehicles(vehicles); 
        alert("Vehicle added successfully!");
        showVehicles(stage); 
    });

    Button backBtn = new Button("Back");
     backBtn.setStyle( "-fx-background-color: darkGray");
    backBtn.setOnAction(ev -> showAdmin(stage));  
     Label addVeh = new Label(" ADD NEW VEHICLE");
    VBox addBox = new VBox(10);
    addBox.getChildren().addAll(addVeh,vehicleIDField, modelField, typeField, ownerField, addBtn, backBtn);
    addBox.setPadding(new Insets(20)); 
    HBox root = new HBox(50, vehicleBox, addBox);
    root.setPadding(new Insets(20));
       ScrollPane sp = new ScrollPane(root);
        Scene se=new Scene(sp, 750, 400);
    stage.setScene(se);
    stage.show();
}

    private void showServices(Stage stage) {
    
   services = Service.loadServices();// this is
    vehicles = Vehicle.loadVehicles();

    VBox serviceBox = new VBox(10);
    serviceBox.setPadding(new Insets(20));
    Label existVeh = new Label("EXISTING SERVICES");
    serviceBox.getChildren().add(existVeh);
    for (Service s : services) {
        
        String vehicleModel = "";
        String ownerName = "";
        for (Vehicle v : vehicles) {
            if (v.getVehicleID().equals(s.getVehicleID())) {
                vehicleModel = v.getModel();
                ownerName = v.getOwnerName();
                break;
            }
        }
    Label lbl = new Label(s.getServiceID() + " - " + s.getName() + " - Price: " + s.getPrice()
                               );
        serviceBox.getChildren().add(lbl);
    }

   
    TextField serviceIDField = new TextField();
    serviceIDField.setPromptText("Service ID");
    TextField serviceNameField = new TextField();
    serviceNameField.setPromptText("Service Name");
    TextField priceField = new TextField();
    priceField.setPromptText("Price");
    Button addBtn = new Button("Add Service");
     addBtn.setStyle( "-fx-background-color: paleGreen");
    addBtn.setOnAction(e -> {
        String id = serviceIDField.getText();
        String name = serviceNameField.getText();
        String priceStr = priceField.getText();
       

        if (id.isEmpty() || name.isEmpty() || priceStr.isEmpty() ) {
            alert("All fields are required!");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException ex) {
            alert("Price must be a number!");
            return;
        }

        Service newService = new Service(id, name, price);
        services.add(newService);
        Service.saveServices(services);
        alert("Service added successfully!");
        showServices(stage); 
    });

    Button backBtn = new Button("Back");
    backBtn.setStyle( "-fx-background-color: darkGray");
    backBtn.setOnAction(e -> showAdmin(stage));   
    Label addVeh = new Label(" ADD NEW VEHICLE");
    VBox addBox = new VBox(10); 
    addBox.getChildren().addAll(addVeh,serviceIDField, serviceNameField, priceField,  addBtn, backBtn);
    addBox.setPadding(new Insets(20));
    HBox root = new HBox(50, serviceBox, addBox);
    root.setPadding(new Insets(20));
    ScrollPane sp = new ScrollPane(root);
    stage.setScene(new Scene(sp, 800, 400));
    stage.show();
    }   
    
    
private void showTechStatus(Stage stage) {
     techs = Technician.loadTechnicians();

    VBox box = new VBox(15);
    box.setPadding(new Insets(20));
    box.getChildren().add(new Label("Check Technician Task Status:"));
    ComboBox<String> techCombo = new ComboBox<>();
    for (Technician t : techs){
        techCombo.getItems().add(t.getTechName());
    }
    techCombo.setPromptText("Select Technician");
    Button viewBtn = new Button("View Status");
    viewBtn.setOnAction(e -> {
        String techName = techCombo.getValue();
        if (techName == null) 
        { alert("Select a technician!"); 
        return; 
        }
        
        VBox statusBox = new VBox(10);
        statusBox.setPadding(new Insets(10));
        statusBox.getChildren().add(new Label("Task Status for " + techName + ":"));
     BufferedReader br = null;
    try {
       br = new BufferedReader(new FileReader("service_status.txt"));
   String line;
     boolean found = false;

      while ((line = br.readLine()) != null) {
    String serviceName = "";
    String techNameInFile = "";
    String status = "";

    int barCount = 0;
    for (int i = 0; i < line.length(); i++) {
        char c = line.charAt(i);

        if (c == '|') {
            barCount++;
        } else {
            if (barCount == 0) serviceName += c;
            else if (barCount == 1) techNameInFile += c;
            else if (barCount == 2) status += c;
        }
    }

    if (techNameInFile.equals(techName)) {
        statusBox.getChildren().add(new Label(
            "Service: " + serviceName + " | Status: " + status
        ));
        found = true;
    }
     }

    if (!found) {
    statusBox.getChildren().add(new Label("No tasks found for this technician."));
     }


    } catch (IOException ex) {
    statusBox.getChildren().add(new Label("No status file found."));
   } finally {
    try {
        if (br != null) br.close(); 
    } catch (IOException ee) {
        System.out.println("Error closing file.");
    }
    }


    Button back = new Button("Back");
    back.setStyle( "-fx-background-color: darkGray");
    back.setOnAction(ev -> showTechnicians(stage));
    statusBox.getChildren().add(back);
     stage.setScene(new Scene(statusBox, 400, 300));
    });
    Button backBtn = new Button("Back");
    backBtn.setStyle( "-fx-background-color: darkGray");
    backBtn.setOnAction(e -> showTechnicians(stage));
    box.getChildren().addAll(techCombo, viewBtn, backBtn);
    stage.setScene(new Scene(box, 400, 250));
    stage.show();
}

private void showTechComments(Stage stage) {
    techs = Technician.loadTechnicians();

    VBox box = new VBox(15);
    box.setPadding(new Insets(20));
    box.getChildren().add(new Label("Check Technician Comments/Notes:"));
    ComboBox<String> techCombo = new ComboBox<>();
    for (Technician t : techs){
        techCombo.getItems().add(t.getTechName());
    }
    techCombo.setPromptText("Select Technician");
    Button viewBtn = new Button("View Comments");
    viewBtn.setOnAction(e -> {
        String techName = techCombo.getValue();
        if (techName == null) { alert("Select a technician!"); return; }

        VBox commentBox = new VBox(10);
        commentBox.setPadding(new Insets(10));
        commentBox.getChildren().add(new Label("Comments for " + techName + ":"));
   BufferedReader br = null;
boolean found = false;

try {
    br = new BufferedReader(new FileReader("service_comments.txt"));
    String line;

    while ((line = br.readLine()) != null) {
        String serviceId = "";
        String techname = "";
        String comment = "";

        int barCount = 0;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '|') {
                barCount++;
            } else {
                if (barCount == 0) serviceId += c;
                else if (barCount == 1) techname += c;
                else if (barCount == 2) comment += c;
            }
        }

        if (techname.equals(techName)) {
            commentBox.getChildren().add(new Label(
                "Service: " + serviceId + " | Comment: " + comment
            ));
            found = true;
        }
    }

    if (!found) {
        commentBox.getChildren().add(new Label("No comments found for this technician."));
    }

} catch (IOException ex) {
    commentBox.getChildren().add(new Label("No comments file found."));
} finally {
    try {
        if (br != null) br.close();
    } catch (IOException ee) {
        System.out.println("Error closing file.");
    }
}


        Button back = new Button("Back");
        back.setOnAction(ev -> showTechnicians(stage));
        commentBox.getChildren().add(back);
        stage.setScene(new Scene(commentBox, 500, 300));
        });
       Button backBtn = new Button("Back");
        backBtn.setStyle( "-fx-background-color: darkGray");
       backBtn.setOnAction(e -> showTechnicians(stage));
       box.getChildren().addAll(techCombo, viewBtn, backBtn);
       stage.setScene(new Scene(box, 400, 250));
       stage.show();
}  
    
    
    
    
private void showTechnicians(Stage stage) {

   techs = Technician.loadTechnicians();
    services = Service.loadServices();
VBox techBox = new VBox(10);
techBox.setPadding(new Insets(20));
techBox.getChildren().add(new Label("EXISTING TECHNICIANS"));


      for (Technician t : techs) {
    HBox techRow = new HBox(10); 
    techRow.setAlignment(Pos.CENTER_LEFT);

    Label techLabel = new Label(
        t.getTechName() +
        " > Assigned Services: " +
        String.join(",", t.getAssignedServiceIDs())
    );
    Button deleteBtn = new Button("Delete");
    deleteBtn.setOnAction(e -> {
        techs.remove(t);
        Technician.saveTechnicians(techs); 
    showTechnicians(stage);
        
    });

    techRow.getChildren().addAll(techLabel, deleteBtn);
    techBox.getChildren().add(techRow);
}

    VBox assignBox = new VBox(10);
    assignBox.setPadding(new Insets(20));
    assignBox.getChildren().add(new Label("ASSIGN SERVICE"));
    ComboBox<String> techCombo = new ComboBox<>();
    techCombo.setPromptText("Select Technician");
    for (Technician t : techs) techCombo.getItems().add(t.getTechName());
    ComboBox<String> serviceCombo = new ComboBox<>();
    serviceCombo.setPromptText("Select Service");
    for (Service s : services) serviceCombo.getItems().add(s.getServiceID());

    Button assignBtn = new Button("Assign Service");
    assignBtn.setStyle("-fx-background-color: paleGreen");
    assignBtn.setOnAction(e -> {
        String tName = techCombo.getValue();
        String sID = serviceCombo.getValue();

        if (tName == null || sID == null) {
            alert("Select technician and service");
            return;
        }

        for (Technician t : techs) {
            if (t.getTechName().equals(tName)) {
                t.assignService(sID);
                break;
            }
        }

        Technician.saveTechnicians(techs);
        
        
        alert("Service assigned successfully!");
        showTechnicians(stage);
    });

    Button backBtn = new Button("Back");
    backBtn.setStyle("-fx-background-color: darkGray");
    backBtn.setOnAction(e -> showAdmin(stage));
    assignBox.getChildren().addAll(
            techCombo, serviceCombo, assignBtn, backBtn
    );

   
      

  


       
    VBox statusBox = new VBox(15);
    statusBox.setPadding(new Insets(20));
    statusBox.getChildren().add(new Label("CHECK TECHNICIAN"));
    Button statusBtn = new Button("View Task Status");
    statusBtn.setOnAction(e -> showTechStatus(stage));
    Button commentBtn = new Button("View Comments");
    commentBtn.setOnAction(e -> showTechComments(stage));
    statusBox.getChildren().addAll(statusBtn, commentBtn);
    HBox root = new HBox(30, techBox, assignBox,  statusBox);
    root.setPadding(new Insets(20));
    stage.setScene(new Scene(new ScrollPane(root), 1150, 450));
    stage.show();
}
   
    
private void showUsers(Stage stage) {
    
     userList = new ArrayList<>(users.values());

    
    VBox userBox = new VBox(10);
    userBox.setPadding(new Insets(20));
    Label existU =new Label("EXISTING USERS   ");
    userBox.getChildren().add(existU);
    
    for (User u : userList) {
        HBox userRow = new HBox(10);
        Label lbl = new Label(u.getUsername() + " - Role: " + u.getRole());     
        Button delBtn = new Button("Delete");
         delBtn.setStyle( "-fx-background-color: violet");
        delBtn.setOnAction(e -> {
            users.remove(u.getUsername());
            saveUsers(); 
            alert("User deleted successfully!");
            showUsers(stage); 
        });

        userRow.getChildren().addAll(lbl, delBtn);
        userBox.getChildren().add(userRow);
    }

    
    VBox addBox = new VBox(10);
    addBox.setPadding(new Insets(20));
    Label addUser =new Label("ADD NEW USER");
    addBox.getChildren().add(addUser);
    TextField usernameField = new TextField();
    usernameField.setPromptText("Username");
    PasswordField passField = new PasswordField();
    passField.setPromptText("Password");
    ToggleGroup tg = new ToggleGroup();
    RadioButton r1 = new RadioButton("ADMIN");
    RadioButton r2 = new RadioButton("TECHNICIAN");
    RadioButton r3 = new RadioButton("USER");
    r1.setToggleGroup(tg);
    r2.setToggleGroup(tg);
    r3.setToggleGroup(tg);

    HBox roleBox = new HBox(10, r1, r2, r3);
    Button addBtn = new Button("Add User");
     addBtn.setStyle( "-fx-background-color: paleGreen");
    addBtn.setOnAction(e -> {
        String uname = usernameField.getText();
        String pass = passField.getText();
        RadioButton rb = (RadioButton) tg.getSelectedToggle();
       String role = rb.getText();
        if (uname.isEmpty() || pass.isEmpty() || rb == null) {
            alert("All fields are required!");
            return;
        }

        if (users.containsKey(uname)) {
            alert("Username already exists!");
            return;
        }

       
        User newUser = new User(uname, pass, role);
        users.put(uname, newUser);
       saveUsers(); 
       if (role.equals("TECHNICIAN")) {
        techs = Technician.loadTechnicians();
       techs.add(new Technician(uname));
       Technician.saveTechnicians(techs);
}

        alert("User added successfully!");
        showUsers(stage); 
    });

    
    Button backBtn = new Button("Back");
     backBtn.setStyle( "-fx-background-color: darkGray");
    backBtn.setOnAction(e -> showAdmin(stage));
    addBox.getChildren().addAll(usernameField, passField, roleBox, addBtn, backBtn);   
    HBox root = new HBox(50, userBox, addBox);
    root.setPadding(new Insets(20));
    ScrollPane sp = new ScrollPane(root);
    stage.setScene(new Scene(sp, 900, 400));
    stage.show();
}

private void showInvoices(Stage stage) {
     invoiceList = Invoice.loadInvoices();
    vehicles = Vehicle.loadVehicles();
    services = Service.loadServices();

    VBox invoiceBox = new VBox(15);
    invoiceBox.setPadding(new Insets(20));
     Label existV =new Label("EXISTING INVOICES");   
    invoiceBox.getChildren().add(existV);
 
    for (Invoice inv : invoiceList) {

        VBox singleInvoice = new VBox(5);
        singleInvoice.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-padding: 10;");

        
        Label companyLbl = new Label("HIM Vehicle Service Center");    
        companyLbl.setStyle("-fx-font-weight: bold;-fx-background-color: indianRed; -fx-font-size: 16;");
        Label ownerLbl = new Label("Owners: Irfan & Mustafa");
        Label phoneLbl = new Label("PH#: 03209925798");
        Separator sepTop = new Separator();
        Label invHeader = new Label("Invoice ID: " + inv.getInvoiceID());
        invHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");       
        Label custLbl = new Label("Customer: " + inv.getOwnerName());
        
        String vehicleModel = "";
        for (Vehicle v : vehicles) {
            if (v.getVehicleID().equals(inv.getVehicleID())) {
                vehicleModel = v.getModel();
                break;
            }
        }
        Label vehLbl = new Label("Vehicle: " + inv.getVehicleID() + " (" + vehicleModel + ")");      
        String serviceName = "";
        for (Service s : services) {
            if (s.getServiceID().equals(inv.getServiceID())) {
                serviceName = s.getName();
                break;
            }
        }
        Label servLbl = new Label("Service: " + inv.getServiceID() + " - " + serviceName);       
        Label amtLbl = new Label("Amount:    " + inv.getAmount()+"     PKR");       
        Button delBtn = new Button("Delete");
         delBtn.setStyle( "-fx-background-color: violet");
         delBtn.setOnAction(e -> {
            invoiceList.remove(inv);
            Invoice.saveInvoices(invoiceList);
            alert("Invoice deleted!");
            showInvoices(stage);
        });

        
        Separator sepBottom = new Separator();
        Label footerLbl = new Label("Thank you for your business!");   
        singleInvoice.getChildren().addAll(
            companyLbl, ownerLbl, phoneLbl, sepTop,
            invHeader, vehLbl,custLbl,  servLbl, amtLbl,
            
            sepBottom, footerLbl,
       delBtn );

        invoiceBox.getChildren().add(singleInvoice);
    }

   
    VBox addBox = new VBox(10);
    addBox.setPadding(new Insets(20));
    addBox.getChildren().add(new Label("CREATE NEW INVOICE"));
    TextField idField = new TextField();
    idField.setPromptText("Invoice ID");
    ComboBox<String> ownerCombo = new ComboBox<>();
    ownerCombo.setPromptText("Select owner");
    for (Vehicle v : vehicles) ownerCombo.getItems().add(v.getOwnerName());
    ComboBox<String> vehicleCombo = new ComboBox<>();
    vehicleCombo.setPromptText("Select Vehicle ID");
    for (Vehicle v : vehicles) vehicleCombo.getItems().add(v.getVehicleID());
     TextField serviceField = new TextField();
    serviceField.setPromptText("Add all services ");
    TextField amountField = new TextField();
    amountField.setPromptText("Amount");
    Button addBtn = new Button("Create Invoice");
   addBtn.setStyle( "-fx-background-color: paleGreen");
    addBtn.setOnAction(e -> {
        String id = idField.getText();
        String cust = ownerCombo.getValue();
        String veh = vehicleCombo.getValue();
        String serv = serviceField.getText();
        String amtStr = amountField.getText();

        if (id.isEmpty() || cust == null || veh == null || serv == null || amtStr.isEmpty()) {
            alert("All fields are required!");
            return;
        }

        double amount;
        try { amount = Double.parseDouble(amtStr); }
        catch(Exception ex) {
            alert("Amount must be a number!"); return; }

        Invoice newInv = new Invoice(id, cust, veh, serv, amount);
        invoiceList.add(newInv);
        Invoice.saveInvoices(invoiceList);
        alert("Invoice created successfully!");
        showInvoices(stage); 
    });

    Button backBtn = new Button("Back");
     backBtn.setStyle( "-fx-background-color: darkGray");
    backBtn.setOnAction(e -> showAdmin(stage));
    addBox.getChildren().addAll(idField, vehicleCombo,ownerCombo,  serviceField, amountField, addBtn, backBtn);   
    HBox root = new HBox(50, invoiceBox, addBox);
    root.setPadding(new Insets(20));
    ScrollPane sp = new ScrollPane(root);  
    Scene invoiceScene =new Scene(sp, 1000, 500);
    stage.setScene(invoiceScene);
    stage.show();
}
   
    
      private void alert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    } 

}
