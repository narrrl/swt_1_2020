package org.iMage.mosaique.rectangle;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.base.IMosaiqueArtist;

/**
 * This class represents an {@link IMosaiqueArtist} who uses rectangles as tiles.
 *
 * @author Dominik Fuchss
 */
public class RectangleArtist implements IMosaiqueArtist<BufferedArtImage> {
    HashMap<Color, BufferedImage> imagesMap;
    final int tileWidht;
    final int tileHeigt;

    /**
     * Create an artist who works with {@link RectangleShape RectangleShapes}
     *
     * @param images     the images for the tiles
     * @param tileWidth  the desired width of the tiles
     * @param tileHeight the desired height of the tiles
     * @throws IllegalArgumentException iff tileWidth or tileHeight &lt;= 0, or images is empty.
     */
    public RectangleArtist(Collection<BufferedArtImage> images, int tileWidth, int tileHeight) {
        if (images.isEmpty()) {
            throw new IllegalArgumentException("images shouldn't be empty");
        }

        if (tileWidth == 0 || tileHeight == 0) {
            throw new IllegalArgumentException(tileHeight + " or "
                + tileWidth + " is 0");
        }
        this.imagesMap = new HashMap<>();
        images.forEach(img -> imagesMap.put(averageColor(img.toBufferedImage()), img.toBufferedImage()));
        this.tileHeigt = tileHeight;
        this.tileWidht = tileWidth;
    }

    @Override
    public List<BufferedImage> getThumbnails() {
        return List.copyOf(imagesMap.values());
    }

    @Override
    public BufferedArtImage getTileForRegion(final BufferedArtImage region) {
      Color current = averageColor(region.toBufferedImage());
      Color nearest = null;
        int shortestDistance;
        shortestDistance = Integer.MAX_VALUE;

        for (Color c : imagesMap.keySet()) {
            int distance;

            distance = RectangleArtist.getDistance(current, c);

            if (distance < shortestDistance) {
                nearest = c;
                shortestDistance = distance;
            }
        }
      return new BufferedArtImage(imagesMap.get(nearest));
    }

    public static Color averageColor(final BufferedImage img) {
      final int h = img.getHeight();
      final int w = img.getWidth();
      long alpha = 0;
      long red = 0;
      long blue = 0;
      long green = 0;
      for (int x = img.getMinX(); x < w; x++) {
        for (int y = img.getMinY(); y < h; y++) {
          Color pixel = new Color(img.getRGB(x, y));
          red += pixel.getRed();
          blue += pixel.getBlue();
          green += pixel.getGreen();
          alpha += pixel.getAlpha();
        }
      }
      float total = w * h * 255;
      return new Color(red / total, green / total, blue / total, alpha / total);
    }

    private static int getDistance(final Color current, final Color match) {
        int redDifference;
        int greenDifference;
        int blueDifference;
        int alphaDifference;
        alphaDifference = current.getAlpha() - match.getAlpha();
        redDifference = current.getRed() - match.getRed();
        greenDifference = current.getGreen() - match.getGreen();
        blueDifference = current.getBlue() - match.getBlue();

        return alphaDifference * alphaDifference + redDifference * redDifference + greenDifference * greenDifference
                + blueDifference * blueDifference;
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
