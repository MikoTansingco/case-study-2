/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Miko Tansingco
 */
public class ButtonFunction implements ActionListener{
    
    List<JButton> buttons;
    JPanel content;
    JButton currentButton;
    MgmtTab mgmtTab;
    CardLayout contentView;
    
    public ButtonFunction(Collection<JButton> buttons, JButton currentButton, JPanel content, MgmtTab mgmtTab, CardLayout contentView){
        this.buttons = new ArrayList(buttons);
        this.currentButton = currentButton;
        this.content = content;
        this.mgmtTab = mgmtTab;
        this.contentView = contentView;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        mgmtTab.init();
        
        for(int i = 0; i < buttons.size(); i++)
            if(buttons.get(i).equals(currentButton))
                this.buttons.get(i).setForeground(Color.red);
            else this.buttons.get(i).setForeground(Color.black);
        
        //System.out.println("Button has an action");
        contentView.show(content, mgmtTab.getTabName());
    }
    
}
