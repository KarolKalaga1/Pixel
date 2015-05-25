
package com.project.slideshow;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Karol
 */
public class ChangeImageResize {
    
    
    public static boolean isBetween(int x, int lower, int upper) {
    return lower <= x && x <= upper;
    }
    
      public static BufferedImage changeSize(String inputImagePath) throws IOException
    {
         double percent = 0.3;
        
          File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        
         int scaledWidth = inputImage.getWidth() ;
         int scaledHeight = inputImage.getHeight();
        
        if (isBetween(inputImage.getWidth(), 3000, 5000)) {
         scaledWidth = (int) (inputImage.getWidth() * 0.3);
         scaledHeight = (int) (inputImage.getHeight() * 0.3);
      } else if (isBetween(inputImage.getWidth(), 2000, 3000)) {
         scaledWidth = (int) (inputImage.getWidth() * 0.4);
         scaledHeight = (int) (inputImage.getHeight() * 0.4);
      }
        else if (isBetween(inputImage.getWidth(), 1500, 2000)) {
         scaledWidth = (int) (inputImage.getWidth() * 0.7);
         scaledHeight = (int) (inputImage.getHeight() * 0.7);
      }
 
        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());
 
        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
 

 
        return outputImage;
        // writes to output file
        //ImageIO.write(outputImage, formatName, new File(inputImagePath));
        
    }
}
