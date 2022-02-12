package dev.jwaters.hacknotts21.swing;

import javax.swing.*;

public class NodeUIUtils {
    public static JPanel wrapBody(JComponent body) {
        JPanel wrapper = new JPanel();
        wrapper.setBorder(BorderFactory.createLoweredBevelBorder());
        wrapper.add(body);
        return wrapper;
    }
}
