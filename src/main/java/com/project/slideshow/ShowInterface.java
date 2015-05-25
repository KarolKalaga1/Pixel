
package com.project.slideshow;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.util.HashMap;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;




           
public class ShowInterface extends JFrame implements ActionListener{
           
           private JButton btSlideShow;
           private JPanel panel;
           private ImageShow show;
           private boolean stop=true;
           private ImageSlider slider;
           private JMenuBar mainmenu;
                private JMenu menu;
                     private JMenuItem mopen;
                     private JMenuItem mexit;
                private JMenu options;
                     private JMenuItem flipTime;
            public ShowInterface(){

               try {
                   setTitle("Image Slide Show");
                   setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                   
                   setSize(350, 350);
                   mainmenu=new JMenuBar();
                   JMenu menu=new JMenu("File");
                   mopen=new JMenuItem("Open...");
                   mopen.setMnemonic(KeyEvent.VK_O);
                   mopen.addActionListener(this);
                   mexit=new JMenuItem("Exit");
                   mexit.setMnemonic(KeyEvent.VK_X);
                   mexit.addActionListener(this);
                   menu.add(mopen);
                   menu.add(mexit);
                   mainmenu.add(menu);
                   setJMenuBar(mainmenu);
                   setLocationRelativeTo(null);
                   
                   //btSlideShow=new JButton(new ImageIcon("play.png"));
                   btSlideShow = new JButton("Play");
                   btSlideShow.setBackground(Color.BLACK);
                   btSlideShow.addActionListener(this);
                   btSlideShow.setEnabled(false);
                   
                   panel=new JPanel();
                   panel.setLayout(new FlowLayout());
                   panel.add(btSlideShow);
                   panel.setBackground(Color.BLACK);
                   add(panel, BorderLayout.SOUTH);
                   
                   show=new ImageShow();
                   BufferedImage image = ImageIO.read(ShowInterface.class.getResourceAsStream("/show1.png"));
                   show.firstMatch(true, image);
                   //btSlideShow.setEnabled(true);
                   add(show,BorderLayout.CENTER );
               
                   setResizable(false);
               } catch (IOException ex) {
                   Logger.getLogger(ShowInterface.class.getName()).log(Level.SEVERE, null, ex);
               }
                       
                                   
                        }
           
           
        public void actionPerformed(ActionEvent e){
                    if(e.getSource()==btSlideShow){
                        if(stop){
                           
                            startClick();
                            }
                        else{
                            stopClick();
                            }
                        }
                    else{
                        JMenuItem source = (JMenuItem)(e.getSource());
                        if(source.getText().compareTo("Open...")==0)
                            {
                            show.storeImages();
                            if(show.getImgFiles().size()>0){
                                        setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);
                                        show.moveFirst();                                                   
                                        btSlideShow.setEnabled(true);
                                        }
                            }
                        else if(source.getText().compareTo("Exit")==0)
                                   dispose();

                        }
                                   
                      }
           
        public void startClick(){
                        btSlideShow.setIcon(new ImageIcon("pause.png"));
                        stop=false;
                        slider=new ImageSlider();
                        slider.start();
            }          
        public void stopClick(){
                        btSlideShow.setIcon(new ImageIcon("play.png"));
                        stop=true;                 
                        slider.stopShow();
            }

        class ImageSlider extends Thread{
                    private boolean started;
                    private HashMap<Integer,String> map;
                    
                    private ImageSlider(){
                            started=true;
                            map=show.getImgFiles();
                        }

                public void run(){
                            int i;
                            try{
                                for(i=0;i<map.size();i++)
                                {
                                if(started!=false){
                                            Thread.sleep(5000);                                                           
                                             show.moveNext();

                                            }          
                                }
                            stopClick();
                            show.moveFirst();
                            }catch(InterruptedException ie){System.out.println("Interrupted slide show...");}
                         }

            public void stopShow(){
                        started=false;
                        }
           
            }
           
}

