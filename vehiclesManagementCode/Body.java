package com.mycompany.vehicleservicecentermanagment;

public class Body 
{
    String partName;
    double price;
public Body(){
    this.partName=null;
    this.price=0;
}
    
    public Body(String partName){
        this();
        this.partName=partName;
    }
    public Body(String partName, double price) 
    {    this(partName);
        this.partName = partName;
        this.price = price;
    }

    public String showDetails() 
    {
        return partName + " - Rs " + price;
    }
}
