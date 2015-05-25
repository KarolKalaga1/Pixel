
package com.project.tools;

import com.project.algorithms.ImageTransformer;
import com.project.workspace.PaintSurface;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.GraphiteAquaSkin;

/**
 *
 * @author Karol
 */
public class ChangeImageSize extends javax.swing.JFrame {

    private final PaintSurface paintSurface;
    private final ImageTransformer imageTransformer;
    public ChangeImageSize(PaintSurface paintSurface, ImageTransformer parentTransformer) {
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
//        SubstanceLookAndFeel.setSkin(new GraphiteAquaSkin());//new GraphiteAquaSkin()//new GraphiteGlassSkin()//new GraphiteSkin() //new TwilightSkin()
//        SubstanceLookAndFeel.setSkin("org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel");
        this.paintSurface     = paintSurface;
        this.imageTransformer = parentTransformer;
        setTitle("Change Image Size");
        setLocation(300, 250);
       
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Set percent size : ");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loupe.png"))); // NOI18N

        jSpinner1.setName(""); // NOI18N
        jSpinner1.setPreferredSize(new java.awt.Dimension(65, 28));

        jButton1.setText("set");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      
       
        if(Integer.valueOf(jSpinner1.getValue().toString())<=100 && Integer.valueOf(jSpinner1.getValue().toString())>=1)
        {
        int size = Integer.valueOf(jSpinner1.getValue().toString());
            BufferedImage im = imageTransformer.transform(paintSurface.getImage(),(0.01*size),0);
            if(im.getWidth()<200 ||im.getHeight()<200)
            {
                JOptionPane.showMessageDialog(null,"You can't set smaller image", "Please verify",JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }else{
            int x=im.getWidth();
            int y=im.getHeight();
            paintSurface.setPreferredSize(new Dimension(x-20, y-50));
            paintSurface.setImages(im);
            }

            dispose();
        }else
        {
            JOptionPane.showMessageDialog(null,"Please set value in between 1-100", "Please verify",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

   
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSpinner jSpinner1;
    // End of variables declaration//GEN-END:variables
}
