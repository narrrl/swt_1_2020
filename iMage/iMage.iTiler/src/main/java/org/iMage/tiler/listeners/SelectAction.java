package org.iMage.tiler.listeners;

import org.iMage.tiler.Tiler;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Creates a action to load a image in {@link org.iMage.Tiler}
 */
public class SelectAction implements ActionListener {
    private final Tiler tiler;

    public SelectAction(final Tiler tiler) {
        super();
        this.tiler = tiler;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JFileChooser chooser = new JFileChooser();
        FileFilter imageFilter = new FileNameExtensionFilter(
                "only image files", ImageIO.getReaderFileSuffixes());

        chooser.setFileFilter(imageFilter);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setMultiSelectionEnabled(false);
        chooser.showDialog(tiler, "Select image");

        File file = chooser.getSelectedFile();

        tiler.setImage(file);
    }
}
