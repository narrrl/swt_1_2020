package org.iMage.mosaique.parallel;

import org.apache.commons.lang3.time.StopWatch;
import org.iMage.mosaique.MosaiqueEasel;
import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.rectangle.RectangleArtist;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Performanzermittlung
 *
 * @author tk
 * @author Mathias
 * @author weigelt
 * @author Dominik Fuchss
 */
public class IPDPerformanceTest {

  /**
   * Anzahl der Messläufe.
   */
  private static final int NUMLOOPS = 5;

  /**
   * Alle zu testenden Fadenanzahlen.
   */
  private Integer[] threads = { 1, 2, 4, 8, 16, 32, 64, 128 };

  protected static final String TILE_DIR = "src/test/resources/tiles";
  protected static final String IN_PATH = "src/test/resources/parallel_test.png";

  protected static List<BufferedArtImage> getTiles() throws IOException {
    var tileFolder = new File(TILE_DIR);
    List<BufferedArtImage> tiles = new ArrayList<>();
    var files = new ArrayList<>(Arrays.asList(tileFolder.listFiles()));
    files.sort((f, g) -> f.getName().compareTo(g.getName()));
    for (var tile : files) {
      tiles.add(new BufferedArtImage(ImageIO.read(tile)));
    }
    return tiles;
  }

  private static final int TILE_WIDTH = 20;
  private static final int TILE_HEIGHT = 15;

  private List<BufferedArtImage> tilesAsAI;
  private List<BufferedImage> tilesAsI;
  private BufferedImage input;

  @Before
  public void loadImages() throws IOException {
    // Convert all to ARGB images ..
    input = new BufferedArtImage(ImageIO.read(new File(IN_PATH))).toBufferedImage();
    tilesAsAI = getTiles();
    tilesAsI = //
        getTiles().stream().map(BufferedArtImage::toBufferedImage).collect(Collectors.toList());
  }

  @Test
  public void testPerformance() {
    long time = System.currentTimeMillis();
    this.testPerformanceSequential();
    this.testPerformanceParallel();
    System.err.println("Die Messung ging " + ((System.currentTimeMillis() - time) / 1000) + " s");
  }

  private void testPerformanceParallel() {
    long[] measurements = new long[this.threads.length];

    System.err.println("Performanzmessung");
    for (int t = 0; t < this.threads.length; t++) {
      System.err.println("Starte Messung für " + this.threads[t] + " Fäden");
      int curThreadCount = this.threads[t];

      // Für mehr Messgenauigkeit :)
      StopWatch sw = new StopWatch();
      for (int loop = 0; loop < IPDPerformanceTest.NUMLOOPS; loop++) {
        // Start der Messung
        sw.reset();
        sw.start();
        this.executeParallel(curThreadCount);
        // Ende der Messung
        sw.stop();
        measurements[t] += sw.getTime();
      }
      measurements[t] /= IPDPerformanceTest.NUMLOOPS;
    }
    System.err.println("Messergebnisse:");
    System.err.println(Arrays.toString(measurements));
  }

  private void testPerformanceSequential() {
    long measurements = 0;

    System.err.println("Performanzmessung Sequenziell");

    // Für mehr Messgenauigkeit :)
    StopWatch sw = new StopWatch();
    for (int loop = 0; loop < IPDPerformanceTest.NUMLOOPS; loop++) {
      // Start der Messung
      sw.reset();
      sw.start();
      this.executeSequential();

      // Ende der Messung
      sw.stop();
      measurements += sw.getTime();
    }
    measurements /= IPDPerformanceTest.NUMLOOPS;
    System.err.println("Messergebnis:");
    System.err.println(measurements);
  }

  private BufferedImage executeSequential() {
    RectangleArtist artist = new RectangleArtist(tilesAsAI, TILE_WIDTH, TILE_HEIGHT);
    MosaiqueEasel easel = new MosaiqueEasel();
    return easel.createMosaique(input, artist);
  }

  private BufferedImage executeParallel(int threads) {
    var artist = new ParallelRectangleArtist(tilesAsI, TILE_WIDTH, TILE_HEIGHT, threads);
    ParallelMosaiqueEasel easel = new ParallelMosaiqueEasel(threads);
    return easel.createMosaique(input, artist);
  }

}
