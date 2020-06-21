package org.iMage.tiler.panels;

import javax.swing.*;

import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.rectangle.RectangleShape;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
    private static final int MAX_HEIGHT = 250;
    private static final int MAX_WIDTH = 350;
    private static final int TOTAL_WIDTH = 800;
    private static final Dimension PIC_DIM = new Dimension(MAX_WIDTH, MAX_HEIGHT);
    private static final Dimension DIM =  new Dimension(TOTAL_WIDTH, MAX_HEIGHT + 40);
    private static final Dimension CENTER_DIM = new Dimension(TOTAL_WIDTH - 40,
            MAX_HEIGHT);
    private static final Dimension SPACER_DIM = new Dimension(20, MAX_HEIGHT);
    private static final Dimension TOP_DIM = new Dimension(MAX_WIDTH, 20);
    private final JLabel left;
    private final JLabel right;


    public ImagePanel() {
        setPreferredSize(DIM);
        setBackground(new Color(65, 65, 65));
        setLayout(new BorderLayout());

        left = new JLabel(new ImageIcon(new BufferedImage(PIC_DIM.width, PIC_DIM.height, 1)), JLabel.CENTER);
        left.setPreferredSize(PIC_DIM);
        left.setBackground(Color.BLACK);
        left.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));

        right =  new JLabel(new ImageIcon(new BufferedImage(PIC_DIM.width, PIC_DIM.height, 1)), JLabel.CENTER);
        right.setPreferredSize(PIC_DIM);
        left.setBackground(Color.BLACK);
        right.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));

        createPanel();
    }

    private void createPanel() {
        JPanel top = new JPanel();
        JPanel top2 = new JPanel();
        JPanel spacer = new JPanel();
        JPanel spacer2 = new JPanel();
        JPanel center = new JPanel();

        center.setBackground(new Color(30, 30, 30));
        center.setLayout(new BorderLayout());
        center.setPreferredSize(CENTER_DIM);
        center.add(left, BorderLayout.WEST);
        center.add(right, BorderLayout.EAST);

        top.setBackground(new Color(30, 30, 30));
        top.setPreferredSize(TOP_DIM);

        top2.setBackground(new Color(30, 30, 30));
        top2.setPreferredSize(TOP_DIM);

        spacer.setBackground(new Color(30, 30, 30));
        spacer.setPreferredSize(SPACER_DIM);

        spacer2.setBackground(new Color(30, 30, 30));
        spacer2.setPreferredSize(SPACER_DIM);

        add(spacer, BorderLayout.WEST);
        add(spacer2, BorderLayout.EAST);
        add(center, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);
        add(top2, BorderLayout.SOUTH);
    }

    public void setLeftImage(BufferedArtImage img) {
        left.setIcon(new ImageIcon(scaleImage(img)));
    }

    public void setRightImage(BufferedArtImage img) {
        right.setIcon(new ImageIcon(scaleImage(img)));
    }

    public static BufferedImage scaleImage(final BufferedArtImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        float scale = width > height ? (float) MAX_WIDTH / width : (float) MAX_HEIGHT / height;
        width = (int) (width * scale);
        height = (int) (height * scale);
        return new RectangleShape(img, width, height).getThumbnail();
    }

}