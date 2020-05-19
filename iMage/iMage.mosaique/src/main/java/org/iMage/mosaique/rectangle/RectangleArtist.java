package org.iMage.mosaique.rectangle;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.base.IMosaiqueArtist;

/**
 * This class represents an {@link IMosaiqueArtist} who uses rectangles as tiles.
 *
 * @author Dominik Fuchss
 *
 */
public class RectangleArtist implements IMosaiqueArtist<BufferedArtImage> {
  Collection<BufferedArtImage> images;
  final int tileWidht;
  final int tileHeigt;

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
    if (images.isEmpty()) throw new IllegalArgumentException("images shouldn't be empty");
    if (tileWidth == 0 || tileHeight == 0) throw new IllegalArgumentException(tileHeight + " or "
            + tileWidth + " is 0");
    this.images = images;
    this.tileHeigt = tileHeight;
    this.tileWidht = tileWidth;
  }

  @Override
  public List<BufferedImage> getThumbnails() {
    return images.stream().map(BufferedArtImage::toBufferedImage).collect(Collectors.toList());
  }

  @Override
  public BufferedArtImage getTileForRegion(BufferedArtImage region) {
    throw new RuntimeException("not implemented");
  }

  @Override
  public int getTileWidth() {
    return this.tileWidht;
  }

  @Override
  public int getTileHeight() {
    return this.tileHeigt;
  }
}
