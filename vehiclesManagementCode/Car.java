package com.mycompany.vehicleservicecentermanagment;

public class Car extends VehicleParts 
{ 
    
    public Car(){
    super(null);
   
    }
    public Car(String vehicle){
        
        super(vehicle);
        
    }
    public void addEngineParts(Engine part){
       eng.add(part);
    }
    
    public void addBodyParts(Body part){
        bd.add(part);
    }
    public void addElectricalParts(Electrical part){
        elec.add(part);
    }
    public  void displayCarBody() {
        bd.add(new Body("Front Bumper", 5000));
        bd.add(new Body("Rear Bumper", 4800));
        bd.add(new Body("Hood", 7000));
        bd.add(new Body("Roof Panel", 6500));
        bd.add(new Body("Door Left", 3500));
        bd.add(new Body("Door Right", 3500));
        bd.add(new Body("Fender Left", 4000));
        bd.add(new Body("Fender Right", 4000));
        bd.add(new Body("Trunk Lid", 5500));
        bd.add(new Body("Headlight Assembly", 2500));
    }

    
   public void displayCarEngine() {
        eng.add(new Engine("Spark Plug", 800));
        eng.add(new Engine("Air Filter", 1500));
        eng.add(new Engine("Fuel Injector", 5000));
        eng.add(new Engine("Timing Belt", 2000));
        eng.add(new Engine("Oil Pump", 3000));
        eng.add(new Engine("Water Pump", 2500));
        eng.add(new Engine("Cylinder Head", 7000));
        eng.add(new Engine("Piston", 4000));
        eng.add(new Engine("Camshaft", 4500));
        eng.add(new Engine("Crankshaft", 6000));
    }

   
   public void displayCarElectrical() {
        elec.add(new Electrical("Battery", 5000));
        elec.add(new Electrical("Alternator", 7000));
        elec.add(new Electrical("Starter Motor", 3500));
        elec.add(new Electrical("Headlight Bulb", 800));
        elec.add(new Electrical("Tail Light", 1200));
        elec.add(new Electrical("Wiper Motor", 1500));
        elec.add(new Electrical("Horn", 900));
        elec.add(new Electrical("Fuse Box", 2500));
        elec.add(new Electrical("AC Compressor", 6500));
        elec.add(new Electrical("Indicator Relay", 1100));
    }
}
