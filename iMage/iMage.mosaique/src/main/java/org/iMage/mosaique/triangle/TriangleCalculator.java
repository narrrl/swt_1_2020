package org.iMage.mosaique.triangle;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Helper class for the {@link TriangleArtist} and {@link TriangleShape}.
 *
 * @author Dominik Fuchss
 *
 */
public final class TriangleCalculator {
  private TriangleCalculator() {
    throw new IllegalAccessError();
  }

  /**
   * Calculate average for upper triangle of a region.
   *
   * @param region
   *          the region
   * @return the average color as argb
   */
  public static int averageUpperColor(BufferedImage region) {
    float m = (1F * region.getHeight()) / region.getWidth();
    long r = 0;
    long g = 0;
    long b = 0;
    long a = 0;
    int ctr = 0;

    for (int x = 0; x < region.getWidth(); x++) {
      float yBound = Math.min((x + 1) * m, region.getHeight());
      for (int y = 0; y < yBound; y++) {
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

  /**
   * Calculate average for lower triangle of a region.
   *
   * @param region
   *          the region
   * @return the average color as argb
   */
  public static int averageLowerColor(BufferedImage region) {
    float m = (1F * region.getHeight()) / region.getWidth();
    long r = 0;
    long g = 0;
    long b = 0;
    long a = 0;
    int ctr = 0;

    for (int x = 0; x < region.getWidth(); x++) {
      float yBound = Math.max((x + 1) * m, 0);
      for (int y = region.getHeight() - 1; y >= yBound; y--) {
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
