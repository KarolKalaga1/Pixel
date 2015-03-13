
package com.project.workspace;

import com.project.main.Paint;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.*; 

/**
 *
 * @author Karol
 */
public class PaintMenu extends JMenuBar{
 
    JMenu file;
        JMenuItem quit;
        JMenuItem news;
        JMenuItem open;
        JMenuItem save; 
        JMenuItem saveas;

    JMenu tools;
        JMenuItem undo;
        JMenu rotation;
            JMenuItem right30;
            JMenuItem right45;
            JMenuItem right90;
            JMenuItem horizontalRotation;
            JMenuItem verticalRotation;
        
    JMenu options; 

    JMenu help;
        JMenuItem helpPaint;
        
    private String fileName;

  public PaintMenu(){

        file    =   new JMenu("File");
        help    =   new JMenu("Help");
        tools    =   new JMenu("View");
        options =   new JMenu("Tools");

        news    =   new JMenuItem("New File");
        open    =   new JMenuItem("Open File");
        save    =   new JMenuItem("Save");
        saveas  =   new JMenuItem("Save as...");
        quit    =   new JMenuItem("Quit");
        
        ActionMenu actionMenu = new ActionMenu();
        quit.addActionListener(actionMenu);
        save.addActionListener(actionMenu);
        saveas.addActionListener(actionMenu);
        news.addActionListener(actionMenu);
        open.addActionListener(actionMenu);
        
        save.setEnabled(false);

        undo                 =  new JMenuItem("Undo");
        rotation             =  new JMenu("Rotation");
        
        horizontalRotation   =  new JMenuItem("Horizontal Rotation");
        verticalRotation     =  new JMenuItem("Vertical Rotation");
        right30              =  new JMenuItem("right 30");
        right45              =  new JMenuItem("right 45");
        right90              =  new JMenuItem("right 90");

        file.add(news);
        file.add(open);
        file.add(save);
        file.add(saveas);
        file.addSeparator();
        file.add(quit);

        helpPaint = new JMenuItem("Help...");
     
        help.add(helpPaint); 
        
        helpPaint.addActionListener(actionMenu);

        add(file);

        rotation.add(horizontalRotation);
        rotation.add(verticalRotation);
        rotation.addSeparator();
        rotation.add(right30);
        rotation.add(right45);
        rotation.add(right90);
        horizontalRotation.setEnabled(false);
        verticalRotation.setEnabled(false);
//        right90.setEnabled(false);
//        left90.setEnabled(false);
//        rotation180.setEnabled(false);
        
       tools.add(undo);
       tools.add(rotation);
        
       undo.addActionListener(actionMenu);
       right45.addActionListener(actionMenu);
       right30.addActionListener(actionMenu);
       right90.addActionListener(actionMenu);
       verticalRotation.addActionListener(actionMenu);
       horizontalRotation.addActionListener(actionMenu);
       
       options.setEnabled(false);
        add(tools);
        add(options);
        add(help);
    }

  public class ActionMenu implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
       
        if(e.getSource()==quit)
            System.exit(0); 
        if(e.getSource()==news){
            Paint.paintStart.NewDocument();
            save.setEnabled(false);
        }
        
        if(e.getSource()==save){
            
            try {
                BufferedImage image=Paint.paintStart.getBuffImage();
                ImageIO.write(image, "BMP", new File(fileName));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,"Save file Exception "+ex.getMessage(), "Save exception", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        
        if(e.getSource()==saveas){
          
            try {
                fileName=Paint.paintStart.getSaveFileName();
                if(fileName!=null){
                BufferedImage image=Paint.paintStart.getBuffImage();
                ImageIO.write(image, "BMP", new File(fileName));
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,"Save file Exception "+ex.getMessage(), "Save exception", JOptionPane.INFORMATION_MESSAGE);
            }
            
        }
        
        if(e.getSource()==open){
            try {
                File openFile = Paint.paintStart.getOpenFileName();
                if(openFile!=null){
                    BufferedImage image = ImageIO.read(openFile);
                    if(image!=null){
                        int height = image.getHeight();
                        int width  = image.getWidth();

                        Paint.paintStart.setSizeSurface(new Dimension(width, height));
                        Paint.paintStart.setBufferedImage(image);
                        System.out.print(openFile.getPath());

                        save.setEnabled(true);
                    }
                }
            } catch (IOException ex) {
                 JOptionPane.showMessageDialog(null,"Open file Exception "+ex.getMessage(), "Open exception", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if(e.getSource()==right30){
            Paint.paintStart.rotationImage(30);
        }
        if(e.getSource()==right45){
            Paint.paintStart.rotationImage(45);
        }
        if(e.getSource()==right90){
           Paint.paintStart.rotationImage(90);
        }
        
        if(e.getSource()==undo){
            Paint.paintStart.UndoOperation();
        }
        if(e.getSource()==helpPaint){
         ImageIcon icon = new ImageIcon(this.getClass().getResource("/about.png"));
         JOptionPane.showMessageDialog(null, "\n"
                + "Program created by Karol Kalaga.\nFor more information please write at kalagakarol@gmail.com", "About", JOptionPane.INFORMATION_MESSAGE, icon);
        }
            
        }
    
}
    

      
}


