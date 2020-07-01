package org.iMage.mosaique.triangle;


import org.iMage.mosaique.AbstractCalculator;

public final class LowerTriangleCalculator extends AbstractCalculator {
    private static LowerTriangleCalculator calc;

    public static LowerTriangleCalculator getCalculator() {
        if (calc == null) {
            calc = new LowerTriangleCalculator();
        }
        return calc;
    }

}
