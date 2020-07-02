package org.iMage.mosaique.triangle;


import org.iMage.mosaique.AbstractCalculator;
import java.util.Iterator;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public final class LowerTriangleCalculator extends AbstractCalculator {
    private static LowerTriangleCalculator calc;

    private LowerTriangleCalculator() {}

    @Override
    public Iterator<Integer> getPixelIterator(BufferedImage region) {
        ArrayList<Integer> pixels = new ArrayList<>();

        float m = (1F * region.getHeight()) / region.getWidth();

        for (int x = 0; x < region.getWidth(); x++) {
            float yCond = Math.min((x + 1) * m, 0);
            for (int y = region.getHeight() - 1; y >= yCond; y--) {
                pixels.add(region.getRGB(x, y));
            }
        }

        return pixels.iterator();
    }

    public static LowerTriangleCalculator getCalculator() {
        if (calc == null) {
            calc = new LowerTriangleCalculator();
        }
        return calc;
    }

}
