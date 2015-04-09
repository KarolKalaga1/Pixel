
package com.project.algorithms;

import java.awt.image.BufferedImage;

/**
 *
 * @author Karol
 */
public class ImageTransformerMQ implements ImageTransformer
{
    private final ImageTransformer parentTransformer = new ImageTransformerLQ();
   
    private double qualityOffset;
   
    public ImageTransformerMQ()
    {
       
        qualityOffset = 0.5d;
    }
 
    public ImageTransformerMQ(double qualityOffset)
    {
        setQualityOffset(qualityOffset);
    }
 
    public BufferedImage transform(final BufferedImage input, final double scale, int orientation)
    {
        if (scale <= 0 || scale > 1){
            throw new IllegalArgumentException("Scale value " + scale + " out of range");
        }
 
        final int targetMaxSide;
        {
            final int width = input.getWidth();
            final int height = input.getHeight();
            final int max = Math.max(width, height);
            targetMaxSide = (int)Math.round(scale * max);
        }
       
        final double scaleLimit = qualityOffset * 0.4d + 0.1d;
        BufferedImage thumbnail = input;
        do{
            final int width = thumbnail.getWidth();
            final int height = thumbnail.getHeight();
            final int max = Math.max(width, height);
            double scale2 = (double)targetMaxSide / (double)max;
           
            if (scale2 >= 1){
                return thumbnail;
            }
            if (scale2 < scaleLimit){
                scale2 = scaleLimit;
            }
 
            thumbnail = parentTransformer.transform(thumbnail, scale2, orientation);
            orientation = 0;
           
           
        }while(true);
    }
 
 
    public double getQualityOffset()
    {
        return qualityOffset;
    }
 
 
    /**
     * @param qualityOffset Współczynnik jakości - 0 - zła, 1 - dobra
     */
    public void setQualityOffset(double qualityOffset)
    {
        if (qualityOffset < 0 || qualityOffset > 1){
            throw new IllegalArgumentException("Quality offset value " + qualityOffset + " out of range");
        }
       
        this.qualityOffset = qualityOffset;
    }
   
}
