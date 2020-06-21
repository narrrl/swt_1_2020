package org.iMage.tiler.panels;

import org.iMage.tiler.Tiler;
import org.iMage.tiler.listeners.SaveAction;
import org.iMage.tiler.listeners.SelectAction;

import javax.swing.*;
import java.awt.*;

public class ConfigurationPanel extends JPanel {
    private final Tiler tiler;
    private static final Dimension DIM_BUTTON = new Dimension(200, 32);
    private JButton save;



    public ConfigurationPanel(final Tiler tiler) {
        this.tiler = tiler;
        setSize(800, 80);
        setPreferredSize(new Dimension(800, 80));
        setOpaque(false);
        setLayout(null);
        createButtons();
    }

    private void createButtons() {
        JButton load = new JButton("Load Input");
        save = new JButton("Save Result");

        load.addActionListener(new SelectAction(tiler));
        load.setPreferredSize(DIM_BUTTON);
        load.setSize(DIM_BUTTON);
        load.setAlignmentX(Component.CENTER_ALIGNMENT);
        load.setBackground(Color.LIGHT_GRAY);
        load.setLocation(100, 15);

        save.addActionListener(new SaveAction(tiler));
        save.setAlignmentX(Component.CENTER_ALIGNMENT);
        save.setPreferredSize(DIM_BUTTON);
        save.setSize(DIM_BUTTON);
        save.setBackground(Color.LIGHT_GRAY);
        save.setLocation(500, 15);
        save.setEnabled(false);

        add(load);
        add(save);
    }

    public void setButtonEnabled(final boolean b) {
        save.setEnabled(b);
    }
}
