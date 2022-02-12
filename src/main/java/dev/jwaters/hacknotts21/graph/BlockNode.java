package dev.jwaters.hacknotts21.graph;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public final class BlockNode implements GraphNode<JPanel> {
    private final List<GraphNode<?>> children = new ArrayList<>();

    @Override
    public JPanel createComponent() {
        return null;
    }

    @Override
    public void readFromComponent(JPanel component) {

    }

    @Override
    public void writeToComponent(JPanel component) {

    }

    public List<GraphNode<?>> getChildren() {
        return children;
    }
}
