package org.iMage.mosaique.rectangle;

import org.iMage.mosaique.base.BufferedArtImage;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RectangleArtistTest {
    private RectangleArtist artist;
    private ArrayList<BufferedArtImage> images;

    @Before
    public final void createArtist() throws IOException, URISyntaxException {
        String path = this.getClass().getResource(File.separator).toURI().getPath() + "org"
                + File.separator + "iMage" + File.separator + "mosaique" + File.separator + "images" + File.separator;
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
        artist = new RectangleArtist(images,256,256);
    }

    @Test
    public final void getTileForRegionTestWithSameImage() {
        for (int i = 0; i < images.size(); i++) {
            BufferedArtImage source = images.get(i);
            BufferedArtImage testPic = artist.getTileForRegion(source);
            assertTrue(RectangleArtistTest.imageEquals(source.toBufferedImage(), testPic.toBufferedImage()));
        }
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
