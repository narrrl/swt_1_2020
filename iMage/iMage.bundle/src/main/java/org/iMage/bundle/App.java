package org.iMage.bundle;

/**
 * The "main" class of the bundle.
 *
 * @author Dominik Fuchss
 *
 */
public final class App {
  private App() {
    throw new IllegalAccessError();
  }

  /**
   * The "main" method.
   *
   * @param args
   *          the command line arguments
   * @see org.jis.Main
   */
  public static void main(String[] args) {
    org.jis.Main.main(args);
  }
}
