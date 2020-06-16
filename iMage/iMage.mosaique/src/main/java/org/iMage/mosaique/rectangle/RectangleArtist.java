package org.iMage.mosaique.rectangle;

import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.base.IMosaiqueArtist;
import org.iMage.mosaique.base.IMosaiqueShape;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents an {@link IMosaiqueArtist} who uses rectangles as tiles.
 *
 * @author Dominik Fuchss
 */
public class RectangleArtist implements IMosaiqueArtist<BufferedArtImage> {

  private static final double EPS = 1E-8;

  private List<RectangleShape> shapes;

  private int tileWidth;
  private int tileHeight;

  /**
   * Create an artist who works with {@link RectangleShape RectangleShapes}
   *
   * @param images
   *     the images for the tiles
   * @param tileWidth
   *     the desired width of the tiles
   * @param tileHeight
   *     the desired height of the tiles
   * @throws IllegalArgumentException
   *     iff tileWidth or tileHeight &lt;= 0, or images is empty.
   */
  public RectangleArtist(Collection<BufferedArtImage> images, int tileWidth, int tileHeight) {
    if (images.isEmpty()) {
      throw new IllegalArgumentException("no tiles provided");
    }
    if (tileWidth <= 0 || tileHeight <= 0) {
      throw new IllegalArgumentException("tileWidth and tileHeight have to be > 0");
    }
    this.tileWidth = tileWidth;
    this.tileHeight = tileHeight;
    this.shapes = new ArrayList<>();
    for (BufferedArtImage image : images) {
      shapes.add(new RectangleShape(image, tileWidth, tileHeight));
    }
  }

  @Override
  public List<BufferedImage> getThumbnails() {
    return shapes.stream().map(RectangleShape::getThumbnail).collect(Collectors.toList());
  }

  @Override
  public BufferedArtImage getTileForRegion(BufferedArtImage region) {
    if (region.getWidth() > this.tileWidth || region.getHeight() > this.tileHeight) {
      throw new IllegalArgumentException(
          "requested tiling is greater than tileWidth or tileHeight");
    }

    int average = RectangleCalculator.averageColor(region.toBufferedImage());

    IMosaiqueShape<BufferedArtImage> tile = findNearest(average);
    BufferedArtImage result = region.createBlankImage();
    tile.drawMe(result);

    return result;
  }

  @Override
  public int getTileWidth() {
    return tileWidth;
  }

  @Override
  public int getTileHeight() {
    return tileHeight;
  }

  /**
   * Find the shape with the best matching color from the list of shapes.
   *
   * @param target
   *     the target color as ARGB.
   * @return the best matching shape
   */
  protected final IMosaiqueShape<BufferedArtImage> findNearest(int target) {
    List<RectangleShape> nearest = new ArrayList<>();

    Iterator<RectangleShape> iter = shapes.iterator();
    RectangleShape shape = iter.next();
    nearest.add(shape);

    double dist = colorError(target, shape.getAverageColor());

    while (iter.hasNext()) {
      RectangleShape next = iter.next();
      double nextDist = colorError(target, next.getAverageColor());

      if (Math.abs(dist - nextDist) < EPS) {
        // Distances equal
        nearest.add(next);
      } else if (nextDist < dist) {
        // New smallest
        nearest.clear();
        nearest.add(next);
        dist = nextDist;
      }
    }
    return nearest.get((int) (Math.random() * nearest.size()));
  }

  /**
   * Calculate the difference between two argb colors as euclidean distance.<br> 
   * Range: [0, sqrt(4 * pow(255, 2))]
   *
   * @param colorA
   *     the first color
   * @param colorB
   *     the second color
   * @return the difference of the colors
   */
  private static double colorError(int colorA, int colorB) {
    Color a = new Color(colorA, true);
    Color b = new Color(colorB, true);
    double colorError = Math.pow(a.getRed() - b.getRed(), 2);
    colorError += Math.pow(a.getGreen() - b.getGreen(), 2);
    colorError += Math.pow(a.getBlue() - b.getBlue(), 2);
    colorError += Math.pow(a.getAlpha() - b.getAlpha(), 2);
    return Math.sqrt(colorError);
  }

}
