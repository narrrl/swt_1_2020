package org.iMage.mosaique.parallel;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 * This class handles the parallel execution of {@link MosaiqueEasel}.
 *
 * @author Nils Pukropp
 * @version 0.0.1-SNAPSHOT
 */
public final class ParallelMosaiqueEasel {
    private final ExecutorService exec;
    private final int threadCount;


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

        this.threadCount = threadCount;
        exec = Executors.newCachedThreadPool();
    }

    /**
     * Creates a new {@link ParallelMosaiqueEasel} that gets its {@link ParallelMosaiqueEasel#threadCount}
     * from {@link Runtime#availableProcessors}. Should be optimal amount of logical
     * cores and not just physical cores.
     *
     */
    public ParallelMosaiqueEasel() {
        threadCount = Runtime.getRuntime().availableProcessors();

        if (threadCount < 1) {
            throw new IllegalArgumentException("Your cpu is about to die or the universe");
        }

        exec = Executors.newCachedThreadPool();
    }

    public void start() {

        // TODO: implement

    }

}
