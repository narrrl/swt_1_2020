package org.iMage.mosaique.rectangle;

import org.iMage.mosaique.AbstractCalculator;

/**
 * Helper class for the {@link RectangleArtist} and {@link RectangleShape}.
 *
 * @author Dominik Fuchss
 *
 */
public final class RectangleCalculator extends AbstractCalculator {
  private static RectangleCalculator calc = null;

  private RectangleCalculator() {}

  public static RectangleCalculator getCalculator() {
      if (calc == null) {
          calc = new RectangleCalculator();
      }
      return calc;
  }

}
