package org.iMage.mosaique.triangle;

import org.iMage.mosaique.AbstractShape;
import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.base.IMosaiqueShape;

/**
 * This abstract class represents a lower triangle as {@link IMosaiqueShape} based on an
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
public class LowerTriangleShape extends AbstractShape {
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
  public LowerTriangleShape(BufferedArtImage image, int w, int h) {
    super(image, w, h);
  }

  @Override
  protected void drawShape(BufferedArtImage targetRect, int w, int h) {
    float m = (1F * h) / w;
    for (int x = 0; x < w; x++) {
      float yBound = Math.max((x + 1) * m, 0);
      for (int y = h - 1; y >= yBound; y--) {
        targetRect.setRGB(x, y, image.getRGB(x, y));
      }
    }
  }

  @Override
  protected int calcAverage() {
    return TriangleCalculator.averageLowerColor(this.image);
  }

}
