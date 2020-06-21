package org.iMage.tiler.listeners;

import org.iMage.tiler.Tiler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadAction implements ActionListener {
    private final Tiler tiler;

    public LoadAction(final Tiler tiler) {

        this.tiler = tiler;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setMultiSelectionEnabled(false);
        chooser.setDialogTitle("Load Tiles");
        chooser.showDialog(tiler, "Load");
        tiler.loadTiles(chooser.getSelectedFile());
    }
}
