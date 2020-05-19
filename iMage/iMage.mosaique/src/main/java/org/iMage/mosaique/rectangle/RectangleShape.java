package org.iMage.mosaique.rectangle;

import java.awt.image.BufferedImage;

import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.base.IMosaiqueShape;
import org.iMage.mosaique.base.ImageUtils;

/**
 * This class represents a rectangle as {@link IMosaiqueShape} based on an {@link BufferedArtImage}.
 *
 * @author Dominik Fuchss
 */
public class RectangleShape implements IMosaiqueShape<BufferedArtImage> {
    private final BufferedImage picture;
    private final int height;
    private final int width;

    /**
     * Create a new {@link IMosaiqueShape}. It
     *
     * @param image the image to use
     * @param w     the width
     * @param h     the height
     */
    public RectangleShape(final BufferedArtImage image, final int w, final int h) {
        this.picture = ImageUtils.scaleAndCrop(image.toBufferedImage(), w, h);
        height = this.picture.getHeight();
        width = this.picture.getWidth();
    }

    @Override
    public int getAverageColor() {
        if (this.picture != null) {
            int argb = 0;
            for (int i = picture.getMinX(); i < height; i++) {
                for (int i2 = picture.getMinY(); i2 < width; i2++) {
                    argb += this.picture.getRGB(i, i2);
                }
            }
            return argb / (height * width);
        }
        return 0;
    }

    @Override
    public BufferedImage getThumbnail() {
        return this.picture;
    }

    @Override
    public void drawMe(BufferedArtImage targetRect) {
        final int heightDif = Math.abs(this.height - targetRect.getHeight());
        final int widthDif = Math.abs(this.width - targetRect.getWidth());
        final int h = (this.height >= targetRect.getHeight())
                ? this.height - heightDif : targetRect.getHeight() - heightDif;
        final int w = (this.width >= targetRect.getWidth())
                ? this.width - widthDif : targetRect.getWidth() - widthDif;
        for (int i = 0; i < h; i++) {
          for (int i2 = 0; i2 < w; i2++) {
            targetRect.setRGB(i,i2,picture.getRGB(i,i2));
          }
        }
    }

    @Override
    public int getHeight() {
        if (this.picture != null) {
            return this.height;
        }
        return 0;
    }

    @Override
    public int getWidth() {
        if (this.picture != null) {
            return this.width;
        }
        return 0;
    }
}