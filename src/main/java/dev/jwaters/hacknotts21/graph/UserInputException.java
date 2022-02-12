package dev.jwaters.hacknotts21.graph;

import javax.swing.*;

public class UserInputException extends Exception {
    private final JComponent component;

    public UserInputException(JComponent component) {
        super(component.getName() + " is not a valid input");
        this.component = component;
    }

    public JComponent getComponent() {
        return component;
    }
}
