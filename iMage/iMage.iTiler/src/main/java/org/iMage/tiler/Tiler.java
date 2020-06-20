package org.iMage.tiler;

import org.iMage.mosaique.base.BufferedArtImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.Buffer;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

public class Tiler extends JFrame {
    private static final Dimension DIM = new Dimension(800, 450);
    private final Container pane;
    private final BorderLayout layout;
    private final ImagePanel preview;
    private BufferedArtImage selectedImage;
    private boolean imageSelected;
    private boolean hasMosaique;

    /**
     *
     */
    private static final long serialVersionUID = 1337L;

    Tiler() throws IOException {
        imageSelected = false;
        selectedImage = null;
        setTitle("iTiler");
        setSize(DIM);
        setPreferredSize(DIM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        URL url = ClassLoader.getSystemResource("icons/iTiler-icon.png");
        setIconImage(new ImageIcon(url).getImage());
        pane = getContentPane();
        layout = new BorderLayout();
        pane.setLayout(layout);
        pane.setBackground(new Color(30, 30, 30));
        preview = new ImagePanel();
        pane.add(preview, BorderLayout.NORTH);
        createTextBox();
        url = ClassLoader.getSystemResource("images/image2.png");
        BufferedArtImage img = new BufferedArtImage(ImageIO.read(url));
        preview.setLeftImage(img);
        url = ClassLoader.getSystemResource("images/image1.png");
        img = new BufferedArtImage(ImageIO.read(url));
        preview.setRightImage(img);
        pack();
    }


    private void createTextBox() {
       JPanel box = new JPanel();
       box.setLayout(new BorderLayout());
       box.setPreferredSize(new Dimension(800, 160));
       box.setBackground(new Color(30, 30, 30));
       box.add(new ConfigurationPanel(this), BorderLayout.NORTH);
       box.add(new ArtisticPanel(), BorderLayout.SOUTH);
       pane.add(box, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Tiler tiler;
            try {
                tiler = new Tiler();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            tiler.setVisible(true);
        });
    }

    public void setImage(File file) {
        if (file == null) {
            return;
        }
        try {
            BufferedArtImage tmp = new BufferedArtImage(ImageIO.read(file));
            preview.setLeftImage(tmp);
            selectedImage = tmp;
            imageSelected = true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Couldn't find image!", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public boolean hasMosaique() {
        return hasMosaique;
    }

    public boolean isImageSelected() {
        return imageSelected;
    }

    public BufferedImage getImg() {
        return this.selectedImage.toBufferedImage();
    }
}