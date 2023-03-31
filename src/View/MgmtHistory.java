/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.CentralizedAccessControl;
import Controller.SQLite;
import Model.History;
import Model.Product;
import Model.User;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import Controller.MgmtTab;
import static Controller.SessionManagment.addSessionLog;


/**
 *
 * @author beepxD
 */
public class MgmtHistory extends javax.swing.JPanel implements MgmtTab {

    public SQLite sqlite;
    public DefaultTableModel tableModel;
   
    private User user;
    private String tabName;
    
    public MgmtHistory(SQLite sqlite, User user, String tabName) {
        initComponents();
        this.sqlite = sqlite;
        this.user = user;
        this.tabName = tabName;
        tableModel = (DefaultTableModel)table.getModel();
        table.getTableHeader().setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
        javax.swing.table.DefaultTableCellRenderer rightAlign = new javax.swing.table.DefaultTableCellRenderer();
        rightAlign.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        table.getColumnModel().getColumn(2).setCellRenderer(rightAlign);
        table.getColumnModel().getColumn(3).setCellRenderer(rightAlign);
        table.getColumnModel().getColumn(4).setCellRenderer(rightAlign);
        table.getColumnModel().getColumn(5).setCellRenderer(rightAlign);
        
//        UNCOMMENT TO DISABLE BUTTONS
//        searchBtn.setVisible(false);
//        reportBtn.setVisible(false);
    }

    @Override
    public void init(){      
//      CLEAR TABLE
        for(int nCtr = tableModel.getRowCount(); nCtr > 0; nCtr--){
            tableModel.removeRow(0);
        }
        
        ArrayList<History> history;
                
//      LOAD CONTENTS

        if(CentralizedAccessControl.checkAuthority(user, "getHistory"))
            history = sqlite.getHistory();
        else history = sqlite.getHistoryByUser(user.getUsername());     
        
        for(int nCtr = 0; nCtr < history.size(); nCtr++){
            Product product = sqlite.getProduct(history.get(nCtr).getName());
            
            if(product == null)
                continue;
            
            tableModel.addRow(new Object[]{
                history.get(nCtr).getUsername(), 
                history.get(nCtr).getName(), 
                history.get(nCtr).getStock(), 
                product.getPrice(), 
                product.getPrice() * history.get(nCtr).getStock(), 
                history.get(nCtr).getTimestamp()
            });
        }
        
        CentralizedAccessControl.setHistoryButtons(searchBtn, reloadBtn, user);
        
    }
    
    public void designer(JTextField component, String text){
        component.setSize(70, 600);
        component.setFont(new java.awt.Font("Tahoma", 0, 18));
        component.setBackground(new java.awt.Color(240, 240, 240));
        component.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        component.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), text, javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12)));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        searchBtn = new javax.swing.JButton();
        reloadBtn = new javax.swing.JButton();

        table.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Username", "Name", "Stock", "Price", "Total", "Timestamp"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setRowHeight(24);
        table.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(160);
            table.getColumnModel().getColumn(1).setPreferredWidth(160);
            table.getColumnModel().getColumn(2).setMinWidth(80);
            table.getColumnModel().getColumn(3).setMinWidth(100);
            table.getColumnModel().getColumn(4).setMinWidth(100);
            table.getColumnModel().getColumn(5).setPreferredWidth(240);
        }

        searchBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        searchBtn.setText("SEARCH USERNAME OR PRODUCT");
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });

        reloadBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        reloadBtn.setText("RELOAD");
        reloadBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reloadBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(reloadBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(searchBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(reloadBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
        JTextField searchFld = new JTextField("0");

        Object[] message = {
            searchFld
        };
        
        if(CentralizedAccessControl.checkAuthority(user, "getHistory"))
        {
            designer(searchFld, "SEARCH USERNAME OR PRODUCT");
        }
        else designer(searchFld, "SEARCH PRODUCT");
                
        int result = JOptionPane.showConfirmDialog(null, message, "SEARCH HISTORY", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);

        if (result == JOptionPane.OK_OPTION) {
//          CLEAR TABLE

            //System.out.println("TRUE");
            
            for(int nCtr = tableModel.getRowCount(); nCtr > 0; nCtr--){
                tableModel.removeRow(0);
            }
            
            ArrayList<History> history;
            
//          LOAD CONTENTS

            if(CentralizedAccessControl.checkAuthority(user, "getHistory"))
            {
                 history = sqlite.getHistoryByUser(searchFld.getText());
                 history.addAll(sqlite.getHistoryByProduct(searchFld.getText()));
            }
            else{
                history = sqlite.getHistoryByUserAndProduct(user.getUsername(), searchFld.getText());
            }
               
            
            
            for(int nCtr = 0; nCtr < history.size(); nCtr++){
                if(searchFld.getText().contains(history.get(nCtr).getUsername()) || 
                   history.get(nCtr).getUsername().contains(searchFld.getText()) || 
                   searchFld.getText().contains(history.get(nCtr).getName()) || 
                   history.get(nCtr).getName().contains(searchFld.getText())){
                
                    Product product = sqlite.getProduct(history.get(nCtr).getName());
                    
                    if(product == null)
                        continue;
                    
                    tableModel.addRow(new Object[]{
                        history.get(nCtr).getUsername(), 
                        history.get(nCtr).getName(), 
                        history.get(nCtr).getStock(), 
                        product.getPrice(), 
                        product.getPrice() * history.get(nCtr).getStock(), 
                        history.get(nCtr).getTimestamp()
                    });
                }
            }
            addSessionLog("Searched for" + searchFld.getText());

            
            //this.init();
        }
    }//GEN-LAST:event_searchBtnActionPerformed

    private void reloadBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reloadBtnActionPerformed
        addSessionLog("Pressed Reload Button");
        this.init();
    }//GEN-LAST:event_reloadBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton reloadBtn;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables

    @Override
    public String getTabName() {
        return this.tabName;
    }
}
