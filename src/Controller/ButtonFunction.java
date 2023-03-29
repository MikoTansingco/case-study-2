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
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Miko Tansingco
 */
public class ButtonFunction implements ActionListener{
    
    ArrayList<JButton> buttons;
    JPanel parent;
    int index;
    Initializable init;
    CardLayout contentView;
    
    public ButtonFunction(ArrayList<JButton> buttons, int index, JPanel parent, Initializable init, CardLayout contentView){
        this.buttons = buttons;
        this.index = index;
        this.parent = parent;
        this.init = init;
        this.contentView = contentView;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        init.init();
        
        for(int i = 0; i < buttons.size(); i++)
            if(i == index)
                this.buttons.get(i).setForeground(Color.red);
            else this.buttons.get(i).setForeground(Color.black);
        
        
        //System.out.println("Button has an action");
        contentView.show(parent, "Management");
    }
    
}
