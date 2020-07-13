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
public final class RightCalculator extends AbstractCalculator {
  private static RightCalculator instance;

  /**
   * Get the one and only instance of the calculator.
   *
   * @return the instance
   */
  public static RightCalculator getInstance() {
    if (instance == null) {
      instance = new RightCalculator();
    }
    return instance;
  }

  private RightCalculator() {
  }

  @Override
  protected Iterator<Integer> getIteratorForColumn(int w, int h, int x) {
    float m = (1F * h) / w;

    int yMin = (int) Math.floor(Math.max((h - (x * m)), 0));
    int yMax = (int) Math.ceil(Math.max(x * m, 0));

    return (x >= w / 2 ? IntStream.range(yMin, yMax) : IntStream.empty()).iterator();
  }
}
