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
import javax.swing.*;
import javax.swing.GroupLayout.Group;
/**
 *
 * @author Miko Tansingco
 */
public class CentralizedAccessControl {
    
    public static void hideButtons(int role, JButton adminBtn, JButton clientBtn, JButton managerBtn, JButton staffBtn,
            CardLayout contentView, JPanel content){
        //hides the button when logging in with a specific role
        
        switch(role){
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

    public static void unhideButtons(JButton adminBtn, JButton clientBtn, JButton managerBtn, JButton staffBtn){//unhides the buttons after logging out
            adminBtn.setVisible(true);
            clientBtn.setVisible(true);
            managerBtn.setVisible(true);
            staffBtn.setVisible(true);
                
    }
    
    public static void SetAvailableButtons(User user, JPanel content, JPanel panel, CardLayout contentView,
    SQLite sqlite){
       
        MgmtHistory mgmtHistory = new MgmtHistory(sqlite, user);
        MgmtLogs mgmtLogs = new MgmtLogs(sqlite, user);
        MgmtProduct mgmtProduct = new MgmtProduct(sqlite, user);
        MgmtUser mgmtUser = new MgmtUser(sqlite, user);
       
        content.setLayout(contentView);
        content.add(new Home("WELCOME MANAGER!", new java.awt.Color(153,102,255)), "home");
        content.add(mgmtUser, "mgmtUser");
        content.add(mgmtHistory, "mgmtHistory");
        content.add(mgmtProduct, "mgmtProduct");
        content.add(mgmtLogs, "mgmtLogs");
        
        ButtonFunction buttonFunction;
        
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        
        ArrayList<Initializable> mgmts = new ArrayList<Initializable>();
        ArrayList<JButton> buttons = new ArrayList<JButton>();
        
        JButton usersBtn = new JButton("USERS");
        JButton productsBtn = new JButton("PRODUCTS");
        JButton historyBtn = new JButton("HISTORY");
        JButton logsBtn = new JButton("LOGS");
        
        usersBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        productsBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        historyBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        logsBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        
        buttons.add(usersBtn);
        buttons.add(productsBtn);
        buttons.add(historyBtn);
        buttons.add(logsBtn);
        
        mgmts.add(mgmtUser);
        mgmts.add(mgmtProduct);
        mgmts.add(mgmtHistory);
        mgmts.add(mgmtLogs);
        
        
        
        
        Group horizontalButtonGroup = layout.createSequentialGroup();
        Group verticalButtonGroup = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        
        for(int i = 0; i < buttons.size(); i++){
            buttonFunction = new ButtonFunction(buttons,i,content,mgmts.get(i),contentView);
            buttons.get(i).addActionListener(buttonFunction);
            
            
            horizontalButtonGroup.addComponent(buttons.get(i), javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
            verticalButtonGroup.addComponent(buttons.get(i), javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE);
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
}
