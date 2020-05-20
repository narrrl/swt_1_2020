package org.iMage.mosaique;

import java.awt.image.BufferedImage;

import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.base.IMosaiqueArtist;
import org.iMage.mosaique.base.IMosaiqueEasel;
import org.iMage.mosaique.rectangle.RectangleShape;

/**
 * This class defines an {@link IMosaiqueEasel} which operates on {@link BufferedArtImage
 * BufferedArtImages}.
 *
 * @author Dominik Fuchss
 *
 */
public class MosaiqueEasel implements IMosaiqueEasel<BufferedArtImage> {

  /**
   * Lasset die threads schwitzen
   * @param input input image
   * @param artist to get tile stuff
   * @return the mosaique
   */
  @Override
  public BufferedImage createMosaique(BufferedImage input,
      IMosaiqueArtist<BufferedArtImage> artist) {
    final int hPic = input.getHeight();
    final int wPic = input.getWidth();
    final int hTil = artist.getTileHeight();
    final int wTil = artist.getTileWidth();
    int xImg = 0;
    int yImg = 0;
    float hScale = hPic / (float) hTil;
    float wScale = wPic / (float) wTil;
    if (hScale - (int) hScale > 0f) {
      hScale++;
    }

    if (wScale - (int) wScale > 0f) {
      wScale++;
    }

    BufferedArtImage[] tiles = new BufferedArtImage[(int) hScale * (int) wScale];
    int counter = 1;
    for (int i = 0; i < tiles.length; i++) {
        tiles[i] = new BufferedArtImage(wTil, hTil);
        for (int x = 0; x < wTil; x++) {
          if (xImg + x < wPic) {
            for (int y = 0; y < hTil; y++) {
              if (y + yImg < hPic) {
                tiles[i].setRGB(x, y, input.getRGB(xImg + x, yImg + y));
              }

            }
          }
        }
        xImg += wTil;
        if (counter % (int) wScale == 0) {
          yImg += hTil;
          xImg = 0;
        }
        counter++;
    }
    for (int i = 0; i < tiles.length; i++) {
      tiles[i] = new BufferedArtImage(new RectangleShape(artist.getTileForRegion(tiles[i]), wTil, hTil).getThumbnail());
      System.out.println("found image for tile " + i + " of " + (tiles.length - 1));
    }
    counter = 1;
    BufferedArtImage out = new BufferedArtImage(wPic, hPic);
    int index = 0;
    xImg = 0;
    yImg = 0;
    for (BufferedArtImage tile : tiles) {
      for (int x = 0; x < wTil; x++) {
        if (xImg + x < wPic) {
          for (int y = 0; y < hTil; y++) {
            if (y + yImg < hPic) {
              out.setRGB(xImg + x, yImg + y, tile.getRGB(x, y));
            }

          }
        }
      }
      xImg += wTil;
      if (counter % (int) wScale == 0) {
        yImg += hTil;
        xImg = 0;
      }
      counter++;
      System.out.println("drawn tile " + index + " of " + tiles.length);
      index++;
    }
    return out.toBufferedImage();
  }
}
