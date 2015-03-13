
package com.project.workspace;

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
public class PaintFigures extends JPanel{
   
    public JButton figures[] = new JButton[6];

    public PaintFigures() {
        
        setBackground   (Color.LIGHT_GRAY);
        setPreferredSize(new Dimension   (80 , 0));
        setLayout       (new BorderLayout(6 , 6));
        
        JPanel toolPanel =  new JPanel();
        toolPanel.setLayout(new GridLayout(6 , 1));
        toolPanel.setBackground(Color.darkGray );
        toolPanel.setPreferredSize(new Dimension(290 , 470));
        
         Icon line      =   new ImageIcon(getClass().getResource("/line.png"));
         Icon rect      =   new ImageIcon(this.getClass().getResource("/rect.png"));
         Icon roundRect =   new ImageIcon(this.getClass().getResource("/RoundRect.png"));
         Icon circle    =   new ImageIcon(this.getClass().getResource("/circle.png"));
         Icon triang    =   new ImageIcon(this.getClass().getResource("/triang.png"));
         Icon diamond   =   new ImageIcon(this.getClass().getResource("/diamond.png"));

        figures[0] =    new JButton(line);
        figures[1] =    new JButton(rect);
        figures[2] =    new JButton(roundRect);
        figures[3] =    new JButton(circle);
        figures[4] =    new JButton(triang);
        figures[5] =    new JButton(diamond);
         
        FigureAction figureAction = new FigureAction();
        
        figures[0].addActionListener(figureAction);
        figures[1].addActionListener(figureAction);
        figures[2].addActionListener(figureAction);
        figures[3].addActionListener(figureAction);
        figures[4].setEnabled(false);
        figures[5].setEnabled(false);
        
        
        for(int i=0 ; i<figures.length ;++i){
            toolPanel.add(figures[i]);
        }
     
        add(toolPanel, BorderLayout.NORTH);
    }
    
    
    class FigureAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==figures[0]){
                Paint.paintStart.SetFigure(FigureEnum.LINE);
            }
            if(e.getSource()==figures[1]){
                Paint.paintStart.SetFigure(FigureEnum.RECT);
            }
            if(e.getSource()==figures[2]){
                Paint.paintStart.SetFigure(FigureEnum.ROUNDRECT);
            }
            if(e.getSource()==figures[3]){
                Paint.paintStart.SetFigure(FigureEnum.CIRCLE);
            }
        }
    }
    
    
    
}
