/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.security.SecureRandom;
import java.sql.Timestamp;


/**
 *
 * @author User
 */
public class SessionManagment {
    public static String Stoken;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 8;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateSt() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
    public static void addSessionLog(String logDescription){
        
        //Courtesy of https://howtodoinjava.com/java/date-time/get-current-timestamp/
        
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SQLite.addLogs("SESSION",Stoken, logDescription, timestamp.toString());
    }
    public static void setSt(){
        Stoken = generateSt();
        addSessionLog("New Session Created");
    }
    public static void removeSt(){
        addSessionLog("Session End");
        Stoken = null;
    }

}
