
package com.project.workspace;

import com.project.enums.OptionsEnum;
import com.project.enums.ImageProcessEnum;
import com.project.enums.FigureEnum;
import com.project.algorithms.SizedStack;
import com.project.algorithms.FloodFill;
import com.project.filters.BlackWhite;
import com.project.filters.Brightness;
import com.project.tools.Filters;
import java.awt.BasicStroke; 
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JScrollPane;

/**
 *
 * @author Karol
 */
public final class PaintSurface extends JScrollPane{

    //zmienna przechowująca domysly rozmiar obrazu//
    private BufferedImage  image  = new BufferedImage(1500, 1200, BufferedImage.TYPE_INT_RGB);  
    private WritableRaster raster;
    
    private Image         imageDrow;
    private Graphics2D    graphics2d;
    private Color         colorPencil;  
    private OptionsEnum   options;
    private FigureEnum    figure;
    private final Filters filters;
    
    private final int pixels[];
    private int sizeTools;
    private int x,y,x1,y1;
    boolean spr,ima;
    private  SizedStack<Image> undoStack;
    private  SizedStack<Image> redoStack;
    
    private ImageIcon iconPen;
    private ImageIcon iconBrush;
    private ImageIcon iconPaint;
    private ImageIcon iconEraser;
    
    public PaintSurface() { 
        
        //Wczytywanie skórek//
        this.iconPen    = new ImageIcon(this.getClass().getResource("/pen.png"));
        this.iconBrush  = new ImageIcon(this.getClass().getResource("/brush.png"));
        this.iconPaint  = new ImageIcon(this.getClass().getResource("/paint.png"));
        this.iconEraser = new ImageIcon(this.getClass().getResource("/erasers.png"));
        
        // wartości inicjalizujące// 
        this.filters   = new Filters();
        this.redoStack = new SizedStack<Image>(3);
        this.undoStack = new SizedStack<Image>(3);
        this.pixels    = new int[3];
        this.raster    = null;
          
        this.sizeTools = 1;
        this.setSize(1500, 1200);
        this.colorPencil=new Color(0,0,0);
     
      //ustawienie rozmiaru okna po którym mozna rysowac//
      setPreferredSize(new Dimension(getWidth(),getHeight()));
       
      //Akcje przewidzane z obsługą myszki MouseListener i MouseMotion//
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
            }});
      //Stop MouseListener//

        addMouseMotionListener(new MouseMotionAdapter(){
            
            @Override
            public void mouseDragged(MouseEvent e){
                    if(getGraphics() != null)
                    {
                       checkMousePencil(e);
                    }
                }  

            @Override
            public void mouseMoved(MouseEvent e) {
                if(figure==FigureEnum.LINE || figure == FigureEnum.RECT || figure == FigureEnum.ROUNDRECT || figure == FigureEnum.CIRCLE )
                {
                Cursor hourglassCursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
                setCursor(hourglassCursor);
                }
                if(options ==OptionsEnum.PEN)
                {
                  setCursor(Toolkit.getDefaultToolkit().createCustomCursor(iconPen.getImage(),new Point(4,26),"custom cursor"));
                }
                if(options == OptionsEnum.BRUSH)
                {
                setCursor(Toolkit.getDefaultToolkit().createCustomCursor(iconBrush.getImage(),new Point(4,26),"custom cursor"));
                }
                 if(options == OptionsEnum.PAINT)
                {
                setCursor(Toolkit.getDefaultToolkit().createCustomCursor(iconPaint.getImage(),new Point(4,26),"custom cursor"));
                }
                  if(options == OptionsEnum.ERASER)
                {
                setCursor(Toolkit.getDefaultToolkit().createCustomCursor(iconEraser.getImage(),new Point(7,24),"custom cursor"));
                }
            }});
        //Stop MouseMotion//
    }

    //pobranie Rastra konieczne dla filtórw//
    public WritableRaster getRaster() {
        return raster;
    }

    public void setRaster(WritableRaster raster) {
        this.raster = raster;
    }

    //akcja odmalowania panelu//
  
    public void repaints()
    {
        repaint();
    }
    
    //Pobranie rozmiaru narzedzia//
    public int getSizeTools() {
        return sizeTools;
    }
    //Ustawienie rozmiaru narzedzia//
    public void setSizeTools(int sizeTools) {
        this.sizeTools = sizeTools;
    }

    private BufferedImage copyImage(Image img) {
    BufferedImage copyOfImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
    Graphics g = copyOfImage.createGraphics();
    g.drawImage(img, 0, 0,getWidth(), getHeight(), null);
    return copyOfImage;
    }
    
    // zapisywanie na stosie obrazu przed zmianami
    private void saveToStack(Image img) {
    undoStack.push(copyImage(img));
    }
    
    private void setImage(Image img) {  
    image = (BufferedImage) img;
    setSize(image.getWidth(),image.getHeight());
    repaint();
    }
    
    //wybór Filtrów//
    public void imageProcesses(ImageProcessEnum processEnum){
        saveToStack(image);
        raster = image.getRaster();
        
        switch(processEnum)
        {
            case SEPIA :
            {
                    raster = filters.sepiaFilter(raster);
            }break;
            case GRAY : 
            {
             
                    raster = filters.grayFilter(raster);       
            }break;
            case BLACKWHITE : 
            {
                BlackWhite blackWhite = new BlackWhite(this, filters);
                blackWhite.setVisible(true);
//                    raster = filters.blackWhiteFilter(raster);
            }break;
                
            case BRIGHTNESS :
            {
                Brightness brightness = new Brightness(this,filters);
                brightness.setVisible(true);
            }
        }
        image.setData(raster);
        repaint();
    }
    
    //przywrócenie poprzedniego stanu do tyłu
    public void undo() {
            if (undoStack.size() > 0) {
                redoStack.push(copyImage(image));
                setImage(undoStack.pop());
            }
        }
    //przywrócenie stanu sprzed cofniecia undo   
    public void redo(){
            if(redoStack.size() > 0 ){
                saveToStack(image);
                setImage(redoStack.pop());
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
    
    //Obracanie  za pomocą wbudowanej transformacji w java
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
    
    //Ustawienie opcji z Panelu po prawej stronie,
    //sprawdzenie czy nie jest to zmiana koloru,
    //jeśli tak to wywołanie dialogu 
    public void setOption(OptionsEnum o){
        options = o;
        figure = FigureEnum.NONE;
        check();
    }
    
    //Sprawdzenie jaka została wybrana figura w panelu głownym i rysowanie jej
    public void setFigure(FigureEnum f){
        figure = f;
        options = OptionsEnum.NONE;
    }
 
    public void check(){
    if(options==OptionsEnum.COLORCHOOSE){    
            this.colorPencil = JColorChooser.showDialog(null, "Choose a color", Color.BLUE);
        }
    }
    //Ustawianie kolorów
    public void setColor(Color color)
    {
        this.colorPencil= color;
    }
    
    //Sprawdzenie akcji przedmiotów którymi możemy pisac po panelu
    public void checkMousePencil(MouseEvent e)
    {
        x1 = e.getX();
        y1 = e.getY();
        
        if(options !=null)
        {
        switch(options)
        {
            case ERASER :
            {
                 graphics2d.setColor(Color.WHITE);
                if(sizeTools==1)
                {
                graphics2d.setStroke(new BasicStroke(3));    
                }
                else
                {
                graphics2d.setStroke(new BasicStroke(sizeTools));
                }
                graphics2d.drawLine(x, y, x1, y1);
                repaint();
                x = x1;
                y = y1;
            }break;
            case PEN :
            {
                graphics2d.setColor(colorPencil);
                graphics2d.setStroke(new BasicStroke(sizeTools));
                graphics2d.drawLine(x, y, x1, y1);
                repaint();
                x = x1;
                y = y1; 
            }break;
            case BRUSH :
            {
                graphics2d.setColor(colorPencil);
                if(sizeTools==1)
                {
                graphics2d.setStroke(new BasicStroke(3));    
                }
                else
                {
                graphics2d.setStroke(new BasicStroke(sizeTools));
                }
                graphics2d.drawLine(x, y, x1, y1);
                repaint();
                 x = x1;
                 y = y1;
            }break;
        }
        }
    }

    //Sprawdzenie akcji myszy dla wypełniania obszaru kolorem 
    //oraz dla rysowania figur
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
        if(figure!=null)
        {
                
        switch(figure)
        {
            case LINE :
            {
                graphics2d.setColor(colorPencil);
                graphics2d.setStroke(new BasicStroke(sizeTools));
                graphics2d.drawLine(x, y,x1,y1);
                repaint();   
            }break;
            case RECT :
            {
                graphics2d.setColor(colorPencil);
                graphics2d.setStroke(new BasicStroke(sizeTools));
                graphics2d.drawRect(x,y,w,h);
                repaint();         
            }break;
            case CIRCLE :
            {
                 graphics2d.setColor(colorPencil);
                 graphics2d.setStroke(new BasicStroke(sizeTools));
                 graphics2d.drawOval(x,y,Math.abs(x-x1),Math.abs(y-y1));
                 repaint();
            }break;
            case ROUNDRECT :
            {
                 graphics2d.setColor(colorPencil);
                 graphics2d.setStroke(new BasicStroke(sizeTools));
                 graphics2d.drawRoundRect(x, y,w,h,20,20);
                 repaint();  
            }break;
        }
        }
    }
    
}

