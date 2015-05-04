
package com.project.algorithms;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

/**
 *
 * @author Karol
 */
 public class ImageTransformerLQ extends BaseImageTransformer
{
    @Override
    public BufferedImage transform(final BufferedImage input, final double scale, final int orientation)
    {
        checkScale(scale);
        checkOrientation(orientation);
       
        final AffineTransform at = new AffineTransform();
        setupAffineTransform(at, input, scale, orientation);
       
        final BufferedImageOp bio = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        final Rectangle2D size = bio.getBounds2D(input);
       
        final int newWidth = (int)Math.round(size.getWidth());
        final int newHeight = (int)Math.round(size.getHeight());
       
        BufferedImage output = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_3BYTE_BGR);
        output = bio.filter(input, output);
       
        return output;
    }
}
