package org.iMage.tiler.panels;

import org.iMage.tiler.Tiler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

public class DigitTextField extends JTextField {
    private final Tiler tiler;
    // xD
    private static final Pattern VALID_NUMBER = Pattern.compile("([1-9]|[1-8][0-9]|9[0-9]|[1-8][0-9]{2}|9[0-8][0-9]|"
            + "99[0-9]|[1-8][0-9]{3}|9[0-8][0-9]{2}|99[0-8][0-9]|999[0-9]|[1-8][0-9]{4}|9[0-8][0-9]{3}|99[0-8][0-9]{2}|"
            + "999[0-8][0-9]|9999[0-9]|[1-8][0-9]{5}|9[0-8][0-9]{4}|99[0-8][0-9]{3}|999[0-8][0-9]{2}|9999[0-8][0-9]|"
            + "99999[0-9]|[1-8][0-9]{6}|9[0-8][0-9]{5}|99[0-8][0-9]{4}|999[0-8][0-9]{3}|9999[0-8][0-9]{2}|99999[0-8]"
            + "[0-9]|999999[0-9]|[1-8][0-9]{7}|9[0-8][0-9]{6}|99[0-8][0-9]{5}|999[0-8][0-9]{4}|9999[0-8][0-9]{3}|99999"
            + "[0-8][0-9]{2}|999999[0-8][0-9]|9999999[0-9]|[1-8][0-9]{8}|9[0-8][0-9]{7}|99[0-8][0-9]{6}|999[0-8][0-9]"
            + "{5}|9999[0-8][0-9]{4}|99999[0-8][0-9]{3}|999999[0-8][0-9]{2}|9999999[0-8][0-9]|99999999[0-9]|1[0-9]{9}"
            + "|20[0-9]{8}|21[0-3][0-9]{7}|214[0-6][0-9]{6}|2147[0-3][0-9]{5}|21474[0-7][0-9]{4}|214748[0-2][0-9]{3}"
            + "|2147483[0-5][0-9]{2}|21474836[0-3][0-9]|214748364[0-7])");

    DigitTextField(final String value, Tiler tiler) {
        super(value);
        this.tiler = tiler;
    }


    @Override
    protected void processKeyEvent(KeyEvent e) {
        char c = e.getKeyChar();
        if (Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) {
            super.processKeyEvent(e);
            if (VALID_NUMBER.matcher(getText()).matches()) {
                this.setForeground(Color.BLACK);
            } else {
                this.setForeground(Color.RED);
                JOptionPane.showMessageDialog(tiler, "Width/Height should be at least 1 and max "
                        + Integer.MAX_VALUE, "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        e.consume();
    }

    public int getNumber() throws NumberFormatException {
        return Integer.parseInt(getText());
    }
}
