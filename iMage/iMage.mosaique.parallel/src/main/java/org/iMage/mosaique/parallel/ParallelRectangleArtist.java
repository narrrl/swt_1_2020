package org.iMage.mosaique.parallel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.image.BufferedImage;
import org.iMage.mosaique.AbstractArtist;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.iMage.mosaique.rectangle.RectangleCalculator;
import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.base.IMosaiqueArtist;
import org.iMage.mosaique.base.IMosaiqueShape;


public final class ParallelRectangleArtist extends AbstractArtist
    implements IMosaiqueArtist<BufferedArtImage> {

    private List<ParallelRectangleShape> shapes;

    public ParallelRectangleArtist(Collection<BufferedImage> images,
            int tileWidth, int tileHeight) {
        super(tileWidth, tileHeight);

        if (images.isEmpty()) {
            throw new IllegalArgumentException("no tiles provided");
        }

        start(Runtime.getRuntime().availableProcessors(), images);

    }

    public ParallelRectangleArtist(Collection<BufferedImage> images,
            int tileWidth, int tileHeight, int threadCount) {
        super(tileWidth, tileHeight);

        if (images.isEmpty()) {
            throw new IllegalArgumentException("no tiles provided");
        }

        start(threadCount, images);
    }

    private void start(final int threadCount,
            final Collection<BufferedImage> images) {

        if (threadCount < 1) {
            throw new IllegalArgumentException("Thread count can't be less then 1");
        }

        ExecutorService exec = Executors.newFixedThreadPool(threadCount);

        this.shapes = new ArrayList<>();
        for (BufferedImage image : images) {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    shapes.add(new ParallelRectangleShape(image, tileWidth, tileHeight));
                }
            });
        }

        exec.shutdown();
        try {
            exec.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public List<BufferedImage> getThumbnails() {
        return shapes.stream().map(ParallelRectangleShape::getThumbnail)
            .collect(Collectors.toList());
    }

    @Override
    protected void drawTileForRegion(BufferedImage region, BufferedArtImage target) {
        int average = RectangleCalculator.getInstance().averageColor(region);
        IMosaiqueShape<BufferedArtImage> tile = findNearest(average, shapes);
        tile.drawMe(target);
    }

}
