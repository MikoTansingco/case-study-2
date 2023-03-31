/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import static Controller.DataValidation.isSQL;
import static java.awt.image.ImageObserver.HEIGHT;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

import View.Frame;
import java.sql.Timestamp;

/**
 *
 * @author Miko Tansingco
 */
public class RegisterSecurityFeatures {
    //Attributes and Methods defined by Miko Tansingco
    boolean hasError = false;
    boolean isUsingEmail = false;
    
    String username;
    String password;
    String confpass;
    
    Frame frame;
    
    public void attemptRegistration(String username, String password, String confpass, boolean isUsingEmail, Frame frame){
        
        this.username = username;
        this.password = password;
        this.confpass = confpass;
        
        this.isUsingEmail = isUsingEmail;
        this.frame = frame;
        
        checkIfSql();
        checkIfNull();
        checkIfUsernameAlreadyTaken();
        checkIfValidEmail();
        checkIfValidPassword();
        checkIfPasswordsMatch();
        
        if(hasError)
        {
            hasError = false;
            return;
        }
        
        System.out.println("Registration Successful");
        frame.registerAction(username, hashPasswordToMD5(this.password), hashPasswordToMD5(this.confpass));
        frame.loginNav();
        
        addRegisterLog("User creation successful");
    }
    void checkIfSql(){
        if(isSQL(username)||isSQL(password)||isSQL(confpass)){
            displayMessage("ERROR!");
            hasError = true;
        }
    }
    void checkIfNull(){
        
        if(hasError)
            return;
        
        if(username.length() == 0 || password.length() == 0 || confpass.length() == 0)
        {
            hasError = true;
            displayMessage("Fields cannot be null.");
        }
    }
    
    void checkIfUsernameAlreadyTaken(){
        
        if(hasError)
            return;
        
        ArrayList<Model.User> users;
        users = SQLite.getUsers();
        
        for(int i = 0; i < users.size(); i++){
            String usernameCheckLowerCase = users.get(i).getUsername().toLowerCase();
            String usernameInputLowerCase = username.toLowerCase();
            
            if(usernameCheckLowerCase.equals(usernameInputLowerCase)){
                
                displayMessage("Username already taken.");
                
                hasError = true;  
                break;
            }   
        }
    }
    
    void checkIfValidPassword(){
        
        if(hasError)
            return;
        
        //Courtesy of https://www.geeksforgeeks.org/check-if-a-string-contains-uppercase-lowercase-special-characters-and-numeric-values/
        
        String regex = "^(?=.*[a-z])(?=."
                       + "*[A-Z])(?=.*\\d)"
                       + "(?=.*[-+_!@#$%^&*., ?]).+$";
        
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        boolean isComplex = m.matches();
        
        //Password Length
        if(password.length() < 8)
        {
            displayMessage("Password must be 8 characters long.");
            hasError = true;
        }
        
        //Password Complexity
        else if(!isComplex){
            displayMessage("Password must contain at least one lowercase character,\none uppercase character, one numeric value, and one special character.");
            hasError = true;
        }     
    }
    
    
    void checkIfPasswordsMatch(){
        
        if(hasError)
            return;
        
        if(!password.equals(confpass))
        {
            displayMessage("Passwords do not match.");
            hasError = true;
        }
            
    }
    
    void checkIfValidEmail(){
        
        //Courtesy of https://www.geeksforgeeks.org/check-email-address-valid-not-java/
        
        if(hasError || !isUsingEmail)
            return;
       
        
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
        Pattern p = Pattern.compile(emailRegex);
        Matcher m = p.matcher(username);
        
        boolean isValid = m.matches();
        
        if(!isValid){
            displayMessage("Inputted Email is not valid");
            hasError = true;
        }
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
    
    void addRegisterLog(String logDescription){
        
        //Courtesy of https://howtodoinjava.com/java/date-time/get-current-timestamp/
        
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SQLite.addLogs("NOTICE", this.username, logDescription, timestamp.toString());
    }
    
    
    void displayMessage(String message){
        JOptionPane.showMessageDialog(frame, message, "ERROR MESSAGE", HEIGHT);
            
        //System.out.println();
        //System.out.println(message);
    }
}
