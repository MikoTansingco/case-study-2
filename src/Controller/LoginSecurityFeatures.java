/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;


import static Controller.DataValidation.isSQL;
import static Controller.DataValidation.notPassword;
import static Controller.SessionManagment.setSt;
import Model.User;
import static java.awt.image.ImageObserver.HEIGHT;
import javax.swing.JOptionPane;

import View.Frame;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Jericho Dizon
 */
public class LoginSecurityFeatures {
    //Attributes and Methods defined by Miko Tansingco
    boolean hasError = false;
    String username;
    String password;   
    Frame frame;
    int attemps=0;
    SQLite sqlite;
    
    public User user;
    
    
    public void attemptLogin(String username, String password, Frame frame) {
        
        boolean isTesting = false;
        
        this.username = username;
        this.password = password;
        this.frame = frame;
        
        if(!isTesting)
        {
            
        NullEntry();
        checkValidUser();
        checkIfLockedOut();
        
        if (hasError || user == null) {
            if(attemps>=5){
                displayMessage("Application Lockout please close the app");
                addLoginLog("Application Lockout");
                
                return;
            }
            else{
                hasError = false;
                return;      
            }    
            }
        }
        
        addLoginLog("User Login Successful");
        setSt();
        attemps=0;
       
        frame.SetUser(user);
        frame.hideButtons();
        frame.mainNav();
    }
    void NullEntry() { //Function that checks if there's an input in the textfield
        if (hasError) {
            return;
        }
        if (username.length() == 0 || password.length() == 0) {
            displayMessage("Fields cannot be null.");
            hasError = true;
        }
    }
    void checkValidUser() { //Functions that check if the input is a valid user
        if (hasError) {
            return;
        }

        ArrayList<User> users;
        users = SQLite.getUsers();
        
        String usernameInputLowerCase = username.toLowerCase();
        String hashedPassword = hashPasswordToMD5(password);
        //String hashedPassword = password;
        
        for (int i = 0; i < users.size(); i++) {//Loop to iterate the array of users
            String usernameDB = users.get(i).getUsername().toLowerCase();
            String passwordDB = users.get(i).getPassword();
            
            if(usernameDB.equals(usernameInputLowerCase)&& (passwordDB.equals(hashedPassword)|| passwordDB.equals(password))) {//If statement that checks if the inputed username and password matched properly
                user = users.get(i);
                break;
            }
            else if(isSQL(usernameInputLowerCase)||isSQL(password)||isSQL(hashedPassword)){
                addLoginLog("User Attempted SQL injection");
                displayMessage("Error");
                attemps = 5;
                hasError = true;
                break;
            }
            else if(notPassword(password)){
                addLoginLog("User entered incorrect format");//log that an attempt was made and what password was used to prevent password spraying
                displayMessage("Error");
                hasError = true;
                break;
            }
            else{
              if(i+1==users.size()){//After iterating the users and found no valid combination of username and password
                    attemps++;      //the number of attemps increase
                    displayMessage("Invalid Username and/or Password number of tries: "+attemps);//displays a dialogue box of the error message and the number of attemps
                    addLoginLog("User Attempt made using the password: "+password);//log that an attempt was made and what password was used to prevent password spraying
                    hasError = true;
                    break;
                }      
            }
            
            
        }
    }
    
    void checkIfLockedOut(){
        
        if(hasError)
            return;
        
        User userTemp = sqlite.getUser(this.username);
        System.out.println(userTemp);
        
        if(userTemp.getLocked() == 1)
        {
            displayMessage("Error. User is locked out.");
            hasError = true;
        }
    }
    
    void addLoginLog(String logDescription){
        
        //Courtesy of https://howtodoinjava.com/java/date-time/get-current-timestamp/
        
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SQLite.addLogs("NOTICE", this.username, logDescription, timestamp.toString());
    }
    void displayMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, "ERROR MESSAGE", HEIGHT);

    }

    String hashPasswordToMD5(String password){
        //Courtesy of https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/#:~:text=The%20MD5%20Message%2DDigest%20Algorithm,chunks%20of%20512%2Dbit%20blocks.
        String generatedPassword = null;
        
        try 
        {
          // Create MessageDigest instance for MD5
          MessageDigest md = MessageDigest.getInstance("MD5");

          // Add password bytes to digest
          md.update(password.getBytes());

          // Get the hash's bytes
          byte[] bytes = md.digest();

          // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
          StringBuilder sb = new StringBuilder();
          for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
          }

          // Get complete hashed password in hex format
          generatedPassword = sb.toString();
          
        } catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
        }
        
        //System.out.println("Hashed Password: " + generatedPassword);
        
        return generatedPassword;
    }
    
}
