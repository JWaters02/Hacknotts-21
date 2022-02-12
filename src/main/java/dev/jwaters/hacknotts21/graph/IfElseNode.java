package dev.jwaters.hacknotts21.graph;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class IfElseNode implements GraphNode<JPanel> {
    @Nullable
    private GraphNode<?> condition;
    private final BlockNode ifBody = new BlockNode();
    private final BlockNode elseBody = new BlockNode();

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

    @Nullable
    public GraphNode<?> getCondition() {
        return condition;
    }

    public void setCondition(@Nullable GraphNode<?> condition) {
        this.condition = condition;
    }

    public BlockNode getIfBody() {
        return ifBody;
    }

    public BlockNode getElseBody() {
        return elseBody;
    }
}
