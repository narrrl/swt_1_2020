package org.iMage.mosaique;

import java.awt.image.BufferedImage;

import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.base.IMosaiqueArtist;
import org.iMage.mosaique.base.IMosaiqueEasel;

/**
 * This class defines an {@link IMosaiqueEasel} which operates on {@link BufferedArtImage
 * BufferedArtImages}.
 *
 *
 * @author Dominik Fuchss
 *
 */
public class MosaiqueEasel implements IMosaiqueEasel<BufferedArtImage> {

  @Override
  public BufferedImage createMosaique(BufferedImage input,
      IMosaiqueArtist<BufferedArtImage> artist) {
    int tileWidth = artist.getTileWidth();
    int tileHeight = artist.getTileHeight();

    BufferedArtImage image = new BufferedArtImage(input);
    BufferedArtImage result = image.createBlankImage();

    for (int x = 0; x < image.getWidth(); x += tileWidth) {
      for (int y = 0; y < image.getHeight(); y += tileHeight) {
        int width = x + tileWidth < image.getWidth() ? tileWidth : image.getWidth() - x;
        int height = y + tileHeight < image.getHeight() ? tileHeight : image.getHeight() - y;

        BufferedArtImage sub = image.getSubimage(x, y, width, height);
        BufferedArtImage tile = artist.getTileForRegion(sub);
        result.setSubimage(x, y, tile);
      }
    }
    return result.toBufferedImage();

  }

}
