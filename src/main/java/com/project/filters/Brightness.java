
package com.project.filters;

import com.project.enums.ImageProcessEnum;
import com.project.tools.PerformanceFilters;
import com.project.tools.Filters;
import com.project.tools.imagePanel;
import com.project.workspace.PaintSurface;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JRootPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.GraphiteAquaSkin;

/**
 *
 * @author Karol
 */
public class Brightness extends PerformanceFilters{

    private final FiltersAction       filtersAction = new FiltersAction();

    
    private int jasnoscPlus;
    
    public Brightness(PaintSurface paintSurface,Filters filters){
        super(paintSurface, filters);
       
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
//        SubstanceLookAndFeel.setSkin(new GraphiteAquaSkin());//new GraphiteAquaSkin()//new GraphiteGlassSkin()//new GraphiteSkin() //new TwilightSkin()
//        SubstanceLookAndFeel.setSkin("org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel");
        
        setTitle("Brightness");
        setLayout(new BorderLayout());
        jasnoscPlus  = 0;
        
    if(paintSurface.getImage().getWidth()>800 || paintSurface.getImage().getHeight()>600)
        {
            smallImage         = imageTransformerMQ.transform(paintSurface.getImage(),0.18, 0);  
        }else{
            smallImage         = imageTransformerMQ.transform(paintSurface.getImage(),0.99, 0); 
        }
            
           
        setSize(smallImage.getWidth()+5,smallImage.getHeight()+40);
        setLocation(200, 50);
        setResizable(false);
        
        init();
    }
    
    public void init()
    {
        imaPanel        = new imagePanel(paintSurface,filters,smallImage);
        buttonAcctept   = new JButton("Accept");
       
        changeValue     = new JSlider(0,200);
        changeValue.setValue(0);
        
        buttonAcctept.addActionListener(filtersAction);
        changeValue.addChangeListener(filtersAction);
        

        add(imaPanel,BorderLayout.CENTER);
        add(changeValue,BorderLayout.NORTH);
        add(buttonAcctept,BorderLayout.SOUTH);
    }
    
    public class FiltersAction implements ActionListener,ChangeListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == buttonAcctept)
            {
                paintSurface.setRaster(filters.setBrightnessFilter(paintSurface.getRaster(), jasnoscPlus));
               // paintSurface.repaint();
                dispose();
            }
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            if(e.getSource() == changeValue)
            {
               jasnoscPlus  = changeValue.getValue();
             if(paintSurface.getImage().getWidth()>800 || paintSurface.getImage().getHeight()>600)
                {
                    smallImage         = imageTransformerMQ.transform(paintSurface.getImage(),0.18, 0);  
                }else{
                    smallImage         = imageTransformerMQ.transform(paintSurface.getImage(),0.99, 0); 
                }
               imaPanel.changes(smallImage,jasnoscPlus,ImageProcessEnum.BRIGHTNESS);
            }
        }
    }
 
}
