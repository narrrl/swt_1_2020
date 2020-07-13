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
public final class UpperCalculator extends AbstractCalculator {
  private static UpperCalculator instance;

  /**
   * Get the one and only instance of the calculator.
   *
   * @return the instance
   */
  public static UpperCalculator getInstance() {
    if (instance == null) {
      instance = new UpperCalculator();
    }
    return instance;
  }

  private UpperCalculator() {
  }

  @Override
  protected Iterator<Integer> getIteratorForColumn(int w, int h, int x) {
    float m = (1F * h) / w;

    int yMax;
    if (x < w / 2) {
      yMax = (int) Math.floor(Math.max(x * m, 0));
    } else {
      yMax = (int) Math.floor(Math.max((h - (x * m)), 0));
    }

    return IntStream.range(0, yMax).iterator();
  }
}
