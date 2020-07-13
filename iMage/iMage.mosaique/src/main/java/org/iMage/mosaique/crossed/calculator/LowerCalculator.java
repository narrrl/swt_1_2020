package org.iMage.mosaique.crossed.calculator;

import java.util.Iterator;
import java.util.stream.IntStream;

import org.iMage.mosaique.AbstractCalculator;
import org.iMage.mosaique.AbstractShape;
import org.iMage.mosaique.crossed.CrossedRectangleArtist;

/**
 * Helper class for the {@link CrossedRectangleArtist} and {@link AbstractShape CrossedShapes}.
 *
 * @author Dominik Fuchss
 *
 */
public final class LowerCalculator extends AbstractCalculator {
  private static LowerCalculator instance;

  /**
   * Get the one and only instance of the calculator.
   *
   * @return the instance
   */
  public static LowerCalculator getInstance() {
    if (instance == null) {
      instance = new LowerCalculator();
    }
    return instance;
  }

  private LowerCalculator() {
  }

  @Override
  protected Iterator<Integer> getIteratorForColumn(int w, int h, int x) {
    float m = (1F * h) / w;

    int yMin;
    if (x < w / 2) {
      yMin = (int) Math.ceil(Math.max((h - (x * m)), 0));
    } else {
      yMin = (int) Math.ceil(Math.max(x * m, 0));
    }

    return IntStream.range(yMin, h).iterator();
  }
}
