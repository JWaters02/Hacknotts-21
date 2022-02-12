package dev.jwaters.hacknotts21.graph;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class IfNode implements GraphNode<JPanel> {
    @Nullable
    private GraphNode<?> condition;
    private final BlockNode body = new BlockNode();

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

    public void setCondition(@Nullable GraphNode<?> condition) {
        this.condition = condition;
    }

    @Nullable
    public GraphNode<?> getCondition() {
        return condition;
    }

    @Nullable
    public BlockNode getBody() {
        return body;
    }
}
