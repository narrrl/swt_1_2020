package org.iMage.mosaique.rectangle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.base.IMosaiqueArtist;

/**
 * This class represents an {@link IMosaiqueArtist} who uses rectangles as tiles.
 *
 * @author Dominik Fuchss
 */
public class RectangleArtist implements IMosaiqueArtist<BufferedArtImage> {
    LinkedList<BufferedArtImage> images;
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
        if (images.isEmpty()) throw new IllegalArgumentException("images shouldn't be empty");
        if (tileWidth == 0 || tileHeight == 0) throw new IllegalArgumentException(tileHeight + " or "
                + tileWidth + " is 0");
        this.images = new LinkedList<>();
        this.images.addAll(images);
        this.tileHeigt = tileHeight;
        this.tileWidht = tileWidth;
    }

    @Override
    public List<BufferedImage> getThumbnails() {
        return images.stream().map(BufferedArtImage::toBufferedImage).collect(Collectors.toList());
    }

    @Override
    public BufferedArtImage getTileForRegion(final BufferedArtImage region) {
        Vector<Integer> rVec = toColorVector(region);
        double dist = euclideanDistance(rVec, toColorVector(images.getFirst()));
        BufferedArtImage out = images.getFirst();
        double tmpDist;
      for (BufferedArtImage img : images) {
        tmpDist = euclideanDistance(rVec, toColorVector(img));
        if (tmpDist < dist) out = img;
        dist = Math.min(dist, tmpDist);
      }
      return out;
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
          Color pixel = new Color(img.getRGB(x,y));
          red += pixel.getRed();
          blue += pixel.getBlue();
          green += pixel.getGreen();
          alpha += pixel.getAlpha();
        }
      }
      float total = w * h * 255;
      return new Color(red / total, green / total, blue / total, alpha / total);
    }

    private double euclideanDistance(final Vector<Integer> p, final Vector<Integer> q) {
      if (p.size() != q.size()) throw new IllegalArgumentException("vectors must have same dimensions");
      double sum = 0;
      for (int i = 0; i < p.size(); i++) {
        sum += Math.pow(p.get(i) - q.get(i),2);
      }
      return Math.sqrt(sum);
    }

    private Vector<Integer> toColorVector(final BufferedArtImage img) {
      Vector<Integer> vec = new Vector<>();
      Color c = RectangleArtist.averageColor(img.toBufferedImage());
      vec.add(c.getAlpha());
      vec.add(c.getRed());
      vec.add(c.getGreen());
      vec.add(c.getBlue());
      return vec;
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
