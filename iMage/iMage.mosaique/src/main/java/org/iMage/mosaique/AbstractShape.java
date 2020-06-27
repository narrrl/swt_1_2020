package org.iMage.mosaique;

import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.base.IMosaiqueShape;
import org.iMage.mosaique.base.ImageUtils;

import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * This class represents an abstract {@link IMosaiqueShape} based on an {@link BufferedArtImage}.
 *
 * @author Dominik Fuchss
 */
public abstract class AbstractShape implements IMosaiqueShape<BufferedArtImage> {
  protected final BufferedImage image;
  private int average;

  /**
   * Create a new {@link IMosaiqueShape} by image.
   *
   * @param image
   *     the image to use
   * @param w
   *     the width
   * @param h
   *     the height
   */
  protected AbstractShape(BufferedArtImage image, int w, int h) {
    this.image = ImageUtils.scaleAndCrop(Objects.requireNonNull(image.toBufferedImage()), w, h);
    this.average = this.calcAverage();
  }

  /**
   * Calculate the average color of {@link #image}.
   *
   * @return the average color
   */
  protected abstract int calcAverage();

  @Override
  public int getAverageColor() {
    return average;
  }

  @Override
  public BufferedImage getThumbnail() {
    BufferedArtImage res = new BufferedArtImage(image.getWidth(), image.getHeight());
    this.drawMe(res);
    return res.toBufferedImage();
  }

  @Override
  public final void drawMe(BufferedArtImage targetRect) {
    if (targetRect.getWidth() > this.getWidth() || targetRect.getHeight() > this.getHeight()) {
      throw new IllegalArgumentException("dimensions of target are too big for this tile");
    }

    int w = Math.min(this.getWidth(), targetRect.getWidth());
    int h = Math.min(this.getHeight(), targetRect.getHeight());

    drawShape(targetRect, w, h);

  }

  /**
   * Draw the shape of the specific rectangle based on the target region and the scaled instance.
   *
   * @param targetRect
   *     the target region
   * @param w
   *     the width to be drawn
   * @param h
   *     the height to be drawn
   */
  protected abstract void drawShape(BufferedArtImage targetRect, int w, int h);

  @Override
  public final int getWidth() {
    return image.getWidth();
  }

  @Override
  public final int getHeight() {
    return image.getHeight();
  }
}
