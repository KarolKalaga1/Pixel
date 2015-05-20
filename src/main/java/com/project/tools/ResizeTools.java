
package com.project.tools;

import com.project.workspace.PaintSurface;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.GraphiteAquaSkin;

/**
 *
 * @author Karol
 */
public class ResizeTools extends JFrame {

    BufferedImage image = null;
    private final PaintSurface paintSurface;
    public ResizeTools(PaintSurface paintSurface) {
         setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        SubstanceLookAndFeel.setSkin(new GraphiteAquaSkin());//new GraphiteAquaSkin()//new GraphiteGlassSkin()//new GraphiteSkin() //new TwilightSkin()
        SubstanceLookAndFeel.setSkin("org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel");
       this.paintSurface = paintSurface;
        initComponents();
          
        setLocation(300, 250);
        setTitle("Resize Draw Tools");    
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        size1 = new javax.swing.JLabel();
        size3 = new javax.swing.JLabel();
        size5 = new javax.swing.JLabel();
        size7 = new javax.swing.JLabel();
        size10 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        saveSize = new javax.swing.JButton();
        choiseSize = new javax.swing.JSpinner();

        setResizable(false);

        size1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/size1.png"))); // NOI18N
        size1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        size1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                size1MouseReleased(evt);
            }
        });

        size3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/size3.png"))); // NOI18N
        size3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        size3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                size3MouseReleased(evt);
            }
        });

        size5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/size5.png"))); // NOI18N
        size5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        size5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                size5MouseReleased(evt);
            }
        });

        size7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/size7.png"))); // NOI18N
        size7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        size7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                size7MouseReleased(evt);
            }
        });

        size10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/size10.png"))); // NOI18N
        size10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        size10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                size10MouseReleased(evt);
            }
        });

        jLabel6.setText("Set size :");

        saveSize.setText("ok");
        saveSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveSizeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(size1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                    .addComponent(size3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(size5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(size7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(choiseSize, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(size10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saveSize, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(size1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(size3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(size5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(size7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(size10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(choiseSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(saveSize, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveSizeActionPerformed
        this.paintSurface.setSizeTools(Integer.valueOf(choiseSize.getValue().toString()));
        setVisible(false);
    }//GEN-LAST:event_saveSizeActionPerformed

    private void size1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_size1MouseReleased
        choiseSize.setValue(1);
        this.paintSurface.setSizeTools(1);
    }//GEN-LAST:event_size1MouseReleased

    private void size3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_size3MouseReleased
         choiseSize.setValue(3);
         this.paintSurface.setSizeTools(3);
    }//GEN-LAST:event_size3MouseReleased

    private void size5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_size5MouseReleased
         choiseSize.setValue(5);
         this.paintSurface.setSizeTools(5);
    }//GEN-LAST:event_size5MouseReleased

    private void size7MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_size7MouseReleased
         choiseSize.setValue(7);
         this.paintSurface.setSizeTools(7);
    }//GEN-LAST:event_size7MouseReleased

    private void size10MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_size10MouseReleased
         choiseSize.setValue(10);
         this.paintSurface.setSizeTools(10);
    }//GEN-LAST:event_size10MouseReleased

    public int returnSize()
    {
        return Integer.valueOf(choiseSize.getValue().toString());
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner choiseSize;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JButton saveSize;
    private javax.swing.JLabel size1;
    private javax.swing.JLabel size10;
    private javax.swing.JLabel size3;
    private javax.swing.JLabel size5;
    private javax.swing.JLabel size7;
    // End of variables declaration//GEN-END:variables
}
