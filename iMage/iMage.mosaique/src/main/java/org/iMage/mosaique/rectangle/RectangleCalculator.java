package org.iMage.mosaique.rectangle;

import org.iMage.mosaique.AbstractCalculator;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.image.BufferedImage;

/**
 * Helper class for the {@link RectangleArtist} and {@link RectangleShape}.
 *
 * @author Dominik Fuchss
 *
 */
public final class RectangleCalculator extends AbstractCalculator {
  private static RectangleCalculator calc = null;

  private RectangleCalculator() {}

  @Override
  public Iterator<Integer> getPixelIterator(BufferedImage region) {
      ArrayList<Integer> pixels = new ArrayList<>();

      for (int x = 0; x < region.getWidth(); x++) {
          for (int y = 0; y < region.getHeight(); y++) {
            pixels.add(region.getRGB(x, y));
          }
      }

      return pixels.iterator();
  }

  public static RectangleCalculator getCalculator() {
      if (calc == null) {
          calc = new RectangleCalculator();
      }
      return calc;
  }

}
