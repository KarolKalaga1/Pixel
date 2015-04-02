
package com.project.tools;

import java.awt.image.WritableRaster;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Karol
 */
public class Filters {
     public double ww[]=new double[3];
     private int pixels[] = new int[3];
    
    // Filtr sepia//
    public WritableRaster sepiaFilter(WritableRaster raster)
    {
        for(int i=0;i<raster.getWidth();i++)
        {
            for(int j=0;j<raster.getHeight();j++)
            {

                raster.getPixel(i, j, pixels);

                ww[0] = (pixels[0] * 0.393 + pixels[1] * 0.769 + pixels[2] * 0.189 ) / 1.351;
                ww[1] = (pixels[0] * 0.349 + pixels[1] * 0.686 + pixels[2] * 0.186 ) / 1.203;
                ww[2] = (pixels[0] * 0.272 + pixels[1] * 0.534 + pixels[2] * 0.131 ) / 2.140;

                raster.setPixel(i, j, ww);
            }
       }
         return raster;
    }
     // Filtr gray//
    public WritableRaster grayFilter(WritableRaster raster)
    {
        
        for(int i=0;i<raster.getWidth();i++)
        {

            for(int j=0;j<raster.getHeight();j++)
            {

                raster.getPixel(i, j, pixels);

                ww[0] = 0.299*pixels[0]+0.587*pixels[1]+0.114*pixels[2];
                ww[1] = 0.299*pixels[0]+0.587*pixels[1]+0.114*pixels[2];
                ww[2] = 0.299*pixels[0]+0.587*pixels[1]+0.114*pixels[2];

                raster.setPixel(i, j, ww);

            }

       }
        return raster;
    }
     // Filtr brightness//
    public WritableRaster brightnessFilter(WritableRaster raster)
    {
        int jasnosc = 100;
        double hsv[];
        for(int i=0;i<raster.getWidth();i++)
        {

            for(int j=0;j<raster.getHeight();j++)
            {

                raster.getPixel(i, j, pixels);
                hsv=rgb2hsv(pixels[0], pixels[1], pixels[2]);
                hsv[2]=hsv[2]+jasnosc>240?240:hsv[2]+jasnosc;

                ww=hsv2rgb(hsv[0], hsv[1], hsv[2]);
                raster.setPixel(i, j, ww);

            }

       }
        return raster;
    }
     // Filtr black&white//
    public WritableRaster blackWhiteFilter(WritableRaster raster)
    {
        boolean better = true;
        int ton = 100; 
        Random r = new Random();
        double min = -(0.15*ton);
        double max = 0.15*ton;
        int ton2 = 0;
        double hsv[];

        for(int i=0;i<raster.getWidth();i++)
        {

             for(int j=0;j<raster.getHeight();j++)
             {
                  raster.getPixel(i, j, pixels);
                  hsv=rgb2hsv(pixels[0], pixels[1], pixels[2]);

                  if(better){

                      ton2=ton+(int)(min + (int)(Math.random()  * ((max - min) + 1)));

                  }
                  else
                      ton2=ton;
                  if(hsv[2]>ton2)
                  {

                      ww=hsv2rgb(hsv[0], hsv[1], hsv[2]);
                      Arrays.fill(ww, 0);
                  }
                  else
                  {

                      ww=hsv2rgb(hsv[0], hsv[1], hsv[2]);
                      Arrays.fill(ww, 255);

                  }

                   raster.setPixel(i, j, ww);  
             }
        }   
        return raster;
    }
    
     // Change rgb to hsv//
      private double[] rgb2hsv(double red, double grn, double blu)
    {
        double hue, sat, val;
        double x, f, i;
        double result[] = new double[3];

        x = Math.min(Math.min(red, grn), blu);
        val = Math.max(Math.max(red, grn), blu);
        if (x == val){
            hue = 0;
            sat = 0;
        }
        else 
        {
            f = (red == x) ? grn-blu : ((grn == x) ? blu-red : red-grn);
            i = (red == x) ? 3 : ((grn == x) ? 5 : 1);
            hue = ((i-f/(val-x))*60)%360;
            sat = ((val-x)/val);
        }
        result[0] = hue;
        result[1] = sat;
        result[2] = val;
        return result;
    }
    //Change hsv to rgb//
     private double[] hsv2rgb(double hue, double sat, double val) 
    {
        double red = 0, grn = 0, blu = 0;
        double i, f, p, q, t;
        double result[] = new double[3];
 
        if(val==0) 
        {
            red = 0;
            grn = 0;
            blu = 0;
        } else 
        {
            hue/=60;
            i = Math.floor(hue);
            f = hue-i;
            p = val*(1-sat);
            q = val*(1-(sat*f));
            t = val*(1-(sat*(1-f)));
            if (i==0) {red=val; grn=t; blu=p;}
            else if (i==1) {red=q; grn=val; blu=p;}
            else if (i==2) {red=p; grn=val; blu=t;}
            else if (i==3) {red=p; grn=q; blu=val;}
            else if (i==4) {red=t; grn=p; blu=val;}
            else if (i==5) {red=val; grn=p; blu=q;}
        }
        result[0] = red; 
        result[1] = grn;
        result[2] = blu;
        return result;
}
}
