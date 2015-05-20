
package com.project.workspace;

import com.project.enums.OptionsEnum;
import com.project.enums.ImageProcessEnum;
import com.project.enums.FigureEnum;
import com.project.algorithms.SizedStack;
import com.project.algorithms.FloodFill;
import com.project.filters.BlackWhite;
import com.project.filters.Brightness;
import com.project.main.Paint;
import com.project.tools.Filters;
import com.project.tools.Rotation3D;
import com.sun.j3d.utils.scenegraph.io.state.javax.media.j3d.Text3DState;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.BasicStroke; 
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.*;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.TexCoord3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4f;
import sun.awt.image.WritableRasterNative;



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
    
    //ImageTransformer parentTransformer = new ImageTransformerLQ();
    
    public PaintSurface() { 
        
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
           //g2.drawImage(image, 0, 0,image.getWidth(),image.getHeight(),null);
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
    
//   public void zapiszMacierz() {
//        WritableRaster rast = getImage().getRaster();
//        WritableRaster newRast = getImage().getRaster().createCompatibleWritableRaster(rast.getWidth() + 100, rast.getHeight());
//
//        for (int i = 0; i < newRast.getWidth(); i++) {
//            for (int j = 0; j < newRast.getHeight(); j++) {
//                pixels[0] = 255;
//                pixels[1] = 255;
//                pixels[2] = 255;
//
//                newRast.setPixel(i, j, pixels);
//            }
//        }
//
//        for (int j = 0; j < rast.getHeight(); j++) {
//            int max = 0;
//
//            for (int l = 0; l < 100 - (int)(j * (100.00 / rast.getHeight())); ++l) {
//                pixels[0] = 255;
//                pixels[1] = 255;
//                pixels[2] = 255;
//
//                newRast.setPixel(l, j, pixels);
//                
//                max = l;
//            }
//            
//            for (int i = 0; i < rast.getWidth() - max * 2; i++) {
//
//                //rast.getPixel(i, j, pixels);
//
//                int newX = 0,
//                        newY = 0,
//                        newZ = 0;
//
//                newX = i;
//                newY = (int) (j * Math.cos(30 * Math.PI / 180));
//                newZ = (int) (j * Math.sin(30 * Math.PI / 180));
//                
//                rast.getPixel((int)((i + max) * (rast.getWidth() / (rast.getWidth() - max * 2))), j, pixels);
//                newRast.setPixel(newX + max, newY, pixels);
//            }
//        }
//        
//        BufferedImage bufferedImage = new BufferedImage(newRast.getWidth(), newRast.getHeight(), BufferedImage.TYPE_INT_RGB);
//        setImage(bufferedImage);
//        
//        getImage().setData(newRast);
//        this.repaint();
//    }
   
   
  
 public BranchGroup createSceneGraph(WritableRaster raster) {

        // Create the root of the branch graph
        BranchGroup objRoot = new BranchGroup();

        // Create a transform group to center the object
        TransformGroup objOrient = new TransformGroup();
        Transform3D orient = new Transform3D();
        orient.set(new Vector3d(-0.25, -0.0, -0.0), 0.5);
        objOrient.setTransform(orient);
        objRoot.addChild(objOrient);

        // Create a transform group node and initialize it to the identity.
        // Enable the TRANSFORM_WRITE capability so that our behavior code
        // can modify it at runtime.  Add it to the root of the subgraph.
        //
        TransformGroup objTrans = new TransformGroup();

        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objOrient.addChild(objTrans);

        //
        // Create a 3D texture
        //
        int width = raster.getWidth();
        int height = raster.getHeight();
        int depth = 1;

        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB); 
        int[] nBits = {8, 8, 8}; 
        ComponentColorModel colorModel = new ComponentColorModel(cs, nBits, 
                false, false, Transparency.OPAQUE, 0); 
        //WritableRaster raster = colorModel.createCompatibleWritableRaster(width, height); 
 
        BufferedImage bImage =                                   
            new BufferedImage(colorModel, raster, false, null); 
 
        byte[] byteData = ((DataBufferByte)raster.getDataBuffer()).getData(); 
 
        ImageComponent3D pArray = new ImageComponent3D(
                ImageComponent.FORMAT_RGB, width, height, depth);

        // set up a volume with the color intensities corresponding to the 
        // s,t,r values: s = red, t = green, r == blue
        for (int k = 0; k < depth; k++) {
            for (int j = 0;j < height;j++){
                for (int i = 0;i < width;i++){ 

                    double s = (double) i / width;
                    double t = (double) j / height;
                    double r = (double) k / depth;

                    // Note: Java3D flips the s coordinate to match the 2D 
                    // image sematics, which put s=0 at the "top" of the image.
                    // Since most 3D data puts the origin at the lower left 
                    // corner, we flip the "s" coordinate
                    s = 1.0 - s;
                    
                    int index = ((j * width) + i) * 3; 
                    byteData[index] =   (byte)(255 * s);
                    byteData[index+1] = (byte)(255 * t);
                    byteData[index+2] = (byte)(255 * r);
                } 
            } 
            pArray.set(k, bImage);
        }
 

        Texture3D tex = new Texture3D(Texture.BASE_LEVEL,
                                           Texture.RGB, width, height, depth);
        tex.setImage(0, pArray);
        tex.setEnable(true);
        tex.setMinFilter(Texture.BASE_LEVEL_LINEAR);
        tex.setMagFilter(Texture.BASE_LEVEL_LINEAR);
        tex.setBoundaryModeS(Texture.CLAMP);
        tex.setBoundaryModeT(Texture.CLAMP);
        tex.setBoundaryModeR(Texture.CLAMP);

        // turn off face culling and lighting so we an see just the texture
        PolygonAttributes p = new PolygonAttributes();
        p.setCullFace(PolygonAttributes.CULL_NONE);
        Material m = new Material();
        m.setLightingEnable(false);

        // Create two squares, one with texture coordinates, and the 
        // other with generated texture coordinates

        Point3f[]       coords = new Point3f[4];
        coords[0] = new Point3f(0.0f, 0.0f, 0.0f);
        coords[1] = new Point3f(1.0f, 1.0f, 0.0f);
        coords[2] = new Point3f(1.0f, 1.0f, 1.0f);
        coords[3] = new Point3f(0.0f, 0.0f, 1.0f);

        // Note that the texture coordinates match the coords: s=x, t=y, r=z
        /*Point3f[]       texCoords = new Point3f[4];*/
        TexCoord3f[]       texCoords = new TexCoord3f[4];
        texCoords[0] = new  TexCoord3f( coords[0]);
        texCoords[1] = new  TexCoord3f( coords[1]);
        texCoords[2] = new  TexCoord3f( coords[2]);
        texCoords[3] = new  TexCoord3f( coords[3]);

        QuadArray coordsSquare = new QuadArray(4, 
                QuadArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_3 );
        coordsSquare.setCoordinates(0, coords);
        coordsSquare.setTextureCoordinates(0, 0, texCoords);

        // create an appearance with the texture but no tex coord gen
        Appearance coordsAppearance = new Appearance();
        coordsAppearance.setTexture(tex);
        coordsAppearance.setMaterial(m);
        coordsAppearance.setPolygonAttributes(p);

        Shape3D coordsShape = new Shape3D(coordsSquare, coordsAppearance); 

        objTrans.addChild(coordsShape);

        // Now the square with generated tex coords.  This crosses the first
        // square, but it has texture coordinates which match up with the
        // the first quad

        // First the shape...
        Point3f[]       genCoords = new Point3f[4];
        genCoords[0] = new Point3f(1.0f, 0.0f, 0.0f);
        genCoords[1] = new Point3f(1.0f, 0.0f, 1.0f);
        genCoords[2] = new Point3f(0.0f, 1.0f, 1.0f);
        genCoords[3] = new Point3f(0.0f, 1.0f, 0.0f);

        QuadArray genSquare = new QuadArray(4, QuadArray.COORDINATES);
        genSquare.setCoordinates(0, genCoords);

        // setup the tex coord gen.  This is just s = x, t = y, r = z
        TexCoordGeneration tg = new TexCoordGeneration();
        tg.setFormat(TexCoordGeneration.TEXTURE_COORDINATE_3);
        tg.setPlaneS(new Vector4f(1.0f, 0.0f, 0.0f, 0.0f));
        tg.setPlaneT(new Vector4f(0.0f, 1.0f, 0.0f, 0.0f));
        tg.setPlaneR(new Vector4f(0.0f, 0.0f, 1.0f, 0.0f));

        // create an appearance with the texture and tex coord gen
        Appearance genAppearance = new Appearance();
        genAppearance.setTexture(tex);
        genAppearance.setTexCoordGeneration(tg);
        genAppearance.setMaterial(m);
        genAppearance.setPolygonAttributes(p);

        Shape3D genShape = new Shape3D(genSquare, genAppearance); 

        objTrans.addChild(genShape);

        BoundingSphere bounds =
            new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);

        // Create a new Behavior object that will perform the desired
        // operation on the specified transform object and add it into the
        // scene graph.
        //
        Transform3D yAxis = new Transform3D();
        Alpha rotorAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE,
                                     0, 0,
                                     4000, 0, 0,
                                     0, 0, 0);
        RotationInterpolator rotator =
            new RotationInterpolator(rotorAlpha,
                                     objTrans,
                                     yAxis,
                                     0.0f, (float) Math.PI*2.0f);
        rotator.setSchedulingBounds(bounds);
        objTrans.addChild(rotator);

        // Have Java 3D perform optimizations on this scene graph.
        objRoot.compile();

        return objRoot;
    }
    
    public BufferedImage Texture3DTest(WritableRaster raster) {
     //   Canvas3D c = new Canvas3D(ra);
	GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
	Canvas3D c = new Canvas3D(gc,true);
        
        //add("Center", c);

        // Create a simple scene and attach it to the virtual universe
        BranchGroup scene = createSceneGraph(raster);
        SimpleUniverse u = new SimpleUniverse(c);

        // This will move the ViewPlatform back a bit so the
        // objects in the scene can be viewed.
        u.getViewingPlatform().setNominalViewingTransform();

        
        u.addBranchGraph(scene);
        
       
        
        J3DGraphics2D bImage =  u.getCanvas().getGraphics2D();//getOffScreenBuffer().getImage();
        
        return null;   
    }
    
    public void zapiszMacierz() {
        
        BufferedImage bi = getImage();
        
        Rotation3D obracanie = new Rotation3D(bi);
        
       // BufferedImage bufferedImage = Texture3DTest(rast);
        
               //Frame frame = new JFrame(, 256, 256);
 
//                try {
//                   String fileName = Paint.paintStart.getSaveFileName();
//                    if (fileName != null) {
//                        BufferedImage images = bufferedImage;
//                        ImageIO.write(images, "BMP", new File(fileName));
//                    }
//                } catch (IOException ex) {
//                    JOptionPane.showMessageDialog(null, "Save file Exception " + ex.getMessage(), "Save exception", JOptionPane.INFORMATION_MESSAGE);
//                }
//        //setImage(bufferedImage);
        
        this.repaint();
    }
    
    }



    


