
package com.project.tools;

import com.project.enums.ImageProcessEnum;
import com.project.workspace.PaintSurface;
import java.awt.Graphics;
import java.awt.image.WritableRaster;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;

/**
 *
 * @author Karol
 */

public class imagePanel extends JPanel{

    private Filters         filters;
    private WritableRaster  raster;
    private BufferedImage   smallImage  = new BufferedImage(300, 300,BufferedImage.TYPE_INT_RGB);  
    
    
    public imagePanel(PaintSurface paintSurface,Filters filters,BufferedImage smallImage) {
     
        this.smallImage   = smallImage;
        this.filters      = filters;
        this.raster       = null;
        this.raster       = smallImage.getRaster();
        
        repaint();
    }

    @Override
    public void paint(Graphics g) {
           g.drawImage(smallImage, 0, 0, null);
          
    }

    public void changes(BufferedImage smallImage,int zmiana,ImageProcessEnum imageProces)
    {  
        this.smallImage = smallImage;
        
        switch(imageProces)
        {
            case BRIGHTNESS:
            {
                 this.raster = filters.setBrightnessFilter(smallImage.getRaster(), zmiana);
            }break;
            case BLACKWHITE:
            {
                 this.raster = filters.setblackWhiteFilter(smallImage.getRaster(), zmiana);
            }break;
        }
        //this.smallImage.setData(raster);
        repaint();
    }

}

