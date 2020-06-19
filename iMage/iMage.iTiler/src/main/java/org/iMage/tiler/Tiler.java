package org.iMage.tiler;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Image;
import java.net.URL;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Tiler extends JFrame {
   
    /**
     *
     */
    private static final long serialVersionUID = 1337L;

    Tiler() {
        setTitle("iTiler");
        setSize(800, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        URL url = ClassLoader.getSystemResource("icons/iTiler-icon.png");
        setIconImage(new ImageIcon(url).getImage());
        Container pane = getContentPane();
        GroupLayout gLayout = new GroupLayout(pane);
        pane.setLayout(gLayout);
        
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){
        
            @Override
            public void run() {
                Tiler tiler = new Tiler();
                tiler.setVisible(true);
            }
        });
    }
}