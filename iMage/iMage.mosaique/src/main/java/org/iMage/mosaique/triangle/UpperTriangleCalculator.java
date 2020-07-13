package org.iMage.mosaique.triangle;

import org.iMage.mosaique.AbstractCalculator;

import java.util.Iterator;
import java.util.stream.IntStream;

/**
 * Helper class for the {@link TriangleArtist} and {@link UpperTriangleShape}.
 *
 * @author Dominik Fuchss
 */
public final class UpperTriangleCalculator extends AbstractCalculator {
  private static UpperTriangleCalculator instance;

  /**
   * Get the one and only instance of the calculator.
   *
   * @return the instance
   */
  public static UpperTriangleCalculator getInstance() {
    if (instance == null) {
      instance = new UpperTriangleCalculator();
    }
    return instance;
  }

  private UpperTriangleCalculator() {
  }

  @Override
  protected Iterator<Integer> getIteratorForColumn(int w, int h, int x) {
    float m = (1F * h) / w;
    float yBound = Math.min((x + 1) * m, h);
    return IntStream.range(0, (int) Math.floor(yBound)).iterator();
  }
}
