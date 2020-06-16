package org.iMage.mosaique.rectangle;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Helper class for the {@link RectangleArtist} and {@link RectangleShape}.
 *
 * @author Dominik Fuchss
 *
 */
public final class RectangleCalculator {
  private RectangleCalculator() {
    throw new IllegalAccessError();
  }

  /**
   * Calculate the average color for an rectangle region.
   *
   * @param region
   *          the region
   * @return the color as ARGB
   */
  public static int averageColor(BufferedImage region) {
    long r = 0;
    long g = 0;
    long b = 0;
    long a = 0;
    int ctr = 0;

    for (int x = 0; x < region.getWidth(); x++) {
      for (int y = 0; y < region.getHeight(); y++) {
        int col = region.getRGB(x, y);

        Color c = new Color(col, true);
        r += c.getRed();
        g += c.getGreen();
        b += c.getBlue();
        a += c.getAlpha();
        ctr++;
      }

    }

    return new Color((int) (r / ctr), (int) (g / ctr), (int) (b / ctr), (int) (a / ctr)).getRGB();
  }

}
