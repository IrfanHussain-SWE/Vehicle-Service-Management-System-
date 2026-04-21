
package com.mycompany.vehicleservicecentermanagment;


class User {
    private String username;
    private String password;
    private String role;

     public User(){
        this.username = null;
        this.password = null;
        this.role = null;
    }
     public User(String username){
         this();
        this.username = username;
       
    }
      public User(String username, String password){
        this(username);
        this.password = password;
        
    }
    public User(String username, String password, String role){
        this(username,password);
        this.role = role;
    }

    public String getUsername(){ 
        return username; 
    }
    public String getPassword(){
        return password;
    }
    public String getRole(){
        return role; 
    }
}
