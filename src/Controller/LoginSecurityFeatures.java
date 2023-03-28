/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;


import static java.awt.image.ImageObserver.HEIGHT;
import javax.swing.JOptionPane;

import View.Frame;
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
    ArrayList<Model.User> users;
    ArrayList<Integer> attemps = new ArrayList<Integer>();

    public void attemptLogin(String username, String password, Frame frame) {

        this.username = username;
        this.password = password;
        this.frame = frame;
        if(hasError){
            
        }
        NullEntry();
        checkValidUser();
        

        if (hasError) {
                hasError = false;
                return;      
            
            
        }
        addLoginLog("User Login Successful");
        //attemps=0; reset
        
        frame.mainNav();

        //addRegisterLog("User creation successful");
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
        users = SQLite.getUsers();
        if(users.size()!=attemps.size()){
            for(int i = 0; i<users.size();i++){
                attemps.add(0);
            }
        }
        
        
        for (int i = 0; i < users.size(); i++) {//Loop to iterate the array of users
            String usernameCheckLowerCase = users.get(i).getUsername().toLowerCase();
            String usernameInputLowerCase = username.toLowerCase();
            String passwordDB = users.get(i).getPassword();
            String passwordInput = password;
            if(usernameCheckLowerCase.equals(usernameInputLowerCase)){
                if(attemps.get(i)>=5){
                    displayMessage("Invalid Login");
                    hasError = true;
                    break;
                }   
            }
            if(usernameCheckLowerCase.equals(usernameInputLowerCase)&& passwordDB.equals(password)) {//If statement that checks if the inputed username and password matched properly
                for(int j = 0; j<users.size();j++){
                    attemps.set(j,0);
                }
                frame.hideButtons(users.get(i).getRole());
                break;
            }
            else{
              if(usernameCheckLowerCase.equals(usernameInputLowerCase)){//After iterating the users and found no valid combination of username and password
                attemps.set(i,attemps.get(i)+1);
                displayMessage("Invalid Username and/or Password number of tries: "+attemps.get(i));//displays a dialogue box of the error message and the number of attemps
                addLoginLog(username+" Attempt Made using the password: "+password);//log that an attempt was made and what password was used to prevent password spraying
                hasError = true;
                break;
                   
                    
              }
            }
            
            
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

    
}
