
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
           private ImageIcon play;
           private ImageIcon stops;
           private JMenuBar mainmenu;
                private JMenu menu;
                    private JMenuItem mopen;
                    private JMenuItem mexit;
                private JMenu options;
                    private JMenu flipTime;
                        private JMenuItem speed1;
                        private JMenuItem speed2;
                        private JMenuItem speed3;
                        private JMenuItem speed4;
                        
            public ShowInterface(){

               try {
                   setTitle("Image Slide Show");
                   setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                   
                   setSize(350, 350);
                   mainmenu=new JMenuBar();
                   menu=new JMenu("File");
                   mopen=new JMenuItem("Open...");
                   mopen.setMnemonic(KeyEvent.VK_O);
                   mopen.addActionListener(this);
                   mexit=new JMenuItem("Exit");
                   mexit.setMnemonic(KeyEvent.VK_X);
                   mexit.addActionListener(this);
                   
                   options = new JMenu("Options");
                        flipTime = new JMenu("Flip Time");
                            speed1 = new JCheckBoxMenuItem("5 seconds");
                            speed2 = new JCheckBoxMenuItem("8 seconds");
                            speed3 = new JCheckBoxMenuItem("10 seconds");
                            speed4 = new JCheckBoxMenuItem("15 seconds");
                            
                   speed1.setSelected(true);

                   flipTime.add(speed1);
                   flipTime.add(speed2);
                   flipTime.add(speed3);
                   flipTime.add(speed4);
                   speed1.addActionListener(this);
                   speed2.addActionListener(this);
                   speed3.addActionListener(this);
                   speed4.addActionListener(this);
                   options.add(flipTime);
                   
                   menu.add(mopen);
                   menu.add(mexit);
                   mainmenu.add(menu);
                   mainmenu.add(options);
                   setJMenuBar(mainmenu);
                   setLocationRelativeTo(null);
                   
                   play  = new ImageIcon(this.getClass().getResource("/play.png"));
                   stops = new ImageIcon(this.getClass().getResource("/stop.png"));
                   btSlideShow = new JButton(play);
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
            if(e.getSource() == speed1)
            {
                speed2.setSelected(false);
                speed3.setSelected(false);
                speed4.setSelected(false);
                slider.changeTime(5000);
            }
            else if(e.getSource() == speed2)
            {
                speed1.setSelected(false);
                speed3.setSelected(false);
                speed4.setSelected(false); 
                slider.changeTime(8000);
            }
             else if(e.getSource() == speed3)
            {
                speed1.setSelected(false);
                speed2.setSelected(false);
                speed4.setSelected(false);   
                slider.changeTime(10000);
            }
              else if(e.getSource() == speed4)
            {
                speed1.setSelected(false);
                speed2.setSelected(false);
                speed3.setSelected(false);
                slider.changeTime(15000);
            }
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
                        btSlideShow.setIcon(stops);
                        stop=false;
                        slider=new ImageSlider();
                        slider.start();
            }          
        public void stopClick(){
                        btSlideShow.setIcon(play);
                        stop=true;                 
                        slider.stopShow();
            }

        class ImageSlider extends Thread{
                    private boolean started;
                    private HashMap<Integer,String> map;
                    private int second;
                    
                    private ImageSlider(){
                            started=true;
                            map=show.getImgFiles();
                            second=5000;
                        }
                    public void changeTime(int time){
                    this.second = time;
                    }

                public void run(){
                            int i;
                            try{
                                for(i=0;i<map.size();i++)
                                {
                                if(started!=false){
                                            Thread.sleep(second);                                                           
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

