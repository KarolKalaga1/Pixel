
package com.project.workspace;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JColorChooser;
import javax.swing.JScrollPane;

/**
 *
 * @author Karol
 */
public final class PaintSurface extends JScrollPane{

    private BufferedImage image = new BufferedImage(1500, 1200, BufferedImage.TYPE_INT_RGB);
    private Image imageDrow;
    private Graphics2D graphics2d;
    private  Color colorPencil;  
    private AffineTransform at;
   
    int x,y,x1,y1;
    
    private boolean mouseDragged;
    private OptionsEnum options;
    private FigureEnum figure;
    
    boolean spr;
    boolean ima;
    
    private final SizedStack<Image> undoStack = new SizedStack<Image>(10);

    public PaintSurface() {
        setSize(1500, 1200);
      colorPencil=new Color(0,0,0);
     
      setPreferredSize(new Dimension(getWidth(),getHeight()));
        
      addMouseListener(new MouseAdapter(){
       
                @Override
                public void mousePressed(MouseEvent e)
                {
                    mouseDragged=false;
                    x=e.getX();
                    y=e.getY();
                     checkMouse(e);
                      repaint();                   
                }

            @Override
            public void mouseDragged(MouseEvent e) {    
                mouseDragged=true;
                x1=e.getX();
                y1=e.getY();
                 checkMouse(e);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                 checkMouse(e);
            }
            
		});

        addMouseMotionListener(new MouseMotionAdapter(){
                        @Override
			public void mouseDragged(MouseEvent e){
                           
				if(getGraphics() != null)
                                {
                                    mouseDragged=true;
                                    checkMouse(e);
                                }
                                   
                            }  
                        
		});

    }

    private BufferedImage copyImage(Image img) {
    BufferedImage copyOfImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
    Graphics g = copyOfImage.createGraphics();
    g.drawImage(img, 0, 0,getWidth(), getHeight(), null);
    return copyOfImage;
    }

    private void saveToStack(Image img) {
    undoStack.push(copyImage(img));
    }
    
    private void setImage(Image img) {
        
    image = (BufferedImage) img;
    repaint();
    }
    
    public void undo() {
    if (undoStack.size() > 0) {
        setImage(undoStack.pop());
    }
}
    
    
    @Override
    public Graphics getGraphics() {
        return super.getGraphics(); 
    }
    
    
    @Override
    public void paint(Graphics g2){  
        
        graphics2d = (Graphics2D) g2;

            if(image!=null){
            imageDrow = image;
            }        
           graphics2d = (Graphics2D)imageDrow.getGraphics();
           graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       
           
           g2.drawImage(image, 0, 0, null);
        
            
    }
    
    public BufferedImage rotateImage(Image image, int angle)
    {
        
        ima=true;
        double sin = Math.abs(Math.sin(angle));
        double cos = Math.abs(Math.cos(angle));
        int originalWidth = image.getWidth(null);
        int originalHeight = image.getHeight(null);
        int newWidth = (int) Math.floor(originalWidth * cos + originalHeight * sin);
        int newHeight = (int) Math.floor(originalHeight * cos + originalWidth * sin);
        setPreferredSize(new Dimension(newWidth, newHeight));
        BufferedImage rotatedBI = new BufferedImage(newWidth, newHeight, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = rotatedBI.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.translate((newWidth - originalWidth) / 2, (newHeight - originalHeight) / 2);
        g2d.rotate(angle, originalWidth / 2, originalHeight / 2);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return rotatedBI;
    }

    public void rotationImage(double angle){
//      setPreferredSize(new Dimension(1500,1500));
        ima=true;          
        setImage(rotateImage(image, (int) angle));  
    }
    

    public BufferedImage getImage() {
        return image;
    }

    public void setImages(BufferedImage image) {
        this.image = image;
        repaint();
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize); 
    }
    
    public void setOption(OptionsEnum o){
        options = o;
        figure = FigureEnum.NONE;
        check();
    }
    
    public void setFigure(FigureEnum f){
        figure = f;
        options = OptionsEnum.NONE;
    }
 
    public void check(){
        
    if(options==OptionsEnum.COLORCHOOSE){    
            this.colorPencil = JColorChooser.showDialog(null, "Choose a color", Color.BLUE);
        }
    }

    public void checkMouse(MouseEvent e){
        
        x1 = e.getX();
        y1 = e.getY();
           int w = x - x1;
        if (w < 0)
            w = w * (-1);

        int h = y - y1;
        if (h < 0)
            h = h * (-1);
       
         if(options==OptionsEnum.ERASER){
             saveToStack(image);
            graphics2d.setColor(Color.WHITE);
            graphics2d.setStroke(new BasicStroke(20));
            graphics2d.drawLine(x, y, x1, y1);
            repaint();
            x = x1;
            y = y1;
            }
         else if(options==OptionsEnum.PEN){       
             saveToStack(image);
             graphics2d.setColor(colorPencil);
                graphics2d.setStroke(new BasicStroke(1));
                graphics2d.drawLine(x, y, x1, y1);
                repaint();
               x = x1;
               y = y1;
        }
        else if(options==OptionsEnum.BRUSH){
            saveToStack(image);
             graphics2d.setColor(colorPencil);
              graphics2d.setStroke(new BasicStroke(10));
                graphics2d.drawLine(x, y, x1, y1);
                repaint();
                 x = x1;
                 y = y1;
        }
     
        else if(options==OptionsEnum.PAINT){
            saveToStack(image);
             graphics2d.setColor(colorPencil);
            graphics2d.fillRect(0, 0,getWidth(), getHeight());
            repaint();
        }
         
         
         if(mouseDragged==true){
             
            if(figure==FigureEnum.LINE){
                saveToStack(image);
            graphics2d.setColor(colorPencil);
            graphics2d.drawLine(x, y,x1,y1);
               repaint();

           }
             else if(figure==FigureEnum.RECT){
                 saveToStack(image);
            graphics2d.setColor(colorPencil);
            graphics2d.drawRect(x,y,w,h);
               repaint();

           }
             else if(figure==FigureEnum.CIRCLE){
                 saveToStack(image);
            graphics2d.setColor(colorPencil);
            graphics2d.drawOval(x,y,Math.abs(x-x1),Math.abs(y-y1));
               repaint();
              }
             else if(figure==FigureEnum.ROUNDRECT){
                 saveToStack(image);
            graphics2d.setColor(colorPencil);
            graphics2d.drawRoundRect(x, y,w,h,20,20);
               repaint();
           }
         
         }
    
    }
    

}


