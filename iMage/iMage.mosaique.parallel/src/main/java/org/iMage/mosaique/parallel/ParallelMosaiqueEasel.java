package org.iMage.mosaique.parallel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.image.BufferedImage;
import org.iMage.mosaique.base.BufferedArtImage;

/**
 * This class handles the parallel execution of {@link MosaiqueEasel}.
 *
 * @author Nils Pukropp
 * @version 0.0.1-SNAPSHOT
 */
public final class ParallelMosaiqueEasel {
    private final ExecutorService exec;


    /**
     * Create a new {@link ParallelMosaiqueEasel} that gets its {@link ParallelMosaiqueEasel#threadCount}
     * parsed as paramater. Shouldn't be less than 1. Would be weird with negative threads.
     *
     * @param threadCount amount of threads
     */
    public ParallelMosaiqueEasel(final int threadCount) {

        if (threadCount < 1) {
            throw new IllegalArgumentException("Thread count can't be less"
                    + " then 1");
        }

        exec = Executors.newFixedThreadPool(threadCount);
    }

    /**
     * Creates a new {@link ParallelMosaiqueEasel} that gets its {@link ParallelMosaiqueEasel#threadCount}
     * from {@link Runtime#availableProcessors}. Should be optimal amount of logical
     * cores and not just physical cores.
     *
     */
    public ParallelMosaiqueEasel() {
        int threadCount = Runtime.getRuntime().availableProcessors();

        if (threadCount < 1) {
            throw new IllegalArgumentException("Your cpu is about to die or the universe");
        }

        exec = Executors.newFixedThreadPool(threadCount);
    }


    public BufferedImage createMosaique(BufferedImage input , ParallelRectangleArtist artist) {

        int tileWidth = artist.getTileWidth();
        int tileHeight = artist.getTileHeight();

        BufferedArtImage image = new BufferedArtImage(input);
        BufferedArtImage result = image.createBlankImage();

        for (int x = 0; x < image.getWidth(); x += tileWidth) {
            for (int y = 0; y < image.getHeight(); y += tileHeight) {
                paintAt(x, y, image, result, artist, tileWidth, tileHeight);
            }
        }

        exec.shutdown();

        return result.toBufferedImage();
    }

    private void paintAt(int x, int y, BufferedArtImage image, BufferedArtImage result,
            ParallelRectangleArtist artist, int tileWidth, int tileHeight) {
                exec.execute(new Runnable() {
                    @Override
                    public void run() {
                        int width = x + tileWidth < image.getWidth()
                            ? tileWidth : image.getWidth() - x;
                        int height = y + tileHeight < image.getHeight()
                            ? tileHeight : image.getHeight() - y;

                        BufferedArtImage sub = image.getSubimage(x, y, width, height);
                        BufferedArtImage tile = artist.getTileForRegion(sub);
                        result.setSubimage(x, y, tile);
                    }

                });

    }
}
