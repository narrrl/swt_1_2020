package org.iMage;

import org.jis.Main;


/**
 * This class starts the {@link org.jis.Main} Jmjrst appplication
 *
 * @author Nils Pukropp
 * @since 0.0.1-SNAPSHOT
 */
public final class App {

    /**
     * Don't instantiate
     */
    private App() {
        throw new IllegalAccessError();
    }

    public static void main(String[] args) {
        Main.main(args);
    }
}
