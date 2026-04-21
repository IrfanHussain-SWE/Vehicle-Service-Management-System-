package com.mycompany.vehicleservicecentermanagment;

public class Bike extends VehicleParts 
{
    
    public Bike(){
    super(null);
   
    }
    public Bike(String vehicle){
        
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

    void displayBikeBody()
    {
        bd.add(new Body("Bike Front Fender", 5000));
        bd.add( new Body("Bike Rear Fender", 4800));
       bd.add(new Body("Bike Fuel Tank", 7000));
        bd.add(new Body("Bike Seat", 6500));
        bd.add( new Body("Bike Side Cover Left", 3500));
        bd.add( new Body("Bike Side Cover Right", 3500));
       bd.add( new Body("Bike Headlight Cover", 4000));
      bd.add(new Body("Bike Tail Cover", 4000));
        bd.add(new Body("Bike Mudguard Front", 5500));
        bd.add(new Body("Bike Mudguard Rear", 2500));
    }
    
    void displayBikeEngine()
    {
       eng.add( new Engine("Bike Spark Plug", 800));
        eng.add( new Engine("Bike Air Filter", 1500));
       eng.add(   new Engine("Bike Fuel Injector", 5000));
       eng.add( new Engine("Bike Timing Belt", 2000));
       eng.add(  new Engine("Bike Oil Pump", 3000));
       eng.add( new Engine("Bike Cylinder Head", 2500));
       eng.add( new Engine("Bike Piston", 7000));
       eng.add( new Engine("Bike Camshaft", 4000));
       eng.add( new Engine("Bike Crankshaft", 4500));
        eng.add( new Engine("Bike Water Pump", 6000));
    }
    
    void displayBikeElectrical()
    {
        elec.add(new Electrical("Bike Battery", 50000));
        elec.add(new Electrical("Bike Alternator", 70000));
        elec.add( new Electrical("Bike Starter Motor", 3500));
       elec.add(  new Electrical("Bike Headlight Bulb", 800));
       elec.add(new Electrical("Bike Tail Light", 1200));
       elec.add( new Electrical("Bike Wiper Motor", 1500));
 
        elec.add( new Electrical("Bike Fuse Box", 2500));
      
        
    }
}
