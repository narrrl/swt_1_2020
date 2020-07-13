package org.iMage.mosaique.triangle;

import org.iMage.mosaique.AbstractArtist;
import org.iMage.mosaique.AbstractShape;
import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.base.IMosaiqueArtist;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents an {@link IMosaiqueArtist} who uses triangles as tiles.
 *
 * @author Dominik Fuchss
 */
public class TriangleArtist extends AbstractArtist implements IMosaiqueArtist<BufferedArtImage> {

  private List<AbstractShape> upper;
  private List<AbstractShape> lower;

  /**
   * Create an artist who works with TriangleShapes
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
  public TriangleArtist(Collection<BufferedArtImage> images, int tileWidth, int tileHeight) {
    super(tileWidth, tileHeight);
    if (images.isEmpty()) {
      throw new IllegalArgumentException("no tiles provided");
    }

    this.upper = new ArrayList<>();
    this.lower = new ArrayList<>();

    for (var image : images) {
      upper.add(new UpperTriangleShape(image, tileWidth, tileHeight));
      lower.add(new LowerTriangleShape(image, tileWidth, tileHeight));
    }
  }

  @Override
  public List<BufferedImage> getThumbnails() {
    List<AbstractShape> shapes = new ArrayList<>();
    shapes.addAll(lower);
    shapes.addAll(upper);
    return shapes.stream().map(AbstractShape::getThumbnail).collect(Collectors.toList());
  }

  @Override
  public void drawTileForRegion(BufferedImage region, BufferedArtImage target) {
    int averageUpper = UpperTriangleCalculator.getInstance().averageColor(region);
    int averageLower = LowerTriangleCalculator.getInstance().averageColor(region);

    var upperImage = findNearest(averageUpper, upper);
    var lowerImage = findNearest(averageLower, lower);

    upperImage.drawMe(target);
    lowerImage.drawMe(target);
  }

}
