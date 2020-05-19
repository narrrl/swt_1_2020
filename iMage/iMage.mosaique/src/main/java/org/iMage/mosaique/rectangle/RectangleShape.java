package org.iMage.mosaique.rectangle;

import java.awt.image.BufferedImage;

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
  private BufferedImage picture;

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
  public RectangleShape(final BufferedArtImage image, final int w, final int h) {
    this.picture = ImageUtils.scaleAndCrop(image.toBufferedImage(),w,h);
  }

  @Override
  public int getAverageColor() {
    throw new RuntimeException("not implemented");
  }

  @Override
  public BufferedImage getThumbnail() {
    throw new RuntimeException("not implemented");
  }

  @Override
  public void drawMe(BufferedArtImage targetRect) {
    throw new RuntimeException("not implemented");
  }

  @Override
  public int getHeight() {
    throw new RuntimeException("not implemented");
  }

  @Override
  public int getWidth() {
    throw new RuntimeException("not implemented");
  }
}