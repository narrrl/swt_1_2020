package org.iMage.tiler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ArtisticPanel extends JPanel {

    public ArtisticPanel() {
        setSize(800, 80);
        setPreferredSize(new Dimension(800, 80));
        setLayout(new GridLayout(1, 5, 20, 0));
        setOpaque(false);
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true), "Artistic",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.LIGHT_GRAY));
    }
}
