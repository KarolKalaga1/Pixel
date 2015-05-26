
package com.project.workspace;

import com.project.enums.OptionsEnum;
import com.project.enums.ImageProcessEnum;
import com.project.enums.FigureEnum;
import com.project.algorithms.SizedStack;
import com.project.algorithms.FloodFill;
import com.project.filters.BlackWhite;
import com.project.filters.Brightness;
import com.project.slideshow.ShowInterface;


import com.project.tools.Filters;
import com.project.tools.Rotation3D;
import java.awt.BasicStroke; 
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JScrollPane;




/**
 *
 * @author Karol
 */
public final class PaintSurface extends JScrollPane{

    //zmienna przechowująca domysly rozmiar obrazu//
    private BufferedImage  image  = new BufferedImage(1500, 1200, BufferedImage.TYPE_3BYTE_BGR);  
   // private BufferedImage  imag   = new BufferedImage
    private WritableRaster raster;
    
    private Image         imageDrow;
    private Graphics2D    graphics2d;
    private Graphics      graphics;
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
    private ImageIcon iconLoupe;
    
    //ImageTransformer parentTransformer = new ImageTransformerLQ();
    
    private Point startPoint = new Point(0, 0);
    private Point rectLocale = new Point();
    private Dimension rectSize = new Dimension();
    private BufferedImage capture = null;
    private int zoom = 200;
    private BufferedImage raw;
    private BufferedImage screen;
    private Myszka beh;
    
    public PaintSurface() { 
        //
        beh = new Myszka();
        this.addMouseMotionListener(beh);
        this.addMouseWheelListener(beh);
        this.addMouseListener(beh);
        
        //Wczytywanie skórek//
        this.iconPen    = new ImageIcon(this.getClass().getResource("/pen.png"));
        this.iconBrush  = new ImageIcon(this.getClass().getResource("/brush.png"));
        this.iconPaint  = new ImageIcon(this.getClass().getResource("/paint.png"));
        this.iconEraser = new ImageIcon(this.getClass().getResource("/erasers.png"));    
        //Wartości inicjalizujące// 
        
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
       
      
        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
                saveToStack(getImage());
                x=e.getX();
                y=e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                checkMouse(e);
               
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
      //Akcje przewidzane z obsługą myszki MouseListener i MouseMotion//

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
                if(options == OptionsEnum.MAGNIFICATION)
                {
                Cursor hourglassCursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
                setCursor(hourglassCursor);
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
    
    //Pobranie rozmiaru narzedzi//
    public int getSizeTools() {
        return sizeTools;
    }
    //Ustawienie rozmiaru narzedzi//
    public void setSizeTools(int sizeTools) {
        this.sizeTools = sizeTools;
    }

    //poprawenie kopiowanie zdjecia bez (undo,redo); Przechowywanie orginalnego rozmiaru.
    private BufferedImage copyImage(Image img) {
        
    BufferedImage copyOfImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);
    Graphics g = copyOfImage.createGraphics();
    g.drawImage(img, 0, 0,copyOfImage.getWidth(), copyOfImage.getHeight(), null);
    return copyOfImage;
    }
    
    // zapisywanie na stosie obrazu przed zmianami
    private void saveToStack(Image img) {
    undoStack.push(copyImage(img));
    }
    
    private void setImage(Image img) {
        setImage((BufferedImage) img);
    setSize(getImage().getWidth(),getImage().getHeight());
    this.repaint();
    }
    
    //wybór Filtrów//
    public void imageProcesses(ImageProcessEnum processEnum){
        saveToStack(getImage());
        raster = getImage().getRaster();
        
        switch(processEnum)
        {
            //filtry konwolucyjne//          
             case SHARPEN: {
                int matrix[][] = {{-1, -1, -1}, {-1, 20, -1}, {-1, -1, -1}};
                raster = filters.convolutionalFilter(raster, matrix);
                getImage().setData(raster);
                this.repaint();
            }
            break;
            case SHARPEN_MORE: {
                int matrix[][] = {{-1, -1, -1}, {-1, 10, -1}, {-1, -1, -1}};
                raster = filters.convolutionalFilter(raster, matrix);
                getImage().setData(raster);
                this.repaint();
            }
            break;
            case BOX: {
                int matrix[][] = {{2, 1, 2}, {1, 0, 1}, {2, 1, 2}};
                raster = filters.convolutionalFilter(raster, matrix);
                getImage().setData(raster);
                this.repaint();
            }
            break;
            case GAUSSIAN: {
                int matrix[][] = {{1, 2, 1}, {2, 4, 2}, {1, 2, 1}};
                raster = filters.convolutionalFilter(raster, matrix);
                getImage().setData(raster);
                this.repaint();
            }
            break;
            case LAPLACE: {
                int matrix[][] = {{-1, -2, -1}, {-2, 6, -2}, {-1, -2, -1}};//DOPRACOWAĆ MACIERZ!!!
                raster = filters.convolutionalFilter(raster, matrix);
                getImage().setData(raster);
                this.repaint();
            }break;
            
            //klasyczne filtry//
            case SEPIA :
            {
                raster = filters.sepiaFilter(raster);
                getImage().setData(raster);
                this.repaint();
            }break;
            case GRAY : 
            {           
                raster = filters.grayFilter(raster);     
                getImage().setData(raster);
                this.repaint();
            }break;
            case BLACKWHITE : 
            {
                BlackWhite blackWhite = new BlackWhite(this, filters);
                blackWhite.setVisible(true);
                getImage().setData(raster);
            }break;
                
            case BRIGHTNESS :
            {
                Brightness brightness = new Brightness(this,filters);
                brightness.setVisible(true);
                getImage().setData(raster);
            }
        }
    }
    
    //przywrócenie poprzedniego stanu do tyłu
    public void undo() {
            if (undoStack.size() > 0) {
                redoStack.push(copyImage(getImage()));
                setImage(undoStack.pop());
            }
        }
    //przywrócenie stanu sprzed cofniecia undo   
    public void redo(){
            if(redoStack.size() > 0 ){
                saveToStack(getImage());
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
    
        if(getImage()!=null){
         imageDrow = getImage();
           }        
           graphics2d = (Graphics2D)imageDrow.getGraphics();
           graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
           //graphics2d.drawImage(image, 0, 0, this);
           g2.clearRect(0, 0, getWidth(), getHeight());
           g2.drawImage(getImage(), 0, 0, getImage().getWidth(),getImage().getHeight(), Color.DARK_GRAY, null);
           
           if(options==OptionsEnum.MAGNIFICATION)
           {
           paintResizeScreen(g2);
           }
    }
   
    public void paintResizeScreen(Graphics g2)
    {
            if (screen != null)                                                             //Powiekszenie
              {
                  int width2 = (int) (rectSize.width + rectSize.width * (zoom / 500d));
                  int height2 = (int) (rectSize.height + rectSize.height * (zoom / 500d));
                  int x2 = rectLocale.x - ((width2 - rectSize.width) / 2);
                  int y2 = rectLocale.y - ((height2 - rectSize.height) / 2);
                  Image scaledInstance = screen.getScaledInstance( width2, height2, Image.SCALE_AREA_AVERAGING);
                  g2.drawImage(scaledInstance, x2, y2, null);
                  g2.setColor(Color.RED);
                  g2.drawRect(x2, y2, width2, height2);
              }
        
//            else if(screen == null)
//             {
////                  int width2 = (int) (rectSize.width + rectSize.width * (zoom / 500d));
////                  int height2 = (int) (rectSize.height + rectSize.height * (zoom / 500d));
////                  int x2 = rectLocale.x - ((width2 - rectSize.width) / 2);
////                  int y2 = rectLocale.y - ((height2 - rectSize.height) / 2);
////                   g2.drawRect(x2, y2, width2, height2);
//                  //g2.drawRect((int)rectLocale.getX(), (int)rectLocale.getY(), rectSize.height, rectSize.width);
//             }
    }
  
    
    public BufferedImage getImage() {
        return image;
    }

    public void setImages(BufferedImage image) {
        saveToStack(this.getImage());
        this.setImage(image);
        //repaint();
    }
        
    //Ustawienie opcji z Panelu po prawej stronie,
    //sprawdzenie czy nie jest to zmiana koloru,
    //jeśli tak to wywołanie dialogu 
    
    public void setOption(OptionsEnum o){
        if(o == OptionsEnum.COLORCHOOSE)
        {
            check();
        }
        else{
        options = o;
        figure = FigureEnum.NONE;
        }
        
    }
 
    
    //Sprawdzenie jaka została wybrana figura w panelu głownym i rysowanie jej
    public void setFigure(FigureEnum f){
        figure = f;
        options = OptionsEnum.NONE;
    }
 
    public void check(){ 
            this.colorPencil = JColorChooser.showDialog(null, "Choose a color", Color.BLUE);
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
                this.repaint();
                x = x1;
                y = y1;
            }break;
            case PEN :
            {
                graphics2d.setColor(colorPencil);
                graphics2d.setStroke(new BasicStroke(sizeTools));
                graphics2d.drawLine(x, y, x1, y1);
                this.repaint();
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
                this.repaint();
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
            raster = getImage().getRaster();
            if(x1<=getImage().getWidth() &&y1<=getImage().getHeight()){
            raster.getPixel(x, y, pixels);
            
            int col = getImage().getRGB(x1, y1);
            
            Color color =new Color(col);
            
             new FloodFill().floodFill(getImage(), new Point(x1, y1),color, colorPencil);
            }

            this.repaint();
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
               this.repaint();   
            }break;
            case RECT :
            {
                graphics2d.setColor(colorPencil);
                graphics2d.setStroke(new BasicStroke(sizeTools));
                graphics2d.drawRect(x,y,w,h);
               this.repaint();         
            }break;
            case CIRCLE :
            {
                 graphics2d.setColor(colorPencil);
                 graphics2d.setStroke(new BasicStroke(sizeTools));
                 graphics2d.drawOval(x,y,Math.abs(x-x1),Math.abs(y-y1));
                 this.repaint();
            }break;
            case ROUNDRECT :
            {
                 graphics2d.setColor(colorPencil);
                 graphics2d.setStroke(new BasicStroke(sizeTools));
                 graphics2d.drawRoundRect(x, y,w,h,20,20);
                 this.repaint();  
            }break;
        }
        }
    }

    /**
     * @param image the image to set
     */
    
    public void setImage(BufferedImage image) {
        this.image = image;
    }
    
    
    public void zapiszMacierz() {
        
        BufferedImage bi = getImage();
        
        Rotation3D obracanie = new Rotation3D(bi);
        obracanie.setVisible(true);
        //obracanie.paint(0.0, 1.0);

        this.repaint();
    }
    
        public  void slideshow()
        {   
            ShowInterface slide = new ShowInterface();   
            slide.setVisible(true);
        }
    
        
        
         public void UstawZoomiCapture(int zom, BufferedImage captur) {
        zoom = zom;
        screen = captur;
    }

    public void UstawRozmiar(Point start, Point rect, Dimension siz) {
        rectLocale = rect;
        startPoint = start;
        rectSize = siz;
    }
        
          public class Myszka extends MouseAdapter {
  
        @Override
        public void mousePressed(MouseEvent e) {
                                                                                             //Punkt Poczatkowy
            startPoint = e.getPoint();
            rectSize = new Dimension();
            capture = null;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Point currentPoint = e.getPoint();                                               //Obecna pozycja myszy

            rectSize.width = Math.abs(currentPoint.x - startPoint.x);                        //Szerokośc i wys zaznaczonego prostokonta
            rectSize.height = Math.abs(currentPoint.y - startPoint.y);

                                                                                             //Pkt pocz zaznaczenia
            rectLocale.x = Math.min(currentPoint.x, startPoint.x);
            rectLocale.y = Math.min(currentPoint.y, startPoint.y);
                                                                                             //Przelamuj zrodlo jesli sie da (jest instacja JComponent)
           UstawZoomiCapture(zoom, capture);                                          //Wyślij konieczne informacje do panelu w celu odmalowania 
            UstawRozmiar(startPoint, rectLocale, rectSize); 
            if (e.getSource() instanceof JScrollPane) {
                ((JComponent) e.getSource()).repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
                                                                                              //Usuwanie zooma poprzez klikniecie mysza
            if (rectSize.width <= 0 || rectSize.height <= 0) {
                capture = null;
            } else {
                try {
                    raw = new Robot().createScreenCapture(new java.awt.Rectangle((int)getLocationOnScreen().getX(),(int)getLocationOnScreen().getY(),//getX()+80, getY()+55,//+ 55 //screen zaznaczonego obszaru //trzeba dodac pozycje okna
                           image.getWidth(),image.getHeight()));
                } catch (Exception ex) {
                }

                capture = raw.getSubimage(Math.max(0, rectLocale.x),                                   //pobranie screena do capture
                        Math.max(0, rectLocale.y), rectSize.width, rectSize.height);
            }
            UstawZoomiCapture(zoom, capture);                                                     //Ustawienie zmiennych koniecznych do odmalowania obszaru okna
            UstawRozmiar(startPoint, rectLocale, rectSize);
            if (e.getSource() instanceof JScrollPane) {
                ((JComponent) e.getSource()).repaint();
            }
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            zoom = Math.min(1000, Math.max(0, zoom + e.getUnitsToScroll() * 10));                        //Obsługa rolki myszy (zbliżanie i oddalanie)
            UstawZoomiCapture(zoom, capture);
            UstawRozmiar(startPoint, rectLocale, rectSize);
            if (e.getSource() instanceof JScrollPane) {
                ((JComponent) e.getSource()).repaint();
            }
        }
    }
    }



    


