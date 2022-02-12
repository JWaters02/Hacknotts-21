package dev.jwaters.hacknotts21.graph;

import dev.jwaters.hacknotts21.type.BooleanType;
import dev.jwaters.hacknotts21.type.Type;
import dev.jwaters.hacknotts21.type.VoidType;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public final class WhileNode extends GraphNode<JPanel> {
    @Nullable
    private GraphNode<?> condition;
    private final BlockNode body = new BlockNode(this);

    public WhileNode(@Nullable GraphNode<?> parent) {
        super(parent);
    }

    @Override
    public @Nullable Type getExpectedChildType(GraphNode<?> child) {
        if (child == condition) {
            return BooleanType.INSTANCE;
        } else if (child == body) {
            return VoidType.INSTANCE;
        }
        return null;
    }

    @Override
    public Type getReturnType() {
        return VoidType.INSTANCE;
    }

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

    public BlockNode getBody() {
        return body;
    }
}
