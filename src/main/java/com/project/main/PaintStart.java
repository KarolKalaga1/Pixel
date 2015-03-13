
package com.project.main;

import com.project.workspace.FigureEnum;
import com.project.workspace.OptionsEnum;
import com.project.workspace.PaintFigures;
import com.project.workspace.PaintMenu;
import com.project.workspace.PaintSurface;
import com.project.workspace.PaintTool;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.TwilightSkin;

/**
 *
 * @author Karol
 */
public final class PaintStart extends JFrame {
    
    private  final PaintMenu    paintMenu;
    private  final PaintSurface paintSurface;
    private  final PaintTool    paintTool;
    private  final PaintFigures paintFigures;
    private  int   Width;
    private  int   Height;
    
    public PaintStart(){

         java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run() {
            try
            {   
               setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        SubstanceLookAndFeel.setSkin(new TwilightSkin());
        SubstanceLookAndFeel.setSkin("org.pushingpixels.substance.api.skin.BusinessBlueSteelSkin");
         }catch(Exception e)
            {
                System.out.println(e);
            }
            }
        });
         Width =1500;
         Height=1200;
         
        this.setSize(800 , 600);
        this.setLocation(400 , 200);
        this.setTitle("PaintNew");
        this.setLayout(new BorderLayout());
        
          paintMenu     = new PaintMenu();
          paintSurface  = new PaintSurface();
          paintTool     = new PaintTool();
          paintFigures  = new PaintFigures();


       
        add(paintFigures , BorderLayout.WEST);
        add(paintTool , BorderLayout.EAST);
        add(new JScrollPane(paintSurface) , BorderLayout.CENTER);
        add(paintMenu , BorderLayout.NORTH);
        
        initializeSurface();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    
    public void initializeSurface(){
        Graphics2D graphics ;
        BufferedImage image = paintSurface.getImage();
        graphics =  ( Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, Width, Height);
        repaint();
    }
    
    public String getSaveFileName(){
       
        JFileChooser openFile = new JFileChooser();
        int returnVal = openFile.showSaveDialog(paintMenu);
        
        if(returnVal== JFileChooser.APPROVE_OPTION){
         File file = openFile.getSelectedFile();
         return file.getPath();
        }
        return null;
    }
    
    public File getOpenFileName(){
    
         JFileChooser openFile = new JFileChooser();
         int returnVal = openFile.showOpenDialog(paintMenu);
         
         if(returnVal== JFileChooser.APPROVE_OPTION){
          File file = openFile.getSelectedFile();
          return file;
         }
        return null;
    }
    
    public BufferedImage getBuffImage(){
        return paintSurface.getImage();
    }
    public void setBufferedImage(BufferedImage image){
        paintSurface.setImages(image);
        repaint();
    }
    
    public void NewDocument(){
        
          Graphics2D graphics ;
        BufferedImage image = paintSurface.getImage();
        graphics =  ( Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, Width, Height);
        repaint();
  
    }
    
    public void setSizeSurface(Dimension dimension){
        Height = (int) dimension.getHeight();
        Width  = (int) dimension.getWidth();
        
        paintSurface.setPreferredSize(dimension);
    }
    
    public void SetOptions(OptionsEnum o){
       paintSurface.setOption(o);
    }
    public void SetFigure(FigureEnum f){
        paintSurface.setFigure(f);
    }
    public void UndoOperation(){
        paintSurface.undo();
    }
    public void rotationImage(double angle){
        
        paintSurface.rotationImage(angle);
    }
   
}
