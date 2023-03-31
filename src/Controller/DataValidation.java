/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author User
 */
public class DataValidation {
    
    public static boolean isSQL(String inputStr) {
    // List of SQL and query keywords to check for
    String[] keywords = {"SELECT", "INSERT", "UPDATE", "DELETE", "DROP", "CREATE", "ALTER", "TRUNCATE"};

    // Check if any keyword is present in the input string
    for (String keyword : keywords) {
        if (inputStr.toUpperCase().contains(keyword.toUpperCase())) {
            // Keyword found, return false
            return true;
        }
    }

    // No keywords found, input is valid
    return false;
    }
    public static boolean notPassword(String pass){
         String regex = "^(?=.*[a-z])(?=."
                       + "*[A-Z])(?=.*\\d)"
                       + "(?=.*[-+_!@#$%^&*., ?]).+$";
        
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(pass);
        boolean isComplex = m.matches();
        
        //Password Length
        if(pass.length() < 8)
        {
            return true;
        }
        //Password for default users
        else if(pass.equals("qwerty1234")){
            return true;
        }
        //Password Complexity
        else if(!isComplex){
            return false;
        }
        return true;
    }

    
}
