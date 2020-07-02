package org.iMage.mosaique.triangle;

import org.iMage.mosaique.AbstractCalculator;
import java.util.Iterator;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public final class UpperTriangleCalculator extends AbstractCalculator {
    private static UpperTriangleCalculator calc;

    private UpperTriangleCalculator() {}

    @Override
    public Iterator<Integer> getPixelIterator(final BufferedImage region) {
        ArrayList<Integer> pixels = new ArrayList<>();

        float m = (1F * region.getHeight()) / region.getWidth();

        for(int x = 0; x < region.getWidth(); x++) {
            float yCond = Math.min((x + 1) * m, region.getHeight());
            for (int y = 0; y < yCond; y++) {
                pixels.add(region.getRGB(x, y));
            }
        }

        return pixels.iterator();
    }

    public static UpperTriangleCalculator gCalculator() {
        if (calc == null) {
            calc = new UpperTriangleCalculator();
        }
        return calc;
    }

}
