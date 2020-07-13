package org.iMage.mosaique.triangle;

import org.iMage.mosaique.AbstractCalculator;
import org.iMage.mosaique.AbstractShape;
import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.base.IMosaiqueShape;

/**
 * This abstract class represents an upper triangle as {@link IMosaiqueShape} based on an
 * {@link BufferedArtImage}.
 *
 * Two different triangles are possible: Upper and Lower.
 *
 * Image:
 *
 * <pre>
 * (0,0)-----------|
 *   | \           |
 *   |  \          |
 *   |   \  UPPER  |
 *   |    \        |
 *   |     \       |
 *   |      \      |
 *   |       \     |
 *   |        \    |
 *   |         \   |
 *   |          \  |
 *   | LOWER     \ |
 *   |            \|
 *   |-----------(w,h)
 * </pre>
 *
 * @author Dominik Fuchss
 *
 */
public class UpperTriangleShape extends AbstractShape {
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
  public UpperTriangleShape(BufferedArtImage image, int w, int h) {
    super(image, w, h);
  }

  @Override
  protected AbstractCalculator getCalculator() {
    return UpperTriangleCalculator.getInstance();
  }
}
