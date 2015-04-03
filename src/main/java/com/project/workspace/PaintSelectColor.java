
package com.project.workspace;

import com.project.enums.ColorEnum;
import com.project.enums.OptionsEnum;
import com.project.main.Paint;
import java.awt.BorderLayout;
import java.awt.Color;
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
public class PaintSelectColor extends JPanel{

     private JButton selectColor[] = new JButton[7];
     private JButton colors[]     = new JButton[24];
     private ActionSelectColor actionSelectColor;
     private ColorEnum color ;
     
    public PaintSelectColor() {
        
        this.actionSelectColor = new ActionSelectColor();
        
        setBackground   (Color.LIGHT_GRAY);
        setPreferredSize(new Dimension   (80 , 80));
        setLayout       (new BorderLayout());
        
        JPanel colorPanel =  new JPanel();
        JPanel selectPanel = new JPanel();
        selectPanel.setLayout(new GridLayout(1 , 1));
        colorPanel.setLayout(new GridLayout(2,12));
        colorPanel.setPreferredSize(new Dimension(600, 45));
        
        selectPanel.setBackground(Color.darkGray );
        selectPanel.setPreferredSize(new Dimension(90 , 70));
        
         Icon line      =   new ImageIcon(getClass().getResource("/PaintColor.png"));
         
        selectColor[0] =   new JButton(line);
        selectColor[0].addActionListener(actionSelectColor);
        
       
        for(int i =0 ; i < 24; i++)
        {
            colors[i]= new JButton();
        }
        
       
        colors[0].setBackground(Color.BLACK.brighter());
        colors[1].setBackground(Color.DARK_GRAY.brighter());
        colors[2].setBackground(new Color(139, 71,38).darker());
        colors[3].setBackground(new Color(255, 0,0).darker());
        colors[4].setBackground(new Color(233, 107,57).darker());
        colors[5].setBackground(new Color(255, 239,0).darker());
        colors[6].setBackground(new Color(0, 128,0).darker());
        colors[7].setBackground(new Color(0, 0,204).darker()); 
        colors[8].setBackground(new Color(0, 180,247).darker());
        colors[9].setBackground(new Color(184, 3,255).darker());
        colors[10].setBackground(new Color(102, 0,102).darker());
        colors[11].setBackground(new Color(0, 0,128).darker());

        colors[12].setBackground(Color.WHITE.darker());
        colors[13].setBackground(Color.LIGHT_GRAY.darker());
        colors[14].setBackground(new Color(150, 75, 0).darker());
        colors[15].setBackground(new Color(195, 92,111).darker());
        colors[16].setBackground(new Color(233, 150,123).darker());
        colors[17].setBackground(new Color(255, 255,51).darker());
        colors[18].setBackground(new Color(51, 204,102).darker());
        colors[19].setBackground(new Color(0, 127,255).darker());        
        colors[20].setBackground(new Color(0, 255,255).darker());
        colors[21].setBackground(new Color(238, 130,238).darker());
        colors[22].setBackground(new Color(153, 102,204).darker());
        colors[23].setBackground(new Color(25, 36,124).darker());


        for(int i = 0 ; i<24 ; i++){
        colors[i].addActionListener(actionSelectColor);
        }
        
    
     
            selectPanel.add(selectColor[0]);
           
        for(int i=0 ; i<24 ;++i){
            colorPanel.add(colors[i]);
        }  
        
        add(selectPanel, BorderLayout.WEST);
        add(colorPanel,BorderLayout.CENTER);
    }
    
    
    public class ActionSelectColor implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==selectColor[0]){
                Paint.paintStart.SetOptions(OptionsEnum.COLORCHOOSE);
            }
            if(e.getSource()==colors[0])
            {
                Paint.paintStart.setColor(ColorEnum.BLACK);
            }
            if(e.getSource()==colors[1])
            {
                Paint.paintStart.setColor(ColorEnum.DARKGRAY);
            }
            if(e.getSource()==colors[2])
            {
                Paint.paintStart.setColor(ColorEnum.BROWN);
            }
            if(e.getSource()==colors[3])
            {
                Paint.paintStart.setColor(ColorEnum.RED);
            }
            if(e.getSource()==colors[4])
            {
                Paint.paintStart.setColor(ColorEnum.ORANGE);
            }
            if(e.getSource()==colors[5])
            {
                Paint.paintStart.setColor(ColorEnum.YELLOW);
            }
            if(e.getSource()==colors[6])
            {
                Paint.paintStart.setColor(ColorEnum.GREEN);
            }
            if(e.getSource()==colors[7])
            {
                Paint.paintStart.setColor(ColorEnum.BLUE);
            }
            if(e.getSource()==colors[8])
            {
                Paint.paintStart.setColor(ColorEnum.SKY);
            }
            if(e.getSource()==colors[9])
            {
                Paint.paintStart.setColor(ColorEnum.PURPLE);
            }
            if(e.getSource()==colors[10])
            {
                Paint.paintStart.setColor(ColorEnum.DARKPURPLE);
            }
            if(e.getSource()==colors[11])
            {
                Paint.paintStart.setColor(ColorEnum.NAVY);
            }
            
            
             if(e.getSource()==colors[12])
            {
                Paint.paintStart.setColor(ColorEnum.WHITE);
            }
            if(e.getSource()==colors[13])
            {
                Paint.paintStart.setColor(ColorEnum.LIGHTGRAY);
            }
            if(e.getSource()==colors[14])
            {
                Paint.paintStart.setColor(ColorEnum.LIGHTBROWN);
            }
            if(e.getSource()==colors[15])
            {
                Paint.paintStart.setColor(ColorEnum.LIGHTRED);
            }
            if(e.getSource()==colors[16])
            {
                Paint.paintStart.setColor(ColorEnum.LIGHTORANGE);
            }
            if(e.getSource()==colors[17])
            {
                Paint.paintStart.setColor(ColorEnum.LIGHTYELLOW);
            }
            if(e.getSource()==colors[18])
            {
                Paint.paintStart.setColor(ColorEnum.LIGHTGREEN);
            }
            if(e.getSource()==colors[19])
            {
                Paint.paintStart.setColor(ColorEnum.LIGHTBLUE);
            }
            if(e.getSource()==colors[20])
            {
                Paint.paintStart.setColor(ColorEnum.SKYLIGHT);
            }
            if(e.getSource()==colors[21])
            {
                Paint.paintStart.setColor(ColorEnum.LIGHTPURPLE);
            }
            if(e.getSource()==colors[22])
            {
                Paint.paintStart.setColor(ColorEnum.VERYLIGHTPURPLE);
            }
            if(e.getSource()==colors[23])
            {
                Paint.paintStart.setColor(ColorEnum.LIGHTNAVY);
            }
        }
        
    }
    
    
    
}
