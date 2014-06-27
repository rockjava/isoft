/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package  com.isoftframework.common.io;


import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.SwingConstants;

/**
 *
 * @author aim
 */
public class IOUtilities {

    private IOUtilities() {
        // never instantiate
    }


    public static String getURLFileName(URL url) {
    	//toURI().getPath();
        String path=null;
		try {
			path = url.toURI().getPath();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String tabName=path.substring(path.lastIndexOf("/") + 1,path.lastIndexOf("."));
        return tabName;

    }

    private static BufferedImage createCompatibleImage(int width, int height) {

        return GraphicsEnvironment.getLocalGraphicsEnvironment().
                    getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width, height);

    }

    public static BufferedImage createTranslucentImage(int width, int height) {

        return GraphicsEnvironment.getLocalGraphicsEnvironment().
                    getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);

    }

    public static BufferedImage createGradientImage(int width, int height, Color gradient1, Color gradient2) {

            BufferedImage gradientImage = createCompatibleImage(width, height);
            GradientPaint gradient = new GradientPaint(0, 0, gradient1, 0, height, gradient2, false);
            Graphics2D g2 = (Graphics2D)gradientImage.getGraphics();
            g2.setPaint(gradient);
            g2.fillRect(0, 0, width, height);
            g2.dispose();

            return gradientImage;
    }


    public static BufferedImage createGradientMask(int width, int height, int orientation) {
        // algorithm derived from Romain Guy's blog
        BufferedImage gradient = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = gradient.createGraphics();
        GradientPaint paint = new GradientPaint(0.0f, 0.0f,
                new Color(1.0f, 1.0f, 1.0f, 1.0f),
                orientation == SwingConstants.HORIZONTAL? width : 0.0f,
                orientation == SwingConstants.VERTICAL? height : 0.0f,
                new Color(1.0f, 1.0f, 1.0f, 0.0f));
        g.setPaint(paint);
        g.fill(new Rectangle2D.Double(0, 0, width, height));

        g.dispose();
        gradient.flush();

        return gradient;
    }

    public static Color deriveColorAlpha(Color base, int alpha) {
        return new Color(base.getRed(), base.getGreen(), base.getBlue(), alpha);
    }

    /**
     * Derives a color by adding the specified offsets to the base color's
     * hue, saturation, and brightness values.   The resulting hue, saturation,
     * and brightness values will be contrained to be between 0 and 1.
     * @param base the color to which the HSV offsets will be added
     * @param dH the offset for hue
     * @param dS the offset for saturation
     * @param dB the offset for brightness
     * @return Color with modified HSV values
     */
    public static Color deriveColorHSB(Color base, float dH, float dS, float dB) {
        float hsb[] = Color.RGBtoHSB(
                base.getRed(), base.getGreen(), base.getBlue(), null);

        hsb[0] += dH;
        hsb[1] += dS;
        hsb[2] += dB;
        return Color.getHSBColor(
                hsb[0] < 0? 0 : (hsb[0] > 1? 1 : hsb[0]),
                hsb[1] < 0? 0 : (hsb[1] > 1? 1 : hsb[1]),
                hsb[2] < 0? 0 : (hsb[2] > 1? 1 : hsb[2]));

    }

    public static String getHTMLColorString(Color color) {
        String red = Integer.toHexString(color.getRed());
        String green = Integer.toHexString(color.getGreen());
        String blue = Integer.toHexString(color.getBlue());

        return "#" +
                (red.length() == 1? "0" + red : red) +
                (green.length() == 1? "0" + green : green) +
                (blue.length() == 1? "0" + blue : blue);
    }

   public static void printColor(String key, Color color) {
       float hsb[] = Color.RGBtoHSB(
                color.getRed(), color.getGreen(),
                color.getBlue(), null);
       System.out.println(key+": RGB=" +
               color.getRed() + ","+ color.getGreen() + ","+ color.getBlue() + "  " +
                "HSB=" + String.format("%.0f%n",hsb[0]*360) + "," +
                            String.format("%.3f%n",hsb[1]) + "," +
                            String.format("%.3f%n", hsb[2]));
   }
}
