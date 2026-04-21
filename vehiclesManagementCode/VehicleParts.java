        package com.mycompany.vehicleservicecentermanagment;

import java.util.ArrayList;

public class   VehicleParts implements SpareParts<String>
{
     public String vehicleType;
   
   public  ArrayList<Body> bd ;
     public ArrayList<Engine> eng ;
    public ArrayList<Electrical> elec ;
 public VehicleParts(){
 this.vehicleType=null;
 bd = new ArrayList<>();
 eng = new ArrayList<>();
 elec = new ArrayList<>();
 
 }
 public VehicleParts(String vehicleType){
     this();
 this.vehicleType=vehicleType;
 
 }
   
   
    @Override
    public  String sparePaartsInfo() 
    {
        
        String a= " SPARE PARTS TERMS AND CONDITIONS\n"+
    "1. All spare parts are sold in sealed condition and cannot be returned once opened.\n" +
    "2. Warranty applies only to manufacturing defects and not damage caused by misuse.\n" +
    "3. Replacement of defective parts is subject to inspection and approval by the service center.\n" +
    "4. Customers must provide the original invoice for any warranty claim or part verification.\n" +
    "5. Installed spare parts are non-refundable, and labor charges are not included in warranty.";

        return a;
    }

    @Override
    public String priceInfo() 
    {
     
        String a=" PRICES TERMS AND CONDITIONS\n"+
       "1. All spare part prices are subject to change without prior notice.\n" +
       "  2. Prices listed include only the cost of the part and exclude service charges.\n" +
      " 3. Final billed price may vary based on availability and current market rate.\n"  +
      "4. Discounts and promotions are applicable only when officially announced";
        return a;
    }
    
   
    
}
