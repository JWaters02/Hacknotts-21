package dev.jwaters.hacknotts21.graph;

import dev.jwaters.hacknotts21.type.StringType;
import dev.jwaters.hacknotts21.type.Type;
import dev.jwaters.hacknotts21.type.VoidType;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

public final class PrintNode extends GraphNode<JPanel> {
    @Nullable
    private GraphNode<?> value;

    public PrintNode(@Nullable GraphNode<?> parent) {
        super(parent);
    }

    @Override
    public Type getExpectedChildType(GraphNode<?> child) {
        return StringType.INSTANCE;
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
        return value != null ? List.of(value) : List.of();
    }

    @Nullable
    public GraphNode<?> getValue() {
        return value;
    }

    public void setValue(@Nullable GraphNode<?> value) {
        this.value = value;
    }
}
