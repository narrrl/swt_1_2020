package org.iMage.tiler.panels;

import org.iMage.tiler.Tiler;
import org.iMage.tiler.listeners.SaveAction;
import org.iMage.tiler.listeners.SelectAction;

import javax.swing.*;
import java.awt.*;

/**
 * This class creates the configuration panel for the {@link Tiler}
 * It contains two buttons to load a image and save a image
 */
public class ConfigurationPanel extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = -8108074923641314505L;

    private final Tiler tiler;
    private static final Dimension DIM_BUTTON = new Dimension(200, 32);
    private JButton save;



    public ConfigurationPanel(final Tiler tiler) {
        this.tiler = tiler;
        setSize(800, 60);
        setPreferredSize(new Dimension(800, 60));
        setOpaque(false);
        setLayout(null);
        createButtons();
    }

    private void createButtons() {
        JButton load = new JButton("Load Input");
        save = new JButton("Save Result");

        // new action to select a image
        load.addActionListener(new SelectAction(tiler));
        load.setPreferredSize(DIM_BUTTON);
        load.setSize(DIM_BUTTON);
        load.setAlignmentX(Component.CENTER_ALIGNMENT);
        load.setBackground(Color.LIGHT_GRAY);
        load.setLocation(100, 0);

        // new action to save the mosaique
        save.addActionListener(new SaveAction(tiler));
        save.setAlignmentX(Component.CENTER_ALIGNMENT);
        save.setPreferredSize(DIM_BUTTON);
        save.setSize(DIM_BUTTON);
        save.setBackground(Color.LIGHT_GRAY);
        save.setLocation(500, 0);
        save.setEnabled(false);

        add(load);
        add(save);
    }

    /**
     * sets {@link ConfigurationPanel#save} enabled/disabled
     * @param b if save is enabled
     */
    public void setButtonEnabled(final boolean b) {
        save.setEnabled(b);
    }
}
