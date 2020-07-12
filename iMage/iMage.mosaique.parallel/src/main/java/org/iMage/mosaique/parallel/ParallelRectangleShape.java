package org.iMage.mosaique.parallel;

import java.awt.image.BufferedImage;
import org.iMage.mosaique.AbstractShape;
import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.rectangle.RectangleCalculator;




public final class ParallelRectangleShape extends AbstractShape {

    public ParallelRectangleShape(BufferedImage image, int w, int h) {
        super(new BufferedArtImage(image), w, h);
    }

    @Override
    public BufferedImage getThumbnail() {
        return image;
    }

    @Override
    protected int calcAverage() {
        return RectangleCalculator.getCalculator().averageColor(image);
    }

    @Override
    protected void drawShape(BufferedArtImage targetRect, int w, int h) {
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                targetRect.setRGB(x, y, image.getRGB(x, y));
            }
        }
    }

}

