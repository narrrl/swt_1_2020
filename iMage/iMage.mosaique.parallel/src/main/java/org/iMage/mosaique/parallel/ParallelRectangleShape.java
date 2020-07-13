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
    public RectangleCalculator getCalculator() {
        return RectangleCalculator.getInstance();
    }

}

