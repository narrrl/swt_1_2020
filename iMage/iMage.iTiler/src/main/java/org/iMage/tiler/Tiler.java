package org.iMage.tiler;

import org.iMage.mosaique.MosaiqueEasel;
import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.rectangle.RectangleArtist;
import org.iMage.mosaique.rectangle.RectangleShape;
import org.iMage.mosaique.triangle.TriangleArtist;
import org.iMage.tiler.panels.ArtisticPanel;
import org.iMage.tiler.panels.ConfigurationPanel;
import org.iMage.tiler.panels.ImagePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * This program is the main class for iMage.iTiler. It stores all the needed data to create the mosaique.
 */
public class Tiler extends JFrame {
    private static final Dimension DIM = new Dimension(800, 450);
    private final Container pane;
    private final ImagePanel preview;
    private BufferedArtImage selectedImage;
    private BufferedArtImage mosaique;
    private ArrayList<BufferedArtImage> tiles;
    private final ArtisticPanel artisticPanel;
    private final ConfigurationPanel configurationPanel;

    /**
     *
     */
    private static final long serialVersionUID = 1337L;


    /**
     * Creates the Frame with all the compomnents
     */
    Tiler() {
        preview = new ImagePanel();
        artisticPanel = new ArtisticPanel(this);
        configurationPanel = new ConfigurationPanel(this);
        selectedImage = null;
        pane = getContentPane();
        BorderLayout layout = new BorderLayout();
        setTitle("iTiler");
        setSize(DIM);
        setPreferredSize(DIM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        URL url = ClassLoader.getSystemResource("icons/iTiler-icon.png");
        setIconImage(new ImageIcon(url).getImage());

        pane.setLayout(layout);
        pane.setBackground(new Color(30, 30, 30));

        pane.add(preview, BorderLayout.NORTH);

        createTextBox();

        pack();
    }

    private void createTextBox() {
       JPanel box = new JPanel();

       box.setLayout(new BorderLayout());
       box.setPreferredSize(new Dimension(800, 120));
       box.setBackground(new Color(30, 30, 30));

       box.add(configurationPanel, BorderLayout.NORTH);
       box.add(artisticPanel, BorderLayout.SOUTH);

       pane.add(box, BorderLayout.SOUTH);

       box.setBorder(BorderFactory.createTitledBorder(
               BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Configuration",
               TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.LIGHT_GRAY));
    }

    /**
     * Creates a new iTiler and starts the program
     * @param args -
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Tiler tiler;
            tiler = new Tiler();
            tiler.setVisible(true);
        });
    }


    /**
     * Creates a new BufferedImage from file {@link Tiler#selectedImage}
     * @param file the new image
     */
    public void setImage(File file) {
        if (file == null) {
            return;
        }
        try {
            BufferedArtImage tmp = new BufferedArtImage(ImageIO.read(file));
            preview.setLeftImage(tmp);
            selectedImage = tmp;
        } catch (IOException e){
            JOptionPane.showMessageDialog(this,
                    "Couldn't find image!", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * checks if mosaique was created
     * @return true if a mosaique was created
     */
    public boolean hasMosaique() {
        return mosaique != null;
    }

    /**
     * creates a new mosaique from the {@link Tiler#tiles} and the {@link Tiler#selectedImage}
     */
    public void createMosaique() {
        // checks if tiles were selected and a image has been choosen
        if (isImageSelected() && tiles != null) {
            MosaiqueEasel easel = new MosaiqueEasel();

            int h;
            int w;
            // tries to get the tile height and width or else sets to default value
            try {
                h = artisticPanel.getH() > 0 ? artisticPanel.getH() : 42;
                w = artisticPanel.getW() > 0 ? artisticPanel.getW() : 42;
            } catch (NumberFormatException e) {
                h = 42;
                w = 42;
            }
            // checks if the rectangle or triangle
            if (artisticPanel.artistIsRectangle()) {
                mosaique = new BufferedArtImage(easel.createMosaique(selectedImage.toBufferedImage(), new RectangleArtist(tiles, w, h)));
            } else {
                mosaique = new BufferedArtImage(easel.createMosaique(selectedImage.toBufferedImage(), new TriangleArtist(tiles, w, h)));
            }
            // sets the right image of the image preview
            preview.setRightImage(mosaique);
            // enables button to save
            configurationPanel.setButtonEnabled(true);
            return;
        }
        // error messages
        if (!isImageSelected() && tiles == null) {
            JOptionPane.showMessageDialog(this, "Select a image and load tiles first!",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        } else if (!isImageSelected()) {
            JOptionPane.showMessageDialog(this, "Select a image first!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Load tiles first!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * checks if image was selected
     * @return
     */
    public boolean isImageSelected() {
        return selectedImage != null;
    }

    /**
     * gets the mosaique as buffered image
     * @return {@link Tiler#mosaique}
     */
    public BufferedImage getImg() {
        return this.mosaique.toBufferedImage();
    }


    /**
     * Loads all {@link Tiler#tiles} from a folder f
     * @param f the folder
     */
    public void loadTiles(final File f) {
        tiles = new ArrayList<>(); // new list of tiles

        if (f != null && f.isDirectory()) { // checks if f is directory

            // New FileFilter to make sure that only images get loaded
            File[] files = f.listFiles(file -> {
                if (file == null) {
                    return false;
                }
                return isImage(file);
            });

            if (files == null) {
                JOptionPane.showMessageDialog(this, "No images found!", "Info",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            JProgressBar bar = new JProgressBar(0, files.length);
            JDialog dialog = new JDialog(this, "Progress");

            bar.setValue(0);
            bar.setStringPainted(true);
            bar.setBackground(new Color(30, 30, 30));

            dialog.add(bar);
            dialog.pack();
            dialog.setVisible(true);
            dialog.setLocationRelativeTo(null);

            // creates new thread that progressbar gets updated parallel
            Thread t = new Thread(() -> {
                for (File tmp : files) {

                    if (isImage(tmp)) {
                        try {
                            tiles.add(new BufferedArtImage(ImageIO.read(tmp)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    // updates the progressbar
                    bar.setValue(bar.getValue() + 1);

                    // close on finish
                    if (bar.getValue() == files.length) {
                        dialog.dispose();
                    }
                }
            });

            t.start();
        }
    }

    /**
     * checks if f is image
     * @param f the file that gets checked
     */
    private boolean isImage(final File f) {
        for (String type : ImageIO.getReaderFileSuffixes()) {
            if (f.getName().endsWith(type)) {
                return true;
            }
        }
        return false;
    }


    /**
     *  Creates a new window with all loaded tiles
     */
    public void showTiles() {

        if (tiles == null) {
            JOptionPane.showMessageDialog(this, "Load tiles first!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Tiles");
        JScrollPane scrollPane = new JScrollPane();
        GridBagLayout l = new GridBagLayout();
        JPanel p = new JPanel(l);
        GridBagConstraints c = new GridBagConstraints();

        dialog.setSize(new Dimension(530, 530));
        dialog.setLayout(new BorderLayout(0, 0));
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setBackground(new Color(30, 30, 30));

        c.insets = new Insets(1, 1, 1, 1);
        c.gridy = 0;
        c.fill = GridBagConstraints.VERTICAL;

        int counter = 0;
        int w;
        int h;

        // tries to get the resolution of the tiles
        // if the user input is invalid, the default of 70 get set
        try {
            w = artisticPanel.getW() > 0 && artisticPanel.getW() >= 70 ? artisticPanel.getW() : 70;
            h = artisticPanel.getH() > 0 && artisticPanel.getH() >= 70 ? artisticPanel.getH() : 70;
        } catch (NumberFormatException e) {
            w = 70;
            h = 70;
        }

        // Adds all images as JLabels to the dialog
        for (BufferedArtImage img : tiles) {
            JLabel label = new JLabel(new ImageIcon());

            label.setIcon(new ImageIcon(new RectangleShape(img, w, h).getThumbnail()));
            label.setSize(new Dimension(70, 70));
            label.setMaximumSize(new Dimension(70, 70));
            label.setMinimumSize(new Dimension(70, 70));
            label.setPreferredSize(new Dimension(70, 70));

            // new row after 7 images
            if (counter == 7) {
                counter = 0;
                c.gridy++;
            }

            p.add(label, c);

            counter++;
        }

        scrollPane.setViewportView(p);

        dialog.add(scrollPane, BorderLayout.CENTER);

        dialog.pack();

        dialog.setVisible(true);
    }
}
