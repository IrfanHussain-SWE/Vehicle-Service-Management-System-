package com.mycompany.vehicleservicecentermanagment;

public class Electrical 
{
    String partName;
    double price;

   public Electrical(){
    this.partName=null;
    this.price=0;
      }
    
    public Electrical(String partName){
        this();
        this.partName=partName;
    }
    public Electrical(String partName, double price) 
    {    this(partName);
        this.partName = partName;
        this.price = price;
    }

    public String showDetails() 
    {
        return partName + " - Rs " + price;
    }
}
