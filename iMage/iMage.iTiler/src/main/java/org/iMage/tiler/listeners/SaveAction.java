package org.iMage.tiler.listeners;

import org.iMage.tiler.Tiler;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * creates a action to save the created mosaique
 */
public class SaveAction implements ActionListener {
    private final Tiler tiler;

    public SaveAction(final Tiler tiler) {
        super();
        this.tiler = tiler;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        // check if mosaique was created
        if (!tiler.hasMosaique()) {
            JOptionPane.showMessageDialog(tiler, "Create a mosaique first!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFileChooser chooser = new JFileChooser();
        FileFilter imageFilter = new FileNameExtensionFilter(".png", "png", "jpg");

        chooser.setDialogTitle("Save mosaique as ...");
        chooser.setFileFilter(imageFilter);

        int userSelection = chooser.showSaveDialog(tiler);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = chooser.getSelectedFile();
            try {
                ImageIO.write(tiler.getImg(), "png", fileToSave);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
