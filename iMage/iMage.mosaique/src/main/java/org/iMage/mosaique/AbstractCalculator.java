package org.iMage.mosaique;

import java.awt.image.BufferedImage;
import java.awt.Color;
import org.iMage.mosaique.rectangle.RectangleCalculator;
import java.util.Iterator;

/**
 * Wrapper class for the {@link TriangleCalculator} and
 * {@link RectangleCalculator} that makes both deprecated.
 *
 * @author Nils Purkopp
 *
 */
public abstract class AbstractCalculator {

    public abstract Iterator<Integer> getPixelIterator(BufferedImage region);

    public int averageColor(BufferedImage region) {
        long r = 0;
        long g = 0;
        long b = 0;
        long a = 0;
        int ctr = 0;

        Iterator<Integer> i = this.getPixelIterator(region);

        while (i.hasNext()) {
            Color c = new Color(i.next());
            r += c.getRed();
            g += c.getGreen();
            b += c.getBlue();
            a += c.getAlpha();
            ctr++;
        }

        return new Color((int) (r / ctr), (int) (g / ctr), (int) (b / ctr),
                (int) (a / ctr)).getRGB();
    }
}
