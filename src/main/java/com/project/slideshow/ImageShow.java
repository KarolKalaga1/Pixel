
package com.project.slideshow;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Karol
 */
class ImageShow extends Canvas implements ImageObserver{
            private BufferedImage img;
            private Dimension ds;
            private HashMap<Integer, String> imgFiles;
            private String filename;
            private int curImgIndex;
            private boolean first;

           public ImageShow(){
                
                      ds=getToolkit().getScreenSize();
                      filename=null;
                       
                                    }
           
          public void paint(Graphics g){
              if(first)
              {
                   drawIt(g, img);
              }
              else{
                        if(filename!=null){
                            try {
//                                ChangeImageResize.changeSize();
                               // img=getToolkit().getImage(filename);           
                                drawIt(g, ChangeImageResize.changeSize(filename));
                            } catch (IOException ex) {
                                Logger.getLogger(ImageShow.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
              }
            }

          public void drawIt(Graphics g, BufferedImage img){
                        Graphics2D g2d=(Graphics2D)g;
                        if(first)
                        {
                             g2d.drawImage(img,50,10,256,256,this);
                             first=false;
                        }
                        else{
                        int x=-img.getWidth(this)/2;
                        int y=-img.getHeight(this)/2;
                        int w=img.getWidth(this);
                        int h=img.getHeight(this);
                        int mX=(int)ds.getWidth()/2;
                        int mY=(int)ds.getHeight()/2;                     
                        g2d.translate(mX,mY);
                         g2d.drawImage(img,x,y,w,h,this);
                        }
                        
                       
            }
           
           
          public void storeImages(){
                        imgFiles=new HashMap<Integer, String>();                  
                        JFileChooser chooser = new JFileChooser();
                        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "gif","png","gif","bmp");
                        chooser.setFileFilter(filter);
                        chooser.setMultiSelectionEnabled(true);
                        int returnVal = chooser.showOpenDialog(this);
                        if(returnVal == JFileChooser.APPROVE_OPTION) {
                                    File[] Files=chooser.getSelectedFiles();
                                                for( int i=0;i<Files.length;i++){     
                                                            imgFiles.put(i,Files[i].toString());
                                                            }
                                 }
                        }
          public void moveFirst(){
                        if(!imgFiles.isEmpty()){
                                    curImgIndex=0;                               
                                    filename=imgFiles.get(curImgIndex);
                                    repaint();
                                    }
                        }
           
          public void moveNext(){
                        if(!imgFiles.isEmpty() && curImgIndex<imgFiles.size()-1){
                                    curImgIndex++;                               
                                    filename=imgFiles.get(curImgIndex);
                                    repaint();
                                    }
                        }

          public HashMap<Integer,String> getImgFiles(){
                        return imgFiles;
            }
          
          public void firstMatch(boolean first,BufferedImage image)
          {
              this.first = first;
              this.img = image;
              repaint();
          }
}