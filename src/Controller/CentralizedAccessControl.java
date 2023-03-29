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
    
    public void SetAvailableButtons(JPanel content, JPanel panel, CardLayout contentView){
        InitButtonsBasedOnAccess();
        content.setLayout(contentView);
        
        for(MgmtTab tab : tabAndButton.keySet())
            content.add((Component) tab, tab.getTabName());
        
        ButtonFunction buttonFunction;
        
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        
        
        Group horizontalButtonGroup = layout.createSequentialGroup();
        Group verticalButtonGroup = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        
        for(MgmtTab tab : tabAndButton.keySet())
        {
            buttonFunction = new ButtonFunction(tabAndButton.values(),tabAndButton.get(tab),content,tab,contentView);
            tabAndButton.get(tab).addActionListener(buttonFunction);
            horizontalButtonGroup.addComponent(tabAndButton.get(tab), javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
            verticalButtonGroup.addComponent(tabAndButton.get(tab), javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE);
        }
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(horizontalButtonGroup))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(verticalButtonGroup)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }
    
    public void InitButtonsBasedOnAccess(){
        mgmtLogs = new MgmtLogs(sqlite, user, "mgmtUser");
        mgmtProduct = new MgmtProduct(sqlite, user, "mgmtProduct");
        mgmtHistory = new MgmtHistory(sqlite, user, "mgmtHistory");
        mgmtUser = new MgmtUser(sqlite, user, "mgmtLogs");
        
        JButton usersBtn = new JButton("USERS");
        JButton productsBtn = new JButton("PRODUCTS");
        JButton historyBtn = new JButton("HISTORY");
        JButton logsBtn = new JButton("LOGS");
        
        usersBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        productsBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        historyBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        logsBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
                
        switch(user.getRole()){
            case 1: break;
            case 2: //Client
                tabAndButton.put(mgmtProduct, productsBtn);
                tabAndButton.put(mgmtHistory, historyBtn);
                tabAndButton.put(mgmtUser,usersBtn);
                break;

            case 3: //Staff 
                tabAndButton.put(mgmtProduct, productsBtn);
                break;

            case 4: //Manager
                tabAndButton.put(mgmtProduct, productsBtn);
                tabAndButton.put(mgmtHistory, historyBtn);
                break;
                
            case 5: //Admin 
                tabAndButton.put(mgmtUser, usersBtn);
                tabAndButton.put(mgmtLogs, logsBtn);
                break;
                
            default:
            }
    }   
}