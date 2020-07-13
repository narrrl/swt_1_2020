package org.iMage.mosaique.crossed;

import org.iMage.mosaique.AbstractCalculator;
import org.iMage.mosaique.AbstractShape;
import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.base.IMosaiqueShape;
import org.iMage.mosaique.crossed.calculator.LeftCalculator;

public class LeftCrossedShape extends AbstractShape {
  /**
   * Create a new {@link IMosaiqueShape} by image.
   *
   * @param image
   *          the image to use
   * @param w
   *          the width
   * @param h
   *          the height
   */
  public LeftCrossedShape(BufferedArtImage image, int w, int h) {
    super(image, w, h);
  }

  @Override
  protected AbstractCalculator getCalculator() {
    return LeftCalculator.getInstance();
  }
}
