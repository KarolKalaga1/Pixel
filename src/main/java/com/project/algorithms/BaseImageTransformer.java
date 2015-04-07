
package com.project.algorithms;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author Karol
 */
    public abstract class BaseImageTransformer implements ImageTransformer
{
    protected void checkScale(double scale)
    {
        if (scale <= 0 || scale > 1){
            throw new IllegalArgumentException("Scale value " + scale + " out of range");
        }
    }
   
    protected void checkOrientation(int orientation)
    {
        if (orientation < 0 || orientation > 7){
            throw new IllegalArgumentException("orientation must be in range [0..7], got " + orientation);
        }
    }
   
    protected void setupAffineTransform(final AffineTransform at,
            final BufferedImage image, final double scale, final int orientation)
    {
        final double scWidth = scale * image.getWidth();
        final double scHeight = scale * image.getHeight();
       
        switch (orientation){
            case 0: {
                // top left
                at.setTransform(scale, 0, 0, scale, 0, 0); // just scale
                break;
            }
            case 1: {
                // top right
                at.setTransform(-scale, 0, 0, scale, scWidth, 0); // flip horizontally
                break;
            }
            case 2: {
                // botton right
                at.setTransform(-scale, 0, 0, -scale, scWidth, scHeight); // rotate 180°
                break;
            }
            case 3: {
                // bottom left
                at.setTransform(scale, 0, 0, -scale, 0, scHeight); // flip vertically
                break;
            }
            case 4: {
                // left top
                at.setTransform(0, scale, scale, 0, 0, 0); // flip vertically and rotate 90°
                break;
            }
            case 5: {
                // right top
                at.setTransform(0, scale, -scale, 0, scHeight, 0); // rotate 90°
                break;
            }
            case 6: {
                // right bottom
                at.setTransform(0, -scale, -scale, 0, scHeight, scWidth); // flip horizontally and rotate 90°
                break;
            }
            case 7: {
                // left bottom
                at.setTransform(0, -scale, scale, 0, 0, scWidth); // rotate 270°
                break;
            }
            default: {
                throw new IllegalArgumentException("orientation must be in range [0..7], got " + orientation);
            }
        }
    }
 
 
}
    
