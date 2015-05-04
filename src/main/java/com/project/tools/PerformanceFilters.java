
package com.project.tools;

import com.project.algorithms.ImageTransformerMQ;
import com.project.workspace.PaintSurface;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSlider;

/**
 *
 * @author Karol
 */
public class PerformanceFilters extends JFrame{
    
    protected PaintSurface        paintSurface;
    protected Filters             filters;
    protected imagePanel          imaPanel;
    protected JButton             buttonAcctept;
    protected JSlider             changeValue;
   
    protected BufferedImage       smallImage  = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB); 
    protected ImageTransformerMQ  imageTransformerMQ; 

    public PerformanceFilters(PaintSurface paintSurface,Filters filters)  {
        imageTransformerMQ = new ImageTransformerMQ();
        this.paintSurface = paintSurface;
        this.filters      = filters;
    }
    
}
