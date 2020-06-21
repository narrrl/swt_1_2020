package org.iMage.tiler.listeners;

import org.iMage.tiler.Tiler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Creates a action to show all loaded tiles
 */
public class ShowAction implements ActionListener {
    private final Tiler tiler;

    public ShowAction(Tiler tiler) {
        this.tiler = tiler;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        tiler.showTiles();
    }
}
