
package com.project.main;

import com.project.enums.ColorEnum;
import com.project.tools.ResizeTools;
import com.project.enums.FigureEnum;
import com.project.enums.ImageProcessEnum;
import com.project.enums.OptionsEnum;
import com.project.tools.SelectedColor;
import com.project.workspace.PaintFigures;
import com.project.workspace.PaintMenu;
import com.project.workspace.PaintSelectColor;
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
    
    private  final PaintMenu      paintMenu;
    private  final PaintSurface   paintSurface;
    private  final PaintTool      paintTool;
    private  final PaintFigures   paintFigures;
    private  final PaintSelectColor paintSelectColor;
    private SelectedColor selectedColor;
    private ResizeTools  resizeTools;
    
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
         selectedColor = new SelectedColor();
         
        selectedColor.setNewColor(ColorEnum.BLACK, Color.BLACK);
        selectedColor.setNewColor(ColorEnum.WHITE, Color.WHITE);
        selectedColor.setNewColor(ColorEnum.DARKGRAY, Color.DARK_GRAY);
        selectedColor.setNewColor(ColorEnum.LIGHTGRAY, Color.LIGHT_GRAY);
        selectedColor.setNewColor(ColorEnum.BROWN, new Color(139, 71, 38));
        selectedColor.setNewColor(ColorEnum.LIGHTBROWN,  new Color(150, 75, 0));
        selectedColor.setNewColor(ColorEnum.RED, new Color(255, 0, 0));
        selectedColor.setNewColor(ColorEnum.LIGHTRED,  new Color(195, 92, 111));
        selectedColor.setNewColor(ColorEnum.ORANGE, new Color(233, 107, 57));
        selectedColor.setNewColor(ColorEnum.LIGHTORANGE,  new Color(233, 150, 123));
        selectedColor.setNewColor(ColorEnum.YELLOW, new Color(255, 239, 0));
        selectedColor.setNewColor(ColorEnum.LIGHTYELLOW,  new Color(255, 255, 51));
        selectedColor.setNewColor(ColorEnum.GREEN, new Color(0, 128, 0));
        selectedColor.setNewColor(ColorEnum.LIGHTGREEN,  new Color(51, 204, 102));         
        selectedColor.setNewColor(ColorEnum.BLUE,  new Color(0, 0, 204));
        selectedColor.setNewColor(ColorEnum.LIGHTBLUE, new Color(0, 127,255));
        selectedColor.setNewColor(ColorEnum.SKY,  new Color(0, 180, 247));
        selectedColor.setNewColor(ColorEnum.SKYLIGHT, new Color(0, 255,255));
        selectedColor.setNewColor(ColorEnum.PURPLE,  new Color(184,2, 255));
        selectedColor.setNewColor(ColorEnum.LIGHTPURPLE, new Color(238, 230,238));
        selectedColor.setNewColor(ColorEnum.DARKPURPLE,  new Color(102,0, 102));
        selectedColor.setNewColor(ColorEnum.VERYLIGHTPURPLE, new Color(153, 102,204));      
        selectedColor.setNewColor(ColorEnum.NAVY,  new Color(0,0, 128));
        selectedColor.setNewColor(ColorEnum.LIGHTNAVY, new Color(25, 36,124)); 
// określenie rozmiaru obszaru roboczego panelu do rysowania
         Width =    1500;
         Height=    1200;
         
        this.setSize(800 , 700);
        this.setLocation(200 , 0);
        this.setTitle("Pixel");
        this.setLayout(new BorderLayout());
        
          paintMenu      = new PaintMenu();
          paintSurface   = new PaintSurface();
          paintTool      = new PaintTool();
          paintFigures   = new PaintFigures();
          paintSelectColor = new PaintSelectColor();


        add(paintMenu , BorderLayout.NORTH);
        add(paintFigures , BorderLayout.WEST);
        add(paintTool , BorderLayout.EAST);
        add(new JScrollPane(paintSurface) , BorderLayout.CENTER);
        add(paintSelectColor,BorderLayout.SOUTH);
        
        
        
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
    public void setColor(ColorEnum color)
    {     
        Color col = selectedColor.getValue(color);
        paintSurface.setColor(col);
    }
    public void SetFigure(FigureEnum f){           
        paintSurface.setFigure(f);
    }
    public void UndoOperation(){
        paintSurface.undo();
    }
     public void RedoOperation(){
        paintSurface.redo();
    }
    public void rotationImage(double angle){
        
        paintSurface.rotationImage(angle);
    }
   
    public void imageProcess(ImageProcessEnum processEnum){
        paintSurface.imageProcesses(processEnum);
    }
    
    public void sizeTools()
    {
        resizeTools = new ResizeTools(paintSurface);
        resizeTools.setVisible(true);
    }
    
}
