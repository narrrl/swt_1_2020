package org.iMage.mosaique.rectangle;

import org.iMage.mosaique.base.BufferedArtImage;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class RectangleArtistTest {
    private RectangleArtist artist;
    private ArrayList<BufferedArtImage> images;

    @Before
    public final void createArtist() throws IOException {
        String path = Paths.get("").toAbsolutePath().toString() + File.separator + "target"+ File.separator
                + "test-classes" + File.separator + "org" + File.separator + "iMage" + File.separator + "mosaique"
                + File.separator + "images" + File.separator;
        final File resource = new File(path);
        File[] files = resource.listFiles();
        if (files == null) throw new IOException("no images");
        images = new ArrayList<>();
        for (File f :
                files) {
            if (f != null) {
                images.add(new BufferedArtImage(ImageIO.read(f)));
            }
        }
        artist = new RectangleArtist(images,16,16);
    }

    @Test
    public final void getTileForRegionTestWithSameImage() {
        for (BufferedArtImage source : images) {
            BufferedArtImage testPic = artist.getTileForRegion(source);
            assertTrue(RectangleArtistTest.imageEquals(source.toBufferedImage(), testPic.toBufferedImage()));
        }
    }

    @Test
    public final void getTileForRegion() throws IOException {
        String path = Paths.get("").toAbsolutePath().toString() + File.separator + "target"+ File.separator
                + "test-classes" + File.separator + "shino.jpg";
        BufferedArtImage source = new BufferedArtImage(ImageIO.read(new File(path)));
        BufferedArtImage result1 = artist.getTileForRegion(source);
        BufferedArtImage result2 = artist.getTileForRegion(source);
        assertTrue(imageEquals(result1.toBufferedImage(),result2.toBufferedImage()));
    }

    static boolean imageEquals(BufferedImage expected, BufferedImage actual) {
        if (expected == null || actual == null) {
            return false;
        }

        if (expected.getHeight() != actual.getHeight()) {
            return false;
        }

        if (expected.getWidth() != actual.getWidth()) {
            return false;
        }

        for (int i = 0; i < expected.getHeight(); i++) {
            for (int j = 0; j < expected.getWidth(); j++) {
                if (expected.getRGB(j, i) != actual.getRGB(j, i)) {
                    return false;
                }
            }
        }

        return true;
    }

    @After
    public final void cleanUp() {
        artist = null;
        images = null;
    }
}
