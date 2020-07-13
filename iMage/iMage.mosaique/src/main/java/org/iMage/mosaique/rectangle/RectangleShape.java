package org.iMage.mosaique.rectangle;

import org.iMage.mosaique.AbstractCalculator;
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
  protected AbstractCalculator getCalculator() {
    return RectangleCalculator.getInstance();
  }

}