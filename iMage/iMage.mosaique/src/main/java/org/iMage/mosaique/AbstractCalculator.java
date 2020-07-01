package org.iMage.mosaique;

import java.awt.image.BufferedImage;
import java.awt.Color;
import org.iMage.mosaique.triangle.UpperTriangleCalculator;
import org.iMage.mosaique.triangle.LowerTriangleCalculator;
import org.iMage.mosaique.rectangle.RectangleCalculator;

/**
 * Wrapper class for the {@link TriangleCalculator} and
 * {@link RectangleCalculator} that makes both deprecated.
 *
 * @author Nils Purkopp
 *
 */
public abstract class AbstractCalculator {
    private static final float EPSILON = 0.00001F;

    public int averageColor(BufferedImage region) {
        long r = 0;
        long g = 0;
        long b = 0;
        long a = 0;
        int ctr = 0;
        boolean isRectangle;
        if ( this.getClass() == RectangleCalculator.class) {
            isRectangle = true;
        } else {
            isRectangle = false;
        }

        int xCond = region.getWidth();
        float yCond = region.getHeight();
        float m = 0;
        float y = 1;

        if (!isRectangle) {
            m = (1F * region.getHeight()) / region.getWidth();
        }

        for (int x = 0; x < xCond; x++) {

            if (this.getClass() == UpperTriangleCalculator.class) {
                yCond = Math.min((x + 1) * m, region.getHeight());
            } else if (this.getClass() == LowerTriangleCalculator.class) {
                y = Math.max((x + 1) * m, 0);
                yCond = region.getHeight() - 1;
            }

            // I tried to revert the loop of the old lowerTrianlge averageColor
            // method, hope it works
            //
            // I start with y = 1 when rectangle or upper (and subtract 1
            // in getRGB) because old loop condition was just < not <=
            // If its lower triangle y and yCond are swapped. To still get the
            // right rgb value I subtract i from it



            int i = 0;

            while ( y <= yCond ) {
                int col;

                if (this.getClass() == LowerTriangleCalculator.class) {
                    col = region.getRGB(x, (int) yCond - i);
                } else {
                    col = region.getRGB(x, (int) y - 1);
                }

                Color c = new Color(col, true);

                r += c.getRed();
                g += c.getGreen();
                b += c.getBlue();
                a += c.getAlpha();
                ctr++;
                y++;
            }

        }

        return new Color((int) (r / ctr), (int) (g / ctr), (int) (b / ctr),
                (int) (a / ctr)).getRGB();
    }
}
