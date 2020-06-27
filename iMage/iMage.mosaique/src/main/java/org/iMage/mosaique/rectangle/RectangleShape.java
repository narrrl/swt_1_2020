package org.iMage.mosaique.rectangle;

import java.awt.image.BufferedImage;

import org.iMage.mosaique.AbstractShape;
import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.base.IMosaiqueShape;

/**
 * This class represents a rectangle as {@link IMosaiqueShape} based on an {@link BufferedArtImage}.
 *
 * @author Dominik Fuchss
 *
 */
public class RectangleShape extends AbstractShape implements IMosaiqueShape<BufferedArtImage> {

  /**
   * Create a new {@link IMosaiqueShape}.
   *
   * @param image
   *          the image to use
   * @param w
   *          the width
   * @param h
   *          the height
   */
  public RectangleShape(BufferedArtImage image, int w, int h) {
    super(image, w, h);
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
