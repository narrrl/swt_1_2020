package org.iMage.tiler;

import org.iMage.tiler.listeners.SaveAction;
import org.iMage.tiler.listeners.SelectAction;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.net.URL;

public class ConfigurationPanel extends JPanel {
    private final Tiler tiler;
    private static final Dimension DIM_BUTTON = new Dimension(200, 32);



    public ConfigurationPanel(final Tiler tiler) {
        this.tiler = tiler;
        setSize(800, 80);
        setPreferredSize(new Dimension(800, 80));
        setOpaque(false);
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Configuration",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.LIGHT_GRAY));
        setLayout(null);
        createButtons();
    }

    private void createButtons() {
        URL url = ClassLoader.getSystemResource("icons/load.gif");
        JButton load = new JButton("Load Input", new ImageIcon(url));
        load.addActionListener(new SelectAction(tiler));
        load.setPreferredSize(DIM_BUTTON);
        load.setSize(DIM_BUTTON);
        load.setAlignmentX(Component.CENTER_ALIGNMENT);
        load.setBackground(Color.LIGHT_GRAY);
        url = ClassLoader.getSystemResource("icons/save.png");
        JButton save = new JButton("Save Result", new ImageIcon(url));
        save.addActionListener(new SaveAction(tiler));
        save.setAlignmentX(Component.CENTER_ALIGNMENT);
        save.setPreferredSize(DIM_BUTTON);
        save.setSize(DIM_BUTTON);
        save.setBackground(Color.LIGHT_GRAY);
        add(load);
        load.setLocation(100, 28);
        add(save);
        save.setLocation(501, 28);
    }
}
