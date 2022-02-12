package dev.jwaters.hacknotts21.graph;

import dev.jwaters.hacknotts21.type.StringType;
import dev.jwaters.hacknotts21.type.Type;
import dev.jwaters.hacknotts21.type.VoidType;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collections;
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
    public void readFromComponent(JPanel component) throws UserInputException {

    }

    @Override
    public void writeToComponent(JPanel component) {

    }

    @Override
    public List<GraphNode<?>> getChildren() {
        return Collections.singletonList(value);
    }

    @Nullable
    public GraphNode<?> getValue() {
        return value;
    }

    public void setValue(@Nullable GraphNode<?> value) {
        this.value = value;
    }
}
