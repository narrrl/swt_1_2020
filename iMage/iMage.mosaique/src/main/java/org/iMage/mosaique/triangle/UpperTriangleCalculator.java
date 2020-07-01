package org.iMage.mosaique.triangle;

import org.iMage.mosaique.AbstractCalculator;

public final class UpperTriangleCalculator extends AbstractCalculator {
    private static UpperTriangleCalculator calc;

    private UpperTriangleCalculator() {}

    public static UpperTriangleCalculator gCalculator() {
        if (calc == null) {
            calc = new UpperTriangleCalculator();
        }
        return calc;
    }

}
