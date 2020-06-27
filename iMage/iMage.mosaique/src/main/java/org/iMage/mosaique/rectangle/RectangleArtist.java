package org.iMage.mosaique.rectangle;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.iMage.mosaique.AbstractArtist;
import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.base.IMosaiqueArtist;
import org.iMage.mosaique.base.IMosaiqueShape;

/**
 * This class represents an {@link IMosaiqueArtist} who uses rectangles as tiles.
 *
 * @author Dominik Fuchss
 *
 */
public class RectangleArtist extends AbstractArtist implements IMosaiqueArtist<BufferedArtImage> {

  private List<RectangleShape> shapes;

  /**
   * Create an artist who works with {@link RectangleShape RectangleShapes}
   *
   * @param images
   *          the images for the tiles
   * @param tileWidth
   *          the desired width of the tiles
   * @param tileHeight
   *          the desired height of the tiles
   * @throws IllegalArgumentException
   *           iff tileWidth or tileHeight &lt;= 0, or images is empty.
   */
  public RectangleArtist(Collection<BufferedArtImage> images, int tileWidth, int tileHeight) {
    super(tileWidth, tileHeight);
    if (images.isEmpty()) {
      throw new IllegalArgumentException("no tiles provided");
    }

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
  protected void drawTileForRegion(BufferedImage region, BufferedArtImage target) {
    int average = RectangleCalculator.averageColor(region);
    IMosaiqueShape<BufferedArtImage> tile = findNearest(average, shapes);
    tile.drawMe(target);
  }

}
