
package com.project.tools;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.GraphiteAquaSkin;

/**
 *
 * @author Robert
 */
public class Rotation3D extends JFrame {

    BufferedImage bi;
    JButton buttonSave;
    JSlider sliderX;
    JSlider sliderY;
    RotationAction rotationAction = new RotationAction();
    //private Rotation3DPanel rotation3DPanel;
    JPanel imagePanel;
        Canvas im;
    public Rotation3D(BufferedImage bi) throws HeadlessException {
       // rotation3DPanel = new Rotation3DPanel();
        this.bi = bi;
        
//        setUndecorated(true);
//        getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
//        SubstanceLookAndFeel.setSkin(new GraphiteAquaSkin());//new GraphiteAquaSkin()//new GraphiteGlassSkin()//new GraphiteSkin() //new TwilightSkin()
//        SubstanceLookAndFeel.setSkin("org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel");
//        
        setTitle("Rotation 3D");
        setLayout(new BorderLayout());
        setSize(500, 500);
       
        setResizable(false);
        
        
           imagePanel = new JPanel();
        imagePanel.setVisible(true);
        
        imagePanel.setSize(100, 100);
        
        buttonSave   = new JButton("Save");
        sliderX = new JSlider(-10, 10, 0);
        sliderY = new JSlider(JSlider.VERTICAL, -10, 10, 0);
        
        buttonSave.addActionListener(rotationAction);
        sliderX.addChangeListener(rotationAction);
        sliderY.addChangeListener(rotationAction);
        
        add(imagePanel,BorderLayout.CENTER);
        add(sliderX,BorderLayout.NORTH);
        add(sliderY,BorderLayout.EAST);
        add(buttonSave,BorderLayout.SOUTH);
        
        paint(0.4, 0.75);
       // init();
    }
    
//    public void init(){
//        imagePanel = new JPanel();
//        imagePanel.setVisible(true);
//        
//        imagePanel.setSize(100, 100);
//        
//        buttonSave   = new JButton("Save");
//        sliderX = new JSlider(-10, 10, 0);
//        sliderY = new JSlider(JSlider.VERTICAL, -10, 10, 0);
//        
//        buttonSave.addActionListener(rotationAction);
//        sliderX.addChangeListener(rotationAction);
//        sliderY.addChangeListener(rotationAction);
//        
//        add(imagePanel,BorderLayout.CENTER);
//        add(sliderX,BorderLayout.NORTH);
//        add(sliderY,BorderLayout.EAST);
//        add(buttonSave,BorderLayout.SOUTH);
//        
//        paint(0.0, 1.0);
//    }     
    
    public class RotationAction implements ActionListener,ChangeListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == buttonSave)
            {
                 setVisible(true);
            }
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            if(e.getSource() == sliderX)
            {
               paint(sliderX.getValue(),sliderY.getValue());
            }
            
            if(e.getSource() == sliderY)
            {
               paint(sliderX.getValue(), sliderX.getValue());
            }
        }
    }
     
    public  void paint(double angleX, double angleY){
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas = new Canvas3D(config);
        //imagePanel.
                add("Center", canvas);

        TransformGroup tg_content = getScene(bi);
        
        Transform3D rotation = new Transform3D();
        Transform3D rotationX = new Transform3D();
        Transform3D rotationY = new Transform3D();
        
        Transform3D globalRotation = new Transform3D();
        
        rotationY.rotX(angleX);
        rotationX.rotY(angleY);
        
        globalRotation.mul(rotationX, rotationY);
        
        tg_content.setTransform(globalRotation);
        
        BranchGroup content = new BranchGroup();

        content.addChild(tg_content);
        content.compile();

        SimpleUniverse universe = new SimpleUniverse(canvas);
        
        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(content);
     
    }

    public TransformGroup getScene(BufferedImage bi) {
        TransformGroup group = new TransformGroup();

        Appearance app = new Appearance();
        Texture tex = new TextureLoader(bi, this).getTexture();
        app.setTexture(tex);
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        app.setTextureAttributes(texAttr);

        // Create textured cube and add it to the scene graph.
        Box textureCube = new Box(0.4f, 0.4f, 0.4f,
                Box.GENERATE_TEXTURE_COORDS, app);
        group.addChild(textureCube);

        Transform3D yAxis = new Transform3D();
        Alpha rotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0,
                4000, 0, 0, 0, 0, 0);

        RotationInterpolator rotator = new RotationInterpolator(rotationAlpha,
                group, yAxis, 0.0f, (float) Math.PI * 2.0f);
        
//        //zakomentowanie tych 3 linijek usuwa błąd ale nie wiem czy to poprawnie wpływa na obracanie
//        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
//                100.0);
//        rotator.setSchedulingBounds(bounds);
//        //
        group.addChild(rotator);

        return group;
    }
}
