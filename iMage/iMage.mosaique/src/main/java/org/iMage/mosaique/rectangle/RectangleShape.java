package org.iMage.mosaique.rectangle;

import java.awt.image.BufferedImage;
import java.util.Objects;

import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.base.IMosaiqueShape;
import org.iMage.mosaique.base.ImageUtils;

/**
 * This class represents a rectangle as {@link IMosaiqueShape} based on an {@link BufferedArtImage}.
 *
 * @author Dominik Fuchss
 *
 */
public class RectangleShape implements IMosaiqueShape<BufferedArtImage> {

  private BufferedImage image;
  private int average;

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
    this.image = ImageUtils.scaleAndCrop(Objects.requireNonNull(image).toBufferedImage(), w, h);
    this.average = RectangleCalculator.averageColor(this.image);
  }

  @Override
  public int getAverageColor() {
    return average;
  }

  @Override
  public BufferedImage getThumbnail() {
    return image;
  }

  @Override
  public void drawMe(BufferedArtImage targetRect) {
    if (targetRect.getWidth() > this.getWidth() || targetRect.getHeight() > this.getHeight()) {
      throw new IllegalArgumentException("dimensions of target are too big for this tile");
    }

    int w = Math.min(this.getWidth(), targetRect.getWidth());
    int h = Math.min(this.getHeight(), targetRect.getHeight());

    for (int x = 0; x < w; x++) {
      for (int y = 0; y < h; y++) {
        targetRect.setRGB(x, y, image.getRGB(x, y));
      }
    }
  }

  @Override
  public int getHeight() {
    return image.getHeight();
  }

  @Override
  public int getWidth() {
    return image.getWidth();
  }
}