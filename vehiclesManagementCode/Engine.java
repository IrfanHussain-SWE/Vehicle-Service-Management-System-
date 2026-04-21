package com.mycompany.vehicleservicecentermanagment;

public class Engine 
{
    String partName;
    double price;

   public Engine(){
    this.partName=null;
    this.price=0;
}
    
    public  Engine(String partName){
        this();
        this.partName=partName;
    }
    public  Engine(String partName, double price) 
    {    this(partName);
        this.partName = partName;
        this.price = price;
    }

    public String showDetails() 
    {
        return partName + " - Rs " + price;
    }
}
