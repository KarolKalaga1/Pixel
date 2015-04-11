
package com.project.algorithms;

import java.awt.image.BufferedImage;

/**
 *
 * @author Karol
 */
public interface ImageTransformer {

    /**
     * Transformuje przekazany obraz skalując go oraz obracając
     * @param input
     * @param scale Współczynnik skali w przedziale (0..1]
     * @param orientation Określa obrót źródłowego obrazka
     * <table border="1">
     *          <tr><th>{@code orientation}</th><th>Znaczenie</th></tr>
     *          <tr><td>0</td><td>top left (nie wymaga dalszej obróbki)</td></tr>
     *          <tr><td>1</td><td>top right (flip horizontally)</td></tr>
     *          <tr><td>2</td><td>botton right (rotate 180°)</td></tr>
     *          <tr><td>3</td><td>bottom left (flip vertically)</td></tr>
     *          <tr><td>4</td><td>left top (flip vertically and rotate 90° clockwise)</td></tr>
     *          <tr><td>5</td><td>right top (rotate 90°) </td></tr>
     *          <tr><td>6</td><td>right bottom (flip horizontally and rotate 90°)</td></tr>
     *          <tr><td>7</td><td>left bottom (rotate 270°)</td></tr>
     * </table>
     * @return
     */
    public BufferedImage transform(final BufferedImage input, final double scale, final int orientation);
}

