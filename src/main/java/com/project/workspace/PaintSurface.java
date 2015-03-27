
package com.project.workspace;

import com.project.algorithms.SizedStack;
import com.project.algorithms.FloodFill;
import java.awt.BasicStroke; 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JColorChooser;
import javax.swing.JScrollPane;

/**
 *
 * @author Karol
 */
public final class PaintSurface extends JScrollPane{

    private BufferedImage image = new BufferedImage(1500, 1200, BufferedImage.TYPE_INT_RGB);  
    private WritableRaster raster=null;
    private Image imageDrow;
    private int pixels[] = new int[3];
  
   
    private Graphics2D graphics2d;
    private  Color colorPencil;  
   
    private int x,y,x1,y1;
    
    private OptionsEnum options;
    private FigureEnum figure;
    
    boolean spr;
    boolean ima;
    
    private final SizedStack<Image> undoStack = new SizedStack<>(10);

    public PaintSurface() {  
      setSize(1500, 1200);
      colorPencil=new Color(0,0,0);
     
      setPreferredSize(new Dimension(getWidth(),getHeight()));
        
      addMouseListener(new MouseAdapter(){
       
                @Override
                public void mousePressed(MouseEvent e)
                {
                    saveToStack(image);
                    x=e.getX();
                    y=e.getY();
                     checkMouse(e);
                      repaint();                   
                }

            @Override
            public void mouseDragged(MouseEvent e) { 

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
                                   checkMousePencil(e);
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
    
    
    public void imageProcesses(ImageProcessEnum processEnum){
         saveToStack(image);
          raster = image.getRaster();
        double ww[]=new double[3];
        double hsv[];
    
        switch(processEnum)
        {
            case SEPIA :
            {
                 for(int i=0;i<raster.getWidth();i++)
                    {
                        for(int j=0;j<raster.getHeight();j++)
                        {

                            raster.getPixel(i, j, pixels);

                            ww[0] = (pixels[0] * 0.393 + pixels[1] * 0.769 + pixels[2] * 0.189 ) / 1.351;
                            ww[1] = (pixels[0] * 0.349 + pixels[1] * 0.686 + pixels[2] * 0.186 ) / 1.203;
                            ww[2] = (pixels[0] * 0.272 + pixels[1] * 0.534 + pixels[2] * 0.131 ) / 2.140;

                            raster.setPixel(i, j, ww);
                        }
                   }
            }break;
            case GRAY : 
            {
                
            
                    for(int i=0;i<raster.getWidth();i++)
                    {

                        for(int j=0;j<raster.getHeight();j++)
                        {

                            raster.getPixel(i, j, pixels);

                            ww[0] = 0.299*pixels[0]+0.587*pixels[1]+0.114*pixels[2];
                            ww[1] = 0.299*pixels[0]+0.587*pixels[1]+0.114*pixels[2];
                            ww[2] = 0.299*pixels[0]+0.587*pixels[1]+0.114*pixels[2];

                            raster.setPixel(i, j, ww);

                        }

                   }
            }break;
            case BLACKWHITE : 
            {
                boolean better = true;
                int ton = 100;
                
                
                Random r = new Random();
                double min=-(0.15*ton);
                double max=0.15*ton;
                int ton2=0;

                for(int i=0;i<raster.getWidth();i++)
                {

                     for(int j=0;j<raster.getHeight();j++)
                     {
                          raster.getPixel(i, j, pixels);
                          hsv=rgb2hsv(pixels[0], pixels[1], pixels[2]);

                          if(better){

                              ton2=ton+(int)(min + (int)(Math.random()  * ((max - min) + 1)));

                          }
                          else
                              ton2=ton;
                          if(hsv[2]>ton2)
                          {

                              ww=hsv2rgb(hsv[0], hsv[1], hsv[2]);
                              Arrays.fill(ww, 0);
                          }
                          else
                          {

                              ww=hsv2rgb(hsv[0], hsv[1], hsv[2]);
                              Arrays.fill(ww, 255);

                          }

                           raster.setPixel(i, j, ww);  


                     }

                }         
            }break;
                
            case BRIGHTNESS :
            {
                int jasnosc = 100;
                 
    
        for(int i=0;i<raster.getWidth();i++)
        {

            for(int j=0;j<raster.getHeight();j++)
            {

                raster.getPixel(i, j, pixels);
                hsv=rgb2hsv(pixels[0], pixels[1], pixels[2]);
                hsv[2]=hsv[2]+jasnosc>240?240:hsv[2]+jasnosc;

                ww=hsv2rgb(hsv[0], hsv[1], hsv[2]);
                raster.setPixel(i, j, ww);

            }

       }   
            }
        }
      
     image.setData(raster);
        repaint();
    }
    
       private double[] rgb2hsv(double red, double grn, double blu)
    {
        double hue, sat, val;
        double x, f, i;
        double result[] = new double[3];

        x = Math.min(Math.min(red, grn), blu);
        val = Math.max(Math.max(red, grn), blu);
        if (x == val){
            hue = 0;
            sat = 0;
        }
        else 
        {
            f = (red == x) ? grn-blu : ((grn == x) ? blu-red : red-grn);
            i = (red == x) ? 3 : ((grn == x) ? 5 : 1);
            hue = ((i-f/(val-x))*60)%360;
            sat = ((val-x)/val);
        }
        result[0] = hue;
        result[1] = sat;
        result[2] = val;
        return result;
    }
    
     private double[] hsv2rgb(double hue, double sat, double val) 
    {
        double red = 0, grn = 0, blu = 0;
        double i, f, p, q, t;
        double result[] = new double[3];
 
        if(val==0) 
        {
            red = 0;
            grn = 0;
            blu = 0;
        } else 
        {
            hue/=60;
            i = Math.floor(hue);
            f = hue-i;
            p = val*(1-sat);
            q = val*(1-(sat*f));
            t = val*(1-(sat*(1-f)));
            if (i==0) {red=val; grn=t; blu=p;}
            else if (i==1) {red=q; grn=val; blu=p;}
            else if (i==2) {red=p; grn=val; blu=t;}
            else if (i==3) {red=p; grn=q; blu=val;}
            else if (i==4) {red=t; grn=p; blu=val;}
            else if (i==5) {red=val; grn=p; blu=q;}
        }
        result[0] = red; 
        result[1] = grn;
        result[2] = blu;
        return result;
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
    
    public void checkMousePencil(MouseEvent e)
    {
          x1 = e.getX();
        y1 = e.getY();
        
         if(options==OptionsEnum.ERASER){
            graphics2d.setColor(Color.WHITE);
            graphics2d.setStroke(new BasicStroke(20));
            graphics2d.drawLine(x, y, x1, y1);
            repaint();
            x = x1;
            y = y1;
            }
         else if(options==OptionsEnum.PEN){       
             graphics2d.setColor(colorPencil);
                graphics2d.setStroke(new BasicStroke(1));
                graphics2d.drawLine(x, y, x1, y1);
                repaint();
               x = x1;
               y = y1;
        }
        else if(options==OptionsEnum.BRUSH){
             graphics2d.setColor(colorPencil);
              graphics2d.setStroke(new BasicStroke(10));
                graphics2d.drawLine(x, y, x1, y1);
                repaint();
                 x = x1;
                 y = y1;
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

        
     
        if(options==OptionsEnum.PAINT){
            raster = image.getRaster();
            raster.getPixel(x, y, pixels);
            
            int col = image.getRGB(x1, y1);
            
            Color color =new Color(col);
            
             new FloodFill().floodFill(image, new Point(x1, y1),color, colorPencil);

            repaint();
        }
                
            if(figure==FigureEnum.LINE){
            graphics2d.setColor(colorPencil);
            graphics2d.drawLine(x, y,x1,y1);
               repaint();

           }
             else if(figure==FigureEnum.RECT){
            graphics2d.setColor(colorPencil);
            graphics2d.drawRect(x,y,w,h);
               repaint();

           }
             else if(figure==FigureEnum.CIRCLE){
            graphics2d.setColor(colorPencil);
            graphics2d.drawOval(x,y,Math.abs(x-x1),Math.abs(y-y1));
               repaint();
              }
             else if(figure==FigureEnum.ROUNDRECT){
            graphics2d.setColor(colorPencil);
            graphics2d.drawRoundRect(x, y,w,h,20,20);
               repaint();
           }

    
    }
    

}


