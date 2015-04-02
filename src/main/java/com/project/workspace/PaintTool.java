
package com.project.workspace;

import com.project.enums.OptionsEnum;
import com.project.main.Paint;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Karol
 */
public class PaintTool extends JPanel{
    
    public JButton buttons[]=new JButton[7];
 
    public PaintTool() {
        
        setBackground   (Color.LIGHT_GRAY);
        setPreferredSize(new Dimension   (80 , 0));
        setLayout       (new BorderLayout(7 , 7));
        
        JPanel toolPanel =  new JPanel(        );
        toolPanel.setLayout(new GridLayout(7 , 1));
        toolPanel.setBackground(Color.darkGray);
        toolPanel.setPreferredSize(new Dimension(290 , 470));      
        
         Icon pen        =    new ImageIcon(this.getClass().getResource("/Pencil.png"));
         Icon marker     =    new ImageIcon(this.getClass().getResource("/Marker.png"));
         Icon eraser     =    new ImageIcon(this.getClass().getResource("/Eraser.png"));
         Icon paint      =    new ImageIcon(this.getClass().getResource("/Painting.png"));
         Icon loupe      =    new ImageIcon(this.getClass().getResource("/loupe.png"));
         Icon text       =    new ImageIcon(this.getClass().getResource("/Text.png"));
         Icon paintColor =    new ImageIcon(this.getClass().getResource("/PaintColor.png"));

        buttons[0] =   new JButton(pen);
        buttons[1] =   new JButton(marker);
        buttons[2] =   new JButton(paint);
        buttons[3] =   new JButton(eraser);
        buttons[4] =   new JButton(text);
        buttons[5] =   new JButton(loupe);    
        buttons[6] =   new JButton(paintColor);
        
        PaintButtons paintButtons=new PaintButtons();
      
        for(int i = 0 ; i<7 ; i++){
        buttons[i].addActionListener(paintButtons);
        }
        
        buttons[4].setEnabled(false);
        buttons[5].setEnabled(false);
      
        for(int i=0 ; i<buttons.length ;++i){
            toolPanel.add(buttons[i]);
        }   

        add(toolPanel, BorderLayout.NORTH);

    }
    
    public class PaintButtons implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==buttons[0]){
                Paint.paintStart.SetOptions(OptionsEnum.PEN);
            }
            if(e.getSource()==buttons[1]){
                Paint.paintStart.SetOptions(OptionsEnum.BRUSH);
            }
            if(e.getSource()==buttons[2]){
                Paint.paintStart.SetOptions(OptionsEnum.PAINT);
            }
            if(e.getSource()==buttons[3]){
                Paint.paintStart.SetOptions(OptionsEnum.ERASER);
            }
            if(e.getSource()==buttons[6]){
                Paint.paintStart.SetOptions(OptionsEnum.COLORCHOOSE);
            }
            
        }
    }
    
    
}
