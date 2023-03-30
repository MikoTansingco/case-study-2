/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.User;
import View.Home;
import View.MgmtHistory;
import View.MgmtLogs;
import View.MgmtProduct;
import View.MgmtUser;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.GroupLayout.Group;
/**
 *
 * @author Miko Tansingco
 */
public final class CentralizedAccessControl {
    
    MgmtLogs mgmtLogs;
    MgmtProduct mgmtProduct;
    MgmtHistory mgmtHistory;
    MgmtUser mgmtUser;
        
    User user;
    SQLite sqlite;
    
    HashMap<MgmtTab,JButton> tabAndButton = new HashMap<MgmtTab,JButton>();
    
    public CentralizedAccessControl(User user, SQLite sqlite){
        this.user = user;
        this.sqlite = sqlite;
    }
    
    public void hideButtons(JButton adminBtn, JButton clientBtn, JButton managerBtn, JButton staffBtn,
            CardLayout contentView, JPanel content){
        //hides the button when logging in with a specific role
        
        switch(user.getRole()){
            case 1://hides all buttons
                adminBtn.setVisible(false);
                clientBtn.setVisible(false);
                managerBtn.setVisible(false);
                staffBtn.setVisible(false);
                break;
            case 2://Client
                adminBtn.setVisible(false);
                managerBtn.setVisible(false);
                staffBtn.setVisible(false);
                contentView.show(content, "clientHomePnl");
                break;
            case 3://Staff
                adminBtn.setVisible(false);
                managerBtn.setVisible(false);
                clientBtn.setVisible(false);
                contentView.show(content, "staffHomePnl");
                break;
            case 4://Manager
                adminBtn.setVisible(false);
                clientBtn.setVisible(false);
                staffBtn.setVisible(false);
                contentView.show(content, "managerHomePnl");
                break;
            case 5://Admin
                clientBtn.setVisible(false);
                managerBtn.setVisible(false);
                staffBtn.setVisible(false);
                contentView.show(content, "adminHomePnl");
                break;
        }
    }

    public void unhideButtons(JButton adminBtn, JButton clientBtn, JButton managerBtn, JButton staffBtn){//unhides the buttons after logging out
            adminBtn.setVisible(true);
            clientBtn.setVisible(true);
            managerBtn.setVisible(true);
            staffBtn.setVisible(true);
    }
    
    
    public void setHomeButtons(JButton history, JButton logs, JButton product, JButton user){
            
        switch(this.user.getRole()){
            case 2://Client
                history.setVisible(true);
                logs.setVisible(false);
                product.setVisible(true);
                user.setVisible(false);
                break;
            case 3://Staff
                history.setVisible(false);
                logs.setVisible(false);
                product.setVisible(true);
                user.setVisible(false);
                break;
            case 4://Manager
                history.setVisible(true);
                logs.setVisible(false);
                product.setVisible(true);
                user.setVisible(false);
                break;
            case 5://Admin
                history.setVisible(false);
                logs.setVisible(true);
                product.setVisible(false);
                user.setVisible(true);
                break;
        }
    }
    
    public void setHistoryButtons(JButton search, JButton reload){
        
        switch(this.user.getRole()){
            case 2://Client
                search.setVisible(true);
                reload.setVisible(true);
                break;
            case 4://Manager
                search.setVisible(true);
                reload.setVisible(true);
                break;
            default: 
                search.setVisible(false);
                reload.setVisible(false);
        }
    }
    
    public void setLogButtons(JButton debug, JButton clear){
        switch(this.user.getRole()){
            case 5://Admin
                debug.setVisible(true);
                clear.setVisible(true);
                break;
            default: 
                debug.setVisible(false);
                clear.setVisible(false);
        }
    }
    
    public void setProductButtons(JButton purchase, JButton add, JButton edit, JButton delete){
        
        switch(this.user.getRole()){
            case 2://Client
                purchase.setVisible(true);
                add.setVisible(false);
                edit.setVisible(false);
                delete.setVisible(false);
                break;
            case 3://Staff
                purchase.setVisible(false);
                add.setVisible(true);
                edit.setVisible(true);
                delete.setVisible(true);
                break;
            case 4://Manager
                purchase.setVisible(false);
                add.setVisible(true);
                edit.setVisible(true);
                delete.setVisible(true);
                break;
            default: 
                purchase.setVisible(false);
                add.setVisible(false);
                edit.setVisible(false);
                delete.setVisible(false);
                break;
        }
    }
    
        public void setUserButtons(JButton edit, JButton delete, JButton lock, JButton changePass){
        
        switch(this.user.getRole()){
            case 5://Admin
                edit.setVisible(true);
                delete.setVisible(true);
                lock.setVisible(true);
                changePass.setVisible(true);
                break;
            default: 
                edit.setVisible(false);
                delete.setVisible(false);
                lock.setVisible(false);
                changePass.setVisible(false);
                break;
        }
    }
    
}