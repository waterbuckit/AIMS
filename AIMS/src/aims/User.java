/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aims;

/**
 *
 * @author waterbucket
 * 
 * Class to define what a user can do when they log in. Privileges are attained 
 * by difference in account
 * Dependent on rank in company.
 */

public class User {
    
    String userName;
    
    public User(String[] memVars) {
        userName = memVars[0];
    }
    @Override
    public String toString() {
        return userName; 
    }
    
    public String getName(){
        return userName;
    }
    
}
