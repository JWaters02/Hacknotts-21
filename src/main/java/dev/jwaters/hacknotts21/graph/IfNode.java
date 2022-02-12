package dev.jwaters.hacknotts21.graph;

import dev.jwaters.hacknotts21.type.BooleanType;
import dev.jwaters.hacknotts21.type.Type;
import dev.jwaters.hacknotts21.type.VoidType;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public final class IfNode extends GraphNode<JPanel> {
    @Nullable
    private GraphNode<?> condition;
    private final BlockNode body = new BlockNode(this);

    public IfNode(@Nullable GraphNode<?> parent) {
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

    @Override
    public List<GraphNode<?>> getChildren() {
        return Arrays.asList(condition, body);
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
