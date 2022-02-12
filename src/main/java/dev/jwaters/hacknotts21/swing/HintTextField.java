package dev.jwaters.hacknotts21.swing;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class HintTextField extends JTextField {
    private Color normalColor;
    private Color hintColor = Color.GRAY;
    private boolean focused = false;

    public HintTextField(final String hint) {
        setText(hint);
        normalColor = getForeground();
        setForeground(hintColor);

        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(hint)) {
                    setText("");
                } else {
                    setText(getText());
                }
                focused = true;
                updateColor();
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(hint);
                } else {
                    setText(getText());
                }
                focused = false;
                updateColor();
            }
        });

        this.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateColor();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateColor();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateColor();
            }
        });
    }

    public void setNormalColor(Color normalColor) {
        this.normalColor = normalColor;
        updateColor();
    }

    public void setHintColor(Color hintColor) {
        this.hintColor = hintColor;
        updateColor();
    }

    private void updateColor() {
        if (focused || !getText().isEmpty()) {
            setForeground(normalColor);
        } else {
            setForeground(hintColor);
        }
    }
}
