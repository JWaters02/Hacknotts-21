package dev.jwaters.hacknotts21.graph;

import javax.swing.*;

public interface GraphNode<C extends JComponent> {
    C createComponent();
    void readFromComponent(C component);
    void writeToComponent(C component);
}
